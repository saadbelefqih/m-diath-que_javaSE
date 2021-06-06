package com.mycompany.mediatheque.dao;


import com.mycompany.mediatheque.config.Config_DATABASE;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import com.mycompany.mediatheque.model.Document;
import com.mycompany.mediatheque.model.Livre;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
public class CRUD_Document {
    Connection con;
    public CRUD_Document() throws SQLException {
        
        
    con = DriverManager.getConnection(Config_DATABASE.db_URL,
            Config_DATABASE.username,
            Config_DATABASE.passwrd);
    }
    
//**************************************************** Document ********************************************************
    
    //VERIFIER EXISTE AUTEUR
    public Long getDocumentByTitle(String titre) throws SQLException{
        Long id_livre= null;
        if(Objects.nonNull(titre)){
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select id_livre from livre where titre='"+titre+"'");
            if(rs.next()) {
                id_livre= rs.getLong("id_livre");
              }
        }
        return id_livre;
    }
    
        //VERIFIER EXISTE AUTEUR
    public Long getDocumentByIdDoc(Long idDoc) throws SQLException{
        Long id_livre= null;
        if(Objects.nonNull(idDoc)){
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select id_livre from livre where id_livre="+idDoc);
            if(rs.next()) {
                id_livre= rs.getLong("id_livre");
              }
        }
        return id_livre;
    }
    
    public Boolean saveDocument(Document document) throws SQLException{
        if(Objects.nonNull(document) && getDocumentByTitle(document.getTitre())==null ){
            Statement stmt = con.createStatement();
            String query="";
            if(document instanceof Livre ){
                
                query = "INSERT INTO livre (nbPages, titre, editeur, edition, isbn, url) VALUES "
                        + "("+((Livre)document).getNbPages()
                        +", '"+document.getTitre()
                        +"', '"+document.getEditeur()
                        +"', "+document.getEdition()
                        +", '"+document.getIsbn()
                        +"', '"+document.getUrl()+"')";
            }
        int nbUpdated = stmt.executeUpdate(query);
        
        if(nbUpdated>0){
            return saveLigneDocument(document.getAuteurs(), document.getTitre())>0;}
        }
        saveLigneDocument(document.getAuteurs(), document.getTitre());
        return false;
    }
    
    
    public Boolean modifier(Document document) throws SQLException{
        if(Objects.nonNull(document)){
            Statement stmt = con.createStatement();
            String query="";
            if(document instanceof Livre){
                query=" UPDATE livre SET"+
                        "  nbPages="+((Livre)document).getNbPages()+
                        " , SET titre='"+document.getTitre()+
                        " , SET editeur='"+document.getEditeur()+
                        " , SET edition='"+document.getEdition()+
                        " , SET isbn='"+document.getIsbn()+
                        " , SET url='"+document.getUrl()+
                        " WHERE id_livre = "+document.getIdDoc()+" ";
                 int nbUpdated = stmt.executeUpdate(query);
                return nbUpdated>0;
            }
        }
        return false;
    }
    
    
    public boolean supprimer(Document document) throws SQLException{
        Statement stmt = con.createStatement();
        String query="";
    
    if (document instanceof Livre){
      query=" DELETE FROM livre WHERE id_livre ="+document.getIdDoc();
    }
    
    int nbUpdated = stmt.executeUpdate(query);
    return nbUpdated>0;
    }
//**************************************************** AUTEUR ********************************************************
    //VERIFIER EXISTE AUTEUR
    public Long searchAuteur(String auteurName) throws SQLException{
        Long resultat= null;
        if(Objects.nonNull(auteurName)){
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select auteur_id from auteur where nom_complet='"+auteurName+"'");
            if(rs.next()) {
                resultat= rs.getLong("auteur_id");
              }
        }
        return resultat;
    }
    
   // sauvgarder LOT AUTEURS
    public  int insertActeurs(String[] auteurs) throws SQLException{
         con.setAutoCommit(false);
        int len=auteurs.length;
        int count = 0;
        if(len>0){
            String SQL = "INSERT INTO auteur(nom_complet) "
                + "VALUES(?)";
            
            
            PreparedStatement statement = con.prepareStatement(SQL);
            for (int i = 0; i < len; i++) {
                if(searchAuteur(auteurs[i])==null){
                    statement.setString(1, auteurs[i]);
                    statement.addBatch();
                    count++;}
                
                // execute pour chaque 5 ligne ou moins
                if (count % 5 == 0 || i == len-1) {
                    statement.executeBatch();
                }
            }
            
        }
        con.setAutoCommit(true);
        return count;
    }
     
         
    public boolean supprimerAuteur(String auteurName) throws SQLException{
    Statement stmt = con.createStatement();
    String query="";
    Long id_Auteur=searchLigneDocumentByAuteur(auteurName);
    if(id_Auteur==null){
      query=" DELETE FROM auteur WHERE auteur_id ="+id_Auteur;
      int nbUpdated = stmt.executeUpdate(query);
    return nbUpdated>0;}
    return false;
    }
     
     
     
