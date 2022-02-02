package com.company.Classes;

import com.company.Estruturas.ArrayUnorderedList;
import com.company.Estruturas.NoComparableException;
import com.company.Models.Vendedor;

import java.io.IOException;
import java.util.Scanner;

public class GestaoVendedores {

    private GestaoEmpresa gestaoEmpresa;
    private Gestor gestor;
    private Writter writter;

    public GestaoVendedores(GestaoEmpresa gestaoEmpresa){
        this.gestaoEmpresa = gestaoEmpresa;
        this.gestor = new Gestor(gestaoEmpresa.getVendedors());
        this.writter = new Writter(gestaoEmpresa);
    }

    /**
     * Método para alterar um atributo do vendedor
     */
    public void changeSeller() {
        int sellectSeller = 0 , sellectAtribut = 0;
        ArrayUnorderedList<Vendedor> vendedores = gestaoEmpresa.getVendedors();
        sellectSeller = gestor.changeUser();
        sellectAtribut = gestor.changeAtribute();
        switch (sellectAtribut) {
            case 1:
                Vendedor vendedor = vendedores.getIndex(sellectSeller);
                String string = gestor.changeName();
                vendedor.setNome(string);
                break;
            case 2:
                Vendedor vendedor2 = vendedores.getIndex(sellectSeller);
                long capacidade = gestor.changeCapacidade();
                vendedor2.setCapacidade(capacidade);
                break;
            case 3:
                Vendedor vendedor3 = vendedores.getIndex(sellectSeller);
                ArrayUnorderedList<String> mercados = gestor.atribuirLista();
                vendedor3.setMercados(mercados);
                break;
            default:
        }
    }

    /**
     * Método para adicionar vendedor
     * @throws NoComparableException
     */
    public void addSeller() throws NoComparableException {
        Vendedor vendedor;
        String nome;
        long id, capacidade;

        System.out.println("ID:");
        Scanner input3 = new Scanner(System.in);
        id = Long.parseLong(input3.nextLine());

        System.out.println("Name:");
        Scanner input = new Scanner(System.in);
        nome = input.nextLine();

        System.out.println("Capacidade:");
        Scanner input2 = new Scanner(System.in);
        capacidade = Long.parseLong(input2.nextLine());

        ArrayUnorderedList<String> mercados = new ArrayUnorderedList<String>();
        System.out.println("Adicionar mercado - 1");
        System.out.println("Não adicionar - 2");
        Scanner input5 = new Scanner(System.in);
        int choice = input5.nextInt();

        while(choice != 2){
            System.out.println("Mercados:");
            Scanner input4 = new Scanner(System.in);
            String mercado = String.valueOf(input4.nextLine());
            mercados.addToRear(mercado);
            System.out.println("Adicionar mercado - 1");
            System.out.println("Não adicionar - 2");
            choice = input5.nextInt();
        }
        vendedor = new Vendedor(id, nome, capacidade, mercados);
        gestaoEmpresa.addVendedor(vendedor);
    }

    /**
     * Menu de vendedor
     * @throws NoComparableException
     */
    public void sellerMenu() throws NoComparableException {
        int choice;
        System.out.println("O que deseja fazer?");
        System.out.println("1- Adicionar vendedor");
        System.out.println("2- Alterar vendedor");
        System.out.println("3- Exit");
        Scanner input = new Scanner(System.in);
        choice = Integer.parseInt(input.next());
        while (choice != 3) {
            switch (choice) {
                case 1:
                    addSeller();
                    choice= 3;
                    break;
                case 2:
                    changeSeller();
                    choice=3;
                    break;
                default:
                    return;
            }
        }
    }

    /**
     * Export de um vendedor escolhido
     * @throws IOException
     */
    public void exportUser() throws IOException {
        int sellectUser;
        Scanner input = new Scanner(System.in);
        System.out.println("Qual utilizador quer exportar?");
        System.out.println("-------------------------\n");
        gestor.printSellers();
        ArrayUnorderedList<Vendedor> vendedores = gestaoEmpresa.getVendedors();
        sellectUser = Integer.parseInt(input.nextLine());
        Vendedor vendedor = vendedores.getIndex(sellectUser);
        writter.appendPersonToFile(vendedor);
    }

}
