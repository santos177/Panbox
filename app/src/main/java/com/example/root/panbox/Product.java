package com.example.root.panbox;

/**
 * Created by NgocTri on 10/22/2016.
 */

public class Product {

    private String cliente;
    private String cobro;
    //FIN CAMBIO JAIME

    public Product(String cliente,String cobro) {
        this.cliente = cliente;
        this.cobro = cobro;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getCobro() {
        return cobro;
    }

    public void setCobro(String cobro) {
        this.cobro = cobro;
    }

}
