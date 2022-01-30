package com.company.Models;

public class Caminho {

    private String de;
    private String para;
    private double distancia;

    public Caminho(String de, String para){
        this.de = de;
        this.para = para;
    }

    public Caminho(String de, String para, double distancia){
        this.de = de;
        this.para = para;
        this.distancia = distancia;
    }

    public Caminho(String caminho1) {

    }

    public double getDistancia() {
        return distancia;
    }

    public String getDe() {
        return de;
    }

    public String getPara() {
        return para;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public void setPara(String para) {
        this.para = para;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    @Override
    public String toString() {
        return "Caminho{" +
                "de='" + de + '\'' +
                ", para='" + para + '\'' +
                ", distancia=" + distancia +
                '}';
    }
}
