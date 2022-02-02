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

    /**
     * Método para obter nome do vendedor
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Método para alterar o nome do vendedor
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Método para obter a capacidade do vendedor
     * @return capacidade
     */
    public long getCapacidade() {
        return capacidade;
    }

    /**
     * Método para alterar a capacidade do vendedor
     * @param capacidade
     */
    public void setCapacidade(long capacidade) {
        this.capacidade = capacidade;
    }

    /**
     * Método para obter lista de mercados a visitar
     * @return mercados
     */
    public ArrayUnorderedList<String> getMercados() {
        return mercados;
    }

    /**
     * Método para alterar a lista de mercados a visitar
     * @param mercados
     */
    public void setMercados(ArrayUnorderedList<String> mercados) {
        this.mercados = mercados;
    }

    /**
     * print do vendedor
     * @return s
     */
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
