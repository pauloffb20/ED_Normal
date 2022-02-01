package com.company.Estruturas;

public class Prioridade<T> {
    private T object;
    double prioridade;

    public Prioridade(T objectTemp, double prioridadeTemp){
        this.object = objectTemp;
        this.prioridade = prioridadeTemp;
    }

    public T getObject() {
        return object;
    }

    public double getPrioridade() {
        return prioridade;
    }


}