package Tema2.Act3;

import org.json.JSONObject;
import org.json.XML;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            Juego juego1 = new Juego("FIFA 14", "PC", "DEPORTES", "fifa14", "3+", "Juego de furbo");

            JAXBContext context = JAXBContext.newInstance(Juego.class);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter writer = new StringWriter();

            marshaller.marshal(juego1, writer);


            String xml = writer.toString();
            System.out.println(xml);

            marshaller.marshal(juego1, new File("juegos.xml"));

            JSONObject jo = XML.toJSONObject(xml);
            System.out.println(jo.toString(6));
            FileOutputStream fos = new FileOutputStream("juegosJSON.json");
            fos.write(jo.toString(6).getBytes());
            fos.close();

            //TODO ordenar el json

        } catch (JAXBException | IOException e) {
            throw new RuntimeException(e);
        }


    }
}
