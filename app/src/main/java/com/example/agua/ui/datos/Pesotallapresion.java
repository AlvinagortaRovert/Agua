package com.example.agua.ui.datos;

public class Pesotallapresion {
    String id;
    private String fecha;
    private String valor1;
    private String Valor2;


    public Pesotallapresion() {
    }

    public Pesotallapresion(String id, String fecha, String valor1, String valor2) {
        this.id = id;
        this.fecha = fecha;
        this.valor1 = valor1;
        Valor2 = valor2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getValor1() {
        return valor1;
    }

    public void setValor1(String valor1) {
        this.valor1 = valor1;
    }

    public String getValor2() {
        return Valor2;
    }

    public void setValor2(String valor2) {
        Valor2 = valor2;
    }


    @Override
    public String toString() {
        return id;
    }
}
