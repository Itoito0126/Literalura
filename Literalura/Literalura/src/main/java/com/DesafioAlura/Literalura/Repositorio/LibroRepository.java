package com.DesafioAlura.Literalura.Repositorio;

import com.DesafioAlura.Literalura.Model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    @Query("SELECT COUNT(l) > 0 FROM Libro l WHERE UPPER(l.titulo) LIKE UPPER(CONCAT('%', :titulo, '%'))")
    boolean existsByTitulo(@Param("titulo") String titulo);

    @Query("SELECT l FROM Libro l JOIN FETCH l.autor")
    List<Libro> obtenerAutoresDeLibros();

    @Query("SELECT DISTINCT l FROM Libro l JOIN FETCH l.autor a WHERE a.fechaDeNacimiento <= :anio AND (a.fechaDeMuerte IS NULL OR a.fechaDeMuerte >= :anio)")
    List<Libro> obtenerAutoresVivosDurante(@Param("anio") int anio);

    @Query("SELECT l FROM Libro l JOIN FETCH l.idiomas WHERE :idioma MEMBER OF l.idiomas")
    List<Libro> obtenerLibrosPorIdioma(@Param("idioma") String idioma);
}
