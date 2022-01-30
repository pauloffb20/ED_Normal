package com.company.Classes;
import com.company.Models.Vendedor;
import com.company.Estruturas.ArrayUnorderedList;
import java.util.Scanner;
import static java.lang.Long.valueOf;

public class Gestor {

    ArrayUnorderedList<Vendedor> vendedors;

  public Gestor(ArrayUnorderedList<Vendedor> vendedores){
      this.vendedors = vendedores;
  }

    public Gestor() { }

    public Integer changeUser() {
        Scanner input = new Scanner(System.in);
        String choice1;
        System.out.println("Escolha um vendedor:");
        System.out.println("-------------------------\n");
        printSellers();
        choice1 = input.next();
        return Integer.valueOf(choice1);
    }

    public void printSellers(){
        int number = 0;
        for (int i= 0; i < vendedors.size(); i++){
            System.out.println(number + " para selecionar vendedor abaixo:" + "\n" + vendedors.getIndex(i).toString());
            number++;
        }
    }

    public void printSellersToShow(){
        for (int i= 0; i < vendedors.size(); i++){
            System.out.println(vendedors.getIndex(i).toString());
        }
    }

    public Integer changeAtribute() {
        Scanner input = new Scanner(System.in);
        String choice1;
        System.out.println("O que pretende fazer?");
        System.out.println("-------------------------\n");
        System.out.println("1-Alterar Nome");
        System.out.println("2-Alterar capacidade");
        System.out.println("3-Atribuir lista de mercados");
        System.out.println("4-Sair ");
        choice1 = input.nextLine();
        return Integer.valueOf(choice1);
    }

    public String changeName() {
        Scanner input = new Scanner(System.in);
        String choice;
        System.out.println("Qual nome?");
        choice = input.nextLine();
        return String.valueOf(choice);
    }

    public long changeCapacidade(){
        Scanner input = new Scanner(System.in);
        String choice;
        System.out.println("Qual capacidade?");
        choice = input.nextLine();
        return valueOf(choice);
    }


    public ArrayUnorderedList<String> atribuirLista(){
      ArrayUnorderedList<String> mercados = new ArrayUnorderedList<>();
      int choice1;
      String choice;
      System.out.println("1- Adicionar mercado á lista");
      System.out.println("2- Não adicionar mais");
      Scanner input2 = new Scanner(System.in);

      choice1 = Integer.parseInt(input2.next());
      while(choice1 != 2){
          System.out.println("Mercado:");
          Scanner input1 = new Scanner(System.in);
          choice = String.valueOf(input1.nextLine());
          mercados.addToRear(choice);
          System.out.println("Continuar adicionar - 1");
          System.out.println("Não continuar - 2");
          choice1 = input2.nextInt();
      }
      return mercados;
    }
}
