package Tema1.Tarea1_1;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        Concesionario concesionario = new Concesionario(new File("BBDDCoches.dat"));
        try {
            do {
                System.out.println(" ___MENÚ___ ");
                System.out.println("1. Cargar fichero CSV");
                System.out.println("2. Insertar nuevo coche");
                System.out.println("3. Ordenar BD");
                System.out.println("4. Borrar un registro");
                System.out.println("5. Modificar marca o modelo");
                System.out.println("6. Ver lista y BBDD");
                System.out.println("7. Salir");

                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        concesionario.cargarCVS();
                        break;
                    case 2:
                        concesionario.insertarCoche();
                        break;
                    case 3:
                        concesionario.ordenarBBDD();
                        break;
                    case 4:
                        concesionario.borrarRegistroPorMatriculaOPosicion();
                        break;
                    case 5:
                        concesionario.modificarRegistroPorPosicion();
                        break;
                    case 6:
                        concesionario.verLista();
                        concesionario.mostrarDatosDeArchivoDat();
                        break;
                    case 7:
                        System.out.println("Adios!");
                        break;

                    default:
                        System.out.println("Opción no válida. Por favor, selecciona una opción válida.");
                }

            } while (opcion != 7);

        } catch (NumberFormatException e) {
            System.out.println("Error, introduzca un número válido" + e.getMessage());
        }
        scanner.close();

    }
}
