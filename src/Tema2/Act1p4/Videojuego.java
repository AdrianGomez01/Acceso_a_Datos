package Tema2.Act1p4;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Videojuego")
public class Videojuego {

    private String identificador;
    private String titulo;
    private String genero;
    private String desarrolladora;
    private String pegi;
    private String plataformas;
    private String precio;

    public Videojuego() {

    }

    public Videojuego(String identificador,String titulo, String genero, String desarrolladora, String pegi, String plataformas, String precio) {
        this.identificador=identificador;
        this.titulo = titulo;
        this.genero = genero;
        this.desarrolladora = desarrolladora;
        this.pegi = pegi;
        this.plataformas = plataformas;
        this.precio = precio;
    }

    @XmlAttribute(name = "Identificador")
    public String getIdentificador() {
        return this.identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    @XmlElement
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @XmlElement
    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @XmlElement
    public String getDesarrolladora() {
        return desarrolladora;
    }

    public void setDesarrolladora(String desarrolladora) {
        this.desarrolladora = desarrolladora;
    }

    @XmlElement
    public String getPegi() {
        return pegi;
    }

    public void setPegi(String pegi) {
        this.pegi = pegi;
    }

    @XmlElement
    public String getPlataformas() {
        return plataformas;
    }

    public void setPlataformas(String plataformas) {
        this.plataformas = plataformas;
    }

    @XmlElement
    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
