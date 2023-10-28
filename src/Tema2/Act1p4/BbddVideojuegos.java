package Tema2.Act1p4;

import org.json.JSONObject;
import org.json.XML;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.util.*;

@XmlRootElement(name = "Videojuegos")
public class BbddVideojuegos {

    private List<Videojuego> videojuegos;

    public BbddVideojuegos() {
        this.videojuegos = new ArrayList<>();
    }


    public List<Videojuego> getVideojuegos() {
        return videojuegos;
    }

    public void setVideojuegos(List<Videojuego> videojuegos) {
        this.videojuegos = videojuegos;
    }


    public void cargarCSV() {
        recuperarLista();
        boolean juegoAnhadido = false;
        Videojuego juego = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/Tema2/Act1p4/videojuegos.csv"))) {
            String linea;
            boolean primeraLinea = true;

            while ((linea = bufferedReader.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false; // Salto la primera línea
                    continue;
                }

                String[] parts = linea.split(",");
                String identificador = parts[0];
                String titulo = parts[1];
                String genero = parts[2];
                String desarrolladora = parts[3];
                String pegi = parts[4];
                String plataforma = parts[5];
                String precio = parts[6];
                juego = new Videojuego(identificador, titulo, genero, desarrolladora, pegi, plataforma, precio);
                //Si se encuentra un identificador que coincida no añado el juego.
                if (!comprobarId(identificador)) {
                    videojuegos.add(juego);
                    juegoAnhadido = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error, no se encuentra el fichero csv" + e.getMessage());
        }
        //Compruebo que se haya añadido al menos 1 juego, de lo contrario vaciamos la lista y avisamos al usuario.
        if (juegoAnhadido) {
            volcarLista();
        } else {
            System.out.println("Todos los videojuegos ya se encuentran en la base de datos");
        }
    }

    private void volcarLista() {
        try {
            JAXBContext context = JAXBContext.newInstance(BbddVideojuegos.class);

            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(this, new File("src/Tema2/Act1p4/videojuegos.xml"));

        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean comprobarId(String identificador) {
        Iterator<Videojuego> iterator = videojuegos.iterator();
        while (iterator.hasNext()) {
            Videojuego juego = iterator.next();
            if (juego.getIdentificador().equals(identificador)) {
                //Si lo encontramos devolvemos true.
                return true;
            }
        }
        return false;
    }

    public void recuperarLista() {
        try {
            File f = new File("src/Tema2/Act1p4/videojuegos.xml");
            JAXBContext context = JAXBContext.newInstance(BbddVideojuegos.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            BbddVideojuegos juego = (BbddVideojuegos) unmarshaller.unmarshal(f);
            this.videojuegos = juego.getVideojuegos();

        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertarJuego() {
        recuperarLista();

        System.out.println("Introduzca el identificador del videojuego");
        String identificador = Main.sc.nextLine().toUpperCase();
        if (identificador.length() == 5) {
            if (!comprobarId(identificador)) {
                System.out.println("Introduzca el titulo del videojuego");
                String titulo = Main.sc.nextLine();
                System.out.println("Introduzca el genero del videojuego");
                String genero = Main.sc.nextLine();
                System.out.println("Introduzca la desarrolladora del videojuego");
                String desarrolladora = Main.sc.nextLine();
                System.out.println("Introduzca el PEGI del videojuego");
                String pegi = Main.sc.nextLine();
                System.out.println("Introduzca las plataformas del videojuego");
                String plataformas = Main.sc.nextLine();
                System.out.println("Introduzca el precio del videojuego");
                String precio = Main.sc.nextLine();
                Videojuego videojuego = new Videojuego(identificador, titulo, genero, desarrolladora, pegi, plataformas, precio);
                videojuegos.add(videojuego);
                volcarLista();
            } else {
                System.out.println("Error, el identificador ya se encuentra en la BBDD y debe ser único.");
            }
        } else {
            System.out.println("Error, el identificador debe contener 5 carácteres");
        }
    }

    public void pasarXMLaJSON() {
        recuperarLista();
        try {
            JAXBContext context = JAXBContext.newInstance(BbddVideojuegos.class);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter writer = new StringWriter();
            marshaller.marshal(this, writer);

            String xml = writer.toString();

            //Ahora crearemos un JSON con el xml
            JSONObject jo = XML.toJSONObject(xml);
            System.out.println(jo.toString(7));
            FileOutputStream fos = new FileOutputStream("src/Tema2/Act1p4/BbddjuegosJSON.json");
            fos.write(jo.toString(7).getBytes());
            fos.close();
        } catch (JAXBException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void ordenarPorIdentificador() {
        recuperarLista();
        videojuegos.sort(Comparator.comparing(Videojuego::getIdentificador));
        volcarLista();
        System.out.println("Fichero ordenado por el campo Identificador");
    }

    public void borrarPorIdentificador() {
        recuperarLista();
        System.out.println("Lista de juegos en su XML: \n");
        mostrarLista();
        System.out.println("Introduzca el Identificador del juego que desa borrar:");
        String id = Main.sc.nextLine().toUpperCase();

        boolean juegoEncontrado = comprobarId(id);

        if (juegoEncontrado) {
            Iterator<Videojuego> iterator = videojuegos.iterator();
            while (iterator.hasNext()) {
                Videojuego videojuego = iterator.next();
                if (videojuego.getIdentificador().equals(id)) {
                    iterator.remove();
                    System.out.println("El videojuego " + videojuego.getTitulo() + " con identificador " + id + " se ha eliminado.");
                    volcarLista();
                    break;
                }
            }
        } else {
            System.out.println("No se ha encontrado el juego con el Identificador introducido");
        }
    }

    public void mostrarLista() {
        for (Videojuego juegos : this.videojuegos) {
            System.out.println("Identificador: " + juegos.getIdentificador());
            System.out.println("Titulo: " + juegos.getTitulo() + "\n");
        }
    }

    public Videojuego buscarVideojuegoPorIdentificador(String identificador) {
        for (Videojuego videojuego : videojuegos) {
            if (videojuego.getIdentificador().equals(identificador)) {
                return videojuego;
            }
        }
        return null;
    }

    public void modificarVideojuegoPorIdentificador() {
        recuperarLista();
        System.out.println("Lista de juegos en su XML: \n");
        mostrarLista();
        System.out.println("Introduzca el Identificador del juego que desea modificar:");
        String id = Main.sc.nextLine().toUpperCase();

        Videojuego videojuego = buscarVideojuegoPorIdentificador(id);

        if (videojuego != null) {
            System.out.println("Introduzca el nuevo titulo del videojuego");
            String nuevoTitulo = Main.sc.nextLine();
            System.out.println("Introduzca el nuevo genero del videojuego");
            String nuevoGenero = Main.sc.nextLine();
            System.out.println("Introduzca la nuevo desarrolladora del videojuego");
            String nuevaDesarrolladora = Main.sc.nextLine();
            System.out.println("Introduzca el nuevo PEGI del videojuego");
            String nuevoPegi = Main.sc.nextLine();
            System.out.println("Introduzca las nuevas plataformas del videojuego");
            String nuevasPlataformas = Main.sc.nextLine();
            System.out.println("Introduzca el nuevo precio del videojuego");
            String nuevoPrecio = Main.sc.nextLine();

            videojuego.setTitulo(nuevoTitulo);
            videojuego.setGenero(nuevoGenero);
            videojuego.setDesarrolladora(nuevaDesarrolladora);
            videojuego.setPegi(nuevoPegi);
            videojuego.setPlataformas(nuevasPlataformas);
            videojuego.setPrecio(nuevoPrecio);

            System.out.println("El videojuego con identificador " + id + " se ha modificado.");
            volcarLista();

        } else {
            System.out.println("Error, no se ha encontrado el videojuego por el identificador introducido");
        }
    }

    public void exportarJuegoXMLoJSON() {
        recuperarLista();
        System.out.println("Lista de juegos en su XML: \n");
        mostrarLista();
        System.out.println("Introduzca el Identificador del juego que desea exportar:");
        String id = Main.sc.nextLine().toUpperCase();

        Videojuego videojuego = buscarVideojuegoPorIdentificador(id);

        System.out.println("¿En qué formato desea exportar el videojuego?");
        System.out.println("1. XML");
        System.out.println("2. JSON");

        int opcion = Main.sc.nextInt();
        Main.sc.nextLine(); // Consumir el salto de línea

        switch (opcion) {
            case 1:
                //XML
                try {
                    JAXBContext context = JAXBContext.newInstance(BbddVideojuegos.class);
                    Marshaller marshaller = context.createMarshaller();
                    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    marshaller.marshal(videojuego, new File("src/Tema2/Act1p4/MiVideojuego.xml"));
                    System.out.println("Videojuego guardado en el archivo MiVideojuego.xml");
                } catch (JAXBException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 2:
                //JSON
                try {
                    JAXBContext context = JAXBContext.newInstance(BbddVideojuegos.class);
                    Marshaller marshaller = context.createMarshaller();
                    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    StringWriter writer = new StringWriter();
                    marshaller.marshal(videojuego, writer);
                    String xml = writer.toString();
                    JSONObject jo = XML.toJSONObject(xml);
                    FileOutputStream fos = new FileOutputStream("src/Tema2/Act1p4/MiVideojuego.json");
                    fos.write(jo.toString(7).getBytes());
                    fos.close();
                    System.out.println("Videojuego guardado en el archivo MiVideojuego.json");
                } catch (JAXBException | IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                System.out.println("Opción no válida.");
        }

    }
}
