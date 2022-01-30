package com.company.Models;



public class Armazem extends LocalX{

    private int capacidade , stock;

    public Armazem(String name, String tipo, int capacidade, int stock){
        super(name, tipo);
        this.capacidade = capacidade;
        this.stock = stock;
    }


    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public int getStock() {
        return stock;
    }

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
