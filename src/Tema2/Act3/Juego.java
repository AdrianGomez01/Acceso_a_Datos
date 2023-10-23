package Tema2.Act3;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Juego {

    @XmlElement
    private String nombre;
    @XmlElement
    private String tipo;
    @XmlElement
    private String genero;
    @XmlElement
    private String proceso;
    @XmlElement
    private String edad;
    @XmlElement
    private String descripcion;

    public Juego() {

    }

    public Juego(String nombre, String tipo,
                 String genero, String proceso,
                 String edad, String descripcion) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.genero = genero;
        this.proceso = proceso;
        this.edad = edad;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getGenero() {
        return genero;
    }

    public String getProceso() {
        return proceso;
    }

    public String getEdad() {
        return edad;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
