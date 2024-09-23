import Conexion.ConectorBaseDatos;
import Interfaces.MainFrame;

public class Main {
    public static void main(String[] args) {
        ConectorBaseDatos conectorBaseDatos = new ConectorBaseDatos();
        conectorBaseDatos.insertar_peliculas_json();
        conectorBaseDatos.insertar_series_json();

        MainFrame mainFrame = new MainFrame();
        mainFrame.InterfazLogin();
    }
}
