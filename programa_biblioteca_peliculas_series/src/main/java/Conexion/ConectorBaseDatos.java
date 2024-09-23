package Conexion;

import Clases.*;
import Interfaces.AccionesUsuarioFrame;
import Interfaces.InformacionPeliculasUsuarioFrame;
import Interfaces.MainFrame;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConectorBaseDatos {
    public Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/biblioteca_peliculas_series";
        String user = "root";
        String password = "1234";
        return DriverManager.getConnection(url, user, password);
    }

    public void insertar_peliculas_json() {
        ObjectMapper objectMapper = new ObjectMapper();

        try (Connection connection = connect()) {
            ListaPeliculas listaPeliculas = objectMapper.readValue(new File("peliculas.json"), ListaPeliculas.class);

            String insert_peliculas = "INSERT IGNORE INTO peliculas (titulo, sinopsis, puntuacion, genero, fecha_estreno, duracion) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insert_peliculas);

            for (Pelicula pelicula : listaPeliculas.getPeliculaList()) {
                preparedStatement.setString(1, pelicula.getTitulo());
                preparedStatement.setString(2, pelicula.getSinopsis());
                preparedStatement.setFloat(3, pelicula.getPuntuacion());
                preparedStatement.setString(4, pelicula.getGenero());
                preparedStatement.setString(5, pelicula.getFecha_estreno());
                preparedStatement.setString(6, pelicula.getDuracion());

                preparedStatement.executeUpdate();
            }

            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Peliculas duplicadas");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertar_series_json() {
        ObjectMapper objectMapper = new ObjectMapper();

        try (Connection connection = connect()) {
            ListaSeries listaSeries = objectMapper.readValue(new File("series.json"), ListaSeries.class);

            String insert_series = "INSERT IGNORE INTO series (titulo, sinopsis, puntuacion, genero, fecha_estreno, duracion_media_capitulos_minutos, cantidad_temporadas, cantidad_capitulos) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insert_series);

            for (Serie serie : listaSeries.getSeriesList()) {
                preparedStatement.setString(1, serie.getTitulo());
                preparedStatement.setString(2, serie.getSinopsis());
                preparedStatement.setFloat(3, serie.getPuntuacion());
                preparedStatement.setString(4, serie.getGenero());
                preparedStatement.setString(5, serie.getFecha_estreno());
                preparedStatement.setInt(6, serie.getDuracion_media_capitulos_minutos());
                preparedStatement.setInt(7, serie.getCantidad_temporadas());
                preparedStatement.setInt(8, serie.getCantidad_capitulos());

                preparedStatement.executeUpdate();

            }

            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Series duplicadas");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void registrar_usuario() {
        Usuario usuario = new Usuario();
        usuario.setNombre_usuario(JOptionPane.showInputDialog("Dime un nombre de usuario"));
        usuario.setEmail(JOptionPane.showInputDialog("Dime un email"));
        String contrasena = "";
        JPasswordField jPasswordField = new JPasswordField();
        int opcion = JOptionPane.showConfirmDialog(null, jPasswordField, "Dime una contraseña", JOptionPane.OK_CANCEL_OPTION);

        String comprobar_contrasena = "";
        JPasswordField jPasswordField1 = new JPasswordField();
        int opcion2 = JOptionPane.showConfirmDialog(null, jPasswordField1, "Repite la contraseña", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            char[] password = jPasswordField.getPassword();
            contrasena = new String(password);

            if (opcion2 == JOptionPane.OK_OPTION) {
                char[] confirm_password = jPasswordField1.getPassword();
                comprobar_contrasena = new String(confirm_password);
            }

            while (!comprobar_contrasena.equals(contrasena)) {
                JOptionPane.showMessageDialog(new JFrame(), "La contraseñas no coinciden");
                jPasswordField = new JPasswordField();
                opcion = JOptionPane.showConfirmDialog(null, jPasswordField, "Dime una contraseña", JOptionPane.OK_CANCEL_OPTION);

                jPasswordField1 = new JPasswordField();
                opcion2 = JOptionPane.showConfirmDialog(null, jPasswordField1, "Repite la contraseña", JOptionPane.OK_CANCEL_OPTION);

                if (opcion == JOptionPane.OK_OPTION) {
                    password = jPasswordField.getPassword();
                    contrasena = new String(password);

                    if (opcion2 == JOptionPane.OK_OPTION) {
                        char[] confirm_password = jPasswordField1.getPassword();
                        comprobar_contrasena = new String(confirm_password);
                    }
                }
            }
            System.out.println("Contraseña guardada con exito");
        } else {
            System.out.println("No se ha asignado ninguna contraseña");
        }

        usuario.setContrasena(contrasena);
        usuario.setNombre(JOptionPane.showInputDialog("Dime tu nombre"));
        usuario.setApellidos(JOptionPane.showInputDialog("Dime tus apellidos"));

        System.out.println(usuario);
        if (usuario.getNombre_usuario().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El usuario debe tener nombre de usuario", "Error nombre de usuario", JOptionPane.ERROR_MESSAGE);
        } else {
            try (Connection connection = connect()) {
                String insertar_usuario = "INSERT INTO usuarios (nombre_usuario, email, nombre, apellidos) VALUES (?, ?, ?, ?)";
                String insertar_contrasena = "INSERT INTO contrasenas_usuarios VALUES ((SELECT id FROM usuarios WHERE nombre_usuario = '" + usuario.getNombre_usuario() + "'), SHA2('" + usuario.getContrasena() + "', 256))";

                PreparedStatement preparedStatement = connection.prepareStatement(insertar_usuario);
                preparedStatement.setString(1, usuario.getNombre_usuario());
                preparedStatement.setString(2, usuario.getEmail());
                preparedStatement.setString(3, usuario.getNombre());
                preparedStatement.setString(4, usuario.getApellidos());
                preparedStatement.executeUpdate();

                PreparedStatement preparedStatement1 = connection.prepareStatement(insertar_contrasena);
                preparedStatement1.executeUpdate();

                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean login_usuario(String nombreUsuario, String contrasena) {
        MainFrame mainFrame = new MainFrame();
        AccionesUsuarioFrame accionesUsuarioFrame = new AccionesUsuarioFrame(mainFrame);

        try (Connection connection = connect()) {
            String consulta = "SELECT contrasena_cifrada FROM contrasenas_usuarios passuser JOIN usuarios user ON passuser.id_usuario = user.id WHERE user.nombre_usuario = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(consulta);
            preparedStatement.setString(1, nombreUsuario);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String contrasena_tabla = resultSet.getString("contrasena_cifrada");
                String contrasena_usuario_cifrada = cifrarConSHA2(contrasena);

                if (contrasena_tabla.equals(contrasena_usuario_cifrada)) {
                    System.out.println("Inicio de sesion exitoso para " + nombreUsuario);
                    accionesUsuarioFrame.InterfazUtilidadesUsuario(nombreUsuario);
                    accionesUsuarioFrame.setVisible(true);
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Credenciales inválidas", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Credenciales inválidas", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
            }

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public void cambiar_contrasena() {
        String nombreUser = JOptionPane.showInputDialog("Dime tu nombre de usuairo");
        String email = JOptionPane.showInputDialog("Dime tu email");
        Usuario usuario = new Usuario();

        try (Connection connection = connect()) {
            String consulta_user_email = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND email = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(consulta_user_email);

            preparedStatement.setString(1, nombreUser);
            preparedStatement.setString(2, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                usuario.setNombre_usuario(resultSet.getString("nombre_usuario"));
                usuario.setEmail(resultSet.getString("email"));
                usuario.setNombre(resultSet.getString("nombre"));
                usuario.setApellidos(resultSet.getString("apellidos"));
            } else {
                JOptionPane.showMessageDialog(null, "No se ha encontrado el usuario o el email");
            }

            if (Objects.equals(usuario.getNombre_usuario(), nombreUser) && Objects.equals(usuario.getEmail(), email)) {
                String contrasena_nueva = "";
                contrasena_nueva = JOptionPane.showInputDialog("Dime una contraseña nueva");

                PreparedStatement preparedStatement1 = connect().prepareStatement("UPDATE contrasenas_usuarios SET contrasena_cifrada = SHA2(?, 256) WHERE id_usuario = (SELECT id FROM usuarios WHERE nombre_usuario = ?)");
                preparedStatement1.setString(1, contrasena_nueva);
                preparedStatement1.setString(2, nombreUser);
                preparedStatement1.executeUpdate();

                JOptionPane.showMessageDialog(null, "Contraseña actualizada");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String cifrarConSHA2(String contrasena) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(contrasena.getBytes());
        StringBuilder hexString = new StringBuilder();

        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }


    public void usuario_guarda_peliculas(String usuario_logeado) {
        String nombre_pelicula = JOptionPane.showInputDialog("Dime el nombre de la película que quieres añadir a tu lista (dejar en blanco para ver todas las peliculas)");

        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT titulo FROM peliculas WHERE titulo LIKE ?");
            preparedStatement.setString(1, '%' + nombre_pelicula.toLowerCase() + '%');

            ResultSet resultSet = preparedStatement.executeQuery();

            List<String> lista_titulos = new ArrayList<>();
            while (resultSet.next()) {
                String nombre_pelicula_resultset = resultSet.getString(1);
                lista_titulos.add(nombre_pelicula_resultset);
            }

            if (nombre_pelicula.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Mostrando todas las peliculas");
                String seleccion_nombre_pelicula = (String) JOptionPane.showInputDialog(null, "Selecciona el nombre de la película que buscabas",
                        "Selecciona la pelicula", JOptionPane.PLAIN_MESSAGE, null, lista_titulos.toArray(), null);
                nombre_pelicula = seleccion_nombre_pelicula;

            } else if (lista_titulos.size() > 1) {
                JOptionPane.showMessageDialog(null, "Hay mas de una pelicula con un titulo parecido, porfavor, slecciona el titulo que buscabas");
                String seleccion_nombre_pelicula = (String) JOptionPane.showInputDialog(null, "Selecciona el nombre de la película que buscabas",
                        "Selecciona la pelicula", JOptionPane.PLAIN_MESSAGE, null, lista_titulos.toArray(), null);
                nombre_pelicula = seleccion_nombre_pelicula;
            }

            preparedStatement.close();
            resultSet.close();

            String terminada = JOptionPane.showInputDialog("¿Has terminado de ver la película? (Si/No)");
            String tiempo = null;
            Boolean pelicula_acabada = false;

            if (terminada.equalsIgnoreCase("Si")) {
                pelicula_acabada = true;
            } else if (terminada.equalsIgnoreCase("No")) {
                pelicula_acabada = false;
                tiempo = JOptionPane.showInputDialog("Dime la hora, el minuto y el segundo por el que te has quedado (HH:mm:ss)");
            }

            String insertar_tabla_usuarios_peliculas = "INSERT INTO usuarios_peliculas (id_usuario, id_pelicula, ultimo_minuto_visto, pelicula_terminada) " +
                    "VALUES ((SELECT id FROM usuarios WHERE nombre_usuario = ?), (SELECT id FROM peliculas WHERE titulo = ?), ?, ?)";

            PreparedStatement preparedStatement1 = connection.prepareStatement(insertar_tabla_usuarios_peliculas);
            preparedStatement1.setString(1, usuario_logeado);
            preparedStatement1.setString(2, nombre_pelicula.toLowerCase());
            preparedStatement1.setString(3, tiempo);
            preparedStatement1.setBoolean(4, pelicula_acabada);
            preparedStatement1.executeUpdate();

            JOptionPane.showMessageDialog(null, "Pelicula añadida");

            preparedStatement1.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar la película: La pelicula no existe","Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String listarPeliculasUsuario(String nombreUsuario) {
        Pelicula pelicula = new Pelicula();

        try (Connection connection = connect()) {
            String consultaPeliculas = "SELECT * FROM peliculas p JOIN usuarios_peliculas up ON p.id = up.id_pelicula JOIN usuarios us ON up.id_usuario = us.id " +
                    "WHERE us.nombre_usuario = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(consultaPeliculas);
            preparedStatement.setString(1, nombreUsuario);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<String> lista_peliculas_usuario = new ArrayList<>();

            while (resultSet.next()) {
                pelicula.setTitulo(resultSet.getString("titulo"));

                lista_peliculas_usuario.add(pelicula.getTitulo());
            }
            String tituloPelicula = "";

            String seleccion_nombre_pelicula = (String) JOptionPane.showInputDialog(null, "Selecciona una pelicula para mas información",
                    "Tu lista de peliculas", JOptionPane.PLAIN_MESSAGE, null, lista_peliculas_usuario.toArray(), null);
            tituloPelicula = seleccion_nombre_pelicula;

            String consulta_info_peliculas = "SELECT * FROM peliculas p JOIN usuarios_peliculas up ON p.id = up.id_pelicula WHERE p.titulo = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(consulta_info_peliculas);
            preparedStatement1.setString(1, tituloPelicula);

            ResultSet resultSet1 = preparedStatement1.executeQuery();
            String terminada = "";
            String ultimo_minuto_visto = "";

            String resultado = null;
            if (resultSet1.next()) {
                pelicula.setTitulo(resultSet1.getString("titulo"));
                pelicula.setSinopsis(resultSet1.getString("sinopsis"));
                pelicula.setPuntuacion(resultSet1.getFloat("puntuacion"));
                pelicula.setGenero(resultSet1.getString("genero"));
                pelicula.setFecha_estreno(resultSet1.getString("fecha_estreno"));
                pelicula.setDuracion(resultSet1.getString("duracion"));

                Boolean pelicula_terminada = resultSet1.getBoolean("pelicula_terminada");

                if (pelicula_terminada) {
                    terminada = "Si";
                    ultimo_minuto_visto = pelicula.getDuracion();
                } else {
                    terminada = "No";
                    ultimo_minuto_visto = resultSet1.getString("ultimo_minuto_visto");
                }

                resultado = "- Titulo: " + pelicula.getTitulo() +
                        "\n" +
                        "\n- Sinopsis: " + pelicula.getSinopsis() +
                        "\n" +
                        "\n- Genero: " + pelicula.getGenero() +
                        "\n" +
                        "\n- Fecha de estreno: " + pelicula.getFecha_estreno() +
                        "\n" +
                        "\n- Duración: " + pelicula.getDuracion() +
                        "\n" +
                        "\n- Puntuación: " + pelicula.getPuntuacion() +
                        "\n" +
                        "\n- Pelicula terminada: " + terminada +
                        "\n" +
                        "\n- Ultimo minuto visto: " + ultimo_minuto_visto;
            } else {
                InformacionPeliculasUsuarioFrame informacionPeliculasUsuarioFrame = new InformacionPeliculasUsuarioFrame();
                informacionPeliculasUsuarioFrame.dispose();
            }

            return resultado;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void usuario_guarda_series(String usuario_logeado) {
        String nombre_serie = JOptionPane.showInputDialog("Dime el nombre de la serie que quieres añadir a tu lista (dejar en blanco para ver todas las series)");

        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT titulo FROM series WHERE titulo LIKE ?");
            preparedStatement.setString(1, '%' + nombre_serie.toLowerCase() + '%');

            ResultSet resultSet = preparedStatement.executeQuery();

            List<String> lista_titulos = new ArrayList<>();
            while (resultSet.next()) {
                String nombre_serie_resultset = resultSet.getString(1);
                lista_titulos.add(nombre_serie_resultset);
            }

            if (nombre_serie.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Mostrando todas las series");
                String seleccion_nombre_pelicula = (String) JOptionPane.showInputDialog(null, "Selecciona el nombre de la serie que buscabas",
                        "Selecciona la serie", JOptionPane.PLAIN_MESSAGE, null, lista_titulos.toArray(), null);
                nombre_serie = seleccion_nombre_pelicula;

            } else if (lista_titulos.size() > 1) {
                JOptionPane.showMessageDialog(null, "Hay mas de una serie con un titulo parecido, porfavor, slecciona el titulo que buscabas");
                String seleccion_nombre_pelicula = (String) JOptionPane.showInputDialog(null, "Selecciona el nombre de la serie que buscabas",
                        "Selecciona la serie", JOptionPane.PLAIN_MESSAGE, null, lista_titulos.toArray(), null);
                nombre_serie = seleccion_nombre_pelicula;
            }

            preparedStatement.close();
            resultSet.close();

            String terminada = JOptionPane.showInputDialog("¿Has terminado de ver la serie? (Si/No)");
            int ultimo_capitulo_visto = 0;
            Boolean serie_acabada = false;

            if (terminada.equalsIgnoreCase("Si")) {
                serie_acabada = true;
            } else if (terminada.equalsIgnoreCase("No")) {
                serie_acabada = false;
                ultimo_capitulo_visto = Integer.parseInt(JOptionPane.showInputDialog("Dime el ultimo capitulo en el que te has quedado"));
            }

            String insertar_tabla_usuarios_series = "INSERT INTO usuarios_series (id_usuario, id_serie, ultimo_capitulo_visto, serie_terminada) " +
                    " VALUES ((SELECT id FROM usuarios WHERE nombre_usuario = ?), (SELECT id FROM series WHERE titulo LIKE ?), ?, ?)";

            PreparedStatement preparedStatement1 = connection.prepareStatement(insertar_tabla_usuarios_series);
            preparedStatement1.setString(1, usuario_logeado);
            preparedStatement1.setString(2, '%' + nombre_serie.toLowerCase() + '%');
            preparedStatement1.setInt(3, ultimo_capitulo_visto);
            preparedStatement1.setBoolean(4, serie_acabada);
            preparedStatement1.executeUpdate();

            JOptionPane.showMessageDialog(null, "Serie añadida");

            preparedStatement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar la serie: La serie no existe","Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String listarSeriesUsuario(String nombreUsuario) {
        Serie serie = new Serie();

        try (Connection connection = connect()) {
            String consultaSeries = "SELECT * FROM series s JOIN usuarios_series us ON s.id = us.id_serie JOIN usuarios users ON us.id_usuario = users.id " +
                    "WHERE users.nombre_usuario = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(consultaSeries);
            preparedStatement.setString(1, nombreUsuario);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<String> lista_series_usuario = new ArrayList<>();

            while (resultSet.next()) {
                serie.setTitulo(resultSet.getString("titulo"));

                lista_series_usuario.add(serie.getTitulo());
            }
            String tituloSerie;

            String seleccion_nombre_serie = (String) JOptionPane.showInputDialog(null, "Selecciona una serie para mas información",
                    "Tu lista de peliculas", JOptionPane.PLAIN_MESSAGE, null, lista_series_usuario.toArray(), null);
            tituloSerie = seleccion_nombre_serie;

            String consulta_info_series = "SELECT * FROM series s JOIN usuarios_series us ON s.id = us.id_serie WHERE s.titulo = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(consulta_info_series);
            preparedStatement1.setString(1, tituloSerie);

            ResultSet resultSet1 = preparedStatement1.executeQuery();
            String terminada = "";
            int ultimo_capitulo_visto = 0;

            String salida = null;
            if (resultSet1.next()) {
                serie.setTitulo(resultSet1.getString("titulo"));
                serie.setSinopsis(resultSet1.getString("sinopsis"));
                serie.setPuntuacion(resultSet1.getFloat("puntuacion"));
                serie.setGenero(resultSet1.getString("genero"));
                serie.setFecha_estreno(resultSet1.getString("fecha_estreno"));
                serie.setDuracion_media_capitulos_minutos(resultSet1.getInt("duracion_media_capitulos_minutos"));
                serie.setCantidad_temporadas(resultSet1.getInt("cantidad_temporadas"));
                serie.setCantidad_capitulos(resultSet1.getInt("cantidad_capitulos"));

                Boolean serie_terminada = resultSet1.getBoolean("serie_terminada");

                if (serie_terminada) {
                    terminada = "Si";
                    ultimo_capitulo_visto = serie.getCantidad_capitulos();
                } else {
                    terminada = "No";
                    ultimo_capitulo_visto = resultSet1.getInt("ultimo_capitulo_visto");
                }
                salida = "- Titulo: " + serie.getTitulo() +
                        "\n" +
                        "\n- Sinopsis: " + serie.getSinopsis() +
                        "\n" +
                        "\n- Genero: " + serie.getGenero() +
                        "\n" +
                        "\n- Fecha de estreno: " + serie.getFecha_estreno() +
                        "\n" +
                        "\n- Duración media capitulos: " + serie.getDuracion_media_capitulos_minutos() +
                        "\n" +
                        "Cantidad de temporadas: " + serie.getCantidad_temporadas() +
                        "\n" +
                        "Cantidad de capitulos: " + serie.getCantidad_capitulos() +
                        "\n" +
                        "\n- Puntuación: " + serie.getPuntuacion() +
                        "\n" +
                        "\n- Serie terminada: " + terminada +
                        "\n" +
                        "\n- Ultimo capitulo visto: " + ultimo_capitulo_visto;

                return salida;
            }
            return salida;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Pelicula buscarPeliculas(String titulo) {
        Pelicula pelicula = new Pelicula();
        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT titulo FROM peliculas WHERE titulo LIKE ?");
            preparedStatement.setString(1, '%' + titulo.toLowerCase() + '%');

            ResultSet resultSet = preparedStatement.executeQuery();

            List<String> lista_titulos = new ArrayList<>();
            while (resultSet.next()) {
                String nombre_pelicula_resultset = resultSet.getString(1);
                lista_titulos.add(nombre_pelicula_resultset);
            }

            if (titulo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Mostrando todas las peliculas");
                titulo = (String) JOptionPane.showInputDialog(null, "Selecciona el nombre de la película que buscabas",
                        "Selecciona la pelicula", JOptionPane.PLAIN_MESSAGE, null, lista_titulos.toArray(), null);

            } else if (lista_titulos.size() > 1) {
                JOptionPane.showMessageDialog(null, "Hay mas de una pelicula con un titulo parecido, porfavor, slecciona el titulo que buscabas");
                titulo = (String) JOptionPane.showInputDialog(null, "Selecciona el nombre de la película que buscabas",
                        "Selecciona la pelicula", JOptionPane.PLAIN_MESSAGE, null, lista_titulos.toArray(), null);
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (Connection connection = connect()) {

            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM peliculas WHERE titulo = ?");
            preparedStatement1.setString(1, titulo);

            ResultSet resultSet = preparedStatement1.executeQuery();

            if (resultSet.next()) {
                pelicula.setTitulo(resultSet.getString("titulo"));
                pelicula.setSinopsis(resultSet.getString("sinopsis"));
                pelicula.setPuntuacion(resultSet.getFloat("puntuacion"));
                pelicula.setGenero(resultSet.getString("genero"));
                pelicula.setFecha_estreno(resultSet.getString("fecha_estreno"));
                pelicula.setDuracion(resultSet.getString("duracion"));

                return pelicula;

            } else {
                JOptionPane.showMessageDialog(null, "No se ha encontrado la pelicula", "Pelicula no encontrada", JOptionPane.ERROR_MESSAGE);
            }

            resultSet.close();
            preparedStatement1.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Serie buscarSeries(String titulo_serie) {
        Serie serie = new Serie();

        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT titulo FROM series WHERE titulo LIKE ?");
            preparedStatement.setString(1, '%' + titulo_serie.toLowerCase() + '%');

            ResultSet resultSet = preparedStatement.executeQuery();

            List<String> lista_titulos = new ArrayList<>();
            while (resultSet.next()) {
                String nombre_serie_resultset = resultSet.getString(1);
                lista_titulos.add(nombre_serie_resultset);
            }

            if (titulo_serie.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Mostrando todas las series");
                titulo_serie = (String) JOptionPane.showInputDialog(null, "Selecciona el nombre de la película que buscabas",
                        "Selecciona la pelicula", JOptionPane.PLAIN_MESSAGE, null, lista_titulos.toArray(), null);

            } else if (lista_titulos.size() > 1) {
                JOptionPane.showMessageDialog(null, "Hay mas de una serie con un titulo parecido, porfavor, slecciona el titulo que buscabas");
                titulo_serie = (String) JOptionPane.showInputDialog(null, "Selecciona el titulo de la serie que buscabas",
                        "Selecciona la serie", JOptionPane.PLAIN_MESSAGE, null, lista_titulos.toArray(), null);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (Connection connection = connect()) {

            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM series WHERE titulo = ?");
            preparedStatement1.setString(1, titulo_serie.toLowerCase());

            ResultSet resultSet = preparedStatement1.executeQuery();

            if (resultSet.next()) {
                serie.setTitulo(resultSet.getString("titulo"));
                serie.setSinopsis(resultSet.getString("sinopsis"));
                serie.setPuntuacion(resultSet.getFloat("puntuacion"));
                serie.setGenero(resultSet.getString("genero"));
                serie.setFecha_estreno(resultSet.getString("fecha_estreno"));
                serie.setDuracion_media_capitulos_minutos(resultSet.getInt("duracion_media_capitulos_minutos"));
                serie.setCantidad_temporadas(resultSet.getInt("cantidad_temporadas"));
                serie.setCantidad_capitulos(resultSet.getInt("cantidad_capitulos"));

                return serie;
            } else {
                JOptionPane.showMessageDialog(null, "No se ha encontrado la serie", "Serie no encontrada", JOptionPane.ERROR_MESSAGE);
            }
            resultSet.close();
            preparedStatement1.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void guardarTopPeliculas(int cantidad_peliculas) {
        try (Connection connection = connect()) {
            String consulta = "SELECT titulo, puntuacion FROM peliculas ORDER BY puntuacion DESC LIMIT " + cantidad_peliculas;
            PreparedStatement preparedStatement = connection.prepareStatement(consulta);

            ResultSet resultado = preparedStatement.executeQuery(consulta);

            try (BufferedWriter escritor = new BufferedWriter(new FileWriter("top" + cantidad_peliculas + "_peliculas.txt"))) {
                escritor.write("Top 10 Películas Mejor Puntuadas:");
                escritor.newLine();
                escritor.write("---------------------------------");
                escritor.newLine();
                while (resultado.next()) {
                    String titulo = resultado.getString("titulo");
                    String puntuacion = String.valueOf(resultado.getDouble("puntuacion"));
                    escritor.write(titulo + ". Puntuacion: " + puntuacion + "\n");
                }
                JOptionPane.showMessageDialog(null, "Las 10 películas mejor puntuadas se han guardado en el archivo 'top" + cantidad_peliculas + "_peliculas.txt'.");
            } catch (IOException e) {
                System.out.println("Error al escribir en el archivo: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión a la base de datos: " + e.getMessage());
        }
    }

    public void guardarTopSeries(int cantidad_series) {
        try (Connection connection = connect()) {
            String consulta = "SELECT titulo, puntuacion FROM series ORDER BY puntuacion DESC LIMIT " + cantidad_series;
            PreparedStatement preparedStatement = connection.prepareStatement(consulta);

            ResultSet resultado = preparedStatement.executeQuery(consulta);

            try (BufferedWriter escritor = new BufferedWriter(new FileWriter("top" + cantidad_series + "_series.txt"))) {
                escritor.write("Top 10 Series Mejor Puntuadas:");
                escritor.newLine();
                escritor.write("---------------------------------");
                escritor.newLine();
                while (resultado.next()) {
                    String titulo = resultado.getString("titulo");
                    String puntuacion = String.valueOf(resultado.getDouble("puntuacion"));
                    escritor.write(titulo + ". Puntuacion: " + puntuacion + "\n");
                }
                JOptionPane.showMessageDialog(null, "Las 10 series mejor puntuadas se han guardado en el archivo 'top" + cantidad_series + "_series.txt'.");
            } catch (IOException e) {
                System.out.println("Error al escribir en el archivo: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión a la base de datos: " + e.getMessage());
        }
    }


    public void actualizarUsuario(String nombre_usuario, String valor_boton) {
        String consulta = "";

        switch (valor_boton) {
            case ("Nombre de usuario") -> {
                consulta = "UPDATE usuarios SET nombre_usuario = ? WHERE nombre_usuario = ?";
            }
            case ("Email") -> {
                consulta = "UPDATE usuarios SET email = ? WHERE nombre_usuario = ?";
            }
            case ("Contraseña") -> {
                consulta = "UPDATE contrasenas_usuarios SET contrasena_cifrada = SHA2(?, 256) WHERE id_usuario = (SELECT id FROM usuarios WHERE nombre_usuario = ?)";
            }
            case ("Nombre") -> {
                consulta = "UPDATE usuarios SET nombre = ? WHERE nombre_usuario = ?";
            }
            case ("Apellidos") -> {
                consulta = "UPDATE usuarios SET apellidos = ? WHERE nombre_usuario = ?";
            }
        }

        String valores = JOptionPane.showInputDialog(null, "Dime que valor le quieres asignar", "Nuevo valor", JOptionPane.QUESTION_MESSAGE);

        while (valores.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se puede dejar el campo en blanco", "Error", JOptionPane.ERROR_MESSAGE);
            valores = JOptionPane.showInputDialog(null, "Dime que valor le quieres asignar", "Nuevo valor", JOptionPane.QUESTION_MESSAGE);
        }

        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(consulta);

            preparedStatement.setString(1, valores);
            preparedStatement.setString(2, nombre_usuario);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Actualizado con exito", "Actualizado", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se ha podido actualizar");
            throw new RuntimeException(e);
        }
    }

    public Usuario informacionUsuario (String nombre_usuario) {
        Usuario usuario = new Usuario();
        String consulta = "SELECT * FROM usuarios WHERE nombre_usuario = ?";

        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(consulta);
            preparedStatement.setString(1, nombre_usuario);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                usuario.setNombre_usuario(resultSet.getString("nombre_usuario"));
                usuario.setEmail(resultSet.getString("email"));
                usuario.setNombre(resultSet.getString("nombre"));
                usuario.setApellidos(resultSet.getString("apellidos"));
            } else {
                JOptionPane.showMessageDialog(null, "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }
}
