package Tema1.Tarea1_1;

import java.io.*;
import java.util.*;

public class Concesionario {

    private LinkedList<Coche> listaCoches;
    private int numRegistros;
    private File fichero;


    public Concesionario(File archivo) {
        this.listaCoches = new LinkedList<>();
        this.fichero = archivo;
        //Calculo el numRegistros dividiendo por el num de bytes que tiene cada registro.
        this.numRegistros = (int) (this.fichero.length() / 71);
    }

    /**
     * Este método recupera una lista basada en nuestro archivo .dat, pide por entrada de teclado al usuario la posición
     * del coche que desea modificar, mostrando antes una lista de los coches de nuestro fichero .dat e indicando su posición,
     * para comodidad del usuario, luego comprueba que la posición no salga de los límites de nuestra LinkedList,
     * después pide que nos devuelva el objeto de la lista que se corresponde con esa posición y se pide al usuario
     * que introduzca la nueva marca y modelo del vehículo.
     * Usando los setters de la clase Coche los modifica y volcamos de nuevo la lista en nuestro
     * archivo.
     * Si la posición indicada no es válida se lo indicamos al usuario con un mensaje por consola y limpiamos la lista
     * para dejarla lista para otras operaciones.
     */
    public void modificarRegistroPorPosicion() {
        recuperarLista();
        Scanner sc = new Scanner(System.in);
        System.out.println("Lista de vehículos: ");
        mostrarDatosDeArchivoDat();

        System.out.println("\nIntroduzca la posición del vehiculo que desea modificar: ");
        int pos = Integer.parseInt(sc.nextLine());

        //Comprobamos que la posición sea válida como en nuestro método de Insertar
        if (pos >= 0 && pos <= listaCoches.size()) {
            // Obtenemos el coche en la posición especificada
            Coche cocheAModificar = listaCoches.get(pos);
            // Modificamos los campos del coche según indique el usuario
            System.out.println("Introduzca nueva la marca del vehiculo: ");
            String nuevaMarca = sc.nextLine();
            System.out.println("Introduzca el nuevo modelo del vehiculo: ");
            String nuevoModelo = sc.nextLine();

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
     * Este método primero recupera una LinkedList formada por los datos del fichero .dat.
     * Luego pedimos por entrada de teclado al usuario cómo quiere buscar el vehículo que va a eliminar, si por posición
     * o por matrícula, también dando una opción de salida por si el usuario se equivocó en el menú de opciones.
     * -Al buscarlo por posición, muestra una lista de nuestro fichero .dat indicando sus posiciones para comodidad del
     * usuario, luego comprobamos que es una posición válida y luego eliminamos el registro de esa posición
     * con el método remove de linkedList indicándole dicha posición, acto seguido volvemos a volcar nuestra linkedList.
     * Avisamos al usuario con un mensaje por consola tanto si se ha borrado el vehículo de la posición indicada como
     * si no se ha podido encontrar dicha posición.
     * -Al buscarlo por matrícula, creo un booleano para comprobar si se ha encontrado o no, utilizando un método
     * buscarCoche() que devuelve true o false (explicado en su código más abajo). Si no lo encuentra avisa al usuario
     * con un mensaje por consola, si lo encuentra crea un Iterator con nuestra lista, la recorre para buscar el coche
     * el cual contenga esa matrícula y lo remueve. Vuelve a volcar nuestra lista en el archivo y se notifica al usuario
     * con un mensaje por consola de que el vehículo con la matrícula introducida se ha eliminado.
     */
    public void borrarRegistroPorMatriculaOPosicion() {
        recuperarLista();
        Scanner sc = new Scanner(System.in);
        int opcion;
        System.out.println("Como desea buscar el vehiculo a eliminar? ");
        System.out.println("1. Por posición ");
        System.out.println("2. Por matricula ");
        System.out.println("3. Volver al menú principal");
        opcion = Integer.parseInt(sc.nextLine());
        switch (opcion) {
            case 1:
                System.out.println("Lista de vehículos: ");
                mostrarDatosDeArchivoDat();
                System.out.println("\nIntroduzca la posición del vehiculo que desea borrar: ");
                int pos = sc.nextInt();

                //Comprobamos que la posición sea válida como en nuestro método de Insertar
                if (pos >= 0 && pos <= listaCoches.size()) {
                    listaCoches.remove(pos);

                    System.out.println("El vehiculo de la posición " + pos + " se ha eliminado");
                    //Borro el contenido del fichero dat antes de escribirlo de nuevo.
                    borrarBBDD();
                    volcarLista();
                } else {
                    System.out.println("La posición indicada no es válida.");
                }

                break;
            case 2:
                System.out.println("Introduzca la matricula del vehiculo que desea borrar: ");
                String matricula = sc.nextLine();

                // Iteramos sobre la lista y buscamos el coche por matrícula
                boolean cocheEncontrado = buscarCoche(matricula);

                if (!cocheEncontrado) {
                    System.out.println("No se encontró ningún coche con la matrícula " + matricula);
                } else {
                    Iterator<Coche> iterator = listaCoches.iterator();
                    while (iterator.hasNext()) {
                        Coche coche = iterator.next();
                        if (coche.getMatricula().equals(matricula)) {
                            // Eliminamos el coche de la lista
                            iterator.remove();
                            System.out.println("El vehiculo con matrícula " + matricula + " se ha eliminado.");
                            //Borro el contenido del fichero dat antes de escribirlo de nuevo.
                            borrarBBDD();
                            volcarLista();
                            break;
                        }
                    }
                }
                volcarLista();
                break;
            case 3:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción no válida. Por favor, selecciona una opción válida (1, 2 o 3)");
                System.out.println("Volviendo al menú principal...");
        }


    }

    /**
     * Este método recupera una lista basada en nuestro archivo .dat, luego pedimos al usuario cómo quiere ordenar la
     * BBDD, indicándole cuáles son los campos por los que puede ordenar.
     * Usando expresiones lambda llamaremos al método .sort de nuestra lista y comparamos el campo indicado con un Comparator.
     * Para ello hemos implementado la interfaz Comparable en nuestra clase Coche.java.
     * Estas expresiones nos comparan según el campo indicado y reordenan la lista, acto seguido volcamos
     * nuestra lista en el fichero.
     * También damos una opción al usuario para salir por si no quiere hacer modificaciones.
     */
    public void ordenarBBDD() {
        recuperarLista();
        Scanner sc = new Scanner(System.in);
        int opcion;
        System.out.println(" ¿Cómo desea ordenar la BBDD? ");
        System.out.println("1. Ordenar por matricula");
        System.out.println("2. Ordenar por Marca");
        System.out.println("3. Ordenar por Modelo");
        System.out.println("4. Volver al menú principal");
        opcion = Integer.parseInt(sc.nextLine());
        //Usando estas expresiones lambda, comparo según lo que pida el usuario para ordenarlo.
        switch (opcion) {
            case 1:
                listaCoches.sort(Comparator.comparing(Coche::getMatricula));
                volcarLista();
                break;
            case 2:
                listaCoches.sort(Comparator.comparing(Coche::getMarca));
                volcarLista();
                break;
            case 3:
                listaCoches.sort(Comparator.comparing(Coche::getModelo));
                volcarLista();
                break;
            case 4:
                System.out.println("Volviendo al menú principal...");
                break;
            default:
                System.out.println("Opción no válida. Por favor, selecciona una opción válida.");
                System.out.println("Volviendo al menú principal...");
        }

        System.out.println("La BBDD ha sido ordenada");
    }

    /**
     * Este método nos recupera la linkedList basada en el archivo .bat, nos pide los datos del nuevo coche y tras comprobar
     * que la matrícula no se repite, lo inserta en la lista y la vuelva en el archivo.
     * También se pide la posición en la que el usuario quiere insertarlo, comprobando que sea posible.
     * Si la matrícula se repite se avisará al usuario con un mensaje en la consola.
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
     * En este método vamos a coger los datos de un fichero .csv y pasarlos a una linkedList, para posteriormente volcar
     * esa lista en nuestro fichero .dat.
     * Primero creo un boolean que me compruebe si se ha añadido al menos 1 coche, ya que si se ejecuta la carga varias
     * veces puede dar resultados redundantes.
     * Inicializamos un objeto de tipo coche el cual vamos a asignarle los parámetros más adelante y leemos nuestro
     * fichero .csv con un bufferedReader.
     * Nos saltamos la primera línea que contiene el índice de los campos.
     * Separamos los campos (vienen separados con comas), y creamos un objeto de Coche con esos campos, luego lo
     * insertamos en nuestra lista y al terminar el documento la volcamos al .dat.
     * Si no se ha añadido ningún coche porque ya se encontraba en el archivo, se avisará al usuario.
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
     * Este método accede a nuestro fichero .dat y sobreescribe sus datos, por ello siempre antes de llamarlo debemos
     * llamar al método recuperarLista().
     * Usando bucle vamos recorriendo la lista y realizando un format de Strings para que los campos tengan siempre
     * el tamaño de bytes deseado, aúnque sean más cortos en realidad.
     * Cuando termina de escribir en el archivo limpia la linkedList para que no haya problemas de duplicidad.
     * Siempre que actualicemos el fichero .dat avisaremos por consola de que la BBDD se ha actualizado.
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
     * Este método lee nuestro fichero .dat y lo vuelca en una linkedList, separando por el número de bytes los campos de
     * cada objeto Coche.
     * Esta linkedList es donde realizamos nuestras operaciones, no en el fichero.
     * La matrícula son 7 bytes, la marca 32 bytes y el modelo 32 bytes, haciendo una suma total de 71 bytes.
     * Creamos array de bytes de 71 para ir sacando de registro en registro, indicándole luego el num de bytes de ese buffer
     * que pertenece a cada campo y asignándolo, para posteriormente crear el objeto con esos campos y añadirlo a la lista.
     * No comprueba que haya campos repetidos, ya que no hay manera de que en nuestro fichero .dat los haya, porque lo
     * controlamos a la hora de insertarlos.
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
     * Este método recupera los datos del archivo .dat y crea un iterator, con el cual la recorre buscando una matrícula
     * que sea equals que la que se le ha proporcionado por parámetro.
     *
     * @param matricula - Matrícula proporcionada por el usuario para buscar el vehículo.
     * @return - True si lo ha encontrado, false si no se encuentra la matrícula en la lista.
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
     * Este método es opcional y lo he creado para comprobar el comportamiento de la linkedList.
     */
    public void verLista() {
        recuperarLista();
        // Imprime la lista actualizada
        for (Coche coche : listaCoches) {
            System.out.println(coche);
        }
    }

    /**
     * Este método borra el contenido de la BBDD para que no haya problemas de duplicado de campos al borrar un registro.
     */
    public void borrarBBDD() {
        try {
            // Abre el archivo en modo de escritura sin anexar (esto borrará su contenido)
            FileOutputStream archivoSalida = new FileOutputStream("BBDDCoches.dat");
            // Cierra el archivo
            archivoSalida.close();
        } catch (IOException e) {
            System.err.println("Error al borrar el contenido del archivo: " + e.getMessage());
        }
    }

    /**
     * Este método muestra por consola todos los campos de todos los registros guardados en nuestro fichero .dat, para que
     * el usuario pueda verificarlos cuando quiera sin tener que acceder al fichero.
     * Este método es opcional y solo lo he añadido por comodidad.
     */
    public void mostrarDatosDeArchivoDat() {
        try (FileInputStream fileInputStream = new FileInputStream(new File("BBDDCoches.dat"))) {
            byte[] buffer = new byte[71];
            int bytesLeidos;
            int linea = 0; // Variable para contar las líneas

            while ((bytesLeidos = fileInputStream.read(buffer)) != -1) {
                String bloque = new String(buffer, 0, bytesLeidos);
                System.out.println("Posición " + linea + ": " + bloque);
                linea++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}