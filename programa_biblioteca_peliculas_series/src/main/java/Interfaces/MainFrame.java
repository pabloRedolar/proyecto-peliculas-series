package Interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class MainFrame extends JFrame {

    Conexion.ConectorBaseDatos conectorBaseDatos = new Conexion.ConectorBaseDatos();

    private JTextField campoUsuario;
    private JPasswordField campoContrasena;

    public void InterfazLogin() {
        setTitle("Login");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JPanel panelLogin = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelLogin.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelLogin.add(new JLabel("Nombre usuario", JLabel.RIGHT));
        campoUsuario = new JTextField(20);
        panelLogin.add(campoUsuario);

        panelLogin.add(new JLabel("Contraseña:", JLabel.RIGHT));
        campoContrasena = new JPasswordField(20);
        panelLogin.add(campoContrasena);

        JButton botonOlvidarContrasena = new JButton("Has olvidado la contraseña?");
        panelLogin.add(botonOlvidarContrasena, BorderLayout.SOUTH);

        panel.add(panelLogin, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton botonLogin = new JButton("Iniciar Sesión");
        panelBotones.add(botonLogin);

        JButton botonRegistrarse = new JButton("Registrarse");
        panelBotones.add(botonRegistrarse);

        panel.add(panelBotones, BorderLayout.SOUTH);

        botonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreUsuario = campoUsuario.getText();
                String password = new String(campoContrasena.getPassword());

                final JOptionPane jOptionPane = new JOptionPane("Cargando... Espere un momento", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
                final JDialog dialog = jOptionPane.createDialog("Cargando");

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        dialog.dispose();
                    }
                }, 1500);

                dialog.setVisible(true);

                if (conectorBaseDatos.login_usuario(nombreUsuario, password)) {
                    dispose();

                } else {
                    campoUsuario.setText("");
                    campoContrasena.setText("");
                }
            }
        });

        botonRegistrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Registrese a continuación");
                conectorBaseDatos.registrar_usuario();
            }
        });

        botonOlvidarContrasena.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conectorBaseDatos.cambiar_contrasena();
            }
        });

        setVisible(true);
    }
}