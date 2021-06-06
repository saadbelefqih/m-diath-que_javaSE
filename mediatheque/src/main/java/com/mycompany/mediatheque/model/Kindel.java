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
public class Kindel implements Serializable {
    private Long idKindel;
    private String modele;
    private String mac;
    private float pouces;
    boolean emprunte;
    
    public Kindel(Long idKindel,String modele, String mac, float pouces,boolean emprunte){
        this.idKindel=idKindel;
        this.modele= new String(modele);
        this.mac= new String(mac);
        this.pouces= pouces;
        this.emprunte=emprunte;
    }
    
      public Kindel(Kindel kindel){
        this.modele= new String(kindel.getModele());
        this.mac= new String(kindel.getMac());
        this.pouces= kindel.getPouces();
        emprunte=false;
    }

    public Kindel() {
    }
       

    public String getModele() {
        return modele;
    }

    public String getMac() {
        return mac;
    }

    public float getPouces() {
        return pouces;
    }

    public boolean isEmprunte() {
        return emprunte;
    }

    public void setEmprunte(boolean emprunte) {
        this.emprunte = emprunte;
    }
        /**
     * @return the idKindel
     */
    public Long getIdKindel() {
        return idKindel;
    }

    /**
     * @param idKindel the idKindel to set
     */
    public void setIdKindel(Long idKindel) {
        this.idKindel = idKindel;
    }
    
        /**
     * @param modele the modele to set
     */
    public void setModele(String modele) {
        this.modele = modele;
    }

    /**
     * @param mac the mac to set
     */
    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     * @param pouces the pouces to set
     */
    public void setPouces(float pouces) {
        this.pouces = pouces;
    }
    
    @Override
    public String toString(){
        return  " ID Kindel : "+this.getIdKindel()+" Modele: "+ this.modele+ " Adresse MAC: "+  this.mac+" Pouces: "+pouces;
    }

}
