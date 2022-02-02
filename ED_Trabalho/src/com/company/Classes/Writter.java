package com.company.Classes;
import com.company.Estruturas.ArrayUnorderedList;
import com.company.Models.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class Writter {
    Gson gson = new Gson();
     private GestaoEmpresa gestaoEmpresa;
     private Gestor gestor;
     private String fileNamePath  = "ED_Trabalho/src/com/company/Exports/vendedores.json",
            storagePath = "ED_Trabalho/src/com/company/Exports/Storage.json", enterprise = "ED_Trabalho/src/com/company/Exports/enterprise.json", marketPath = "ED_Trabalho/src/com/company/Exports/Market.json";

     public Writter(){ }

    public Writter(GestaoEmpresa gestaoEmpresa){
        this.gestaoEmpresa =  gestaoEmpresa;
        this.gestor = new Gestor(gestaoEmpresa.getVendedors());
    }

    private class ExportEnterprise{

         private ArrayUnorderedList<Vendedor> vendedores;
         private Object[] locais;
         private ArrayUnorderedList<Caminho> caminhos;

        public ExportEnterprise(ArrayUnorderedList<Vendedor> vendedores, Object[] locais, ArrayUnorderedList<Caminho> caminhos) {
            this.vendedores = vendedores;
            this.locais = locais;
            this.caminhos = caminhos;
        }
    }

    /**
     * Escrever no ficheiro o dados da empresa
     * @param p
     * @throws IOException
     */
    public void appendEnterprise(GestaoEmpresa p) throws IOException {
        Object[] locais = p.getNetworkX().getVertices();
        ExportEnterprise exportEnterprise = new ExportEnterprise(p.getVendedors(), locais, p.getPaths());
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        FileWriter writer = new FileWriter(this.enterprise);
        gson.toJson(exportEnterprise, writer);
        writer.flush();
        writer.close();
    }

    /**
     * Escrever o vendedor escolhido no ficheiro
     * @param p
     * @throws IOException
     */
    public void appendPersonToFile(Vendedor p) throws IOException {
            Vendedor vendedor = p;
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            FileWriter writer = new FileWriter(this.fileNamePath);
            gson.toJson(vendedor, writer);
            writer.flush();
            writer.close();
    }


    /**
     * Escrever no ficheiro um armazem escolhido
     * @param local
     * @throws IOException
     */
    public void appendStorage(Armazem local) throws IOException {
        Armazem local1 = local;
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        FileWriter writer = new FileWriter(this.storagePath);
        gson.toJson(local1, writer);
        writer.flush();
        writer.close();
    }

    /**
     * Escrever um mercado escolhido no ficheiro
     * @param local
     * @throws IOException
     */
    public void appendMarket(Mercado local) throws IOException {
        Mercado local1 = local;
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        FileWriter writer = new FileWriter(this.marketPath);
        gson.toJson(local1, writer);
        writer.flush();
        writer.close();
    }

}
