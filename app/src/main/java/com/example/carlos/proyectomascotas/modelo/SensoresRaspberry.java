package com.example.carlos.proyectomascotas.modelo;

import java.io.Serializable;

public class SensoresRaspberry implements Serializable{

    private String agua;
    private String comida;
    private String luz;
    private String hora;
    private String fecha;

    public SensoresRaspberry(String agua, String comida, String luz, String hora, String fecha) {
        this.agua = agua;
        this.comida = comida;
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

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}

