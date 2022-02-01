package com.company.Classes;
import com.company.Models.LocalX;
import com.company.Models.Vendedor;
import com.company.Estruturas.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Scanner;

public class Menu {
    private String docFileName = null;
    private GestaoEmpresa gestaoEmpresa;
    private Gestor gestor;
    private Writter writter;
    private GestaoVendedores gestaoVendedores;
    private GestãoArmazem gestãoArmazem;
    private GestaoMercados gestaoMercados;
    private GestaoCaminhos gestaoCaminhos;
    private Simulacao simulacao;

    public Menu() {
        this.gestaoEmpresa = new GestaoEmpresa();
        this.gestor = new Gestor(gestaoEmpresa.getVendedors());
        this.writter = new Writter(gestaoEmpresa);
        this.gestaoVendedores = new GestaoVendedores(gestaoEmpresa);
        this.gestãoArmazem = new GestãoArmazem(gestaoEmpresa.getNetworkX());
        this.gestaoMercados = new GestaoMercados(gestaoEmpresa.getNetworkX());
        this.gestaoCaminhos = new GestaoCaminhos(gestaoEmpresa);
        this.simulacao = new Simulacao(gestaoEmpresa.getNetworkX());
    }

    public void run() throws IOException, InvalidIndexException, EmptyException, NotFoundException, ParseException, NoComparableException, EmptyCollectionException, org.json.simple.parser.ParseException {
        int choice = 0;
        System.out.println("Welcome!");
        while (choice != 11) {
            choice = initialMenu();
            switch (choice) {
                case 1:
                    isDocRead();
                    gestaoEmpresa.readJson(docFileName);
                    break;
                case 2:
                    System.out.println(gestaoEmpresa.toString());
                    break;
                case 3:
                    alterarDados();
                    break;
                case 4:
                    gestaoEmpresa.seeMarketsOrStorages();
                    break;
                case 5:
                    printGraph();
                    break;
                case 6:
                    menuSimulacao();
                    break;
                case 7:
                    exports();
                    break;
                default:
            }
        }
    }

    private boolean isDocRead() throws NoComparableException, IOException, EmptyCollectionException {
        boolean isread = false;

        if (this.docFileName == null) {
            String mapFileName = readDoc();
            this.docFileName = "Documents/" + mapFileName;
            isread = true;
        }
        return isread;
    }

    private String readDoc() {
        ArrayUnorderedList<String> results = new ArrayUnorderedList<>();
        int counter = 1;
        System.out.println("Choose a file");

        File[] files = new File("ED_Trabalho/src/Documents").listFiles();

        for (File file : files) {
            if (file.isFile()) {
                results.addToRear(file.getName());
                System.out.println(counter + " - " + file.getName());
                counter++;
            }
        }
        int mapFile;
        Scanner inputMoves = new Scanner(System.in);
        mapFile = inputMoves.nextInt();
        if (mapFile <= results.size()) {
            System.out.println("Valid file!");
        } else {
            return "NotFound";
        }
        return results.getIndex(mapFile - 1);
    }

    public Integer initialMenu() {
        Scanner input = new Scanner(System.in);
        String choice;
            System.out.println("Escolha uma das opções:");
            System.out.println("-------------------------\n");
            System.out.println("1 - Importar documento");
            System.out.println("2 - Ver info da empresa");
            System.out.println("3 - Atualizar/adicionar dados");
            System.out.println("4 - Ver mercados ,armazéns ou vendedores");
            System.out.println("5 - Mostrar Network");
            System.out.println("6- Simulação de trajeto(s)");
            System.out.println("7 - Exports");
            choice = input.next();
        return Integer.valueOf(choice);
    }

    public void printGraph() throws EmptyException {
        Network<LocalX> network;
        network = gestaoEmpresa.getNetworkX();
        Iterator<LocalX> i = network.iteratorBFS(0);
        while(i.hasNext()){
            System.out.println(i.next());
        }
    }

    public void alterarDados() throws EmptyException, IOException, NotFoundException, NoComparableException {
        int choice;
        System.out.println("Qual item quer alterar/adicionar?");
        System.out.println("1- Caminhos");
        System.out.println("2- Vendedores");
        System.out.println("3- Armazéns");
        System.out.println("4- Mercados");
        Scanner input = new Scanner(System.in);
        choice = Integer.parseInt(input.next());
        while (choice != 5) {
            switch (choice) {
                case 1:
                    gestaoCaminhos.pathMenu();
                    choice = 5;
                    break;
                case 2:
                    gestaoVendedores.sellerMenu();
                    choice = 5;
                    break;
                case 3:
                    gestaoEmpresa.AddOrSetStorage();
                    choice = 5;
                    break;
                case 4:
                    gestaoEmpresa.AddOrSetMarkets();
                    choice = 5;
                    break;
                default:
                    return;
            }
        }
    }

    public void menuSimulacao() throws EmptyException {
        int choice, choice2;
        System.out.println("Simulação de trajeto");
        System.out.println("1- Escolha do vendedor");
        System.out.println("2- Todos vendedores");
        System.out.println("3- Ver caminhos");
        Scanner input = new Scanner(System.in);
        choice = Integer.parseInt(input.next());
        while (choice != 5) {
            switch (choice) {
                case 1:
                    System.out.println("Escolha um vendedor:");
                    gestor.printSellers();
                    Scanner input2 = new Scanner(System.in);
                    choice2 = Integer.parseInt(input2.next());
                    ArrayUnorderedList<Vendedor> vendedores = gestaoEmpresa.getVendedors();
                    Vendedor vendedor = vendedores.getIndex(choice2);
                    simulacao.simulate(vendedor);
                    choice = 5;
                    break;
                case 2:
                    simulacao.simulateAll(gestaoEmpresa.getVendedors());
                    choice = 5;
                    break;
                case 3:
                    simulacao.printAll();
                    choice = 5;
                    break;
                default:
                    return;
            }
        }
    }

    public void exports() throws IOException {
        int choice;
        System.out.println("Qual item quer exportar?");
        System.out.println("1- Empresa");
        System.out.println("2- Vendedor");
        System.out.println("3- Armazém");
        System.out.println("4- Mercado");
        Scanner input = new Scanner(System.in);
        choice = Integer.parseInt(input.next());
        while (choice != 5) {
            switch (choice) {
                case 1:
                    gestaoEmpresa.exportEnterprise(gestaoEmpresa);
                    choice = 5;
                    break;
                case 2:
                    gestaoVendedores.exportUser();
                    choice = 5;
                    break;
                case 3:
                    gestãoArmazem.exportArmazem();
                    choice = 5;
                    break;
                case 4:
                    gestaoMercados.exportMercado();
                    choice = 5;
                    break;
                default:
                    return;
            }
        }
    }
}
