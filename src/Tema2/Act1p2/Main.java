package Tema2.Act1p2;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;


public class Main {
    public static void main(String[] args) {
        try {
            File f1 = new File("src/Tema2/Act1p2/books.xml");
            FileReader fr1 = new FileReader(f1);
            JAXBContext context = JAXBContext.newInstance(Catalogo.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Catalogo libro = (Catalogo) unmarshaller.unmarshal(fr1);
            FileWriter fw = new FileWriter("src/Tema2/Act1p2/libros.txt");
            for (Libro l : libro.getLibros()) {

                fw.write("ID: " + l.getId() + "\n");
                fw.write("Autor: " + l.getAuthor() + "\n");
                fw.write("Título: " + l.getTitle() + "\n");
                fw.write("Género: " + l.getGenre() + "\n");
                fw.write("Precio: " + l.getPrice() + "\n");
                fw.write("Fecha de publicación: " + l.getPublishDate() + "\n");
                fw.write("Descripción: " + l.getDescription() + "\n");
            }
            fw.close();

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
