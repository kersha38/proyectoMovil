package com.example.carlos.proyectomascotas.modelo;

import java.io.Serializable;

public class Configuration implements Serializable {

    private String agua;
    private String comida;
    private String url;
    private String luz;
    private String hora;
    private String fecha;

    public Configuration(String agua, String comida, String url, String luz, String hora, String fecha) {
        this.agua = agua;
        this.comida = comida;
        this.url = url;
        this.luz = luz;
        this.hora = hora;
        this.fecha = fecha;
    }

    public String getAgua() {
        return agua;
    }

    public void setAgua(String agua) {
        this.agua = agua;
    }

    public String getComida() {
        return comida;
    }

    public void setComida(String comida) {
        this.comida = comida;
    }

    public String getLuz() {
        return luz;
    }

    public void setLuz(String luz) {
        this.luz = luz;
    }

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


    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "agua=" + agua +
                ", comida=" + comida +
                ", luz='" + luz + '\'' +
                ", hora='" + hora + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
