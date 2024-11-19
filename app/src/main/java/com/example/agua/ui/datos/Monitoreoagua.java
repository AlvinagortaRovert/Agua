package com.example.agua.ui.datos;

public class Monitoreoagua {

    String id;
    private String fecha;
    private String valor1;
    private String Valor2;
    private String valor3;
    private String Valor4;
    private String valor5;

    public Monitoreoagua() {

    }

    public Monitoreoagua(String id, String fecha, String valor1, String valor2, String valor3, String valor4, String valor5) {


        this.id = id;
        this.fecha = fecha;
        this.valor1 = valor1;
        Valor2 = valor2;
        this.valor3 = valor3;
        Valor4 = valor4;
        this.valor5 = valor5;
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

    public String getValor3() {
        return valor3;
    }

    public void setValor3(String valor3) {
        this.valor3 = valor3;
    }

    public String getValor4() {
        return Valor4;
    }

    public void setValor4(String valor4) {
        Valor4 = valor4;
    }

    public String getValor5() {
        return valor5;
    }

    public void setValor5(String valor5) {
        this.valor5 = valor5;
    }

    @Override
    public String toString() {
        return id;
    }
}
