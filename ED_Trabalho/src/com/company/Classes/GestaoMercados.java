package com.company.Classes;
import com.company.Models.LocalX;
import com.company.Models.Mercado;
import com.company.Estruturas.ArrayUnorderedList;
import com.company.Estruturas.Network;
import java.io.IOException;
import java.util.Scanner;

public class GestaoMercados {

    private Writter writter;
    private Network<LocalX> network;
    public GestaoMercados(Network<LocalX> localNetwork){
        this.network = localNetwork;
        this.writter = new Writter();
    }

    /**
     * Print dos mercados na network e seu index
     */
    public void printMarkets() {
        Object[] locais = network.getVertices();

        for (int i = 0; i < network.size(); i++) {
            LocalX atual = (LocalX) locais[i];
            if (atual.getType().equals("Mercado")) {
                System.out.println(i + " para escolher o :" + locais[i].toString());
            }
        }
    }

    /**
     * Print dos mercados na network
     */
    public void printMarketsToShow() {
        Object[] locais = network.getVertices();
        for (int i = 0; i < network.size(); i++) {
            LocalX atual = (LocalX) locais[i];
            if (atual.getType().equals("Mercado")) {
                System.out.println(locais[i].toString());
            }
        }
    }

    /**
     * Método para alterar mercado
     */
    public void setMarket(){
        Object[] locais = network.getVertices();
        int choice, choice4 = 0, cliente, choice2;
        String nome;
        printMarkets();
        System.out.println("Escolha o mercado a alterar:");
        Scanner input = new Scanner(System.in);
        choice = Integer.parseInt(input.next());
        Mercado mercado = (Mercado) locais[choice];

        while (choice4 != 2) {
            System.out.println("Qual item quer alterar?");
            System.out.println("1- Nome");
            System.out.println("2- Lista de clientes");
            System.out.println("3- Exit");
            Scanner input5 = new Scanner(System.in);
            choice4 = Integer.parseInt(input5.next());

            switch (choice4) {
                case 1:
                    System.out.println("Nome?");
                    Scanner input6 = new Scanner(System.in);
                    nome = String.valueOf(input6.nextLine());
                    mercado.setLocal_name(nome);
                    choice4 = 3;
                    break;
                case 2:
                    ArrayUnorderedList<Integer> clientes = new ArrayUnorderedList<>();

                    System.out.println("1- Adicionar cliente");
                    System.out.println("2- Não adicionar");
                    Scanner input7 = new Scanner(System.in);
                    choice2 = input7.nextInt();

                    while(choice2 != 2){
                        System.out.println("Cliente:");
                        Scanner input1 = new Scanner(System.in);
                        cliente = Integer.parseInt(input1.nextLine());
                        clientes.addToRear(cliente);
                        System.out.println("Adicionar cliente - 1");
                        System.out.println("Não adicionar - 2");
                        choice2 = input7.nextInt();
                    }

                    mercado.setClientes(clientes);
                    break;
                default:
                    return;
            }
        }
    }

    /**
     * Método para adicionar mercado
     */
    public void addMarket(){
        String name, tipo = "Mercado";
        int cliente;
        ArrayUnorderedList<Integer> clientes = new ArrayUnorderedList<>();

        System.out.println("Nome:");
        Scanner input4 = new Scanner(System.in);
        name = String.valueOf(input4.nextLine());

        System.out.println("1- Adicionar cliente");
        System.out.println("2- Não adicionar");
        Scanner input5 = new Scanner(System.in);
        int choice = input5.nextInt();

        while(choice != 2){
            System.out.println("Cliente:");
            Scanner input1 = new Scanner(System.in);
            cliente = Integer.parseInt(input1.nextLine());
            clientes.addToRear(cliente);
            System.out.println("Adicionar cliente - 1");
            System.out.println("Não adicionar - 2");
            choice = input5.nextInt();
        }

        Mercado novoMercado = new Mercado(name, tipo, clientes);
        network.addVertex(novoMercado);
    }

    /**
     * Menu de mercado
     */
    public void AddOrSetMarkets() {
        int menu;
        System.out.println("1- Mudar Mercado");
        System.out.println("2- Adicionar mercado");
        System.out.println("3 - exit");
        Scanner input = new Scanner(System.in);
        menu = Integer.parseInt(input.nextLine());

        while (menu != 3) {
            switch (menu) {
                case 1:
                    setMarket();
                    System.out.println("1- Mudar Mercado");
                    System.out.println("2- Adicionar mercado");
                    System.out.println("3 - exit");
                    Scanner input1 = new Scanner(System.in);
                    menu = Integer.parseInt(input1.nextLine());
                    break;
                case 2:
                    addMarket();
                    System.out.println("1- Mudar Mercado");
                    System.out.println("2- Adicionar mercado");
                    System.out.println("3 - exit");
                    Scanner input2 = new Scanner(System.in);
                    menu = Integer.parseInt(input2.nextLine());
                    break;
                default:
                    return;
            }
        }
    }

    /**
     * Export de um mercado escolhido
     * @throws IOException
     */
    public void exportMercado() throws IOException {
        int sellectMarket;
        Object[] locais = network.getVertices();
        Scanner input = new Scanner(System.in);
        System.out.println("Qual mercado quer exportar?");
        System.out.println("-------------------------\n");
        printMarkets();
        sellectMarket = Integer.parseInt(input.nextLine());
        Mercado mercado = (Mercado) locais[sellectMarket];
        writter.appendMarket(mercado);
    }
}
