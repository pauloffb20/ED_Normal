package com.company.Classes;
import com.company.Estruturas.EmptyException;
import com.company.Estruturas.NotFoundException;
import com.company.Models.Caminho;
import com.company.Models.LocalX;
import com.company.Estruturas.Network;
import java.io.IOException;
import java.util.Scanner;

public class GestaoCaminhos {

    private Network<LocalX> localNetwork;
    private GestaoEmpresa gestaoEmpresa;

    public GestaoCaminhos(GestaoEmpresa gestaoEmpresa ){ this.gestaoEmpresa = gestaoEmpresa; }
    public GestaoCaminhos(Network<LocalX> network){
        this.localNetwork = network;
    }

    public Caminho addEdge() {
        int inicio, fim;
        double distancia;
        Object[] objects = localNetwork.getVertices();

        printVertices();
        System.out.println("Escolha um vertice:");
        Scanner input = new Scanner(System.in);
        inicio = Integer.parseInt(input.next());
        LocalX local1 = (LocalX) objects[inicio];
        printVertices();

        System.out.println("Escolha um vertice:");
        Scanner input2 = new Scanner(System.in);
        fim = Integer.parseInt(input2.next());
        LocalX local2 = (LocalX) objects[fim];

        System.out.println("Escolha a distancia:");
        Scanner input3 = new Scanner(System.in);
        distancia = input3.nextDouble();

        localNetwork.addEdge(local1, local2, distancia);
        Caminho caminho = new Caminho(local1.getLocal_name(), local2.getLocal_name(), distancia);

        return caminho;
    }

    public Caminho removeEdge(){
        int inicio, fim;
        Object[] objects = localNetwork.getVertices();

        printVertices();
        System.out.println("Escolha um vertice:");
        Scanner input = new Scanner(System.in);
        inicio = Integer.parseInt(input.next());
        LocalX local1 = (LocalX) objects[inicio];
        printVertices();

        System.out.println("Escolha um vertice:");
        Scanner input2 = new Scanner(System.in);
        fim = Integer.parseInt(input2.next());
        LocalX local2 = (LocalX) objects[fim];

        localNetwork.removeEdge(local1, local2);
        Caminho caminho = new Caminho(local1.getLocal_name(), local2.getLocal_name());

        return caminho;
    }


    public void pathMenu() throws IOException, EmptyException, NotFoundException {
        int choice;
        System.out.println("Qual deseja fazer?");
        System.out.println("1- Adicionar caminho");
        System.out.println("2- Remover caminho");
        System.out.println("3- Listar caminhos");
        Scanner input = new Scanner(System.in);
        choice = Integer.parseInt(input.next());
        while (choice != 4) {
            switch (choice) {
                case 1:
                    gestaoEmpresa.addEdge();
                    choice = 4;
                    break;
                case 2:
                    gestaoEmpresa.removeEdge();
                    choice = 4;
                    break;
                case 3:
                    System.out.println(gestaoEmpresa.printPaths());
                    choice = 4;
                    break;
                default:
                    return;
            }
        }
    }

    public void printVertices(){
        Object[] objects = localNetwork.getVertices();
        for (int i = 0; i < localNetwork.size(); i++) {
            System.out.println(i + objects[i].toString());
        }
    }
}
