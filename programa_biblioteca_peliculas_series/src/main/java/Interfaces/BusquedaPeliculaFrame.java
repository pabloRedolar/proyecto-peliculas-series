package Interfaces;

import Clases.Pelicula;
import Conexion.ConectorBaseDatos;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class BusquedaPeliculaFrame extends JFrame {
    ConectorBaseDatos conectorBaseDatos = new ConectorBaseDatos();

    public void infoPelicula(String tituloPelicula) {
        setSize(900, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setBackground(Color.black);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        add(panelPrincipal);

        JTextArea jTextArea = new JTextArea();
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        jTextArea.setEditable(false);

        // Configuraciones adicionales para mejorar la apariencia del texto
        jTextArea.setFont(new Font("Arial", Font.BOLD, 16));
        jTextArea.setMargin(new Insets(10, 10, 10, 10));
        jTextArea.setForeground(Color.WHITE);
        jTextArea.setBackground(Color.BLACK);

        Pelicula pelicula;
        pelicula = (conectorBaseDatos.buscarPeliculas(tituloPelicula));
        jTextArea.setText(String.valueOf(pelicula));
        setTitle(pelicula.getTitulo());

        JScrollPane scrollPane = new JScrollPane(jTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        setVisible(!Objects.equals(pelicula, "null"));
    }
}
