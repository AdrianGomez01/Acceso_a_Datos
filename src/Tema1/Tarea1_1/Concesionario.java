package Tema1.Tarea1_1;

import EjemploAplicacionFicheros.UserDataCollector;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Concesionario {

    private static final Scanner sc = new Scanner(System.in);


    public static void main(String[] args) throws IOException {
        int opcion = menu();
        while (opcion != 5) {
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

        return UserDataCollector.getEnteroMinMax("Seleccione una opción", 1, 5);
    }


    private static void modificarRegistroPorMatricula() {

    }

    private static void borrarRegistroPorMatricula() {

    }

    private static void ordenarPorMatricula() {

    }

    private static void insertarCoche() throws IOException  {

        System.out.println("En qué posición desea insertar el registro?");
        int pos = sc.nextInt();

        //TODO comprobar que no exista ya el registro

        try (FileOutputStream fos = new FileOutputStream("BBDDCoches.dat", true)) {
//            for (Map.Entry<String, String, String> campo : campos.entrySet()) {
//                int longCampo = campo.getValue();
//                String valorCampo = reg.get(campo.getKey());
//                if (valorCampo == null) {
//                    valorCampo = "";
//                }
//
//                String valorCampoForm = String.format("%1$-" + longCampo + "s", valorCampo); //devuelve el valor del 1er argumento en un String con longitud "longCampo" y alineado a la izquierda (gracias al uso de "-")
//                fos.write(valorCampoForm.getBytes("UTF-8"), 0, longCampo);
//
//            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void cargarCVS() {

    }
}


