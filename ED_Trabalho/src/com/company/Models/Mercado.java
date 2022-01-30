package com.company.Models;

import com.company.Estruturas.ArrayUnorderedList;

public class Mercado extends LocalX {

     private ArrayUnorderedList<Integer> clientes;

    public Mercado(String name, String tipo, ArrayUnorderedList<Integer> clientes){
        super(name, tipo);
        this.clientes = clientes;
    }

    public ArrayUnorderedList<Integer> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayUnorderedList<Integer> clientes) {
        this.clientes = clientes;
    }

    @Override
    public String toString() {
        String s = "Mercado{" + "nome:" + getLocal_name() + ", tipo:" + getType()+
                ", clientes=" ;

        for(Integer i : clientes){
            s += i.toString() + " ";
        }
        return s;
    }
}
