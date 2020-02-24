package Controller;

import com.desktopapplication.DesktopApplication;
import com.desktopapplication.GestioneVisitatori;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class FXMLDocumentController {
  private static Stage GestioneVisitatori = null;
    
     private static GestioneVisitatori GestioneVisitatoriController;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private PasswordField passwd;
    @FXML
   public void btnpressed(ActionEvent event) {
      DesktopApplication.btnpressed();
       
     
    }

    @FXML
    void initialize() {

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
