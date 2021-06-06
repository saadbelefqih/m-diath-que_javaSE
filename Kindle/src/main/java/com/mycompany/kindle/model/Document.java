/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.kindle.model;

import java.io.Serializable;

/**
 *
 * @author rachad
 */
public abstract class Document implements Serializable {


    private Long idDoc;
    private String titre;
    private String editeur;
    private int edition;
    private String isbn;
    private String[] auteurs;
    private String url;
    
    
    public Document(Long idDoc, String titre, String editeur,int edition,String isbn,String[] auteurs, String url){
        this.idDoc=idDoc;
        this.titre= new String(titre);
        this.editeur= new String(editeur);
        this.edition=edition;
        this.isbn=new String(isbn);
        this.auteurs=auteurs;  
        this.url= new String(url);
    }

    public Document() {
    }
    

    public String getTitre() {
        return titre;
    }

    public String getEditeur() {
        return editeur;
    }

    public int getEdition() {
        return edition;
    }

    public String getIsbn() {
        return isbn;
    }

    public String[] getAuteurs() {
        return auteurs;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
        /**
     * @return the idDoc
     */
    public Long getIdDoc() {
        return idDoc;
    }

    /**
     * @param idDoc the idDoc to set
     */
    public void setIdDoc(Long idDoc) {
        this.idDoc = idDoc;
    }
    
        /**
     * @param auteurs the auteurs to set
     */
    public void setAuteurs(String[] auteurs) {
        this.auteurs=auteurs;
    }
    
    @Override
    public String toString(){
        String auts="";
        int len=this.auteurs.length;
        if(len>0){
        for(int i=0;i<len;i++){
            auts=auts+" auteurs"+(i+1)+" : "+this.auteurs[i];}
        }
        return "ID : "+getIdDoc() +"Titre: "+ titre+" Editeur:"+ editeur+ " Edition:"+ edition+ " ISBN:"+isbn +" Auteurs :"+auts;
    }



    
    
    
    
}
