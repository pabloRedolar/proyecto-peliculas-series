package Clases;

public class Usuario {
    String nombre_usuario;
    String email;
    String contrasena;
    String nombre;
    String apellidos;

    public Usuario() {
    }

    public Usuario(String nombre_usuario, String email, String contrasena, String nombre, String apellidos) {
        this.nombre_usuario = nombre_usuario;
        this.email = email;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String setContrasena(String contrasena) {
        this.contrasena = contrasena;
        return contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Override
    public String toString() {
        return "- Nombre de usuario: " + getNombre_usuario() +
                "\n" +
                "\n- Email: " + getEmail() +
                "\n" +
                "\n- Nombre: " + getNombre() +
                "\n" +
                "\n- Apellidos: " + getApellidos();
    }
}
