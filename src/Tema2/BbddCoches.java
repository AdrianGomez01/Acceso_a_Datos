package Tema2;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="coches")
public class BbddCoches {

    private List<Coche> coches;

    public BbddCoches() {
        this.coches = new ArrayList<>();
    }

    @XmlElement(name="coche")
    public List<Coche> getCoches() {
        return coches;
    }

    public void setCoches(List<Coche> coches) {
        this.coches = coches;
    }
}
