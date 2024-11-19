package com.example.agua.ui.datos;

public class Reportevigilante {
    private String   idvigilancia;

    private String fecha;
    private String valor;
    private String control;
    private String unidades;
    private String valormin;
    private String valormax;



    public Reportevigilante() {

    }

    public Reportevigilante(String idvigilancia, String fecha, String valor, String control, String unidades, String valormin, String valormax) {
        this.idvigilancia = idvigilancia;
        this.fecha = fecha;
        this.valor = valor;
        this.control = control;
        this.unidades = unidades;
        this.valormin = valormin;
        this.valormax = valormax;
    }


    public String getIdvigilancia() {
        return idvigilancia;
    }

    public void setIdvigilancia(String idvigilancia) {
        this.idvigilancia = idvigilancia;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getUnidades() {
        return unidades;
    }

    public void setUnidades(String unidades) {
        this.unidades = unidades;
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

    @Override
    public String toString() {
        return control;
    }


}

