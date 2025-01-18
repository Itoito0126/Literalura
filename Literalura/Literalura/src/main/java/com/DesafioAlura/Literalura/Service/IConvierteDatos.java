package com.DesafioAlura.Literalura.Service;

public interface IConvierteDatos {
    <T> T ObtenerDatos (String json, Class<T> Clase);
}