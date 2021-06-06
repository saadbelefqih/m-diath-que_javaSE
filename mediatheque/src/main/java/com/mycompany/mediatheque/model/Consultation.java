/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediatheque.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author rachad
 */
public class Consultation implements Serializable {
    
    Emprunt emprunt;
    Document document;
    Date date_consultation;
    
    public Consultation(Emprunt emprunt,Document document, Date date_consultation ){
        this.emprunt=emprunt;
        this.document=document;
        this.date_consultation=new Date();
    }

    public Emprunt getEmprunt() {
        return emprunt;
    }

    public void setEmprunt(Emprunt emprunt) {
        this.emprunt = emprunt;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Date getDate_consultation() {
        return date_consultation;
    }

    public void setDate_consultation(Date date_consultation) {
        this.date_consultation = date_consultation;
    }
    
    
    @Override
    public String toString(){
        return  " CLIENT : "+this.getEmprunt().toString()+
                " DOCUMENT : "+this.getDocument().toString()+
                " Date Consultation "+this.getDate_consultation();
    }
    
}
