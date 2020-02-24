/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.desktopapplication;

import java.awt.Desktop.Action;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Carmine
 */
public class DesktopApplication extends Application {
     private static Stage GestioneVisitatori = null;
     private static Stage Login=null;
     private static GestioneVisitatori GestioneVisitatoriController;

     
    public static void btnpressed() {
        show_gestione();
    }
  
     @Override
    public void start(Stage stage) throws Exception {
    
        Login=stage;
        show_login(stage);
    }
    
    
      public static void closeLogin() {
        Login .close();
        Login = null;
    }
      
      
    public static void show_login(Stage stage) throws IOException{
    
    try{
     FXMLLoader root = getFxml("FXMLDocument");
         if (stage != null)
                Login = stage;
            else
        Login=loadStage(root);
        Login.setScene(new Scene(root.load()));
        Login.show();
    }catch(IOException e){
        System.out.println("Errore login");
    }
    }
    
   
 
  
   
    public static void main(String[] args) {
        launch(args);
    }
    
    private static FXMLLoader getFxml(String name) {
        return new FXMLLoader(DesktopApplication.class.getResource(
                String.format("%s.fxml", name)));
    }

    private static Stage loadStage(FXMLLoader loader) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        return stage;
    }
    
      private static void show_gestione(){
         
          try {
             
            FXMLLoader loader = getFxml("FXMLGestioneVisitatori");
            GestioneVisitatori = loadStage(loader);
           
          //  GestioneVisitatoriController = loader.getController();
            closeLogin();
            GestioneVisitatori.show();
        }
        catch (Exception e) {
            System.out.println(e);
    }
          
    }
}
