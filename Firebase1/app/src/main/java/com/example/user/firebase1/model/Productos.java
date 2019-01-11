package com.example.user.firebase1.model;

/**
 * Created by user on 06/01/2019.
 */

public class Productos {

    private String nombre;
    private String descripcion;
    private String catageoria;
    private String precio;
    private String uid;
    private String fav;


    public Productos(){}
    public Productos(String nombre, String descripcion, String catageoria, String precio, String uid, String fav){
        this.nombre=nombre;
        this.descripcion=descripcion;
        this.catageoria=catageoria;
        this.precio=precio;
        this.uid=uid;
        this.fav=fav;


    }

    @Override
    public String toString() {
        return "Productos{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", catageoria='" + catageoria + '\'' +
                ", precio='" + precio + '\'' +
                ", uid='" + uid + '\'' +
                ", fav='" + fav + '\'' +
                '}';
    }

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getCatageoria() {
        return catageoria;
    }

    public void setCatageoria(String catageoria) {
        this.catageoria = catageoria;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }


}
