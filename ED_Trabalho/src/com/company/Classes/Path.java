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

    public Vendedor getVendedor() {
        return vendedor;
    }

    public ArrayUnorderedList<LocalX> getPaths() {
        return paths;
    }


}
