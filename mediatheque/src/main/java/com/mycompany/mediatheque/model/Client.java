/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediatheque.model;

import java.io.Serializable;

/**
 *
 * @author rachad
 */
public class Client extends Utilisateur implements Serializable {
    private String cin;
    private String nom;
    private String prenom;
    
    public Client(Long idUser,String login, String password, String cin, String  nom, String  prenom){
        super(idUser,login, password);
        this.cin= new String(cin);
        this.nom= new String(nom);
        this.prenom= new String(prenom);
    }
    
    


    public Client() {
    }
    
    

    /**
     * @return the cin
     */
    public String getCin() {
        return cin;
    }

    /**
     * @param cin the cin to set
     */
    public void setCin(String cin) {
        this.cin = cin;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @param prenom the prenom to set
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


    
    public String toString(){
        return "Cin: "+getCin()+" Nom: "+getNom()+" Prenom: "+ getPrenom();
    }
  
    
    
     
}
