package Tema2;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.StringWriter;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Crear un objeto de la clase que deseamos convertir en XML
            Coche coche = new Coche("4857TTR", "Ford", "Fiesta");
            Coche coche2 = new Coche("8798UIO", "Toyota", "Corolla");
            BbddCoches coches = new BbddCoches();
            coches.getCoches().add(coche);
            coches.getCoches().add(coche2);
            // Crear un contexto JAXB para la clase
            JAXBContext context = JAXBContext.newInstance(BbddCoches.class);
            //JAXBContext context = JAXBContext.newInstance(Coche.class);
            // Crear un objeto Marshaller
            Marshaller marshaller = context.createMarshaller();
            //Establecemos el formato correcto.
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // Realizar el marshalling en un StringWriter
            StringWriter writer = new StringWriter();
            marshaller.marshal(coches, writer);
            // Obtener el XML generado como una cadena
            String xml = writer.toString();
            // Imprimir el XML resultante
            System.out.println(xml);
            //marshaller.marshal(coche, new File("coche.xml"));
            marshaller.marshal(coches, new File("coche.xml"));

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
