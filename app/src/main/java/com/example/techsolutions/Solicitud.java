package com.example.techsolutions;

public class Solicitud {
    private String id;
    private String nombre;
    private String apellido;
    private String email;
    private String mensaje;
    private String fechaTexto;

    public Solicitud() {}

    public Solicitud(String id, String nombre, String apellido, String email, String mensaje, String fechaTexto) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.mensaje = mensaje;
        this.fechaTexto = fechaTexto;
    }

    // Getters y setters...
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getEmail() { return email; }
    public String getMensaje() { return mensaje; }
    public String getFechaTexto() { return fechaTexto; }

    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setEmail(String email) { this.email = email; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public void setFechaTexto(String fechaTexto) { this.fechaTexto = fechaTexto; }
}
