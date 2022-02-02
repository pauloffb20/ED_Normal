package com.company.Classes;
import com.company.Models.*;
import com.company.Estruturas.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

public class GestaoEmpresa {
    private ArrayUnorderedList<Vendedor> vendedores;
    //private ArrayUnorderedList<Local> locais;
    private ArrayUnorderedList<Caminho> paths;
    private Gestor gestor;
    private Network<LocalX> networkX;
    private Writter writter;
    private GestaoCaminhos gestaoCaminhos;

    public GestaoEmpresa() {
        this.vendedores = new ArrayUnorderedList<>();
        //this.locais = new ArrayUnorderedList<>();
        this.paths = new ArrayUnorderedList<>();
        this.networkX = new Network<>();
        this.gestor = new Gestor(vendedores);
        this.writter = new Writter();
        this.gestaoCaminhos = new GestaoCaminhos(networkX);
    }

    /**
     * Adicionar vendedor á lista
     * @param m
     * @throws NoComparableException
     */
    public void addVendedor(Vendedor m) throws NoComparableException {
        this.vendedores.addToRear(m); }

    /**
     * Adicionar caminho á lista
     * @param c
     */
    public void addCaminho(Caminho c){
        this.paths.addToRear(c);
    }

    /**
     * Export da empresa
     * @param gestaoEmpresa
     * @throws IOException
     */
    public void exportEnterprise(GestaoEmpresa gestaoEmpresa) throws IOException {
        GestaoEmpresa gestaoEmpresa1 = gestaoEmpresa;
        writter.appendEnterprise(gestaoEmpresa1);
    }
    public ArrayUnorderedList<Caminho> getPaths() {
        return paths;
    }

    /**
     * Obter vendedores da empresa
     * @return vendedores
     */
    public ArrayUnorderedList<Vendedor> getVendedors() {
        return vendedores;
    }

    /**
     * Ler o json e preencher empresa com os dados nele contidos
     * @param path
     * @throws IOException
     * @throws ParseException
     * @throws NoComparableException
     */
    public void readJson(String path) throws IOException, ParseException, NoComparableException {
        try (FileReader reader = new FileReader(ClassLoader.getSystemResource(path).getFile())) {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONArray lang = (JSONArray) jsonObject.get("vendedores");
            JSONArray locals = (JSONArray) jsonObject.get("locais");
            JSONArray caminhos = (JSONArray) jsonObject.get("caminhos");

            //Users
            Iterator i = lang.iterator();
            while (i.hasNext()) {
                JSONObject innerObj = (JSONObject) i.next();
                long ID = (long) innerObj.get("id");
                String nome = (String) innerObj.get("nome");
                long capacity = (long) innerObj.get("capacidade");
                JSONArray jsonArray = (JSONArray) innerObj.get("mercados_a_visitar");
                ArrayUnorderedList<String> mercados = new ArrayUnorderedList<>();

                if (jsonArray != null)
                    for (int l = 0; l < jsonArray.size(); l++) {
                        String mercado = jsonArray.get(l).toString();
                        mercados.addToRear(mercado);
                    }
                else {
                    mercados.addToRear("null");
                }
                Vendedor vendedor = new Vendedor(ID, nome, capacity, mercados);
                this.addVendedor(vendedor);
            }

            //locais
            Iterator j = locals.iterator();
            while (j.hasNext()) {

                JSONObject newObj = (JSONObject) j.next();
                String name = (String) newObj.get("nome");
                String tipo = (String) newObj.get("tipo");

                if (tipo.equals("Armazém")) {
                    long capacidade = (long) newObj.get("capacidade");
                    long stock = (long) newObj.get("stock");
                    Armazem armazem = new Armazem(name, tipo, (int) capacidade, (int) stock);
                    this.networkX.addVertex(armazem);
                } else if (tipo.equals("Mercado")) {
                    JSONArray jsonArray = (JSONArray) newObj.get("clientes");
                    ArrayUnorderedList<Integer> clienteslist = new ArrayUnorderedList<>();

                    if (jsonArray != null) {
                        for (int l = 0; l < jsonArray.size(); l++) {
                            int clientNr = Integer.parseInt(jsonArray.get(l).toString());
                            clienteslist.addToRear(clientNr);
                        }
                    } else {
                        clienteslist.addToRear(0);
                    }

                    Mercado mercado = new Mercado(name, tipo, clienteslist);
                    this.networkX.addVertex(mercado);
                } else {
                    Sede sede = new Sede(name, tipo);
                    this.networkX.addVertex(sede);
                }
            }

            //caminhos
            Iterator k = caminhos.iterator();
            while (k.hasNext()) {
                JSONObject newObj = (JSONObject) k.next();
                String de = (String) newObj.get("de");
                String para = (String) newObj.get("para");
                long distancia = (long) newObj.get("distancia");
                Caminho caminho = new Caminho(de, para, (int) distancia);

                Object[] locais = networkX.getVertices();
                LocalX inicio = findLocalByName(de);
                LocalX fim = findLocalByName(para);

                for (int t = 0; t < networkX.size(); t++) {
                    LocalX atual = (LocalX) locais[t];
                    if (atual.getLocal_name().equals(de)) {
                        inicio = atual;
                    } else if (atual.getLocal_name().equals(para)) {
                        fim = atual;
                    }
                }

                if (inicio.getLocal_name() != null && fim.getLocal_name() != null) {
                    networkX.addEdge(inicio, fim, distancia);
                }
                this.addCaminho(caminho);
            }
        }
    }

