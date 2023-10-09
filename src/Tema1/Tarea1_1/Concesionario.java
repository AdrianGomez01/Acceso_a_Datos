package Tema1.Tarea1_1;

import java.io.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Concesionario {

    private LinkedList<Coche> listaCoches;
    private int numRegistros;
    private File fichero;


    public Concesionario(File archivo) {
        this.listaCoches = new LinkedList<>();
        this.fichero = archivo;
        //Calculo el numRegistros dividiendo por el num de bytes que tiene cada registro.
        this.numRegistros = (int) (this.fichero.length() / 71);

        //if (this.fichero.length() > 0) {
        //    recuperarLista();
        //}

    }

    /**
     *
     */
    public void modificarRegistroPorPosicion() {
        recuperarLista();
        Scanner sc = new Scanner(System.in);
        //creo otro scanner para guardar la marca y el modelo nuevos porque si no se me salta la marca.
        Scanner mm = new Scanner(System.in);

        System.out.println("Introduzca la posición del vehiculo que desea modificar: ");
        int pos = sc.nextInt();

        //Comprobamos que la posición sea válida como en nuestro método de Insertar
        if (pos >= 0 && pos <= listaCoches.size()) {
            // Obtenemos el coche en la posición especificada
            Coche cocheAModificar = listaCoches.get(pos);
            // Modificamos los campos del coche según indique el usuario
            System.out.println("Introduzca nueva la marca del vehiculo: ");
            String nuevaMarca = mm.nextLine();
            System.out.println("Introduzca el nuevo modelo del vehiculo: ");
            String nuevoModelo = mm.nextLine();

            cocheAModificar.setMarca(nuevaMarca);
            cocheAModificar.setModelo(nuevoModelo);

            System.out.println("Registro en la posición " + pos + " modificado.");
            volcarLista();
        } else {
            System.out.println("La posición indicada no es válida.");
            listaCoches.clear();
        }

    }

    /**
     *
     */
    public void borrarRegistroPorMatriculaOPosicion() {
        recuperarLista();
        Scanner sc = new Scanner(System.in);
        Scanner mm = new Scanner(System.in);
        int opcion;
        System.out.println("Como desea buscar el vehiculo a eliminar? ");
        System.out.println("1. Por posición ");
        System.out.println("2. Por matricula ");
        System.out.println("3. Salir ");
        opcion = sc.nextInt();
        switch (opcion) {
            case 1:
                System.out.println("Introduzca la posición del vehiculo que desea borrar: ");
                int pos = sc.nextInt();

                //Comprobamos que la posición sea válida como en nuestro método de Insertar
                if (pos >= 0 && pos <= listaCoches.size()) {
                    listaCoches.remove(pos);

                    System.out.println("El vehiculo de la posición " + pos + " se ha eliminado");
                    volcarLista();
                } else {
                    System.out.println("La posición indicada no es válida.");
                }

                break;
            case 2:
                System.out.println("Introduzca la matricula del vehiculo que desea borrar: ");
                String matricula = mm.nextLine();

                // Iteramos sobre la lista y buscamos el coche por matrícula
                boolean cocheEncontrado = buscarCoche(matricula);

                if (!cocheEncontrado) {
                    System.out.println("No se encontró ningún coche con la matrícula " + matricula);
                } else {
                    Iterator<Coche> iterator = listaCoches.iterator();
                    while (iterator.hasNext()) {
                        Coche coche = iterator.next();
                        if (coche.getMatricula().equals(matricula)) {
                            iterator.remove(); // Eliminamos el coche de la lista
                            System.out.println("El vehiculo con matrícula " + matricula + " se ha eliminado.");
                            volcarLista();
                            break; //Paramos si lo hemos encontrado
                        }
                    }
                }
                volcarLista();
                break;
            default:
                System.out.println("Opción no válida. Por favor, selecciona una opción válida (1, 2 o 3)");
        }



    }

    /**
     *
     */
    public void ordenarPorMatricula() {
        recuperarLista();
        Collections.sort(listaCoches);
        volcarLista();
        System.out.println("La BBDD ha sido ordenada por matricula (asc)");
    }

    /**
     *
     */
    public void insertarCoche() {
        recuperarLista();
        Scanner sc = new Scanner(System.in);

        System.out.println("Introduzca la matricula del nuevo coche: ");
        String matricula = sc.nextLine();
        System.out.println("Introduzca la marca del nuevo coche: ");
        String marca = sc.nextLine();
        System.out.println("Introduzca el modelo del nuevo coche: ");
        String modelo = sc.nextLine();

        System.out.println("En qué posición desea insertar el registro?");
        int pos = sc.nextInt();

        Coche nuevoCoche = new Coche(matricula, marca, modelo);

        // Verificamos si la matrícula ya existe en la lista
        boolean matriculaExiste = false;
        for (Coche coche : listaCoches) {
            if (coche.getMatricula().equals(nuevoCoche.getMatricula())) {
                matriculaExiste = true;
                break;
            }
        }

        if (!matriculaExiste) {
            // Verificamos si la posición es válida
            if (pos >= 0 && pos <= listaCoches.size()) {
                listaCoches.add(pos, nuevoCoche);
                System.out.println("Nuevo coche agregado en la posición " + pos + ": " + nuevoCoche);
                volcarLista();
            } else {
                System.out.println("El índice especificado no es válido.");
            }
        } else {
            System.out.println("Ya existe un coche con la misma matrícula.");
        }
    }

    /**
     *
     */
    public void cargarCVS() {
        boolean cocheAnhadido = false;
        Coche coche = null;
        //Leemos el fichero .csv y sacamos la primera línea, ya que contiene los índices y no datos reales.
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("BBDDCoches.csv"))) {
            String linea;
            boolean primeraLinea = true;

            while ((linea = bufferedReader.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false; // Salto la primera línea
                    continue;
                }

                String[] parts = linea.split(",");
                String matricula = parts[0];
                String marca = parts[1];
                String modelo = parts[2];
                coche = new Coche(matricula, marca, modelo);
                //Si se encuentra una matrícula que coincida no añado el coche.
                if (!buscarCoche(matricula)) {
                    listaCoches.add(coche);
                    cocheAnhadido = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Compruebo que se haya añadido al menos 1 coche, de lo contrario vaciamos la lista y avisamos al usuario.
        if (cocheAnhadido) {
            volcarLista();
        } else {
            System.out.println("Todos los vehículos ya se encuentran en la base de datos");
            listaCoches.clear();
        }

    }

    /**
     *
     */
    public void volcarLista() {
        try (RandomAccessFile raf = new RandomAccessFile("BBDDCoches.dat", "rw")) {
            for (Coche coche : listaCoches) {
                String valorMatricula = String.format("%1$-" + 7 + "s", coche.getMatricula());
                raf.write(valorMatricula.getBytes("UTF-8"));

                String valorMarca = String.format("%1$-" + 32 + "s", coche.getMarca());
                raf.write(valorMarca.getBytes("UTF-8"));

                String valorModelo = String.format("%1$-" + 32 + "s", coche.getModelo());
                raf.write(valorModelo.getBytes("UTF-8"));
            }
            System.out.println("La BBDD de coches se ha actualizado");
            listaCoches.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void recuperarLista() {
        try (FileInputStream fileInputStream = new FileInputStream(new File("BBDDCoches.dat"))) {
            byte[] buffer = new byte[71]; // Tamaño total de un registro (7 + 32 + 32)
            int bytesRead;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                String matricula = new String(buffer, 0, 7).trim();
                String marca = new String(buffer, 7, 32).trim();
                String modelo = new String(buffer, 32, 32).trim();

                Coche coche = new Coche(matricula, marca, modelo);
                listaCoches.add(coche);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param matricula
     * @return
     */
    public boolean buscarCoche(String matricula) {
        recuperarLista();
        // Iteramos sobre la lista y busca el coche por matrícula
        Iterator<Coche> iterator = listaCoches.iterator();
        while (iterator.hasNext()) {
            Coche coche = iterator.next();
            if (coche.getMatricula().equals(matricula)) {
                //Si lo encontramos devolvemos true.
                return true;
            }
        }
        return false;
    }

    /**
     *
     */
    public void verLista() {
        recuperarLista();
        // Imprime la lista actualizada
        for (Coche coche : listaCoches) {
            System.out.println(coche);
        }
        listaCoches.clear();
    }

    /**
     *
     */
    public void mostrarDatosDeArchivoDat() {
        try (FileInputStream fileInputStream = new FileInputStream(new File("BBDDCoches.dat"))) {
            byte[] buffer = new byte[71];
            int bytesLeidos;
            int linea = 1; // Variable para contar las líneas

            while ((bytesLeidos = fileInputStream.read(buffer)) != -1) {
                String bloque = new String(buffer, 0, bytesLeidos);
                System.out.println("Línea " + linea + ": " + bloque);
                linea++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}