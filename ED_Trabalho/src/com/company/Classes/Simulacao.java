package com.company.Classes;

import com.company.Estruturas.ArrayUnorderedList;
import com.company.Estruturas.EmptyException;
import com.company.Estruturas.InvalidIndexException;
import com.company.Estruturas.Network;
import com.company.Models.Armazem;
import com.company.Models.LocalX;
import com.company.Models.Mercado;
import com.company.Models.Vendedor;

public class Simulacao {
    private Network<LocalX> localNetwork;
    private ArrayUnorderedList<Path> paths;

    public Simulacao(Network<LocalX> network) {
        this.localNetwork = network;
        this.paths = new ArrayUnorderedList<>();
    }

    /**
     * Método para simular as rotas de uma lista de vendedores
     * @param vendedores
     * @throws EmptyException
     */
    public void simulateAll(ArrayUnorderedList<Vendedor> vendedores) throws EmptyException {
        for(int i = 0; i < vendedores.size(); i++){
            simulate(vendedores.getIndex(i));
        }
    }

    /**
     * Método para simulação de um vendedor
     * @param vendedor
     * @throws EmptyException
     */
    public void simulate(Vendedor vendedor) throws EmptyException {
        if (!verification(vendedor)) {
            paths.addToRear(new Path(vendedor));
        }
        int index = findIndex(vendedor);
        iniciarNaSede(index);
        boolean work;

        do {
            int stock= calculateTotalStock();
            int carregamento = calculateClientNeeds(vendedor);
            if(carregamento> stock){
                System.out.println("Stock insufeciente");
                returnBase(index,vendedor);
                return;
            }
            work = findPathToArmazem(carregamento, index);
            if(work) {
                findPathToMercado(vendedor, index, carregamento);
            }
        }while (work);
        returnBase(index,vendedor);
    }

    /**
     * Verificação se o vendedor ja tem algum Path associado
     * @param vendedor
     * @return true caso tenha ou false caso não tenha
     */
    private boolean verification(Vendedor vendedor) {
        for (int i = 0; i < paths.size(); i++) {
            if (paths.getIndex(i).getVendedor() == vendedor) {
                return true;
            }
        }
        return false;
    }