    /**
     * Obter a network da empresa
     * @return networkX
     */
    public Network<LocalX> getNetworkX(){
        return networkX;
    }

    /**
     * Print da empresa
     * @return s
     */
    @Override
    public String toString() {
        String s = "Vendedores:\n";
        s += "*-------------------------------*\n";
        for (Vendedor vendedor: vendedores) {
            s += "\n" + vendedor.toString() + "\n";
        }
        s += "\n" + "*-------------------------------*\n";
        s += "Locais: \n";
        for (int i = 0; i < networkX.size(); i++){
            s += "\n" + networkX.getVertex(i) + "\n";
        }
        s += "\n" + "*-------------------------------*\n";

        for (Caminho caminho: paths) {
            s += "\n" + caminho.toString() + "\n";
        }

        return s;
    }

    /**
     * Método para chamar o menu de armazem
     */
    public void AddOrSetStorage() {
        GestaoArmazem gestaoArmazem = new GestaoArmazem(networkX);
        gestaoArmazem.AddOrSetStorage();
    }

    /**
     * Método para chamar menu de mercado
     */
    public void AddOrSetMarkets() {
        GestaoMercados gestaoMercados = new GestaoMercados(networkX);
        gestaoMercados.AddOrSetMarkets();
    }

    /**
     * Método para print de mercados
     */
    public void printMarkets(){
        GestaoMercados gestaoMercados = new GestaoMercados(networkX);
        gestaoMercados.printMarketsToShow();
    }

    /**
     * Método para print dos armazens
     */
    public void printStorages(){
        GestaoArmazem gestaoArmazem = new GestaoArmazem(networkX);
        gestaoArmazem.printStoragesToShow();
    }

    /**
     * Método para adicionar um caminho na lista e network
     */
    public void addEdge(){
        GestaoCaminhos gestaoCaminhos = new GestaoCaminhos(networkX);
        Caminho caminho = gestaoCaminhos.addEdge();
        paths.addToRear(caminho);
    }

    /**
     * Método para remover um caminho da lista e da network
     * @throws EmptyException
     * @throws NotFoundException
     */
    public void removeEdge() throws EmptyException, NotFoundException {
        GestaoCaminhos gestaoCaminhos = new GestaoCaminhos(networkX);
        Caminho caminho = gestaoCaminhos.removeEdge();
        boolean flag = false;

        for(int i = 0; i< paths.size(); i++){
                if(caminho.getDe().equals(paths.getIndex(i).getDe()) &&
                    caminho.getPara().equals(paths.getIndex(i).getPara())) {
                    paths.removeByIndex(i);
                    flag = true;
                }
            }

        if(flag == true){
            System.out.println("apagado");
        } else {
            System.out.println("caminho inexistente");
        }
    }

    /**
     * Encontrar um local pelo nome
     * @param nome
     * @return local ou null caso não exista/encontre
     */
    public LocalX findLocalByName(String nome){
        Object[] vertices = networkX.vertices;
        for(Object o : vertices){
            LocalX local = (LocalX) o;
            if(local.getLocal_name().equals(nome)){
                return local;
            }
        }
        return null;
    }

    /**
     * Listagem de mercados, armazens ou vendedores da empresa
     */
    public void seeMarketsOrStorages(){
        int choice;
        System.out.println("Qual quer ver?");
        System.out.println("1- Mercados");
        System.out.println("2- Armazens");
        System.out.println("3- Vendedores");
        Scanner input = new Scanner(System.in);
        choice = Integer.parseInt(input.next());
        while (choice != 4) {
            switch (choice) {
                case 1:
                    printMarkets();
                    choice = 5;
                    break;
                case 2:
                    printStorages();
                    choice = 5;
                    break;
                case 3:
                    gestor.printSellersToShow();
                    choice = 5;
                    break;
                default:
                    return;
            }
        }
    }

    /**
     * Print da lista de caminhos
     * @return
     */
    public String printPaths(){
        String s = "";
        for (Caminho caminho: paths) {
            s += "\n" + caminho.toString() + "\n";
        }
        return s;
    }
}
