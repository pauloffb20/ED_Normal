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
    private GestaoArmazem gestaoArmazem;
    private GestaoMercados gestaoMercados;
    private GestaoCaminhos gestaoCaminhos;
    private Simulacao simulacao;

    public Menu() {
        this.gestaoEmpresa = new GestaoEmpresa();
        this.gestor = new Gestor(gestaoEmpresa.getVendedors());
        this.writter = new Writter(gestaoEmpresa);
        this.gestaoVendedores = new GestaoVendedores(gestaoEmpresa);
        this.gestaoArmazem = new GestaoArmazem(gestaoEmpresa.getNetworkX());
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
                    if(isDocRead()){
                        gestaoEmpresa.readJson(docFileName);
                    } else {
                        System.out.println("Ficheiro já importado");
                    }
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

    /**
     * Verifica se o doc já foi lido
     * @return true se foi lido e false se não foi
     * @throws NoComparableException
     * @throws IOException
     * @throws EmptyCollectionException
     */
    private boolean isDocRead() throws NoComparableException, IOException, EmptyCollectionException {
        boolean isread = false;

        if (this.docFileName == null) {
            String mapFileName = readDoc();
            this.docFileName = "Documents/" + mapFileName;
            isread = true;
        }
        return isread;
    }

    /**
     * Método para ir á pasta Documents e dar a escolher ao user o doc a importar
     * @return retorna o index na pasta ou "Not found"
     */
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

    /**
     * Menu inicial
     * @return valor da escolha (choice)
     */
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

    /**
     * Printar grafo
     * @throws EmptyException
     */
    public void printGraph() throws EmptyException {
        Network<LocalX> network;
        network = gestaoEmpresa.getNetworkX();
        Iterator<LocalX> i = network.iteratorBFS(0);
        while(i.hasNext()){
            System.out.println(i.next());
        }
    }

    /**
     * Menu de alterar dados
     * @throws EmptyException
     * @throws IOException
     * @throws NotFoundException
     * @throws NoComparableException
     */
    public void alterarDados() throws EmptyException, IOException, NotFoundException, NoComparableException {
        int choice;

        do {
            System.out.println("Qual item quer alterar/adicionar?");
            System.out.println("1- Caminhos");
            System.out.println("2- Vendedores");
            System.out.println("3- Armazéns");
            System.out.println("4- Mercados");
            System.out.println("5- Exit");
            Scanner input = new Scanner(System.in);
            choice = Integer.parseInt(input.next());
            switch (choice) {
                case 1:
                    gestaoCaminhos.pathMenu();
                    break;
                case 2:
                    gestaoVendedores.sellerMenu();
                    break;
                case 3:
                    gestaoEmpresa.AddOrSetStorage();
                    break;
                case 4:
                    gestaoEmpresa.AddOrSetMarkets();
                    break;
                default:
                    return;
            }
        }while (choice != 5);
    }

    /**
     * Menu da simulação
     * @throws EmptyException
     */
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

    /**
     * Menu dos exports
     * @throws IOException
     */
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
                    gestaoArmazem.exportArmazem();
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
