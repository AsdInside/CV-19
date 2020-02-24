/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.desktopapplication;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Carmine
 */
public class GestioneVisitatori {
    private static Stage ListaUtenti;
    private static Stage  Segnalazioni;
    private static Stage Cancellazioni;
    
    
    public static void clicksegnalazioni() {
        show_segnalazioni();
    }
    

    public static void clickiscritti() {
        show_iscritti();
        
    }

    public static void clickcancellazioni() {
        show_cancellazioni();
    }
    
    
    
    private static void show_iscritti(){
         try {
             
            FXMLLoader loader = getFxml("FXMListaUtenti");
            ListaUtenti = loadStage(loader);
           
          //  GestioneVisitatoriController = loader.getController();
            
            ListaUtenti.show();
        }
        catch (Exception e) {
            System.out.println(e);
    }
         
         
    }
    
      private static void show_cancellazioni(){
         try {
             
            FXMLLoader loader = getFxml("FXMLCancellazioni");
            Cancellazioni = loadStage(loader);
           
          //  GestioneVisitatoriController = loader.getController();
            
            Cancellazioni.show();
        }
        catch (Exception e) {
            System.out.println(e);
    }
         
         
    }
    
      private static void show_segnalazioni(){
         try {
             
            FXMLLoader loader = getFxml("FXMLSegnalazioni");
            Segnalazioni = loadStage(loader);
           
          //  GestioneVisitatoriController = loader.getController();
            
            Segnalazioni.show();
        }
        catch (Exception e) {
            System.out.println(e);
    }
    }
    
        private static FXMLLoader getFxml(String name) {
        return new FXMLLoader(GestioneVisitatori.class.getResource(
                String.format("%s.fxml", name)));
    }

    private static Stage loadStage(FXMLLoader loader) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        return stage;
    }
   
   
}
