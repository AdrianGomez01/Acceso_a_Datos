package Tema1.Tarea1_1;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class Concesionario {

    private static LinkedList<Coche> listaCoches = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("¿Qué desea hacer?");
            System.out.println("1. Cargar fichero CSV");
            System.out.println("2. Insertar nuevo coche");
            System.out.println("3. Ordenar BD por matrícula");
            System.out.println("4. Borrar un registro");
            System.out.println("5. Modificar marca o modelo");
            System.out.println("6. Salir");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    cargarCVS();
                    break;
                case 2:
                    insertarCoche();
                    break;
                case 3:
                    ordenarPorMatricula();
                    break;
                case 4:
                    borrarRegistroPorMatricula();
                    break;
                case 5:
                    modificarRegistroPorMatricula();
                    break;
                case 6:
                    System.out.println("Adios!");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, selecciona una opción válida.");
            }
        } while (opcion != 6);

        scanner.close();
    }

    private static void modificarRegistroPorMatricula() {

    }

    private static void borrarRegistroPorMatricula() {

    }

    private static void ordenarPorMatricula() {

    }

    private static void insertarCoche() throws IOException {

        Scanner sc = new Scanner(System.in);
        System.out.println("En qué posición desea insertar el registro?");
        int pos = sc.nextInt();

        //TODO comprobar que no exista ya el registro

    }

    private static void cargarCVS() throws FileNotFoundException {

        Scanner scanner = new Scanner(new File("BBDDCoches.csv"));
        Coche coche = null;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split(",");
            String matricula = parts[0];
            String marca = parts[1];
            String modelo = parts[2];

            coche = new Coche(matricula, marca, modelo);
            listaCoches.add(coche);
        }
        volcarLista(listaCoches);
    }

    public static void volcarLista(LinkedList<Coche> lista) {
        try (RandomAccessFile raf = new RandomAccessFile("BBDDCoches.dat", "rw")) {
            for (Coche coche : lista) {

                String valorMatricula = String.format("%1$-" + 7 + "s", coche.getMatricula());
                raf.write(valorMatricula.getBytes("UTF-8"));

                String valorMarca = String.format("%1$-" + 32 + "s", coche.getMarca());
                raf.write(valorMarca.getBytes("UTF-8"));

                String valorModelo = String.format("%1$-" + 32 + "s", coche.getModelo());
                raf.write(valorModelo.getBytes("UTF-8"));

            }
            System.out.println("La lista de coches se ha guardado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



