package Clases;

public class Pelicula {
    String titulo;
    String sinopsis;
    Float puntuacion;
    String genero;
    String fecha_estreno;
    String duracion;

    public Pelicula() {
    }

    public Pelicula(String titulo, String sinopsis, Float puntuacion, String genero, String fecha_estreno, String duracion) {
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.puntuacion = puntuacion;
        this.genero = genero;
        this.fecha_estreno = fecha_estreno;
        this.duracion = duracion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public Float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Float puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFecha_estreno() {
        return fecha_estreno;
    }

    public void setFecha_estreno(String fecha_estreno) {
        this.fecha_estreno = fecha_estreno;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    @Override
    public String toString() {
        return "- Titulo: " + getTitulo() +
                "\n" +
                "\n- Sinopsis: " + getSinopsis() +
                "\n" +
                "\n- Genero: " + getGenero() +
                "\n" +
                "\n- Fecha de estreno: " + getFecha_estreno() +
                "\n" +
                "\n- Duración: " + getDuracion() +
                "\n" +
                "\n- Puntuación: " + getPuntuacion();
    }
}
