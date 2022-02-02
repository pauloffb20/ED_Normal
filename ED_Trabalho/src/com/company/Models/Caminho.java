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

    /**
     * Método para obter distancia entre dois pontos
     * @return distancia
     */
    public double getDistancia() {
        return distancia;
    }

    /**
     * Método para obter o ponto de saida(de)
     * @return de
     */
    public String getDe() {
        return de;
    }

    /**
     * Método para obter o ponto de destino
     * @return para
     */
    public String getPara() {
        return para;
    }

    /**
     * Alterar o ponto inicial do caminho
     * @param de
     */
    public void setDe(String de) {
        this.de = de;
    }

    /**
     * Alterar o ponto de final do caminho
     * @param para
     */
    public void setPara(String para) {
        this.para = para;
    }

    /**
     * Alterar a distancia do caminho
     * @param distancia
     */
    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    /**
     * Print do caminho
     * @return string
     */
    @Override
    public String toString() {
        return "Caminho{" +
                "de='" + de + '\'' +
                ", para='" + para + '\'' +
                ", distancia=" + distancia +
                '}';
    }
}
