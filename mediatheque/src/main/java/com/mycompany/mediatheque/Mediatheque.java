package com.mycompany.mediatheque;



import com.mycompany.mediatheque.dao.CRUD_Document;
import com.mycompany.mediatheque.dao.CRUD_Emprunt;
import com.mycompany.mediatheque.dao.CRUD_Kindel;
import com.mycompany.mediatheque.model.Document;
import com.mycompany.mediatheque.model.Kindel;
import com.mycompany.mediatheque.model.Livre;
import java.sql.SQLException;
import com.mycompany.mediatheque.model.Professeur;
import com.mycompany.mediatheque.share.MainThread;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rachad
 */
public class Mediatheque {
    
    public static void main(String[] args) throws SQLException, ParseException{
         // Create Thread for receive Data From capteur
         MainThread mainThread = new MainThread();
            mainThread.start();
            CRUD_Kindel cRUD_Kindel = new CRUD_Kindel();
            
    boolean exit=false;
        do
    {
         
      System.out.println("1.Ajouter Kindel");
      System.out.println("2.Modifier Kindel");
      System.out.println("3.Supprimer Kindel");
      System.out.println("4.Rechercher Kindel");
      System.out.println("5.Ajouter Document");
      System.out.println("6.Ajouter Emprunt");
      System.out.println("7.exit");
      System.out.println("choose one!");
      Scanner sd=new Scanner(System.in);
      System.out.println("enter your choice");
      int num=sd.nextInt();
            switch(num)
           {
                
                case 1:
                    System.out.println("1.Ajouter Kindel \n");
                    System.out.println(" ETAP 1.Saisir Model \n");
                    Scanner sd1=new Scanner(System.in);
                    String model=sd1.nextLine();
                    System.out.println(" ETAP 2.Saisir mac \n");
                    sd1=new Scanner(System.in);
                    String mac=sd1.nextLine();
                    System.out.println(" ETAP 3.Saisir pouces \n");
                    sd1=new Scanner(System.in);
                    String pouces1=sd1.nextLine();
                    float pouces=Float.parseFloat(pouces1);
                     boolean a =false;
                  
                    a=cRUD_Kindel.save(new Kindel(null,model,mac,pouces,false));
                    if(a){
                        System.out.println(" Bien Effectueé \n");}
                    else{
                        System.out.println(" Failed Opération \n");}
                     break;
                case 2:
                    System.out.println("2.Modifier Kindel \n");
                    System.out.println(" ETAP 1.Saisir mac addresse \n");
                    Scanner sd2=new Scanner(System.in);
                    String macAncian=sd2.nextLine();
                    System.out.println(" ETAP 2.Saisir Nouveau Model \n");
                    sd2=new Scanner(System.in);
                    String nVmodel=sd2.nextLine();
                    System.out.println(" ETAP 3.Saisir Nouveau pauce \n");
                    sd2=new Scanner(System.in);
                    String nVpouces1=sd2.nextLine();
                    float nVpouces=Float.parseFloat(nVpouces1);
                    boolean a1 =false;
                    a1=cRUD_Kindel.modifier(new Kindel(null,nVmodel,macAncian,nVpouces,false));
                    if(a1){
                        System.out.println(" Bien Effectueé \n");}
                    else{
                        System.out.println(" Failed Opération  \n");}
                     break;
                case 3:
                    System.out.println("3.Supprimer Kindel");
                    System.out.println(" ETAP 1.Saisir mac addresse \n");
                    Scanner sd3=new Scanner(System.in);
                    String macDelete=sd3.nextLine();
                    
                    boolean a2 =false;
                    a2=cRUD_Kindel.supprimer(macDelete);
                    if(a2){
                        System.out.println(" Bien Effectueé \n");}
                    else{
                        System.out.println(" Failed Opération  \n");}
                    break;
                case 4:
                    System.out.println("4.Rechercher Kindel \n");
                    System.out.println(" ETAP 1.Saisir mac addresse \n");
                    Scanner sd4=new Scanner(System.in);
                    String macSearc=sd4.nextLine();
                   boolean a3 =false;
                    a3=cRUD_Kindel.getOneKindleByMac(macSearc);
                    if(a3){
                        System.out.println(" Bien Trouvé \n");}
                    else{
                        System.out.println(" n'exsiste pas  \n");}
                    break;
                    
                    
                case 7:
                exit=true;
                break;
               }
            }while(!exit);
            
            
        //CRUD_Document cRUD_Document= new CRUD_Document();
       //String auteur[]={"SAAD BELEFQIH","YASSINE BELEFQIH"};
       
       //Long idDonc,String titre, String editeur,int edition,String isbn,String[] auteurs, String url,  int nbPages
       //Livre livre= new Livre(null, "lile au tresore", "saad", 0, "14750", auteur, "Lile.com",14);
       
       // Long idKindel,String modele, String mac, float pouces,boolean emprunte) 
       //List<String> auteurss= new ArrayList<>(Arrays.asList(cRUD_Document.searchLigneDocumentByDoc("lile au tresore").getAuteurs()));
        
        //auteurss.forEach(a->{System.out.println("rabat "+a);});
        
        //cRUD_Document.supprimerAuteurFromDocument("SAAD BELEFQIH", "lile au tresore");
        
        
         //List<String> auteurss1= new ArrayList<>(Arrays.asList(cRUD_Document.searchLigneDocumentByDoc("lile au tresore").getAuteurs()));
        
        //auteurss1.forEach(a->{System.out.println("rabat 2 "+a);});
        
        
        //CRUD_Kindel cRUD_Kindel = new CRUD_Kindel();
        //cRUD_Kindel.save(new Kindel(null, "HP", "0FF:475:411", 7, true));
        
       //cRUD_Document.saveDocument(livre);
       
       
        //CRUD_Emprunt cRUD_Emprunt = new CRUD_Emprunt();
       //cRUD_Emprunt.empunterKindel("a1258", null, "0FF:475:411", "2021-06-04",19 , 23);
       //cRUD_Emprunt.rendreKindel("a1258", null, "0FF:475:411", 20, 53);
       /* 
       CRUD_Client CRL= new CRUD_Client();
       Professeur p= new Professeur("professeur1", "123456", "a1258", "saad", "belefqih", "123985");
       System.out.println("ICI getEtudiantbyCin 1");
       System.out.println(p.toString());
       CRL.Ajouter(p);
       System.out.println("ICI getEtudiantbyCin 1");
       System.out.println(CRL.getEtudiantbyCin("A875"));
       CRL.supprimer(new Etudiant("myClient","123456","A875","nom","prenom","85751"));
       System.out.println("ICI getEtudiantbyCin 2");
       System.out.println(CRL.getEtudiantbyCin("A875"));*/
       
       
       
    }
    
}
