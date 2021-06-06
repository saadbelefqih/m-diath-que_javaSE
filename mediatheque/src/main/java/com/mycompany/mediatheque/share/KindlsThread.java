/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mediatheque.share;

import com.mycompany.mediatheque.config.Config_DATABASE;
import com.mycompany.mediatheque.dao.CRUD_Client;
import com.mycompany.mediatheque.dao.CRUD_Document;
import com.mycompany.mediatheque.dao.CRUD_Emprunt;
import com.mycompany.mediatheque.model.Emprunt;
import com.mycompany.mediatheque.model.Etudiant;
import com.mycompany.mediatheque.model.Kindel;
import com.mycompany.mediatheque.model.Livre;
import com.mycompany.mediatheque.model.Professeur;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class KindlsThread extends Thread{
    
       Socket soc;
       CRUD_Client clientService ;
       CRUD_Emprunt empruntService;
       CRUD_Document documentService;
    Connection con;
    public  KindlsThread(Socket socket) throws SQLException{
        this.soc=socket;
        con = DriverManager.getConnection(Config_DATABASE.db_URL,
            Config_DATABASE.username,
            Config_DATABASE.passwrd);
        clientService = new CRUD_Client();
        empruntService = new CRUD_Emprunt();
        documentService = new CRUD_Document();
    }
    
    
    

    @Override
    public void run() {
        
        Professeur professeur = null;
        Etudiant etudiant = null;
        Boolean isProf=false;
         // Declare Flux 
           try {
                InputStream flux1 = soc.getInputStream();
                BufferedReader entree = new BufferedReader (new InputStreamReader (flux1)) ;
               
               OutputStream flux2 = soc.getOutputStream() ;
               OutputStreamWriter sortie = new OutputStreamWriter (flux2) ;
               
        // authentification
            System.out.println("SERVER : Identification Utilisateur en cours ..... ");
            sortie.write("Vous étes Etudient: < E >  ou Professeur: < P >\n");
            sortie.flush();
            String genre = entree.readLine();
            
            sortie.write("Veuillez saisir votre login\n");
            sortie.flush();
            String login = entree.readLine();
            
            sortie.write("Veuillez saisir votre Mot de passe \n");
            sortie.flush();
            String psw = entree.readLine();
            
            if(genre.contains("P")){
                professeur  =  clientService.authenProf(login, psw);
            }
            else if (genre.contains("E")){
                etudiant= clientService.authenEtudiant(login, psw);
            }
            else{ 
                System.out.println("SERVER : erreur Genre non Renseigner  ");
                soc.close();
                flux1.close();
                flux2.close();}
            
            if(Objects.nonNull(professeur)){
                isProf=true;
                sortie.write("Bienvenue Pr. "+professeur.getNom()+" " + professeur.getPrenom()+" \n");
                sortie.flush();
            }
            else if (Objects.nonNull(etudiant)){
                sortie.write("Bienvenue Mr "+etudiant.getNom()+" " + etudiant.getPrenom()+" \n");
                sortie.flush();
                isProf=false;
            }
            else {
                System.out.println("SERVER : Echec Authentification  ");
                sortie.write("SERVER : Echec Authentification \n");
                sortie.flush();
                soc.close();
                flux1.close();
                flux2.close();
            }
        // Idenfification Kendel 
               System.out.println("SERVER : Identification Kindel en cours ..... ");
               Emprunt emprunt = null;
               
               
               if(isProf){
                   emprunt = empruntService.findUsedKindelByCIN(professeur.getIdUser(), true);
               } 
               else{
                   emprunt = empruntService.findUsedKindelByCIN(etudiant.getIdUser(), false);
               }
               
               if(Objects.isNull(emprunt)){
                   System.out.println("SERVER : Echec Authentification Emprunt "+professeur.getIdUser());
                   sortie.write("SERVER : Vous ne figure plus dans la liste des emprunteurs \n");
                   sortie.flush();
                   soc.close();
                   flux1.close();
                   flux2.close();
               }
               else{
                   System.out.println("kindel "+emprunt.getHeureEmprunt());
                   sortie.write(emprunt.getHeureEmprunt()*60+emprunt.getMinEmprunt() +"\n");
                   sortie.flush();
               }
            // CHARGER DOC
            OutputStream outputStream = soc.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
               List<Livre> livres = new ArrayList<>();
               List<String> ls = new ArrayList<>();
               livres= documentService.getAllLivre();
               livres.forEach(l->{
                   ls.add(l.toString());
               });
            // envoyer List document                  
               objectOutputStream.writeObject(ls);                
           } catch (IOException ex) {
               Logger.getLogger(KindlsThread.class.getName()).log(Level.SEVERE, null, ex);
           } catch (SQLException ex) {
               Logger.getLogger(KindlsThread.class.getName()).log(Level.SEVERE, null, ex);
           } catch (ParseException ex) {
               Logger.getLogger(KindlsThread.class.getName()).log(Level.SEVERE, null, ex);
           }
            
    }
    
    
}
