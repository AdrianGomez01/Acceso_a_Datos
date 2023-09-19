package flujosYFicheros;

import java.io.*;

/**
 * Clase con ejemplo de manejo de ficheros
 */
public class Ejemplo1 {
    public static void main(String[] args) {
        String ruta = ".";
        if (args.length >= 1) {
            ruta = args[0];
        }

        File fich = new File(ruta);


        try {
            ejemploEscritura2(fich);
            if (fich.exists()) {
                if (fich.isFile()) {
                    System.out.println("El argumento introducido es un fichero con nombre " + fich.getName());
                    System.out.println("el contenido del fichero es: ");
//                    System.out.println(ejemploLectura2(fich));

                } else {
                    System.out.println(("El argumento introducido es un directorio con nombre " + fich.getName()));
                }
            } else {
                System.out.println("El fichero solicitado no existe");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Método de ejemplo de lectura de fichero usando directamente FileReader
     *
     * @param f fichero a leer
     * @return String con el contenido del fichero
     * @throws IOException
     */
    private static String ejemploLectura(File f) throws IOException {
        FileReader fr = new FileReader(f);
        String result;
        char[] lectura = new char[(int) f.length()];
        int caractLeidos = fr.read(lectura);
        if (caractLeidos != -1) {
            result = new String(lectura);
        } else {
            return "El fichero está vacío";
        }
        fr.close();

        return result;
    }

    /**
     * Método de ejemplo de lectura usando un BufferedReader
     *
     * @param f fichero a leer
     * @return el contenido del fichero
     * @throws IOException
     */
    private static String ejemploLectura2(File f) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(f));
        String result = "";
        String linea;
        while ((linea = br.readLine()) != null) {
            result += linea + "\n";
        }
        br.close();
        return result;
    }

    private static void ejemploEscritura(File f) throws IOException {
        //Cración de fw con y sin append
        //FileWriter fw = new FileWriter(f, true);
        FileWriter fw = new FileWriter(f);
        //Escritura carácter a carácter
        fw.write("H");
        fw.write("o");
        fw.write("l");
        fw.write("a");
        //Escritura usando String
//        fw.write("Añadido");
        //Añadir al final del fichero
        fw.append(" añadido");
        //Volcado de datos
        fw.flush();
        //Cierre e fichero
        fw.close();
    }

    private static void ejemploEscritura2(File f) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        bw.write("Esto es un ejemplo");
        bw.newLine();
        bw.newLine();
        bw.write("con varias líneas");
        bw.close();
    }


}


