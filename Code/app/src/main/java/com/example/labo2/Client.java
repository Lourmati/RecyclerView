package com.example.labo2;

import java.io.Serializable;

public class Client implements Serializable{
    private String nomDeFamille;
    private String prenom;
    private int    idImage;

    public Client(String nomDeFamille, String prenom, int idImage) {
        this.nomDeFamille = nomDeFamille;
        this.prenom = prenom;
        this.idImage = idImage;
    }

    public String getNom() {
        return nomDeFamille;
    }
    public String getPrenom() {
        return prenom;
    }
    public int getImage() {
        return idImage;
    }

    public void setNomDeFamille(String nomDeFamille) {
        this.nomDeFamille = nomDeFamille;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }


}
