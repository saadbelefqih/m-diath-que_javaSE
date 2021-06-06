package com.mycompany.mediatheque.dao;


import com.mycompany.mediatheque.config.Config_DATABASE;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import com.mycompany.mediatheque.model.Client;
import com.mycompany.mediatheque.model.Etudiant;
import com.mycompany.mediatheque.model.Professeur;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rachad
 */
public class CRUD_Client {
    
    Connection con;
    
    public CRUD_Client() throws SQLException {
    
    con = DriverManager.getConnection(Config_DATABASE.db_URL,
            Config_DATABASE.username,
            Config_DATABASE.passwrd);
}

     
    public boolean ajouter(Client c) throws SQLException{
    Statement stmt = con.createStatement();
    String query="";
    
    if (c instanceof Etudiant){
      query=" insert into etudiant (cin,cne,nom,prenom,login,password) values ('"+c.getCin()+"','"+
             ((Etudiant) c).getCne()+
             "','"+c.getNom()+
             "','"+c.getPrenom()+
             "','"+c.getLogin()+
             "','"+c.getPassword()+"')";
    }
    else  if (c instanceof Professeur){
      query=" insert into professeur (cin,matricule,nom,prenom,login,password) values ('"+c.getCin()+"','"+
             ((Professeur) c).getMatricule()+
             "','"+c.getNom()+
             "','"+c.getPrenom()+
             "','"+c.getLogin()+
             "','"+c.getPassword()+"')";
    }
    
    int nbUpdated = stmt.executeUpdate(query);
    return nbUpdated>0;
    
    }
    
    public Client getEtudiantbyCin( String cin) throws SQLException{
       Statement stmt = con.createStatement();
       ResultSet rs = stmt.executeQuery("select * from etudiant where cin like '"+cin+"' ");
       Etudiant e= null;

    while (rs.next()) {
        e=new Etudiant(rs.getLong("id_etudiant"),rs.getString("login"),rs.getString("password"),rs.getString("cin"),rs.getString("nom"),rs.getString("prenom"),rs.getString("cne"));
    }
    return e;
    }
    
    public Etudiant authenEtudiant( String login, String password) throws SQLException{
       Statement stmt = con.createStatement();
      Etudiant etudiant= null;
       ResultSet rs = stmt.executeQuery("select * from etudiant where login='"+login+"' and password='"+password+"'");
    while (rs.next()) {
        etudiant=new Etudiant(rs.getLong("id_etudiant"),rs.getString("login"),rs.getString("password"),rs.getString("cin"),rs.getString("nom"),rs.getString("prenom"),rs.getString("cne"));
    }
    return etudiant;
    }
    
     public Professeur authenProf( String login, String password) throws SQLException{
       Statement stmt = con.createStatement();
      Professeur professeur= null;
       ResultSet rs = stmt.executeQuery("select * from professeur where login='"+login+"' and password='"+password+"'");
    while (rs.next()) {
        professeur=new Professeur(rs.getLong("id_professeur"),rs.getString("login"),rs.getString("password"),rs.getString("cin"),rs.getString("nom"),rs.getString("prenom"),rs.getString("matricule"));
        System.out.println("authenProf() "+rs.getLong("id_professeur"));
    }
    return professeur;
    }
    
     public Client getEtudiantbyCne( String cne) throws SQLException{
       Statement stmt = con.createStatement();
       ResultSet rs = stmt.executeQuery("select * from etudiant where cne like '"+cne+"' ");
       Etudiant e= null;

    while (rs.next()) {
        e=new Etudiant(rs.getLong("id_etudiant"),rs.getString("login"),rs.getString("password"),rs.getString("cin"),rs.getString("nom"),rs.getString("prenom"),rs.getString("cne"));
    }
    return e;
    }
    
    public LinkedList<Etudiant> getAllEtudiants() throws SQLException{
    
    Statement stmt = con.createStatement();
    ResultSet rs = stmt.executeQuery("select * from etdudiant");
    LinkedList<Etudiant> Liste= new  LinkedList<> ();
    while (rs.next()) {
      Etudiant e=new Etudiant(rs.getLong("id_etudiant"),rs.getString("login"),rs.getString("password"),rs.getString("cin"),rs.getString("nom"),rs.getString("prenom"),rs.getString("cne"));
      Liste.add(e);
    }
     return Liste;
    }
    
    
    public boolean modifier(Client c) throws SQLException{
    Statement stmt = con.createStatement();
    String query="";
    
    if (c instanceof Etudiant){
      query=" UPDATE etudiant "+
             " SET nom='"+c.getNom()+
             "' , SET prenom='"+c.getPrenom()+
             "' , SET login='"+c.getLogin()+
             "' , SET password='"+c.getPassword()+
             "' WHERE cin like '"+c.getCin()+"' ";
    }
    else  if (c instanceof Professeur){
      query=" UPDATE professeur "+
             " SET nom='"+c.getNom()+
             " , SET prenom='"+c.getPrenom()+
             " , SET login='"+c.getLogin()+
             " , SET password='"+c.getPassword()+
             " WHERE cin like '"+c.getCin()+"' ";
    }
    
    int nbUpdated = stmt.executeUpdate(query);
    return nbUpdated>0;
    }
    
    public boolean supprimer(Client c) throws SQLException{
        
        
        Statement stmt = con.createStatement();
        String query="";
    
    if (c instanceof Etudiant){
      query=" DELETE FROM etudiant WHERE cin like '"+c.getCin()+"' ";
    }
    else  if (c instanceof Professeur){
      query=" DELETE FROM professeur WHERE cin like '"+c.getCin()+"' ";
    }
    
    int nbUpdated = stmt.executeUpdate(query);
    return nbUpdated>0;
    }
    
  
    
    
}
