package lk.ijse.pos.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ManageOrderController {
    public AnchorPane manageOrderContext;
    public Label lblTime;
    public Label lblDate;
    public TableView tblOrders;
    public TableColumn colOrderId;
    public TextField txtCustomerId;
    public TextField txtOrderId;

    public void initialize(){
        setDateTime();
    }

    private void setDateTime() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            lblTime.setText(LocalDateTime.now().format(formatter));

            SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            lblDate.setText(formatter2.format(date));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) manageOrderContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/loginPageForm.fxml"))));
    }

    public void searchOnAction(ActionEvent actionEvent) {
    }
}
