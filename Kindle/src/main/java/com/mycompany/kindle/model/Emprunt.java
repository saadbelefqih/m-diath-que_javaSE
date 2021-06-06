/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.kindle.model;

import java.util.Date;

/**
 *
 * @author rachad
 */
public class Emprunt {
    private static int n=0;
    private final int id;
    private Client client;
    private Kindel kindel;
    private Date date_emprunt;
    private int  heureEmprunt;
    private int  minEmprunt;
    private int  heureRetour;
    private int  minRetour;
    
    public Emprunt(Client client,Kindel kindel, Date date_emprunt,int  heureEmprunt,int  minEmprunt,int heureRetour, int  minRetour){
        this.client= client;
        this.kindel= kindel;
        date_emprunt= new Date();
        this.heureEmprunt=heureEmprunt;
        this.minEmprunt=minEmprunt;
        this.heureRetour=heureRetour;
        this.minRetour=minRetour;
        id=++n;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Kindel getKindel() {
        return kindel;
    }

    public void setKindel(Kindel kindel) {
        this.kindel = kindel;
    }

    public Date getDate_emprunt() {
        return date_emprunt;
    }

    public void setDate_emprunt(Date date_emprunt) {
        this.date_emprunt = date_emprunt;
    }
    
        /**
     * @return the heureEmprunt
     */
    public int getHeureEmprunt() {
        return heureEmprunt;
    }

    /**
     * @param heureEmprunt the heureEmprunt to set
     */
    public void setHeureEmprunt(int heureEmprunt) {
        this.heureEmprunt = heureEmprunt;
    }

    /**
     * @return the minEmprunt
     */
    public int getMinEmprunt() {
        return minEmprunt;
    }

    /**
     * @param minEmprunt the minEmprunt to set
     */
    public void setMinEmprunt(int minEmprunt) {
        this.minEmprunt = minEmprunt;
    }

    /**
     * @return the heureRetour
     */
    public int getHeureRetour() {
        return heureRetour;
    }

    /**
     * @param heureRetour the heureRetour to set
     */
    public void setHeureRetour(int heureRetour) {
        this.heureRetour = heureRetour;
    }

    /**
     * @return the minRetour
     */
    public int getMinRetour() {
        return minRetour;
    }

    /**
     * @param minRetour the minRetour to set
     */
    public void setMinRetour(int minRetour) {
        this.minRetour = minRetour;
    }
    
    @Override
    public String toString(){
        return  " Client :  "+this.client.toString()+
                " Kindel : "+this.kindel.toString()+
                " Date Emprunt : "+this.date_emprunt+
                " Heure:Min : "+this.heureEmprunt+":"+this.minEmprunt+
                " --> Heure:Min Retour : "+this.heureRetour+":"+this.minRetour;
    }


    
    
}
