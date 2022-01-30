package com.company.Classes;

import com.company.Estruturas.ArrayUnorderedList;
import com.company.Estruturas.EmptyException;
import com.company.Estruturas.InvalidIndexException;
import com.company.Estruturas.Network;
import com.company.Models.Armazem;
import com.company.Models.LocalX;
import com.company.Models.Mercado;
import com.company.Models.Vendedor;

public class Simulação {
    private Network<LocalX> localNetwork;
    private ArrayUnorderedList<Path> paths;

    public Simulação(Network<LocalX> network) {
        this.localNetwork = network;
        this.paths = new ArrayUnorderedList<>();
    }

    public void simulate(Vendedor vendedor) throws EmptyException, InvalidIndexException {
        if (!verification(vendedor)) {
            paths.addToRear(new Path(localNetwork, vendedor));
        }
        int index = findIndex(vendedor);
        iniciarNaSede(index);
        int carregamento = calculateClientNeeds(vendedor);
        FindPathToArmazem(carregamento,index);

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
        for (int i = 0; i < localNetwork.vertices.length; i++) {
            if (localNetwork.vertices[i] == local) {
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

    private Mercado checkMercado(Vendedor vendedor) {
        Object[] locais = localNetwork.getVertices();
        for (int j = 0; j < vendedor.getMercados().size(); j++) {
            for (int i = 0; i < locais.length; i++) {
                LocalX local = (LocalX) locais[i];
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

    private void FindPathToArmazem(int total,int index) throws EmptyException, InvalidIndexException {
        Object[] locais = localNetwork.getVertices();
        for (int i = 0; i < locais.length; i++) {
            LocalX local = (LocalX) locais[i];
            if (local.getType().equals("Armazém")) {
                Armazem armazem = (Armazem) local;
                System.out.println(armazem.getStock() + " " + total);
                if (armazem.getStock() >= total) {
                    ArrayUnorderedList<ArrayUnorderedList<Integer>> path = localNetwork.getShortestPath(paths.getIndex(index).getPaths().last(), armazem);
                    for(int j = 0; j < path.size(); j++){
                        for(int h = 0; h < path.getIndex(j).size(); h++){
                            System.out.println(path.getIndex(j).getIndex(h));
                        }

                    }

                }
            }
        }
    }


}
