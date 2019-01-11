package com.example.user.firebase1.model;

/**
 * Created by user on 20/12/2018.
 */


public class Usuario {

    private String nomuser;
    private String nomyape;
    private String correo;
    private String direccion;

    public Usuario(){

    }

    public Usuario(String nomuser, String nomyape, String correo, String direccion) {
        this.nomuser = nomuser;
        this.nomyape = nomyape;
        this.correo = correo;
        this.direccion = direccion;
    }



    @Override
    public String toString() {
        return "Usuario{}";
    }

    public String getNomuser() {
        return nomuser;
    }

    public void setNomuser(String nomuser) {
        this.nomuser = nomuser;
    }

    public String getNomyape() {
        return nomyape;
    }

    public void setNomyape(String nomyape) {
        this.nomyape = nomyape;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
