/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.kindle.model;

/**
 *
 * @author rachad
 */
public class Livre extends Document  {
    private int nbPages;
    
    public Livre(Long idDonc,String titre, String editeur,int edition,String isbn,String[] auteurs, String url,  int nbPages){
        super(idDonc, titre,  editeur, edition, isbn, auteurs, url);
        this.nbPages=nbPages;
    }

    public Livre() {
    }

    
    
    

    public int getNbPages() {
        return nbPages;
    }
    
    public String toString(){
        return super.toString()+" Nombre de pages: "+nbPages;
    }
}
