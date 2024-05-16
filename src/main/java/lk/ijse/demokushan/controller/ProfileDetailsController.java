package lk.ijse.demokushan.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileDetailsController {

    public JFXButton Btnback;

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {


        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.Btnback.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login Form");

    }

}
