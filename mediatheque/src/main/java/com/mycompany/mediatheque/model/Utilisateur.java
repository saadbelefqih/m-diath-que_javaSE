/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediatheque.model;

/**
 *
 * @author rachad
 */
public abstract class  Utilisateur {
    
private Long idUser;
private String login;
private String password;

    public Utilisateur() {
    }


public Utilisateur(Long idUser,String login,String password){
    this.idUser=idUser;
    this.login= login;
    this.password= password;}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    

    @Override
    public String toString() {
        return "idUser : "+this.getIdUser() +" login: "+this.login;
    }

    /**
     * @return the idUser
     */
    public Long getIdUser() {
        return idUser;
    }

    /**
     * @param idUser the idUser to set
     */
    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
    
    


    
}
