package Tema1.Tarea1_1;

import EjemploAplicacionFicheros.UserDataCollector;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class Concesionario {

    private static final Scanner sc = new Scanner(System.in);

    private static LinkedList<Coche> listaCoches = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        int opcion = menu();
        while (opcion != 6) {
            opcion = menu();
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
                    System.out.println("Opción incorrecta");
            }
            sc.close();
        }

    }

    public static int menu() {
        System.out.println("¿Qué desea hacer?");
        System.out.println("1. Cargar fichero CSV");
        System.out.println("2. Insertar nuevo coche");
        System.out.println("3. Ordenar BD por matrícula");
        System.out.println("4. Borrar un registro");
        System.out.println("5. Modificar marca o modelo");
        System.out.println("6. Salir");

        return UserDataCollector.getEnteroMinMax("Seleccione una opción", 1, 6);
    }


    private static void modificarRegistroPorMatricula() {

    }

    private static void borrarRegistroPorMatricula() {

    }

    private static void ordenarPorMatricula() {

    }

    private static void insertarCoche() throws IOException {

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
        scanner.close();

    }

    public static void volcarLista(LinkedList<Coche> lista) {
        try (RandomAccessFile raf = new RandomAccessFile("BBDDCoches.dat", "rw")) {
            for (Coche coche : lista) {
                //TODO hacer raf.whrite para probar si falla
                escribirCampo(raf, coche.getMatricula(), 7);
                escribirCampo(raf, coche.getMarca(), 32);
                escribirCampo(raf, coche.getModelo(), 32);
            }
            System.out.println("La lista de coches se ha guardado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void escribirCampo(RandomAccessFile raf, String campo, int longitud) throws IOException {
        byte[] bytes = new byte[longitud];
        byte[] campoBytes = campo.getBytes("UTF-8");
        int len = Math.min(campoBytes.length, longitud);
        System.arraycopy(campoBytes, 0, bytes, 0, len);
        raf.write(bytes);
    }
}



