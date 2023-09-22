package EjemploAplicacionFicheros;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ficheroApp {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int opcion = 0;

        while (opcion != 5) {
            opcion = menu();
            try {
                switch (opcion) {
                    case 1:
                        clonarFichero();
                        break;
                    case 2:
                        contarPalabras();
                        break;
                    case 3:
                        contarOcurrencias();
                        break;
                    case 4:
                        sustituirPalabras();
                        break;
                    case 5:
                        unirFicheros();
                        break;
                    default:
                        System.out.println("Opción incorrecta");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    public static int menu() {
        System.out.println("Menú");
        System.out.println("1. Clonar ficheros");
        System.out.println("2. Contar palabras de un fichero");
        System.out.println("3. Contar ocurrencias de una palabra en un fichero");
        System.out.println("4. Sustituir palabras de un fichero");
        System.out.println("5. Unir ficheros");
        System.out.println("6. Salir");

        return UserDataCollector.getEnteroMinMax("Seleccione una opción", 1, 5);
    }

    private static void clonarFichero() throws IOException {
        //Pedimos el nombre del fichero
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce la ruta del fichero a clonar");
        String ruta = sc.nextLine();

        //hacemos una copia del fichero
        clonacion(ruta, "(copia)");
    }

    private static void clonacion(String ruta, String prefix) {
        //Creamos los flujos necesarios para leer el fichero proporcionado y crear el clonado
        try (BufferedReader br = new BufferedReader(new FileReader(new File(ruta)));
             BufferedWriter bw = new BufferedWriter((new FileWriter(new File(prefix + ruta))))) {
            String linea = br.readLine();
            do {
                bw.write(linea);
                bw.newLine();
                linea = br.readLine();
            } while (linea != null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void contarPalabras() {
        //Pedimos el nombre del fichero
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce la ruta del fichero a contar");
        String ruta = sc.nextLine();

        File fichero = new File(ruta);
        Pattern p = Pattern.compile("\\p{L}+");
        int contador = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Matcher m = p.matcher(linea);
                while (m.find()) {
                    contador++;
                }
            }
            System.out.println("El fichero tiene " + contador + " palabras");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void contarOcurrencias() throws IOException {
        //Pedimos el nombre del fichero
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce la ruta del fichero");
        String ruta = sc.nextLine();
        System.out.println("Introduce la palabra a contar");
        String palabra = sc.nextLine();

        File f = new File(ruta);

        int contador = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.contains(palabra)) {
                    contador++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        System.out.println("En el archivo hay " + contador + " ocurrencias de " + palabra);
    }

    private static void sustituirPalabras() throws IOException {

        //Pedimos el nombre del fichero
        Scanner sn = new Scanner(System.in);
        System.out.println("Introduce la ruta del fichero");
        String ruta = sn.nextLine();
        System.out.println("Introduce la palabra a sustituir");
        String palabraASustituir = sn.nextLine();
        System.out.println("Introduce la palabra que sustituye");
        String palabraSustituta = sn.nextLine();

        //Hacemos copia de seguridad del fichero original
        clonacion(ruta, "bkp-");
        //Creamos los flujos necesarios para leer el fichero proporcionado
        try {
            File f = new File(ruta);
            BufferedReader br = new BufferedReader(new FileReader(f));
            char[] lectura = new char[(int)f.length()];
            int leidos = br.read(lectura);
            String contenido = new String(lectura); //Convertimos el array de caracteres en un String
            br.close();
            contenido = contenido.replaceAll(palabraASustituir, palabraSustituta);
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(contenido);
            bw.close();

        }catch(IOException e){
            e.printStackTrace();
        }

    }

    private static void unirFicheros() throws IOException {

    }

//  Solucion del profesor
//
//    private static void contarPalabras() {
//        int contador = 0;
//        //Pedimos el nombre del fichero
//        Scanner sn = new Scanner(System.in);
//        System.out.println("Introduce la ruta del fichero");
//        String ruta = sn.nextLine();
//        try (BufferedReader br = new BufferedReader(new FileReader(new File(ruta)))){
//            String linea = br.readLine();
//            while (linea != null){
//
//                if (!linea.equals("")) { //obvia las líneas vacías
//                    String[] palabras = linea.split(" ");
//                    contador += palabras.length;
//                }
//                linea = br.readLine();
//            }
//
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//
//        System.out.println("El número total de palabras es: " + contador);
//    }
}
