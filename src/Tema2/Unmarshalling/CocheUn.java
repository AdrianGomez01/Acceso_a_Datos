package Tema2.Unmarshalling;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class CocheUn {

    private String matricula;
    private String marca;
    private String modelo;


    public CocheUn() {

    }

    public CocheUn(String matricula, String marca, String modelo) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
    }


    @XmlAttribute
    public String getMatricula() {
        return matricula;
    }

    @XmlElement
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    @XmlElement
    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }


}

