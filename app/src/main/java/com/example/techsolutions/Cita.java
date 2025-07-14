package com.example.techsolutions;

public class Cita {
    private String id;
    private String uid;
    private String fechaHora;
    private String motivo;
    private String estado;

    public Cita() {}

    public Cita(String id, String uid, String fechaHora, String motivo, String estado) {
        this.id = id;
        this.uid = uid;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.estado = estado;
    }



    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }
    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
