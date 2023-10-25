package Tema2.Act1p4;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "Videojuegos")
public class BbddVideojuegos {

    private List<Videojuego> videojuegos;

    public BbddVideojuegos() {
        this.videojuegos = new ArrayList<>();
    }

    public List<Videojuego> getVideojuegos() {
        return videojuegos;
    }

    public void setVideojuegos(List<Videojuego> videojuegos) {
        this.videojuegos = videojuegos;
    }



    public void cargarCVS(){

    }

}
