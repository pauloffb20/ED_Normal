package com.company.Classes;
import com.company.Estruturas.ArrayUnorderedList;
import com.company.Models.Armazem;
import com.company.Models.LocalX;
import com.company.Estruturas.Network;
import com.company.Models.Vendedor;

import java.io.IOException;
import java.util.Scanner;

public class GestãoArmazem {

    private Network<LocalX> network;
    private Writter writter;

    public GestãoArmazem(Network<LocalX> network){
        this.network = network;
        this.writter = new Writter();
    }

    public void printStorages() {
        Object[] locais = network.getVertices();

        for (int i = 0; i < network.size(); i++) {
            LocalX atual = (LocalX) locais[i];
            if (atual.getType().equals("Armazém")) {
                System.out.println(i + " para escolher o :" + locais[i].toString());
            }
        }
    }

    public void printStoragesToShow() {
        Object[] locais = network.getVertices();
        for (int i = 0; i < network.size(); i++) {
            LocalX atual = (LocalX) locais[i];
            if (atual.getType().equals("Armazém")) {
                System.out.println(locais[i].toString());
            }
        }
    }

    public void setStorage(){
        Object[] locais = network.getVertices();
        int choice, choice2, choice3, choice4;
        String nome;

        printStorages();
        System.out.println("Escolha o armazem a alterar:");
        Scanner input = new Scanner(System.in);
        choice = Integer.parseInt(input.nextLine());

        Armazem armazem = (Armazem) locais[choice];

        System.out.println("Qual item quer alterar?");
        System.out.println("1- Nome");
        System.out.println("2- Capacidade");
        System.out.println("3- Stock");
        System.out.println("4- Exit");
        Scanner input5 = new Scanner(System.in);
        choice4 = Integer.parseInt(input5.nextLine());

        while (choice4 != 4) {
            switch (choice4) {
                case 1:
                    System.out.println("Nome?");
                    Scanner input6 = new Scanner(System.in);
                    nome = String.valueOf(input6.nextLine());
                    armazem.setLocal_name(nome);
                    choice4 = 4;
                    break;
                case 2:
                    System.out.println("Capacidade?");
                    Scanner input2 = new Scanner(System.in);
                    choice2 = Integer.parseInt(input2.nextLine());
                    armazem.setCapacidade(choice2);
                    choice4 = 4;
                    break;
                case 3:
                    System.out.println("Quanto Stock?");
                    Scanner input3 = new Scanner(System.in);
                    choice3 = Integer.parseInt(input3.nextLine());

                    while (choice3 > armazem.getCapacidade() || choice3 < 0){
                        System.out.println("Stock acima da capacidade! Defina um stock:");
                        Scanner input8 = new Scanner(System.in);
                        choice3 = Integer.parseInt(input8.nextLine());
                    }

                    armazem.setStock(choice3);
                    choice4 = 4;
                    break;
                default:
                    return;
            }
        }
    }

    public void addStorage(){
        int cap, stock;
        String name, tipo = "Armazém";

        System.out.println("Nome:");
        Scanner input4 = new Scanner(System.in);
        name = String.valueOf(input4.nextLine());

        System.out.println("capacidade:");
        Scanner input6 = new Scanner(System.in);
        cap = Integer.parseInt(input6.nextLine());

        System.out.println("Stock:");
        Scanner input7 = new Scanner(System.in);
        stock = Integer.parseInt(input7.nextLine());

        while (cap < stock || stock < 0){
            System.out.println("Stock acima da capacidade! Defina um stock:");
            Scanner input8 = new Scanner(System.in);
            stock = Integer.parseInt(input8.nextLine());
        }

        Armazem novoarmazem = new Armazem(name, tipo, cap, stock);
        network.addVertex(novoarmazem);
    }

    public void AddOrSetStorage() {
        int menu;
        Object[] locais = network.getVertices();
        System.out.println("1- Mudar armazem");
        System.out.println("2- Adicionar armazem");
        System.out.println("3 - exit");
        Scanner input = new Scanner(System.in);
        input.hasNext();
        menu = Integer.parseInt(input.next());

        while (menu != 3) {
            switch (menu) {
                case 1:
                    setStorage();
                    System.out.println("1- Mudar armazem");
                    System.out.println("2- Adicionar armazem");
                    System.out.println("3 - exit");
                    Scanner input1 = new Scanner(System.in);
                    input1.hasNext();
                    menu = Integer.parseInt(input1.next());
                    break;
                case 2:
                    addStorage();
                    System.out.println("1- Mudar armazem");
                    System.out.println("2- Adicionar armazem");
                    System.out.println("3 - exit");
                    Scanner input2 = new Scanner(System.in);
                    input2.hasNext();
                    menu = Integer.parseInt(input2.next());
                    break;
                default:
                    return;
            }
        }
    }

    public void exportArmazem() throws IOException {
        int sellectStorage;
        Object[] locais = network.getVertices();
        Scanner input = new Scanner(System.in);
        System.out.println("Qual armazém quer exportar?");
        System.out.println("-------------------------\n");
        printStorages();
        sellectStorage = Integer.parseInt(input.nextLine());
        Armazem armazem = (Armazem) locais[sellectStorage];
        writter.appendStorage(armazem);
    }
}