    /**
     * Encontrar o vendedor na lista de paths
     * @param vendedor
     * @return i ou -1
     */
    private int findIndex(Vendedor vendedor) {
        for (int i = 0; i < paths.size(); i++) {
            if (paths.getIndex(i).getVendedor() == vendedor) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Método para iniciar trajeto no local que for a sede
     * @param index
     */
    private void iniciarNaSede(int index) {
        if (paths.getIndex(index).getPaths().size() == 0) {
            LocalX local = findLocalType("Sede");
            paths.getIndex(index).getPaths().addToRear(local);
        }
    }

    /**
     * Encontrar a posição do local
     * @param local
     * @return i ou -1
     */
    private int findIndexLocal(LocalX local) {
        Object[] locais = localNetwork.getVertices();
        for (int i = 0; i < locais.length; i++) {
            if (locais[i] == local) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Encontrar o local pelo tipo
     * @param tipo
     * @return local ou null
     */
    private LocalX findLocalType(String tipo) {
        Object[] locais = localNetwork.getVertices();
        for (int i = 0; i < locais.length; i++) {
            LocalX local = (LocalX) locais[i];
            if (local.getType().equals(tipo)) {
                return local;
            }
        }
        return null;
    }

    /**
     * Calcular quanto stock ele precisa para atender os clientes
     * @param vendedor
     * @return total ou 0
     */
    private int calculateClientNeeds(Vendedor vendedor) {
        Mercado mercado = checkMercado(vendedor);
        if(mercado != null) {
            int total = 0;
            for (int i = 0; i < mercado.getClientes().size(); i++) {
                if (total + mercado.getClientes().getIndex(i) < vendedor.getCapacidade()) {
                    total += mercado.getClientes().getIndex(i);
                } else {
                    break;
                }
            }
            return total;
        }
        return 0;
    }

    /**
     * Verificar se o mercado na lista de mercados do vendedor existe e se tem clientes
     * @param vendedor
     * @return mercado ou null caso nao exista
     */
    private Mercado checkMercado(Vendedor vendedor) {
        Object[] locais = localNetwork.getVertices();
        for (int j = 0; j < vendedor.getMercados().size(); j++) {
            for (int i = 0; i < locais.length; i++) {
                LocalX local = (LocalX) locais[i];
                if(local == null){
                    break;
                }
                if (local.getType().equals("Mercado")) {
                    Mercado mercado = (Mercado) local;
                    if (mercado.getLocal_name().equals(vendedor.getMercados().getIndex(j))) {
                        if (mercado.getClientes().size() != 0) {
                            return mercado;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Encontrar o melhor caminho para o armazém correto e colocar na lista
     * @param total
     * @param index
     * @return true or false
     * @throws EmptyException
     */
    private boolean findPathToArmazem(int total, int index) throws EmptyException {
        if (total > 0) {
            boolean done = true;
            do {
                Armazem armazemfinal = findCorrectArmazem(index);
                if (armazemfinal == null) {
                    return false;
                }
                int u = paths.getIndex(index).getPaths().size() - 1;
                localNetwork.shortestPathWeight(paths.getIndex(index).getPaths().last(), armazemfinal, paths.getIndex(index).getPaths());
                paths.getIndex(index).getPaths().removeByIndex(u);
                if (armazemfinal.getStock() - total < 0) {
                    total -= armazemfinal.getStock();
                    armazemfinal.setStock(0);
                } else {
                    armazemfinal.setStock(armazemfinal.getStock() - total);
                    done=false;
                }
            }while (done);
        }else{
            return false;
        }
        return true;
    }

    /**
     * Encontrar o armazém mais proximo que tenha stock maior que 0
     * @param index
     * @return armazemfinal
     * @throws EmptyException
     */
    private Armazem findCorrectArmazem(int index) throws EmptyException {
        int weightfinal = -1;
        Armazem armazemfinal = null;
        Object[] locais = localNetwork.getVertices();

        for (int i = 0; i < locais.length; i++) {
            LocalX local = (LocalX) locais[i];
            if (local == null) {
                break;
            }
            if (local.getType().equals("Armazém")) {
                Armazem armazem = (Armazem) local;
                if (armazem.getStock() != 0) {
                    ArrayUnorderedList<LocalX> pathTemp = new ArrayUnorderedList<>();
                    localNetwork.shortestPathWeight(paths.getIndex(index).getPaths().last(), armazem, pathTemp);
                    int weighttemp = getPathWeight(pathTemp);
                    if (weightfinal == -1) {
                        weightfinal = weighttemp;
                        armazemfinal = armazem;
                    } else if (weighttemp < weightfinal) {
                        weightfinal = weighttemp;
                        armazemfinal = armazem;
                    }
                }
            }
        }
        return armazemfinal;
    }

    /**
     * Soma as distancias entre cada dois pontos ate chegar ao armazem
     * @param pathTemp
     * @return weight
     * @throws EmptyException
     */
    private int getPathWeight(ArrayUnorderedList<LocalX> pathTemp) throws EmptyException {
        int weight = 0;
        for (int i=0; i < pathTemp.size();i++){
            if(pathTemp.getIndex(i)!=pathTemp.last()){
                weight += localNetwork.getEdgeWeight(findIndexLocal(pathTemp.getIndex(i)),findIndexLocal(pathTemp.getIndex(i+1)));
            }
        }
        return weight;
    }

    /**
     * Encontrar o melhor caminho para o mercado desejado, depois de encontrado
     * removemos os clientes por ordem após servidos
     * @param vendedor
     * @param index
     * @param total
     * @throws EmptyException
     */
    private void findPathToMercado(Vendedor vendedor,int index,int total) throws EmptyException {
        Mercado mercado = checkMercado(vendedor);
        int u = paths.getIndex(index).getPaths().size() - 1;
        localNetwork.shortestPathWeight(paths.getIndex(index).getPaths().last(), mercado, paths.getIndex(index).getPaths());
        paths.getIndex(index).getPaths().removeByIndex(u);

        while (total != 0){
            total -= mercado.getClientes().first();
            mercado.getClientes().removeFirst();
        }
    }

    /**
     * Calcular o total de stock que existe nos armazéns
     * @return total
     */
    private int calculateTotalStock() {
        int total = 0;
        Object[] locais = localNetwork.getVertices();
        for (int i = 0; i < locais.length; i++) {
            LocalX local = (LocalX) locais[i];
            if (local == null) {
                break;
            }
            if (local.getType().equals("Armazém")) {
                Armazem armazem = (Armazem) local;
                total += armazem.getStock();
            }
        }
        return total;
    }


    /**
     * Método para retornar á sede
     * @param index
     * @param vendedor
     * @throws EmptyException
     */
    private void returnBase(int index,Vendedor vendedor) throws EmptyException {
        if (paths.getIndex(index).getPaths().size() != 0) {
            LocalX local = findLocalType("Sede");
            if (paths.getIndex(findIndex(vendedor)).getPaths().last() != local) {
                int u = paths.getIndex(index).getPaths().size() - 1;
                localNetwork.shortestPathWeight(paths.getIndex(index).getPaths().last(), local, paths.getIndex(index).getPaths());
                paths.getIndex(index).getPaths().removeByIndex(u);
            }
        }
    }

    /**
     * Print de todas as rotas
     */
    public void printAll(){
        for(int i = 0; i < paths.size(); i++){
            System.out.println(paths.getIndex(i).getVendedor().getNome());
            for(int j=0; j<paths.getIndex(i).getPaths().size();j++){
                System.out.println(paths.getIndex(i).getPaths().getIndex(j).getLocal_name());
            }
            System.out.println("*--------------------------*");
        }
    }
}
