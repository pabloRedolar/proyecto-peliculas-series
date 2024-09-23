package Interfaces;

import Clases.Usuario;
import Conexion.ConectorBaseDatos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AccionesUsuarioFrame extends JFrame {
    ConectorBaseDatos conectorBaseDatos = new ConectorBaseDatos();

    private final MainFrame mainFrame;
    public AccionesUsuarioFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void InterfazUtilidadesUsuario(String nombreUsuario) {
        setTitle("¿Que quiere hacer?");
        setSize(800, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setBackground(Color.black);
        JPanel panelPrincipal = new JPanel(new GridLayout(3, 1, 10, -3));
        add(panelPrincipal);

        //Utilidades peliculas
        JPanel panelUtilidadesPeliculas = new JPanel(new BorderLayout(10, 10));
        panelUtilidadesPeliculas.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
        panelUtilidadesPeliculas.add(new JLabel("Peliculas"), BorderLayout.NORTH);
        panelPrincipal.add(panelUtilidadesPeliculas);

        //Panel añadir peliculas
        JPanel panelAnadirPelicula = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelAnadirPelicula.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelAnadirPelicula.add(new JLabel("Añadir pelicula a tu lista"));
        JButton botonAnadirPelicula = new JButton("Añadir película");
        panelAnadirPelicula.add(botonAnadirPelicula);

        panelUtilidadesPeliculas.add(panelAnadirPelicula, BorderLayout.WEST);

        //Panel listar peliculas
        JPanel panelListarPeliculas = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelListarPeliculas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelListarPeliculas.add(new JLabel("Ver tu lista de peliculas"));
        JButton botonListarPeliculas = new JButton("Ver lista de peliculas");
        panelListarPeliculas.add(botonListarPeliculas);

        panelUtilidadesPeliculas.add(panelListarPeliculas, BorderLayout.EAST);

        //Panel buscar peliculas
        JPanel panelBuscarPeliculas = new JPanel(new FlowLayout(FlowLayout.CENTER,10 ,10));
        panelBuscarPeliculas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelBuscarPeliculas.add(new JLabel("Buscar peliculas por su titulo (Dejar en blanco para ver todas las peliculas)"));

        JTextField campoTituloPelicula = new JTextField("Escriba el titulo aquí");
        campoTituloPelicula.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                campoTituloPelicula.setText("");
            }
        });

        JButton botonBuscarPelicula = new JButton("Buscar");
        panelBuscarPeliculas.add(campoTituloPelicula);
        panelBuscarPeliculas.add(botonBuscarPelicula);

        panelUtilidadesPeliculas.add(panelBuscarPeliculas, BorderLayout.SOUTH);

        //Peliculas con mayor puntuacion
        JPanel panelPeliculasPuntuacion = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelPeliculasPuntuacion.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JButton botonImprimirPeliculasMayorPuntuacion= new JButton("Imprimir peliculas con mayor puntuación");
        panelPeliculasPuntuacion.add(botonImprimirPeliculasMayorPuntuacion);

        panelBuscarPeliculas.add(panelPeliculasPuntuacion, BorderLayout.NORTH);


        //Utilidades series
        JPanel panelUtilidadesSeries = new JPanel(new BorderLayout(10, 10));
        panelUtilidadesSeries.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
        panelUtilidadesSeries.add(new JLabel("Series"), BorderLayout.NORTH);
        panelPrincipal.add(panelUtilidadesSeries);

        //Panel añadir series
        JPanel panelAnadirSerie = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelAnadirSerie.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelAnadirSerie.add(new JLabel("Añadir serie a tu lista"));
        JButton botonAnadirSerie = new JButton("Añadir serie");
        panelAnadirSerie.add(botonAnadirSerie);

        panelUtilidadesSeries.add(panelAnadirSerie, BorderLayout.WEST);

        //Panel listar series
        JPanel panelListarSeries = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelListarSeries.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelListarSeries.add(new JLabel("Ver tu lista de series"));
        JButton botonListarSeries = new JButton("Ver lista de series");
        panelListarSeries.add(botonListarSeries);

        panelUtilidadesSeries.add(panelListarSeries, BorderLayout.EAST);

        //Panel buscar series
        JPanel panelBuscarSeries = new JPanel(new FlowLayout(FlowLayout.CENTER,10 ,10));
        panelBuscarSeries.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelBuscarSeries.add(new JLabel("Buscar series por su titulo (Dejar en blanco para ver todas las series)"));

        JTextField campoTituloSerie = new JTextField("Escriba el titulo aquí");
        campoTituloSerie.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                campoTituloSerie.setText("");
            }
        });

        JButton botonBuscarSerie = new JButton("Buscar");
        panelBuscarSeries.add(campoTituloSerie);
        panelBuscarSeries.add(botonBuscarSerie);

        //Series con mayor puntuacion
        JPanel panelSeriesPuntuacion = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelSeriesPuntuacion.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JButton botonImprimirSeriesMayorPuntuacion= new JButton("Imprimir series con mayor puntuación");
        panelSeriesPuntuacion.add(botonImprimirSeriesMayorPuntuacion);

        panelBuscarSeries.add(panelSeriesPuntuacion, BorderLayout.NORTH);

        panelUtilidadesSeries.add(panelBuscarSeries, BorderLayout.SOUTH);


        //Utilidades usuario
        JPanel panelUtilidadesUsuarios = new JPanel(new BorderLayout(10, 10));
        panelUtilidadesUsuarios.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
        panelUtilidadesUsuarios.add(new JLabel("Mi usuario"), BorderLayout.NORTH);
        panelPrincipal.add(panelUtilidadesUsuarios);

        //Panel modificar usuario
        JPanel panelModificarUsuario = new JPanel(new FlowLayout(FlowLayout.CENTER,10, 10));
        panelModificarUsuario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelModificarUsuario.add(new JLabel("Configuración del usuario"));
        JButton botonModificarUsuario = new JButton("Configuracion");
        panelModificarUsuario.add(botonModificarUsuario, BorderLayout.SOUTH);
        panelUtilidadesUsuarios.add(panelModificarUsuario);

        JButton botonMostrarInfoUsuario = new JButton("Información del usuairo");
        panelModificarUsuario.add(botonMostrarInfoUsuario, BorderLayout.SOUTH);


        botonAnadirPelicula.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conectorBaseDatos.usuario_guarda_peliculas(nombreUsuario);
            }
        });

        botonListarPeliculas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InformacionPeliculasUsuarioFrame informacionPeliculasFrame = new InformacionPeliculasUsuarioFrame();
                informacionPeliculasFrame.infoPelicula(nombreUsuario);
            }
        });

        botonAnadirSerie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conectorBaseDatos.usuario_guarda_series(nombreUsuario);
            }
        });

        botonListarSeries.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            InformacionSeriesUsuarioFrame informacionSeriesUsuarioFrame = new InformacionSeriesUsuarioFrame();
            informacionSeriesUsuarioFrame.infoSeries(nombreUsuario);
            }
        });

        botonBuscarPelicula.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = campoTituloPelicula.getText();

                BusquedaPeliculaFrame busquedaPeliculaFrame = new BusquedaPeliculaFrame();
                busquedaPeliculaFrame.infoPelicula(titulo);
            }
        });

        botonImprimirPeliculasMayorPuntuacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int CantidadPelis = Integer.parseInt(JOptionPane.showInputDialog("Dime la cantidad de peliculas que quieres mostrar"));
                conectorBaseDatos.guardarTopPeliculas(CantidadPelis);
            }
        });

        botonBuscarSerie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = campoTituloSerie.getText();

                BusquedaSerieFrame busquedaSerieFrame = new BusquedaSerieFrame();
                busquedaSerieFrame.infoSerie(titulo);
            }
        });

        botonImprimirSeriesMayorPuntuacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int CantidadSeries = Integer.parseInt(JOptionPane.showInputDialog("Dime la cantidad de series que quieres mostrar"));
                conectorBaseDatos.guardarTopSeries(CantidadSeries);
            }
        });

        botonModificarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ActualizarUsuarioFrame actualizarUsuarioFrame = new ActualizarUsuarioFrame(mainFrame);
                actualizarUsuarioFrame.editarUser(nombreUsuario);
            }
        });

        botonMostrarInfoUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario usuario = conectorBaseDatos.informacionUsuario(nombreUsuario);
                JOptionPane.showMessageDialog(null, usuario.toString());
            }
        });

        setVisible(true);
    }
}
