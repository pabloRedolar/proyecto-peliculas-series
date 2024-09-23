CREATE DATABASE biblioteca_peliculas_series;

USE biblioteca_peliculas_series;

CREATE TABLE usuarios (
	id INT AUTO_INCREMENT PRIMARY KEY, 
	nombre_usuario VARCHAR(30) UNIQUE, 
	email VARCHAR(255), 
	nombre VARCHAR(50), 
	apellidos VARCHAR(100)
	);
	
CREATE TABLE contrasenas_usuarios (
	id_usuario INT PRIMARY KEY,
	contrasena_cifrada VARCHAR(255),
	FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

CREATE TABLE peliculas (
    id INT AUTO_INCREMENT, 
    titulo VARCHAR(255) NOT NULL,
    sinopsis VARCHAR(500), 
    puntuacion DECIMAL(3,1),
    genero VARCHAR(50),
    fecha_estreno DATE, 	
    duracion TIME,
    PRIMARY KEY (id),
    UNIQUE (titulo)
	);

CREATE TABLE series (
	id INT AUTO_INCREMENT PRIMARY KEY, 
	titulo VARCHAR(255) NOT NULL,
	sinopsis VARCHAR(500),
	puntuacion DECIMAL(3,1),
	genero VARCHAR(50), 
	fecha_estreno DATE, 
	duracion_media_capitulos_minutos INT, 
	cantidad_temporadas INT, 
	cantidad_capitulos INT, 
	UNIQUE (titulo)
	);

CREATE TABLE usuarios_peliculas (
	id_usuario INT, 
	id_pelicula INT,
	ultimo_minuto_visto TIME DEFAULT '00:00:00',
	pelicula_terminada BOOLEAN DEFAULT FALSE,
	PRIMARY KEY (id_usuario, id_pelicula),
	FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
	FOREIGN KEY (id_pelicula) REFERENCES peliculas(id)
	);

CREATE TABLE usuarios_series (
	id_usuario INT, 
	id_serie INT,
	ultimo_capitulo_visto INT DEFAULT 0,
	serie_terminada BOOLEAN DEFAULT FALSE,
	PRIMARY KEY (id_usuario, id_serie),
	FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
	FOREIGN KEY (id_serie) REFERENCES series(id)
	);


	CREATE TABLE registro_actividades_usuarios (
        id INT AUTO_INCREMENT PRIMARY KEY,
        id_usuario INT,
        nombre_usuario VARCHAR(50),
        actividad VARCHAR(50),
        fecha_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
    );

    DELIMITER //

    CREATE TRIGGER after_usuario_insert
    AFTER INSERT ON usuarios
    FOR EACH ROW
    BEGIN
        INSERT INTO registro_actividades_usuarios (id_usuario, nombre_usuario, actividad, fecha_hora)
        VALUES (NEW.id, NEW.nombre_usuario ,'Registro del usuario', CURRENT_TIMESTAMP);
    END;
    //

    DELIMITER ;

    DELIMITER //

    CREATE TRIGGER after_usuario_update
    AFTER UPDATE ON usuarios
    FOR EACH ROW
    BEGIN
        INSERT INTO registro_actividades_usuarios (id_usuario, nombre_usuario, actividad, fecha_hora)
        VALUES (NEW.id, NEW.nombre_usuario, 'Actualizacion del usuario', CURRENT_TIMESTAMP);
    END;
    //

    DELIMITER ;




