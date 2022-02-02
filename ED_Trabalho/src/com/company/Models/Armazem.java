package com.company.Models;



public class Armazem extends LocalX{

    private int capacidade , stock;

    public Armazem(String name, String tipo, int capacidade, int stock){
        super(name, tipo);
        this.capacidade = capacidade;
        this.stock = stock;
    }

    /**
     * Método para alterar capacidade do armazém
     * @param capacidade
     */
    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    /**
     * Método para alterar o stock do armzem
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Método para obter capacidade do arzazem
     * @return capacidade
     */
    public int getCapacidade() {
        return capacidade;
    }

    /**
     * Método para obter o stock do armzém
     * @return stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * print do armzém
     * @return string
     */
    @Override
    public String toString() {
        return "Armazem{" +
                "nome:" + getLocal_name() +
                ", tipo: " + getType() +
                ", capacidade= " + capacidade +
                ", stock= " + stock +
                '}';
    }
}
