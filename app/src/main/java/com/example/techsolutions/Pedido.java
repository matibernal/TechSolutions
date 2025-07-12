package com.example.techsolutions;

public class Pedido {
    private String id;
    private String descripcion;
    private String fecha;
    private String estado;
    private String uid;

    public Pedido() {}

    public Pedido(String id, String descripcion, String fecha, String estado, String uid) {
        this.id = id;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.estado = estado;
        this.uid = uid;
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getDescripcion() { return descripcion; }
    public String getFecha() { return fecha; }
    public String getEstado() { return estado; }
    public String getUid() { return uid; }
}
