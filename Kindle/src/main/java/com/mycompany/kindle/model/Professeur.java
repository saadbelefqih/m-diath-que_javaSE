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
public class Professeur extends Client {
    private String matricule;
    
    public Professeur(Long idUser,String login, String password, String cin, String  nom, String  prenom, String matricule){
        super(idUser,login, password, cin, nom, prenom);
        
        this.matricule= new String(matricule);
    }

    public Professeur() {
        super();
    }  
    
    @Override
    public String toString(){
        return super.toString()+" Matricule: "+matricule;
    } 

    public String getMatricule() {
        return matricule;
    }


    
    
   
    
}
