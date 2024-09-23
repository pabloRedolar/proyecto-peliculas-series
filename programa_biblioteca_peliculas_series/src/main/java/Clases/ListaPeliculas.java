package Clases;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
@XmlRootElement
public class ListaPeliculas {
    List<Pelicula> peliculaList = new ArrayList<>();

    public ListaPeliculas() {
    }

    public ListaPeliculas(List<Pelicula> peliculaList) {
        this.peliculaList = peliculaList;
    }

    public List<Pelicula> getPeliculaList() {
        return peliculaList;
    }

    public void setPeliculaList(List<Pelicula> peliculaList) {
        this.peliculaList = peliculaList;
    }

    @Override
    public String toString() {
        return "ListaPeliculas{" +
                "peliculaList=" + peliculaList +
                '}';
    }
}
