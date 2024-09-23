package Clases;

public class Serie {
    String titulo;
    String sinopsis;
    Float puntuacion;
    String genero;
    String fecha_estreno;
    int duracion_media_capitulos_minutos;
    int cantidad_temporadas;
    int cantidad_capitulos;

    public Serie() {
    }

    public Serie(String titulo, String fecha_estreno, String sinopsis, String genero, int duracion_media_capitulos_minutos, int cantidad_temporadas, int cantidad_capitulos, Float puntuacion) {
        this.titulo = titulo;
        this.fecha_estreno = fecha_estreno;
        this.sinopsis = sinopsis;
        this.genero = genero;
        this.duracion_media_capitulos_minutos = duracion_media_capitulos_minutos;
        this.cantidad_temporadas = cantidad_temporadas;
        this.cantidad_capitulos = cantidad_capitulos;
        this.puntuacion = puntuacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha_estreno() {
        return fecha_estreno;
    }

    public void setFecha_estreno(String fecha_estreno) {
        this.fecha_estreno = fecha_estreno;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracion_media_capitulos_minutos() {
        return duracion_media_capitulos_minutos;
    }

    public void setDuracion_media_capitulos_minutos(int duracion_media_capitulos_minutos) {
        this.duracion_media_capitulos_minutos = duracion_media_capitulos_minutos;
    }

    public int getCantidad_temporadas() {
        return cantidad_temporadas;
    }

    public void setCantidad_temporadas(int cantidad_temporadas) {
        this.cantidad_temporadas = cantidad_temporadas;
    }

    public int getCantidad_capitulos() {
        return cantidad_capitulos;
    }

    public void setCantidad_capitulos(int cantidad_capitulos) {
        this.cantidad_capitulos = cantidad_capitulos;
    }

    public Float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Float puntuacion) {
        this.puntuacion = puntuacion;
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
                "\n- Temporadas: " + getCantidad_temporadas() +
                "\n" +
                "\n- Capítulos: " + getCantidad_capitulos() +
                "\n" +
                "\n- Duración media de los capitulos: " + getDuracion_media_capitulos_minutos() +
                "\n" +
                "\n- Puntuación: " + getPuntuacion();
    }
}
