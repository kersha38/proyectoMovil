package com.example.carlos.proyectomascotas.modelo;

import java.io.Serializable;

public class Configuration implements Serializable {

    private Double cantidadAgua, cantidadComida;
    private String url;
    private String hora, fecha;

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {

        return hora;
    }

    public String getFecha() {
        return fecha;
    }

    public Configuration(Double cantidadAgua, Double cantidadComida, String url) {
        this.cantidadAgua = cantidadAgua;
        this.cantidadComida = cantidadComida;
        this.url = url;
    }

    public Double getCantidadAgua() {
        return cantidadAgua;
    }

    public void setCantidadAgua(Double cantidadAgua) {
        this.cantidadAgua = cantidadAgua;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "cantidadAgua=" + cantidadAgua +
                ", cantidadComida=" + cantidadComida +
                ", url='" + url + '\'' +
                '}';
    }

    public Double getCantidadComida() {
        return cantidadComida;
    }

    public void setCantidadComida(Double cantidadComida) {
        this.cantidadComida = cantidadComida;
    }
}
