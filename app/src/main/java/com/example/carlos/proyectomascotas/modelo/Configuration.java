package com.example.carlos.proyectomascotas.modelo;

import java.io.Serializable;

public class Configuration implements Serializable {

    public Double getAgua() {
        return agua;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "agua=" + agua +
                ", comida=" + comida +
                '}';
    }

    public void setAgua(Double agua) {
        this.agua = agua;
    }

    public Double getComida() {
        return comida;
    }

    public void setComida(Double comida) {
        this.comida = comida;
    }

    private Double agua;
    private Double comida;

    public Configuration(Double agua, Double comida) {
        this.agua = agua;
        this.comida = comida;
    }
}
