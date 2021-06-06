package com.mycompany.mediatheque.dao;

import com.mycompany.mediatheque.config.Config_DATABASE;
import com.mycompany.mediatheque.model.Client;
import com.mycompany.mediatheque.model.Etudiant;
import com.mycompany.mediatheque.model.Kindel;
import com.mycompany.mediatheque.model.Professeur;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rachad
 */
public class CRUD_Kindel {
    Connection con;

    public CRUD_Kindel() throws SQLException {
        con = DriverManager.getConnection(Config_DATABASE.db_URL,
            Config_DATABASE.username,
            Config_DATABASE.passwrd);
    }
    
    public boolean save(Kindel c) throws SQLException {
        Statement stmt = con.createStatement();
            String query = "INSERT INTO kindel( modele, mac, pouces, emprunte) "
                    + "VALUES( "
                    + "'" + c.getModele() + "',"
                    + "'" + c.getMac() + "',"
                    + "" + c.getPouces() + ","
                    + "" + c.isEmprunte() + ")";
            int nbUpdated = stmt.executeUpdate(query);
            return nbUpdated > 0;
    }

    public boolean modifier(Kindel kindel) throws SQLException {
        Statement stmt = con.createStatement();
        String query = " UPDATE kindel "
                + " SET modele='" + kindel.getModele()
                + "', pouces=" + kindel.getPouces()
                + ", emprunte=" + kindel.isEmprunte()
                + " WHERE  mac='"+kindel.getMac()+"'" ;

        int nbUpdated = stmt.executeUpdate(query);
        return nbUpdated > 0;
    }
    
    boolean updateEmprunteKindle(Long idKindel, int emprunte) throws SQLException {
        Statement stmt = con.createStatement();
        String query = " UPDATE kindel "
                + " SET emprunte='" + emprunte
                + "' WHERE id_kindel = " + idKindel + " ";

        int nbUpdated = stmt.executeUpdate(query);
        return nbUpdated > 0;
    }

    Kindel getOneAvailbleKindle() throws SQLException {
        Statement stmt = con.createStatement();
        Kindel kindel = new Kindel();
        String query = "SELECT * FROM kindel WHERE emprunte = 0 LIMIT 1";

        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            kindel.setModele(rs.getString("modele"));
            kindel.setMac(rs.getString("mac"));
            kindel.setPouces(rs.getFloat("pouces"));
            kindel.setIdKindel(rs.getLong("id_kindel"));
            kindel.setEmprunte(rs.getBoolean("emprunte"));
        }

        return kindel;
    }
        public Boolean getOneKindleByMac(String mac) throws SQLException {
            Boolean isfalse=false;
        Statement stmt = con.createStatement();
        Kindel kindel =  new Kindel();
        String query = "SELECT * FROM kindel WHERE mac='"+mac+"' LIMIT 1";
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            isfalse=true;
        }

        return isfalse;
    }
        
     public boolean supprimer(String  mac) throws SQLException{
        
      Long idKindel=null;
        Statement stmt = con.createStatement();
        String query = "SELECT kindel.id_kindel FROM emprunt,kindel WHERE emprunt.id_kindel=kindel.id_kindel and kindel.mac='"+mac+"' LIMIT 1 ";

        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            idKindel=rs.getLong("id_kindel");
        }
        
        if(idKindel==null){
                int nbUpdated = stmt.executeUpdate(" DELETE FROM kindel WHERE mac='"+mac+"'");
                return nbUpdated>0;
        }
        else{
            System.out.println("CE KINDEL FIGURE DANS LA LISTE DES EMRUNTES  ");
            return false; 
        }


    }    
        
    
    
    
}
