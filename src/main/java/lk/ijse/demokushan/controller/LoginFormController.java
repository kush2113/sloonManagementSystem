package lk.ijse.demokushan.controller;

//import com.sun.javafx.tk.quantum.PaintRenderJob;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.demokushan.Util.Regex;

import java.io.IOException;

public class LoginFormController {


    public TextField txtName;
    public TextField txtPassword;
    public AnchorPane rootNode;
    public ImageView icon1;
    public ImageView icon2;

    public boolean isValied() {
        boolean useNameValied = Regex.setTextColor(lk.ijse.demokushan.Util.TextField.NAME, txtName);
        boolean passwordValid = Regex.setTextColor(lk.ijse.demokushan.Util.TextField.PASSWORD, txtPassword);


        return useNameValied && passwordValid;

    }

    @FXML
    boolean btnLoginOnAction(ActionEvent actionEvent) throws IOException {

        if (isValied()) {

            String name = "Kushan";
            String pass = "12345";

            String userId = txtName.getText();
            String pw = txtPassword.getText();


            if (userId.equals(name) && pass.equals(pw)) {
                navigateToTheDashboard();
            } else {
                new Alert(Alert.AlertType.ERROR, "password is wrong ").show();
            }
        } else {
            // Show error message if validation fails
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Validation Failed");
            alert.setContentText("Please fill in all fields correctly.");
            alert.showAndWait();
        }

            return false;

}


    public void userKeyReleaseOnAction(javafx.scene.input.KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.demokushan.Util.TextField.NAME, txtName);
    }

    public void passwordKeyReleaseOnAction(javafx.scene.input.KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.demokushan.Util.TextField.PASSWORD, txtPassword);
    }

        private void navigateToTheDashboard () throws IOException {

            AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

            Scene scene = new Scene(rootNode);

            Stage stage = (Stage) this.rootNode.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();

            stage.setTitle("Dashboard");

        }


        public void btnNameOnAction (KeyEvent actionEvent) throws IOException {


            String name = "Kushan";
            String pass = "12345";

            String userId = txtName.getText();
            String pw = txtPassword.getText();


            if (userId.equals(name) && pass.equals(pw)) {
                navigateToTheDashboard();
            } else {
                new Alert(Alert.AlertType.ERROR, "password is wrong ").show();
            }

        }

        public void btnPasswordOnAction (ActionEvent actionEvent) throws IOException {
            String name = "Kushan";
            String pass = "12345";
//
//            if (name.contains("Kushan")&pass.contains("1234")){
//                System.out.println("done");
//            }

            String userId = txtName.getText();
            String pw = txtPassword.getText();




            if (userId.equals(name) && pass.equals(pw)) {
                navigateToTheDashboard();
            } else {
                new Alert(Alert.AlertType.ERROR, "password is wrong ").show();
            }
        }


    }


