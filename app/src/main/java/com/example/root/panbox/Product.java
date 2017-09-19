package com.example.root.panbox;

/**
 * Created by NgocTri on 10/22/2016.
 */

public class Product {
    private String id_orden_trabajo;
    private String client_id;
    private String imageId;
    private String title;
    private String document_n;
    private String direction;
    private String date_p;
    private String hora;
    private String direccion_c;
    private String telefono_c;
    private String nombre_c;
    //INICIO CAMBIO JAIME
    private String descripcion;
    private String n_guia;
    //FIN CAMBIO JAIME

    public Product(String id_orden_trabajo, String client_id, String imageId, String title, String document_n, String direction, String date_p, String hora, String direccion_c, String telefono_c, String nombre_c, String descripcion, String n_guia) {
        this.id_orden_trabajo = id_orden_trabajo;
        this.client_id = client_id;
        this.imageId = imageId;
        this.title = title;
        this.document_n = document_n;
        this.direction = direction;
        this.date_p = date_p;
        this.direccion_c=direccion_c;
        this.telefono_c=telefono_c;
        this.hora = hora;
        this.nombre_c= nombre_c;
        this.descripcion = descripcion;
        this.n_guia = n_guia;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String description) {
        this.direction = description;
    }

    public String getDate_p() {
        return date_p;
    }

    public void setDate_p(String date_p) {
        this.date_p = date_p;
    }

    public String getDocument_n() {
        return document_n;
    }

    public void setDocument_n(String document_n) {
        this.document_n = document_n;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getId_orden_trabajo() {
        return id_orden_trabajo;
    }

    public void setId_orden_trabajo(String id_orden_trabajo) {
        this.id_orden_trabajo = id_orden_trabajo;
    }
    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDireccion_c() {
        return direccion_c;
    }

    public void setDireccion_c(String direccion_c) {
        this.direccion_c = direccion_c;
    }

    public String getTelefono_c() {
        return telefono_c;
    }

    public void setTelefono_c(String telefono_c) {
        this.telefono_c = telefono_c;
    }

    public String getNombre_c() {
        return nombre_c;
    }

    public void setNombre_c(String nombre_c) {
        this.nombre_c = nombre_c;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getN_guia() {
        return n_guia;
    }

    public void setN_guia(String n_guia) {
        this.n_guia = n_guia;
    }
}
