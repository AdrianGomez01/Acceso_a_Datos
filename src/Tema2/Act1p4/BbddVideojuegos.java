package Tema2.Act1p4;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
                if (!buscaJuego(identificador)) {
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
            System.out.println("Todos los vehículos ya se encuentran en la base de datos");
            videojuegos.clear();
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


    private boolean buscaJuego(String identificador) {
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

}
