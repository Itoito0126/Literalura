package com.DesafioAlura.Literalura.Main;

import com.DesafioAlura.Literalura.Model.Datos;
import com.DesafioAlura.Literalura.Model.DatosAutor;
import com.DesafioAlura.Literalura.Model.DatosLibro;
import com.DesafioAlura.Literalura.Model.Libro;
import com.DesafioAlura.Literalura.Repositorio.LibroRepository;
import com.DesafioAlura.Literalura.Service.ConsumoAPI;
import com.DesafioAlura.Literalura.Service.ConvierteDatos;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private Scanner Teclado = new Scanner(System.in);
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosLibro> datosLibros = new ArrayList<>();
    private LibroRepository repositorio;

    public Main(LibroRepository repository){
        this.repositorio = repository;
    }

    public void MostrarMenu(){

        var Op = -1;
        while (Op != 0){
            System.out.println("\n***********************");
            System.out.println("1 - Buscar libro");
            System.out.println("2 - Listar libros registrados");
            System.out.println("3 - Listar autores registrados");
            System.out.println("4 - Listar autores vivos en determinado año");
            System.out.println("5 - Listar libros por idioma");
            System.out.println("6 - Estadisticas");
            System.out.println("7 - TOP 10 libros más descargados");
            System.out.println("0 - Salir");

            Op = Teclado.nextInt();
            Teclado.nextLine();

            switch (Op){
                case 1:
                    BuscarLibro();
                    break;
                case 2:
                    MostrarLibrosBuscados();
                    break;
                case 3:
                    MostrarAutores();
                    break;
                case 4:
                    MostrarAutoresVivos();
                    break;
                case 5:
                    MostrarLibrosIdioma();
                    break;
                case 6:
                    MostrarEstadisticas();
                    break;
                case 7:
                    MostrarTOP10();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private DatosLibro getDatosLibro(){
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var TituloLibro = Teclado.nextLine();
        var json = ConsumoAPI.ObtenerDatos(URL_BASE + "?search=" + TituloLibro.replace(" ", "+"));

        Datos datos = conversor.ObtenerDatos(json, Datos.class);

        if (datos.resultados() != null && !datos.resultados().isEmpty()) {
            DatosLibro libro = datos.resultados().get(0);

            try {
                boolean Existe = repositorio.existsByTitulo(TituloLibro);
                System.out.println(Existe);

                if (Existe){
                    System.out.println("El libro '" + libro.titulo() + "' ya se encuentra en la base de datos.");
                    return null;
                }
                else {
                    System.out.println("El libro '" + libro.titulo() + "' fue encontrado.");
                    return libro;
                }
            }
            catch (DataIntegrityViolationException e) {
                System.out.println("Error: El libro '" + libro.titulo() + "' ya existe en la base de datos.");
                return null;
            }
        }
        else {
            System.out.println("No se encontraron resultados.");
            return null;
        }
    }

    private void BuscarLibro(){
        DatosLibro datos = getDatosLibro();
        if (datos == null) {
            System.out.println("Operación cancelada. Regresando al menú principal...");
            return;
        }
        Libro libro = new Libro(datos);
        repositorio.save(libro);
        datosLibros.add(datos);

        System.out.println("\n-----LIBRO-----");
        System.out.println("Titulo: " + libro.getTitulo());
        System.out.println("Autor(es): " + libro.getAutor().stream().map(DatosAutor::nombre).collect(Collectors.joining(", ")));
        System.out.println("Idiomas: " + libro.getIdiomas());
        System.out.println("Numero de descargas: " + libro.getNumeroDeDescargas());
        System.out.println("---------------");
    }

    private void MostrarLibrosBuscados() {

        List<Libro> libros = repositorio.findAll();
        for (Libro libro : libros) {
            System.out.println("\n-----LIBRO-----");
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor(es): " + String.join(", ", libro.getAutor().stream().map(DatosAutor::nombre).toList()));
            System.out.println("Idiomas: " + String.join(", ", libro.getIdiomas()));
            System.out.println("Número de descargas: " + libro.getNumeroDeDescargas());
            System.out.println("---------------");
        }
    }

    private void MostrarAutores() {
        List<Libro> libros1 = repositorio.obtenerAutoresDeLibros();
        if (libros1 != null) {
            for (Libro libro : libros1) {
                for (DatosAutor autor : libro.getAutor()){
                    System.out.println("\n-----AUTOR-----");
                    System.out.println("Nombre: " + autor.nombre());
                    System.out.println("Fecha de nacimiento: " + autor.fechaDeNacimiento());
                    System.out.println("Fecha de fallecimiento: " + autor.fechaDeMuerte());
                    System.out.println("Título del libro: " + libro.getTitulo());
                    System.out.println("-----------------");
                }
            }
        } else {
            System.out.println("No se encontraron autores.");
        }
    }

    private void MostrarAutoresVivos() {
        System.out.println("Ingrese el año para buscar autores que estaban vivos:");
        int anio = Teclado.nextInt();
        Teclado.nextLine();

        List<Libro> libros2 = repositorio.obtenerAutoresVivosDurante(anio);

        if (libros2.isEmpty()) {
            System.out.println("No se encontraron autores vivos durante el año " + anio + ".");
        } else {
            System.out.println("Autores vivos durante el año " + anio + ":");
            for (Libro libro : libros2) {
                for (DatosAutor autor : libro.getAutor()){
                    System.out.println("\n-----AUTOR-----");
                    System.out.println("Autor: " + autor.nombre());
                    System.out.println("Fecha de nacimiento: " + autor.fechaDeNacimiento());
                    System.out.println("Fecha de fallecimiento: " + (autor.fechaDeMuerte() == null ? "N/A" : autor.fechaDeMuerte()));
                    System.out.println("Libros: " + libro.getTitulo());
                    System.out.println("-----------------");
                }
            }
        }
    }

    private void MostrarLibrosIdioma() {

        System.out.println("Ingrese el idioma para buscar los libros");
        System.out.println("es - Español");
        System.out.println("en - Inglés");
        System.out.println("fr - Francés");
        System.out.println("pt - Portugués");
        String idioma = Teclado.nextLine().toLowerCase();

        if (!List.of("es", "en", "fr", "pt").contains(idioma)) {
            System.out.println("Idioma no válido. Inténtelo nuevamente.");
            return;
        }

        List<Libro> libros = repositorio.obtenerLibrosPorIdioma(idioma);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros disponibles en el idioma '" + idioma + "'.");
        } else {
            System.out.println("Libros disponibles en el idioma '" + idioma + "':");
            for (Libro libro : libros) {
                System.out.println("\n-----LIBRO-----");
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor(es): " + String.join(", ", libro.getAutor().stream().map(DatosAutor::nombre).toList()));
                System.out.println("Idiomas: " + String.join(", ", libro.getIdiomas()));
                System.out.println("Número de descargas: " + libro.getNumeroDeDescargas());
                System.out.println("-----------------");
            }
        }
    }

    private void MostrarEstadisticas() {

        List<Libro> libros = repositorio.findAll();

        DoubleSummaryStatistics EST = libros.stream()
                .filter(l -> l.getNumeroDeDescargas() > 0)
                .collect(Collectors.summarizingDouble(Libro::getNumeroDeDescargas));
        System.out.println("\n-----ESTADÍSTICAS-----");
        System.out.println("Cantidad media de descargas: " + EST.getAverage());
        System.out.println("Cantidad máxima de descargas: " + EST.getMax());
        System.out.println("Cantidad minima de descargas: " + EST.getMin());
        System.out.println("----------------------");
    }

    private void MostrarTOP10() {
        List<Libro> libros = repositorio.findAll();

        System.out.println("\n-----TOP 10-----");
        libros.stream()
                .sorted(Comparator.comparing(Libro::getNumeroDeDescargas).reversed())
                .limit(10)
                .forEach(l -> System.out.println(l.getTitulo() + " - " + l.getNumeroDeDescargas()));
        System.out.println("----------------");
    }
}