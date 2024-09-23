package Interfaces;

import Conexion.ConectorBaseDatos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ActualizarUsuarioFrame extends JFrame {
    ConectorBaseDatos conectorBaseDatos = new ConectorBaseDatos();

    private final MainFrame mainFrame;

    public ActualizarUsuarioFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void editarUser(String nombreUsuario) {
        setTitle("Configuración del usuario");
        setSize(980, 180);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        agregarListenerDeCierre();

        setVisible(true);

        // Crear el panel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(panel);

        // Etiqueta de acciones
        JLabel etiquetaAcciones = new JLabel("¿Qué quieres editar?");
        etiquetaAcciones.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetaAcciones.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(etiquetaAcciones, BorderLayout.NORTH);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Usar FlowLayout para una línea
        panel.add(panelBotones, BorderLayout.CENTER);

        // Crear botones más pequeños
        Dimension botonDimension = new Dimension(170, 30); // Tamaño de los botones ajustado

        JButton botonNombreUsuario = new JButton("Nombre de usuario");
        botonNombreUsuario.setPreferredSize(botonDimension);

        JButton botonEmail = new JButton("Email");
        botonEmail.setPreferredSize(botonDimension);

        JButton botonContrasena = new JButton("Contraseña");
        botonContrasena.setPreferredSize(botonDimension);

        JButton botonNombre = new JButton("Nombre");
        botonNombre.setPreferredSize(botonDimension);

        JButton botonApellidos = new JButton("Apellidos");
        botonApellidos.setPreferredSize(botonDimension);

        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.setPreferredSize(botonDimension);


        // Añadir botones al panel de botones
        panelBotones.add(botonNombreUsuario);
        panelBotones.add(botonEmail);
        panelBotones.add(botonContrasena);
        panelBotones.add(botonNombre);
        panelBotones.add(botonApellidos);
        panelBotones.add(botonCancelar);

        JOptionPane.showMessageDialog(null, "¡Después de modificar cualquier campo tendrá que reiniciar el programa cerrando esta ventana! " +
                "\n(Si quieres actualizar todo el nombre de usuario debe ser lo último)", "Advertencia", JOptionPane.WARNING_MESSAGE);

        botonNombreUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conectorBaseDatos.actualizarUsuario(nombreUsuario, botonNombreUsuario.getText());
            }
        });

        botonEmail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conectorBaseDatos.actualizarUsuario(nombreUsuario, botonEmail.getText());
            }
        });

        botonContrasena.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conectorBaseDatos.actualizarUsuario(nombreUsuario, botonContrasena.getText());
            }
        });

        botonNombre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conectorBaseDatos.actualizarUsuario(nombreUsuario, botonNombre.getText());
            }
        });

        botonApellidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conectorBaseDatos.actualizarUsuario(nombreUsuario, botonApellidos.getText());
            }
        });

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AccionesUsuarioFrame accionesUsuarioFrame = new AccionesUsuarioFrame(mainFrame);
                accionesUsuarioFrame.InterfazUtilidadesUsuario(nombreUsuario);
            }
        });

    }

    private void agregarListenerDeCierre() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                MainFrame mainFrame1 = new MainFrame();
                mainFrame1.InterfazLogin();
            }
        });
    }
}

