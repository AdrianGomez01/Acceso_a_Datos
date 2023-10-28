package Tema2.Act1p4;

import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static BbddVideojuegos bbddVideojuegos = new BbddVideojuegos();

    public static void main(String[] args) {

        while (true) {
            System.out.println("Menú Principal");
            System.out.println("1. Cargar videojuegos desde CSV a XML");
            System.out.println("2. Insertar un videojuego");
            System.out.println("3. Ordenar por Identificador");
            System.out.println("4. Borrar un videojuego por Identificador");
            System.out.println("5. Modificar un videojuego por Identificador");
            System.out.println("6. Exportar a XML o JSON por Identificador");
            System.out.println("7. Convertir XML a JSON");
            System.out.println("8. Salir");
            System.out.print("Elige una opción: ");

            int opcion = sc.nextInt();
            sc.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    // Lógica para cargar desde CSV a XML
                    bbddVideojuegos.cargarCSV();
                    break;
                case 2:
                    // Lógica para insertar un videojuego
                    bbddVideojuegos.insertarJuego();
                    break;
                case 3:
                    // Lógica para ordenar por Identificador
                    bbddVideojuegos.ordenarPorIdentificador();
                    break;
                case 4:
                    // Lógica para borrar un videojuego por Identificador
                    bbddVideojuegos.borrarPorIdentificador();
                    break;
                case 5:
                    // Lógica para modificar un videojuego por Identificador
                    bbddVideojuegos.modificarVideojuegoPorIdentificador();
                    break;
                case 6:
                    // Lógica para exportar a XML o JSON por Identificador
                    bbddVideojuegos.exportarJuegoXMLoJSON();
                    break;
                case 7:
                    // Lógica para convertir XML a JSON
                    bbddVideojuegos.pasarXMLaJSON();
                    break;
                case 8:
                    System.out.println("Saliendo del programa. ¡Hasta luego!");
                    sc.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
    }

}
