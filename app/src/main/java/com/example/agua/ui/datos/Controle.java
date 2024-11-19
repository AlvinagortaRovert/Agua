package com.example.agua.ui.datos;

public class Controle {
    private int id;
    private String control;
    private String unidad;
    private String valormin;
    private String valormax;
    private String privado;

    public Controle() {}

    public Controle(int id, String control, String unidad, String valormin, String valormax, String privado) {
        this.id = id;
        this.control = control;
        this.unidad = unidad;
        this.valormin = valormin;
        this.valormax = valormax;
        this.privado = privado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getValormin() {
        return valormin;
    }

    public void setValormin(String valormin) {
        this.valormin = valormin;
    }

    public String getValormax() {
        return valormax;
    }

    public void setValormax(String valormax) {
        this.valormax = valormax;
    }

    public String getPrivado() {
        return privado;
    }

    public void setPrivado(String privado) {
        this.privado = privado;
    }

    @Override
    public String toString() {
        return control;
    }
}
