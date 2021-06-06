package com.mycompany.mediatheque.dao;

import com.mycompany.mediatheque.config.Config_DATABASE;
import com.mycompany.mediatheque.model.Emprunt;
import com.mycompany.mediatheque.model.Kindel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Objects;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rachad
 */
public class CRUD_Emprunt {
    Connection con;
    public CRUD_Emprunt() throws SQLException {
    con = DriverManager.getConnection(Config_DATABASE.db_URL,
            Config_DATABASE.username,
            Config_DATABASE.passwrd);
}
    
   public Boolean empunterKindel(String cinProfesseur,String cinEtudiant,String adresseMacKindel,String dateEmprunt,Integer heureEmprunt,Integer minEmprunt) throws SQLException, ParseException{
        Statement stmt4 = con.createStatement();
        String query="";
        Long idProf=null;
        Long idEtud=null;
        Long idKindel=null;
    

    // formater Date 
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
        
        java.util.Date dateStr = formatter.parse(dateEmprunt);
        java.sql.Date dateEmpruntDB = new java.sql.Date(dateStr.getTime());
        


       // STEP 1 Check if CNE is Nulled and search User
        if(Objects.nonNull(cinProfesseur)){
            Statement stmt1 = con.createStatement();
            ResultSet rs1 = stmt1.executeQuery("select id_professeur from professeur where cin like '"+cinProfesseur+"' ");
            while (rs1.next()) {idProf=rs1.getLong("id_professeur");}}
        
        else if(Objects.nonNull(cinEtudiant)){
            Statement stmt2 = con.createStatement();
            ResultSet rs2 = stmt2.executeQuery("select id_etudiant from etudiant where cin like '"+cinEtudiant+"' ");
            while (rs2.next()) {idEtud=rs2.getLong("id_etudiant");}}
        
        else{System.out.println("CIN NE PEUT PAS ENTRE VIDE ");return false;}
        
        // STEP 2 Check if Kindel Exist and VAILABLE
        
        if(Objects.nonNull(adresseMacKindel)){
            Statement stmt3 = con.createStatement();
            ResultSet rs3 = stmt3.executeQuery("SELECT id_kindel FROM kindel WHERE mac='"+adresseMacKindel+"' and emprunte = 0 LIMIT 1");
            while (rs3.next()) {idKindel=rs3.getLong("id_kindel");}
        }
        else{System.out.println("ERROR : MAC NE PEUT PAS ENTRE VIDE ");
             return false;}
        
         // STEP 3  Check if Kindel and User exist in DB else return EROOR Message 
        if ((idProf==null && idEtud==null) || idKindel==null || (heureEmprunt==null) ||(minEmprunt==null))
        {
            System.out.println("ERROR : CIN NOT FOUND OR KINDEL NOT FOUND OR UNAVAILABLE  ... ");
            return false;
        }
        
        // STEP 4 save emprunt
        query= "INSERT INTO emprunt( id_kindel, id_professeur,id_etudiant, date_emprunt, heureEmprunt,minEmprunt) VALUES( "
                + idKindel +","
                + idProf+","
                + idEtud+","
                +"'"+ dateEmpruntDB+"',"
                + heureEmprunt+","+
                + minEmprunt+")";
   
        int nbUpdated = stmt4.executeUpdate(query);
        
        
        // STEP 5 update Position Kindel
        if(nbUpdated>0){
             Statement stmt = con.createStatement();
         stmt.executeUpdate(" UPDATE kindel SET emprunte=true WHERE id_kindel = " + idKindel);
         
                System.out.print("UPDATE kindel SET emprunte=1 WHERE id_kindel = " + idKindel);
        }
        return nbUpdated > 0;
    }
   
   
   public Boolean rendreKindel(String cinProfesseur,String cinEtudiant,String adresseMacKindel,Integer heureRetour,Integer minRetour) throws SQLException{
        Long idProf=null;
        Long idEtud=null;
        Long idKindel=null;
        String query="";
        Statement stmt4 = con.createStatement();
        // STEP 1 Check if CNE is Nulled and search User
        if(Objects.nonNull(cinProfesseur)){
            Statement stmt1 = con.createStatement();
            ResultSet rs1 = stmt1.executeQuery("select id_professeur from professeur where cin like '"+cinProfesseur+"' ");
            while (rs1.next()) {idProf=rs1.getLong("id_professeur");}}
        
        else if(Objects.nonNull(cinEtudiant)){
            Statement stmt2 = con.createStatement();
            ResultSet rs2 = stmt2.executeQuery("select id_etudiant from etudiant where cin like '"+cinEtudiant+"' ");
            while (rs2.next()) {idEtud=rs2.getLong("id_etudiant");}}
        
        else{System.out.println("ERROR :  CIN NE PEUT PAS ENTRE VIDE ");return false;}
        
        // STEP 2 Check if Kindel Exist and VAILABLE
        
        if(Objects.nonNull(adresseMacKindel)){
            Statement stmt3 = con.createStatement();
            ResultSet rs3 = stmt3.executeQuery("SELECT id_kindel FROM kindel WHERE mac='"+adresseMacKindel+"' and emprunte = 1 LIMIT 1");
            while (rs3.next()) {idKindel=rs3.getLong("id_kindel");}
        }
        else{System.out.println("ERROR : MAC NE PEUT PAS ENTRE VIDE ");
             return false;}
        
        // STEP 3  Check if Kindel and User exist in DB else return EROOR Message 
        
        if ((idProf==null && idEtud==null) || idKindel==null || (heureRetour==null) ||(minRetour==null))
        {
            System.out.println("ERROR : CIN NOT FOUND OR KINDEL NOT FOUND OR UNAVAILABLE  ... ");
            return false;
        }
       // STEP 4 Set retour date emprunt
        query= " UPDATE emprunt AS emprunt2\n" +
               "inner join \n" +
                "(select emprunt1.id_emprunt from emprunt as emprunt1 where emprunt1.heureRetour is null and emprunt1.minRetour is null and emprunt1.id_kindel = 1 and (emprunt1.id_professeur=1 or emprunt1.id_etudiant=null) ORDER BY emprunt1.date_emprunt,emprunt1.heureEmprunt,emprunt1.minEmprunt LIMIT 1 ) as emprunt3\n" +
                "ON emprunt3.id_emprunt = emprunt2.id_emprunt "+
                " SET heureRetour=" + heureRetour +
                " , minRetour=" + minRetour;
        int nbUpdated = stmt4.executeUpdate(query);
        
         // STEP 5 update Position Kindel
        if(nbUpdated>0){
             Statement stmt = con.createStatement();
         stmt.executeUpdate(" UPDATE kindel SET emprunte=false WHERE id_kindel = " + idKindel);
         
        }
        return nbUpdated > 0;
   }
   
