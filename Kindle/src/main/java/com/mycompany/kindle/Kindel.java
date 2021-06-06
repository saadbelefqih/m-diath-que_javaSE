package com.mycompany.kindle;


import com.mycompany.kindle.model.Livre;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class Kindel {
    
    public static void main (String args[]) throws IOException, ClassNotFoundException 
    { 
        
        String hote = "127.0.0.1" ;
        int port = 1234;
        Socket soc= new Socket(hote,port);
        
       InputStream flux1 = soc.getInputStream (); 
        BufferedReader entree = new BufferedReader (new InputStreamReader (flux1)) ; 
        
        OutputStream flux2 = soc.getOutputStream() ; 
        OutputStreamWriter sortie = new OutputStreamWriter (flux2); 
        
        // saisire genre
        String genre=entree.readLine();
        System.out.println("Server: "+genre);
        Scanner sc= new Scanner(System.in);
        genre=sc.nextLine();
        sortie.write(genre+"\n"); 
        sortie.flush();
        
        // saisire login
        String login=entree.readLine();
        System.out.println("Server: "+login);
        sc= new Scanner(System.in);
        login=sc.nextLine();
        sortie.write(login+"\n"); 
        sortie.flush();
        
        // saisire password
        String pwrd=entree.readLine();
        System.out.println("Server: "+pwrd);
        sc= new Scanner(System.in);
        pwrd=sc.nextLine();
        sortie.write(pwrd+"\n"); 
        sortie.flush();
        
        // Resulat Cnx User
        String userCnx=entree.readLine();
        System.out.println("Server: "+userCnx);
        
        // Resulat Cnx Kindel
        String heureEmpruntMin=entree.readLine();
        System.out.println("Server: "+heureEmpruntMin);
        
        Long heureEmprunt=new Long(heureEmpruntMin);
        Long retourntHours = heureEmprunt+(3*60);
        
        
        // CHARGER DOCUMENT 
        List<String> ls = new ArrayList<>();
        InputStream inputStream = soc.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        ls = (List<String>) objectInputStream.readObject();
        
        // MENU CHOIX
        boolean exit=false;
        LocalTime now = LocalTime.now();
        Long cuurent = new Long(now.getHour()*60+now.getMinute());
        do
    {
         System.out.println("heure Retourn en Min   : "+retourntHours);
          System.out.println("heure Current en Min  : "+cuurent);
        LocalTime now1 = LocalTime.now();
        cuurent= new Long(now1.getHour()*60+now1.getMinute());
        System.out.println(" \n  : Menu");
      System.out.println("1.Consulter Document");
      System.out.println("2.exit");
      System.out.println("choose one!");
      Scanner sd=new Scanner(System.in);
      System.out.println("enter your choice");
      int num=sd.nextInt();
            switch(num)
           {
                case 1:
                    System.out.println("Veuillez choisir document à lire \n");
               ls.forEach(l->{System.out.println(l.toString());});
                Scanner scDoc= new Scanner(System.in);
                   String docToRead=scDoc.nextLine();
                   String doc=null; 
                    for (int i = 0; i < ls.size(); i++) {
                        if(ls.get(i).contains(docToRead)){
                            doc = ls.get(i);
                        }
                    }
                    System.out.println("Lire documeent.....  \n");
                    System.out.println(doc+" \n");
                break;

                case 2:
                exit=true;
                break;
               }
            }while(!exit || (cuurent>retourntHours));
         
         soc.close();
        
        
    }
    
}
