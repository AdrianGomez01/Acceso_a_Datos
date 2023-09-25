//package EjemploAplicacionFicheros;
//
//import java.io.*;
//import java.util.InputMismatchException;
//import java.util.Scanner;
//
//class Main {
//  public static void main(String[] args) {
//    //Menú
//        Scanner sn = new Scanner(System.in);
//        boolean salir = false;
//        int opcion; //Guardaremos la opcion del usuario
//
//        while (!salir) {
//
//            System.out.println("1. Clonar fichero");
//            System.out.println("2. Contar apariciones de una palabra");
//            System.out.println("3. Sustituir apariciones de una palabra");
//            System.out.println("4. Número de palabras");
//            System.out.println("5. Unir ficheros");
//            System.out.println("6. Salir");
//
//            try {
//
//                System.out.println("Escribe una de las opciones");
//                opcion = sn.nextInt();
//
//                switch (opcion) {
//                    case 1:
//                        clonarFichero();
//                        break;
//                    case 2:
//                        //contarAparicionesPalabra();
//                        break;
//                    case 3:
//                        //sustituirAparicionesPalabra();
//                        break;
//                    case 4:
//                       // contarPalabras();
//                        break;
//                    case 5:
//                       // unirFicheros();
//                        break;
//                    case 6:
//                        salir = true;
//                        break;
//                    default:
//                        System.out.println("Solo números entre 1 y 4");
//                }
//            } catch (InputMismatchException e) {
//                System.out.println("Debes insertar un número");
//                sn.next();
//            }
//        }
//  }
//
//      private static void clonarFichero() {
//        //Pedimos el nombre del fichero
//        Scanner sn = new Scanner(System.in);
//        System.out.println("Introduce la ruta del fichero a clonar");
//        String ruta = sn.nextLine();
//
//        //hacemos una copia del fichero
//        clonacion(ruta, "(copia)");
//    }
//
//    private static void clonacion(String ruta, String prefix){
//        //Creamos los flujos necesarios para leer el fichero proporcionado y crear el clonado
//        try (BufferedReader br = new BufferedReader(new FileReader(new File(ruta)));
//             BufferedWriter bw = new BufferedWriter((new FileWriter(new File(prefix + ruta))))){
//            String linea = br.readLine();
//            do{
//                bw.write(linea);
//                bw.newLine();
//                linea = br.readLine();
//            }while (linea != null);
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//    }
//}