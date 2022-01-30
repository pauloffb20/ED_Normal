package com.company.Models;
import com.company.Estruturas.ArrayUnorderedList;


public class Vendedor {

    private String nome;
    private long id, capacidade;
    private ArrayUnorderedList<String> mercados = new ArrayUnorderedList<>();

    public Vendedor(long id, String name, long capacidade, ArrayUnorderedList<String> mercados) {
        this.id = id;
        this.nome = name;
        this.capacidade = capacidade;
        this.mercados = mercados;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(long capacidade) {
        this.capacidade = capacidade;
    }

    public ArrayUnorderedList<String> getMercados() {
        return mercados;
    }

    public void setMercados(ArrayUnorderedList<String> mercados) {
        this.mercados = mercados;
    }

    @Override
    public String toString() {
        String s = "";
        s += "nome:'" + nome + '\'' +
                ", id:" + id +
                ", capacidade:" + capacidade +
                ", mercados:";

        for (String mercado: mercados) {
            s += "\n" + mercado;
        }

        return s;


    }
}
