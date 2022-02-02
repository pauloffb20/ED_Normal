package com.company.Classes;

import com.company.Estruturas.ArrayUnorderedList;
import com.company.Estruturas.Network;
import com.company.Models.Caminho;
import com.company.Models.LocalX;
import com.company.Models.Vendedor;

public class Path {
    private Vendedor vendedor;
    private ArrayUnorderedList<LocalX> paths;

    public Path(Vendedor vendedor){
        this.vendedor = vendedor;
        this.paths = new ArrayUnorderedList<>();
    }

    /**
     *  Método para obter o vendedor do path
     * @return vendedor
     */
    public Vendedor getVendedor() {
        return vendedor;
    }

    /**
     * Método para obter a lista de locais
     * @return paths
     */
    public ArrayUnorderedList<LocalX> getPaths() {
        return paths;
    }


}
