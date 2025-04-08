package org.thelarte.inventory.model;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "muebles")
public class Mueble {

    @Id
    private String id;

    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer cantidad;

    // Nuevo campo para múltiples fotos en Base64
    private List<String> fotos;

    // Constructor vacío necesario
    public Mueble() {
    }

    // Constructor completo (incluyendo fotos)
    public Mueble(String nombre, String descripcion, Double precio, Integer cantidad, List<String> fotos) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.fotos = fotos;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Double getPrecio() {
        return precio;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    public Integer getCantidad() {
        return cantidad;
    }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    public List<String> getFotos() {
        return fotos;
    }
    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }
}
