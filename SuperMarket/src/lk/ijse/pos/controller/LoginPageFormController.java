package lk.ijse.pos.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPageFormController {
    public AnchorPane loginPageContext;
    public TextField txtUsername;
    public PasswordField pwdPassword;

    public void loginOnAction(ActionEvent actionEvent) {
        try {
            loginFormManage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loginFormManage() throws IOException {
        if(txtUsername.getText().equals("a")&&pwdPassword.getText().equals("1")){
            Stage window = (Stage) loginPageContext.getScene().getWindow();
            window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/dashBordCashier.fxml"))));
        }else if(txtUsername.getText().equals("b")&&pwdPassword.getText().equals("2")){
            Stage window = (Stage) loginPageContext.getScene().getWindow();
            window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/dashBordAdministrator.fxml"))));
        }


        /*String user = "abc";
        String password = "1234";
        if(txtUsername.getText().equals(user) && pwdPassword.getText().equals(password)){
            Stage window = (Stage) loginPageContext.getScene().getWindow();
            window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/dashBordAdministrator.fxml"))));
        }else{
            new Alert(Alert.AlertType.WARNING,"Login fail...! Check your username and password.").show();
            txtUsername.clear();
            pwdPassword.clear();
        }*/
    }
}
