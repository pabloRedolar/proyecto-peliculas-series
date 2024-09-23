package Clases;

import java.util.ArrayList;
import java.util.List;

public class ListaSeries {
    List<Serie> seriesList = new ArrayList<>();

    public ListaSeries() {
    }

    public ListaSeries(List<Serie> seriesList) {
        this.seriesList = seriesList;
    }

    public List<Serie> getSeriesList() {
        return seriesList;
    }

    public void setSeriesList(List<Serie> seriesList) {
        this.seriesList = seriesList;
    }

    @Override
    public String toString() {
        return "Clases.ListaSeries{" +
                "seriesList=" + seriesList +
                '}';
    }
}
