package Tema2.Unmarshalling;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class EjemploUnmarshalling {
    public static void main(String[] args) {
        try {
            // XML de ejemplo
            File f1 = new File("coche.xml");
            //FileReader fr1 = new FileReader(f1);
            // Crear un contexto JAXB para la clase
            JAXBContext context = JAXBContext.newInstance(BbddCochesUn.class);
            // Crear un objeto Unmarshaller
            Unmarshaller unmarshaller = context.createUnmarshaller();
            // Realizar el unmarshalling desde el XML
            //StringReader reader = new StringReader(fr1.toString());
            BbddCochesUn coche = (BbddCochesUn) unmarshaller.unmarshal(f1);
            //Recorro los coches de la lista
            for (CocheUn c : coche.getCoches()) {
                // Manipular el objeto Java resultante
                System.out.println("Matricula: " + c.getMatricula());
                System.out.println("Marca: " + c.getMarca());
                System.out.println("Modelo: " + c.getModelo());
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}