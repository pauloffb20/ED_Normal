package com.company.Models;

public abstract class LocalX {

    private String local_name, type;


    public LocalX(String name, String type){
        this.local_name = name;
        this.type = type;
    }

    public LocalX() {

    }

    public String getType() {
        return type;
    }

    public void setLocal_name(String local_name) {
        this.local_name = local_name;
    }

    public void setType(String type) {
        this.type = type;
    }

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