 //**************************************************** Ligne DOCUMENT ********************************************************
       //VERIFIER EXISTE AUTEUR IN DOC
    public Long checkExistLigneDoc(String auteurName, String titreDoc) throws SQLException{
        Long idAuteur=null;
        if(Objects.nonNull(auteurName) && Objects.nonNull(titreDoc)){
            Statement stmt1 = con.createStatement();
            ResultSet rs1 = stmt1.executeQuery("select auteur_id from vue_ligne_document where nom_complet ='"+auteurName+"' and titre = '"+titreDoc+"'");
            if(rs1.next()){
                idAuteur=rs1.getLong("auteur_id");
            }
        }
        return idAuteur;
    }
        //search DOC by DocTITLE
    public Livre searchLigneDocumentByDoc(String titreDoc) throws SQLException{
        Livre  livre = new Livre();
        ArrayList<String> auteurs= new ArrayList<>();
      if(Objects.nonNull(titreDoc)){
            Statement stmt1 = con.createStatement();
            ResultSet rs1 = stmt1.executeQuery("select * from livre where titre='"+titreDoc+"'");
            if(rs1.next()) {
                livre = new Livre(rs1.getLong("id_livre"), rs1.getString("titre"), rs1.getString("editeur"), rs1.getInt("edition"),rs1.getString("isbn"),null,rs1.getString("url"),rs1.getInt("nbPages"));
              }
        }
      if(Objects.nonNull(livre)){
          Statement stmt2 = con.createStatement();
            ResultSet rs2 = stmt2.executeQuery("SELECT nom_complet FROM auteur_livre,auteur WHERE auteur_livre.auteur_id=auteur.auteur_id and auteur_livre.id_livre="+livre.getIdDoc());
            while(rs2.next()) {
                auteurs.add(rs2.getString("nom_complet"));
              }
            livre.setAuteurs(auteurs.toArray(new String[0]));
      }
      
           
        return livre;
    }
    
            //search DOC by DocTITLE
    public ArrayList<Livre> getAllLivre() throws SQLException{
        ArrayList<Livre> livres = new ArrayList<>();
        Livre  livre = null;
        ArrayList<String> auteurs= new ArrayList<>();
      
            Statement stmt1 = con.createStatement();
            ResultSet rs1 = stmt1.executeQuery("select * from livre ");
            while(rs1.next()) {
                livre = new Livre(rs1.getLong("id_livre"), rs1.getString("titre"), rs1.getString("editeur"), rs1.getInt("edition"),rs1.getString("isbn"),null,rs1.getString("url"),rs1.getInt("nbPages"));
                    if(Objects.nonNull(livre)){
                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery("SELECT nom_complet FROM auteur_livre,auteur WHERE auteur_livre.auteur_id=auteur.auteur_id and auteur_livre.id_livre="+livre.getIdDoc());
                while(rs2.next()) {
                auteurs.add(rs2.getString("nom_complet"));
              }
                livre.setAuteurs(auteurs.toArray(new String[0]));
                livres.add(livre);
                livre = null;
                }
            
            }
        

      
           
        return livres;
    }

        //search DOC By Auteur
    public Long searchLigneDocumentByAuteur(String auteurName) throws SQLException{
        Long idAuteur=null;
      if(Objects.nonNull(auteurName)){
            Statement stmt1 = con.createStatement();
            ResultSet rs1 = stmt1.executeQuery("SELECT auteur.auteur_id FROM auteur_livre,auteur WHERE auteur_livre.auteur_id=auteur.auteur_id and auteur.nom_complet='"+auteurName+"'");
            if(rs1.next()) {
                idAuteur = rs1.getLong("auteur_id");}
        }
        return idAuteur;
    }
    

// save ligne DOCUMENT
    public int saveLigneDocument(String[] auteurs,String titre) throws SQLException{
         con.setAutoCommit(false);
        int len=auteurs.length;
        int count = 0;
        Long idDoc=getDocumentByTitle(titre);
        // verifier si document existe
        if(len>0 && idDoc!=null){
            String SQL = "INSERT INTO auteur_livre(auteur_id,id_livre) "
                + "VALUES(?,?)";
            PreparedStatement statement = con.prepareStatement(SQL);
            for (int i = 0; i < len; i++) {
                 // verifier si auteur existe sinon creer
                 String []p={auteurs[i]};
                    insertActeurs(p);
                    Long idAuteur =searchAuteur(auteurs[i]);
                if(idAuteur!=null && idDoc!=null && checkExistLigneDoc(auteurs[i], titre)==null ){
                    statement.setLong(1, idAuteur);
                    statement.setLong(2, idDoc);
                    statement.addBatch();
                    count++;
                }
                
                // execute pour chaque 5 ligne ou moins
                if (count % 5 == 0 || i == len-1) {
                    statement.executeBatch();
                }
            }
            
        }
        con.setAutoCommit(true);
        return count;
    }
     
    
    public boolean supprimerAuteurFromDocument(String auteurName,String titre) throws SQLException{
    Statement stmt = con.createStatement();
    String query="";
    Long id_Auteur=searchLigneDocumentByAuteur(auteurName);
    long id_doc =getDocumentByTitle(titre);
    if(id_Auteur!=null){
      query="DELETE auteur_livre FROM auteur_livre WHERE auteur_livre.auteur_id="+id_Auteur+" and auteur_livre.id_livre="+id_doc;
      int nbUpdated = stmt.executeUpdate(query);
    return nbUpdated>0;}
    return false;
    }
    
    
}
