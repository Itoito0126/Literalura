package com.DesafioAlura.Literalura.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<DatosAutor> autor;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "libro_idiomas", joinColumns = @JoinColumn(name = "libro_id"))
    @Column(name = "idiomas")
    private List<String> idiomas;
    private Integer numeroDeDescargas;

    public Libro(){}

    public Libro(DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        this.autor = datosLibro.autor();
        this.idiomas = datosLibro.idiomas();
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
    }

    @Override
    public String toString() {
        return
                "Titulo= '" + titulo + '\'' +
                ", Autor= " + autor + '\'' +
                ", Idiomas= " + idiomas + '\'' +
                ", NumeroDeDescargas= " + numeroDeDescargas + '\'';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<DatosAutor> getAutor() {
        return autor;
    }

    public void setAutor(List<DatosAutor> autor) {
        this.autor = autor;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Integer numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }
}
