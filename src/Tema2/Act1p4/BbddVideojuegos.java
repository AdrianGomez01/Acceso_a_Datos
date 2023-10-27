package Tema2.Act1p4;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

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
                if (comprobarId(identificador)) {
                    videojuegos.add(juego);
                    juegoAnhadido = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Compruebo que se haya añadido al menos 1 juego, de lo contrario vaciamos la lista y avisamos al usuario.
        if (juegoAnhadido) {
            volcarLista();
        } else {
            System.out.println("Todos los videojuegos ya se encuentran en la base de datos");
            //videojuegos.clear();
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
        try {
            File f = new File("src/Tema2/Act1p4/videojuegos.xml");
            JAXBContext context = JAXBContext.newInstance(BbddVideojuegos.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            BbddVideojuegos juego = (BbddVideojuegos) unmarshaller.unmarshal(f);

            boolean idUnico = this.videojuegos.stream().noneMatch(videojuego -> videojuego.getIdentificador() == identificador);

            return !idUnico;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public void recuperarLista() {
        try {
            File f = new File("src/Tema2/Act1p4/videojuegos.xml");
            JAXBContext context = JAXBContext.newInstance(BbddVideojuegos.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            BbddVideojuegos juego = (BbddVideojuegos) unmarshaller.unmarshal(f);
            this.videojuegos = juego.getVideojuegos();

//            for (Videojuego juegos : this.videojuegos) {
//                System.out.println("Identificador: " + juegos.getIdentificador());
//                System.out.println("Titulo: " + juegos.getTitulo());
//            }
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertarJuego() {
        System.out.println("Introduzca el identificador del videojuego");
        String identificador = Main.sc.nextLine();
        if (identificador.length() == 5) {
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
            System.out.println("Error, el identificador debe contener 5 carácteres");
        }
    }

    public void pasarXMLaJSON(){
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
        } catch (JAXBException | FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


}
