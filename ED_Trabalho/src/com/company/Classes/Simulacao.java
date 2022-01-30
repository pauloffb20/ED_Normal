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

    public void simulate(Vendedor vendedor) throws EmptyException, InvalidIndexException {
        if (!verification(vendedor)) {
            paths.addToRear(new Path(localNetwork, vendedor));
        }
        int index = findIndex(vendedor), stock= CalculateTotalStock();
        iniciarNaSede(index);
        boolean work;

        do {
            int carregamento = calculateClientNeeds(vendedor);
            if(carregamento> stock){
                System.out.println("Stock insufeciente");
                return;
            }
            FindPathToArmazem(carregamento, index);
            work = FindPathToMercado(vendedor, index, carregamento);
        }while (work);
        if (paths.getIndex(index).getPaths().size() != 0) {
            LocalX local = findLocalType("Sede");
            if (paths.getIndex(findIndex(vendedor)).getPaths().last() !=local) {
                paths.getIndex(findIndex(vendedor)).getPaths().addToRear(local);
            }
        }
        for(int i=0; i<paths.getIndex(findIndex(vendedor)).getPaths().size();i++){
            System.out.println(paths.getIndex(findIndex(vendedor)).getPaths().getIndex(i).getLocal_name());
        }


    }

    private boolean verification(Vendedor vendedor) {
        for (int i = 0; i < paths.size(); i++) {
            if (paths.getIndex(i).getVendedor() == vendedor) {
                return true;
            }
        }
        return false;
    }

    private int findIndex(Vendedor vendedor) {
        for (int i = 0; i < paths.size(); i++) {
            if (paths.getIndex(i).getVendedor() == vendedor) {
                return i;
            }
        }
        return -1;
    }

    private void iniciarNaSede(int index) {
        if (paths.getIndex(index).getPaths().size() == 0) {
            LocalX local = findLocalType("Sede");
            paths.getIndex(index).getPaths().addToRear(local);
        }
    }

    private int findIndexLocal(LocalX local) {
        Object[] locais = localNetwork.getVertices();
        for (int i = 0; i < locais.length; i++) {
            if (locais[i] == local) {
                return i;
            }
        }
        return -1;
    }

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

    private boolean FindPathToArmazem(int total, int index) throws EmptyException, InvalidIndexException {
        if (total != 0) {
            boolean done = true;
            do {
                Armazem armazemfinal = FindCorrectArmazem(index);
                if (armazemfinal == null) {
                    return false;
                }
                LocalX path = paths.getIndex(index).getPaths().last();
                int u = findIndexLocal(path);
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
        }
        return true;
    }

    private Armazem FindCorrectArmazem(int index) throws EmptyException {
        int weightfinal = -1;
        Armazem armazemfinal = null;
        Object[] locais = localNetwork.getVertices();
        ArrayUnorderedList<LocalX> pathTemp = new ArrayUnorderedList<>();
        for (int i = 0; i < locais.length; i++) {
            LocalX local = (LocalX) locais[i];
            if (local == null) {
                break;
            }
            if (local.getType().equals("Armazém")) {
                Armazem armazem = (Armazem) local;
                if (armazem.getStock() != 0) {
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


    private int getPathWeight(ArrayUnorderedList<LocalX> pathTemp) throws EmptyException {
        int weight = 0;
        for (int i=0; i < pathTemp.size();i++){
            if(pathTemp.getIndex(i)!=pathTemp.last()){
                weight += localNetwork.getEdgeWeight(findIndexLocal(pathTemp.getIndex(i)),findIndexLocal(pathTemp.getIndex(i+1)));
            }
        }
        return weight;
    }

    private boolean FindPathToMercado(Vendedor vendedor,int index,int total) throws EmptyException {
        Mercado mercado = checkMercado(vendedor);
        if(mercado == null){
            return false;
        }
        LocalX path = paths.getIndex(index).getPaths().last();
        int u = findIndexLocal(path);
        localNetwork.shortestPathWeight(paths.getIndex(index).getPaths().last(), mercado, paths.getIndex(index).getPaths());
        paths.getIndex(index).getPaths().removeByIndex(u);
        while (total != 0){
            total -= mercado.getClientes().first();
            mercado.getClientes().removeFirst();
        }

        return true;
    }

    private int CalculateTotalStock() {
        int total = 0;
        Object[] locais = localNetwork.getVertices();
        ArrayUnorderedList<LocalX> pathTemp = new ArrayUnorderedList<>();
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

}