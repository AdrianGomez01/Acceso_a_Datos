package Tema2.Unmarshalling;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "coches")
public class BbddCochesUn {

    private List<CocheUn> coches;

    public BbddCochesUn() {
        this.coches = new ArrayList<>();
    }

    @XmlElement(name = "coche")
    public List<CocheUn> getCoches() {
        return coches;
    }

    public void setCoches(List<CocheUn> coches) {
        this.coches = coches;
    }


}
