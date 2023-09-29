package EjemploBBDDconFicheros;

import javax.imageio.IIOException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FicheroSecuencial {
    private final String nomFich;
    private final Map<String, Integer> campos;
    private final String campoClave;

    private int longReg;
    private long numReg;
    private long numRegMarcadosBorrado;

    public String getCampoClave() {
        return campoClave;
    }

    public String getNomFich() {
        return nomFich;
    }

    public Map<String, Integer> getCampos() {
        return campos;
    }

    public int getLongReg() {
        return longReg;
    }

    public long getNumReg() {
        return numReg;
    }

    public long getNumRegMarcadosBorrado() {
        return numRegMarcadosBorrado;
    }

    /**
     * Constructor de la clase FicheroSecuencial
     *
     * @param nomFich    nombre del fichero que vamos a crear
     * @param campos     campos de cada uno de los registros que se van a almacenar en el fichero
     * @param campoClave primary key de los registros para evitar duplicados
     * @throws IOException
     */
    public FicheroSecuencial(String nomFich, Map<String, Integer> campos, String campoClave) throws IOException {
        this.nomFich = nomFich;
        this.campos = campos;
        this.campoClave = campoClave;
        numReg = 0;
        numRegMarcadosBorrado = 0;
        longReg = 0;

        //Calculo la longitud del fichero sumando la longitud en bytes de cada uno de ellos
        for (Map.Entry<String, Integer> campo : campos.entrySet()) {
            longReg += campo.getValue();
        }

        File f = new File(nomFich);

        //Si el fichero ya existe, calculo el numero de registros que tiene partiendo de la longitud de cada uno de ellos
        if (f.exists()) {
            numReg = f.length() / longReg;
        } else {
            f.createNewFile();
        }
    }

    /**
     * Recupera el registro representado que tiene en su campo clave el valorClave suministrado
     *
     * @param valorClave del registro a recuperar
     * @return un mapa con pares campo-valor correspondiente al registro representado por "valorClave"
     * @throws IOException
     */
    public Map<String, String> recuperar(String valorClave) throws IOException {
        int pos = 0;
        boolean encontrado = false;
        Map<String, String> result;
        try (FileInputStream fis = new FileInputStream(this.nomFich)) {
            result = null;
            //recorremos todos los registros mientras no hayamos encotrado una ocurrencia del valorClave
            while (pos < this.numReg && !encontrado) {
                byte buffer[] = new byte[this.longReg];
                if (fis.read(buffer, 0, this.longReg) < this.longReg) {
                    return null;
                }

                int offsetCampo = 0;
                String unValorClave = null;
                for (Map.Entry<String, Integer> campo : campos.entrySet()) {
                    String unCampo = campo.getKey();
                    int longCampo = campo.getValue();
                    if (unCampo.equals(this.campoClave)) {
                        unValorClave = new String(buffer, offsetCampo, longCampo, StandardCharsets.UTF_8);
                        break;
                    }
                    offsetCampo += longCampo;
                }
                if (valorClave.equals(unValorClave)) {//Para cada registro, comparamos si unValorClave recuperado del campo clave coincide con el que estamos buscando y hemos introducido como parámetro
                    encontrado = true;
                    offsetCampo = 0;
                    result = new HashMap<String, String>();
                    for (Map.Entry<String, Integer> campo : campos.entrySet()) {
                        String unCampo = campo.getKey();
                        int longCampo = campo.getValue();
                        String valorCampo = new String(buffer, offsetCampo, longCampo, StandardCharsets.UTF_8);
                        result.put(unCampo, valorCampo);
                        offsetCampo += longCampo;
                    }
                }
                pos++;
            }
            return result;
        }

    }

    /**
     * Insertar un nuevo registro en el fichero, siempre al final de éste.
     *
     * @param reg Registro a insertar
     * @return posición en la que hemos insertado el registro o -1 en caso de que no se haya podido insertar
     * porque ya existe un registro que tiene el mismo valor en el campoclave
     * @throws IOException
     */
    public long insertar(HashMap<String, String> reg) throws IOException {
        String valorCampoClave = reg.get(this.campoClave);
        if (recuperar(valorCampoClave) != null) {//Comprobamos si ya existe un registro con el mismo valor para el campo clave que el queremos insertar (No está permitido)
            return -1;
        }

        try (FileOutputStream fos = new FileOutputStream(nomFich, true)) {
            for (Map.Entry<String, Integer> campo : campos.entrySet()) {
                int longCampo = campo.getValue();
                String valorCampo = reg.get(campo.getKey());
                if (valorCampo == null) {
                    valorCampo = "";
                }

                //EJEMPLO 1 (PROFESOR)
                String valorCampoForm = String.format("%1$-" + longCampo + "s", valorCampo); //devuelve el valor del 1er argumento en un String con longitud "longCampo" y alineado a la izquierda (gracias al uso de "-")
                fos.write(valorCampoForm.getBytes("UTF-8"), 0, longCampo);

                //EJEMPLO 2 (Da problemas porque rellena con nulos, es un ArrayCopy)
//                // Si el campo es la fecha de alta, le asignamos la fecha actual
//                byte[] bytes = valorCampo.getBytes(StandardCharsets.UTF_8);
//                // Creamos un array de bytes con la longitud del campo
//                byte[] paddedBytes = new byte[longCampo];
//                // Copiamos los bytes del campo en el array de bytes
//                System.arraycopy(bytes, 0, paddedBytes, 0, Math.min(longCampo, bytes.length));
//
//                // Escribimos el campo en el fichero
//                fos.write(paddedBytes);
            }
        } catch (IOException e) {
            System.out.println("Error de E/S: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.numReg++;
        return this.numReg - 1;
    }

    public boolean modificar(String valorClave, String nombreCampo, String valorCampo) throws IOException {
        if (nombreCampo.equals(this.campoClave)) {
            System.out.println("No se puede modificar el campo clave: " + nombreCampo);
            return false;
        }
        int pos = 0;
        boolean encontrado = false;
        RandomAccessFile raf = new RandomAccessFile(this.nomFich, "rws");
        while (pos < this.numReg && !encontrado) {
            byte buffer[] = new byte[this.longReg];
            if (raf.read(buffer, 0, this.longReg) < this.longReg) {
                return false;
            }
            String unValorClave = recuperarValorCampoClave(buffer);

            if (valorClave.equals(unValorClave)) {
                int offsetCampo = 0;
                encontrado = true;
                raf.seek(pos * longReg);
                for (Map.Entry<String, Integer> campo : campos.entrySet()) {
                    String unCampo = campo.getKey();
                    int longCampo = campo.getValue();
                    if (nombreCampo.equals(unCampo)) {
                        raf.skipBytes(offsetCampo);
                        String valorCampoForm = String.format("%1$-" + longCampo + "s", valorCampo);
                        raf.write(valorCampoForm.getBytes("UTF-8"), 0, longCampo);
                        break;
                    }
                    offsetCampo += longCampo;
                }
            }
            pos++;
        }
        return encontrado;

    }

    /**
     * Borrar registro, identificado por valor de campo clave. Realmente no se
     * borra, sino que se marca como borrado, poniendo todos los bytes a cero.
     *
     * @param valorClave Valor del campo clave para localizar el registro
     * @return true si se ha encontrado y borrado el registro, false en otro
     * caso.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public boolean borrar(String valorClave) throws FileNotFoundException, IOException {
        // Muy parecido a la búsqueda y modificación, con la diferencia de que, una vez encontrado,
        // se marca como borrado
        int pos = 0;
        boolean encontrado = false;
        try (RandomAccessFile raf = new RandomAccessFile(this.nomFich, "rws")) {  // Se necesita leer y escribir, y además volver hacia atrás a una posición conocida
            while (pos < this.numReg && !encontrado) {
                byte buffer[] = new byte[this.longReg]; // Leer registro
                if (raf.read(buffer, 0, this.longReg) < this.longReg) {
                    return false;
                }
                int offsetCampo = 0;  // Obtener valor del campo clave
                String unValorClave = null;
                for (Map.Entry<String, Integer> campo : campos.entrySet()) {
                    String unCampo = campo.getKey();
                    int longCampo = campo.getValue();
                    if (unCampo.equals(this.campoClave)) {
                        unValorClave = new String(buffer, offsetCampo, longCampo, StandardCharsets.UTF_8);
                        break;  // Ya tenemos el valor del campo clave
                    }
                    offsetCampo += longCampo;
                }
                if (valorClave.equals(unValorClave)) {
                    offsetCampo = 0;
                    encontrado = true;  // Ahora hay que poner todos los bytes a cero en registro en posición pos
                    raf.seek(pos * this.longReg);
                    java.util.Arrays.fill(buffer, (byte) 0);  // Por si acaso, no es necesario.
                    raf.write(buffer, 0, this.longReg);
                    this.numRegMarcadosBorrado++;
                }
                pos++;
            }
        }
        return encontrado;
    }

    /**
     * Compacta fichero, es decir, elimina registros marcados como borrados
     *
     * @return número de registros marcados como borrado, que se han eliminado.
     * -1 si ha habido algún error que ha impedido compactar el fichero.
     * @throws IOException
     */
    public int compactar() throws IOException {
        int numSuprimidos = 0;
        int numReg_ = 0;
        File fTemp = File.createTempFile(nomFich, "");
        try (FileInputStream fis = new FileInputStream(nomFich);
             FileOutputStream fos = new FileOutputStream(fTemp)) {
            byte[] buffer = new byte[this.longReg];
            for (int pos = 0; pos < this.numReg; pos++) {
                fis.read(buffer);
                boolean noCero = false;  // Ver si marcado para borrado: todo a cero
                for (int j = 0; j < this.longReg && !noCero; j++) {
                    if (buffer[j] != 0) {
                        noCero = true;
                    }
                }
                if (noCero) {
                    fos.write(buffer);
                    numReg_++;
                } else {
                    numSuprimidos++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Copia de seguridad de fichero original, con timestamp y número aleatorio.
        // Necesario para poder renombrar fichero temporal como original. Podría borrarse al final.
        java.util.Random r = new java.util.Random();
        String nombreCopiaSeg = nomFich + "." + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "." + r.nextInt() + ".bak";
        File fOrig = new File(nomFich);
        if (!fOrig.renameTo(new File(nombreCopiaSeg))) {
            System.err.println("Error haciendo copia de seguridad de " + nomFich + " a " + nombreCopiaSeg);
            return -1;
        }
        String nomFichTemp = fTemp.getAbsolutePath(); // Se renombra fichero temporal como original
        if (!fTemp.renameTo(new File(nomFich))) {
            System.err.println("Error copiando de fichero temporal " + nomFichTemp + " a " + nomFich);
            return -1;
        }
        //File fCopiaSeg=new File(nombreCopiaSeg); fCopiaSeg.delete();  // descomentar para borrar copia de seguridad

        this.numReg = numReg_;
        this.numRegMarcadosBorrado = 0;
        return numSuprimidos;
    }

    private String recuperarValorCampoClave(byte[] buffer) {
        int offsetCampo = 0;
        String unValorClave = null;
        for (Map.Entry<String, Integer> campo : campos.entrySet()) {
            String unCampo = campo.getKey();
            int longCampo = campo.getValue();
            if (unCampo.equals(this.campoClave)) {
                unValorClave = new String(buffer, offsetCampo, longCampo, StandardCharsets.UTF_8);
                break;
            }
            offsetCampo += longCampo;
        }
        return unValorClave;
    }

    public static void main(String[] args) {

        try {
            Map<String, Integer> campos = new HashMap<String, Integer>();
            campos.put("DNI", 9);
            campos.put("NOMBRE", 32);
            campos.put("CP", 5);
            FicheroSecuencial fsno = new FicheroSecuencial("fic_sec_ord.dat", campos, "DNI");
            Map reg = new HashMap();
            reg.put("DNI", "56789012B");
            reg.put("NOMBRE", "SAMPER");
            reg.put("CP", "29730");
            if (fsno.insertar((HashMap<String, String>) reg) < 0) {
                System.err.println("No se pudo insertar registro, clave duplicada: " + reg.get("DNI"));
            }
            reg.clear();
            reg.put("DNI", "89012345E");
            reg.put("NOMBRE", "ROJAS");
            reg.put("CP", "29730");
            if (fsno.insertar((HashMap<String, String>) reg) < 0) {
                System.err.println("No se pudo insertar registro, clave duplicada: " + reg.get("DNI"));
            }
            reg.clear();
            reg.put("DNI", "23456789D");
            reg.put("NOMBRE", "DORCE");
            reg.put("CP", "13700");
            if (fsno.insertar((HashMap<String, String>) reg) < 0) {
                System.err.println("No se pudo insertar registro, clave duplicada: " + reg.get("DNI"));
            }
            reg.clear();
            reg.put("DNI", "78901234X");
            reg.put("NOMBRE", "NADALES");
            reg.put("CP", "44126");
            if (fsno.insertar((HashMap<String, String>) reg) < 0) {
                System.err.println("No se pudo insertar registro, clave duplicada: " + reg.get("DNI"));
            }
            reg.clear();
            reg.put("DNI", "12345678Z");
            reg.put("NOMBRE", "ARCOS");
            reg.put("CP", "29730");
            if (fsno.insertar((HashMap<String, String>) reg) < 0) {
                System.err.println("No se pudo insertar registro, clave duplicada: " + reg.get("DNI"));
            }
            reg.clear();
            System.out.println("Fichero " + fsno.getNomFich()
                    + " contiene " + fsno.getNumReg() + " registros.");
            // Se marcan como borrados un registro de enmedio, el del principio y el del final.
//            String DNIParaBorrar = "23456789D";
//            if (fsno.borrar(DNIParaBorrar)) {
//                System.out.println("Borrado registro para DNI: " + DNIParaBorrar);
//            } else {
//                System.out.println("No se encuentra DNI " + DNIParaBorrar + " para borrar registro.");
//            }
//            DNIParaBorrar = "56789012B";
//            if (fsno.borrar(DNIParaBorrar)) {
//                System.out.println("Borrado registro para DNI: " + DNIParaBorrar);
//            } else {
//                System.out.println("No se encuentra DNI " + DNIParaBorrar + " para borrar registro.");
//            }
//            DNIParaBorrar = "12345678Z";
//            if (fsno.borrar(DNIParaBorrar)) {
//                System.out.println("Borrado registro para DNI: " + DNIParaBorrar);
//            } else {
//                System.out.println("No se encuentra DNI " + DNIParaBorrar + " para borrar registro.");
//            }
//            System.out.println(fsno.getNumReg() + " registros en fichero, de los que " + fsno.getNumRegMarcadosBorrado() + " registros están marcados como borrados.");

            System.out.println(fsno.recuperar("12345678Z"));
            int numSuprimidos = fsno.compactar();
            if (numSuprimidos > 0) {
                System.out.println(numSuprimidos + " registros suprimidos al compactar fichero.");
                System.out.println(fsno.getNumReg() + " registros en el fichero, de los que " + fsno.getNumRegMarcadosBorrado() + " están marcados para borrado.");
            }

        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}