package Tema2.Act1p2;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "Catalog")
public class Catalogo {
    private List<Libro> libros;

    public Catalogo() {
        this.libros = new ArrayList<>();
    }

    @XmlElement(name = "Book")
    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }


}