   public Emprunt findUsedKindelByCIN(Long idUser,Boolean isProf) throws ParseException, SQLException{
       Emprunt emprunt=null;
         // formater Date 
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
        java.util.Date dateStr = formatter.parse(LocalDate.now().toString());
        java.sql.Date dateEmpruntDB = new java.sql.Date(dateStr.getTime());
        String query="";
        Statement stmt = con.createStatement();
        if (isProf){
            query="SELECT emprunt.id_emprunt,modele,mac,pouces,emprunte,emprunt.id_kindel,date_emprunt,heureEmprunt,minEmprunt FROM kindel,emprunt WHERE emprunt.id_kindel=kindel.id_kindel and kindel.emprunte=1 and heureRetour is null and minRetour is null and date_emprunt='"+dateEmpruntDB+"' and id_professeur="+idUser+" order by date_emprunt,heureEmprunt,minEmprunt LIMIT 1";
        }
        else{
            query="SELECT emprunt.id_emprunt,modele,mac,pouces,emprunte,emprunt.id_kindel,date_emprunt,heureEmprunt,minEmprunt FROM kindel,emprunt WHERE emprunt.id_kindel=kindel.id_kindel and kindel.emprunte=1 and heureRetour is null and minRetour is null and date_emprunt='"+dateEmpruntDB+"' and id_etudiant="+idUser+" order by date_emprunt,heureEmprunt,minEmprunt LIMIT 1";
        }
                ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
              emprunt = new Emprunt(null, new Kindel(rs.getLong("id_kindel"),rs.getString("modele"),rs.getString("mac"),rs.getFloat("pouces"),rs.getBoolean("emprunte")), rs.getDate("date_emprunt"), rs.getInt("heureEmprunt"), rs.getInt("minEmprunt"), 0,0);
            //new Kindel(rs.getLong("id_kindel"),rs.getString("modele"),rs.getString("mac"),rs.getFloat("pouces"),rs.getBoolean("emprunte"));

            
    }
       return emprunt;
   }
    
}
