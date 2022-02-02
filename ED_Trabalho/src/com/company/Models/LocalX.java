package com.company.Models;

public abstract class LocalX {

    private String local_name, type;


    public LocalX(String name, String type){
        this.local_name = name;
        this.type = type;
    }

    public LocalX() {

    }

    /**
     * Método para obter o tipo de local
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Método para alterar o nome do local
     * @param local_name
     */
    public void setLocal_name(String local_name) {
        this.local_name = local_name;
    }

    /**
     * Método para alterar o tipo de local
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Método para obter o nome do local
     * @return local_name
     */
    public String getLocal_name() {
        return local_name;
    }

    @Override
    public String toString() {
        return "LocalX{" +
                "local_name='" + local_name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
