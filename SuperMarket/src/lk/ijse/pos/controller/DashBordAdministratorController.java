package lk.ijse.pos.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.custom.ItemBO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.model.Item;
import lk.ijse.pos.util.CrudUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class DashBordAdministratorController {
    //public AnchorPane dashBordCashierContext;
    public Label lblMost;
    public Label lblLeast;
    public Label lblTime;
    public Label lblDate;
    public TextField txtItemCode;
    public TextField txtDescription;
    public TextField txtPackSize;
    public TextField txtUnitPrice;
    public Label lblAnnual;
    public Label lblMonthly;
    public Label lblDaily;
    public TableView tblItem;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colPackSize;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public TextField txtQtyOnHand;
    public AnchorPane dashBordAdministratorContext;
    public AnchorPane administratorContext;

    private ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    public void initialize(){

        colItemCode.setCellValueFactory(new PropertyValueFactory("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory("qtyOnHand"));


        try {
            loadAllItems();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
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

    private void loadAllItems() throws SQLException, ClassNotFoundException {

        tblItem.getItems().clear();
        try {
            /*Get all items*/
            ArrayList<ItemDTO> allItems = itemBO.getAllItems();
            for (ItemDTO item : allItems) {
                tblItem.getItems().add(new ItemDTO(item.getItemCode(), item.getDescription(), item.getPackSize(), item.getUnitPrice(), item.getQtyOnHand()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        /*ResultSet result=CrudUtil.execute("SELECT * FROM Item");

        ObservableList<Item> obList= FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new Item(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getBigDecimal(4),
                            result.getInt(5)
                    )
            );
        }
        tblItem.setItems(obList);*/
    }


    public void updateOnAction(ActionEvent actionEvent) throws IOException {

        try {
            itemBO.updateItem(new ItemDTO(txtItemCode.getText(),txtDescription.getText(),txtPackSize.getText(),BigDecimal.valueOf(Double.parseDouble(txtUnitPrice.getText())),Integer.parseInt(txtQtyOnHand.getText())));
            new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
            //tblItems.getItems().add(new ItemTM(code, description, unitPrice, qtyOnHand));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.WARNING, "Update Fail!").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        /*Item i = new Item(
                txtItemCode.getText(),txtDescription.getText(),txtPackSize.getText(),BigDecimal.valueOf(Double.parseDouble(txtUnitPrice.getText())),Integer.parseInt(txtQtyOnHand.getText())
        );
        try {
            if(CrudUtil.execute("UPDATE Item SET Description=?,PackSize=?,UnitPrice=?,QtyOnHand=? WHERE ItemCode=? ",i.getDescription(),i.getPackSize(),i.getUnitPrice(),i.getQtyOnHand(),i.getItemCode())){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated!").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Update Fail!").show();
            }

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }*/
        Parent parent= FXMLLoader.load(getClass().getResource("../views/dashBordAdministrator.fxml"));
        dashBordAdministratorContext.getChildren().add(parent);
    }

    public void saveOnAction(ActionEvent actionEvent) throws IOException {

        try {
            itemBO.saveItem(new ItemDTO(txtItemCode.getText(),txtDescription.getText(),txtPackSize.getText(),BigDecimal.valueOf(Double.parseDouble(txtUnitPrice.getText())),Integer.parseInt(txtQtyOnHand.getText())));
            new Alert(Alert.AlertType.CONFIRMATION, "Item Information saved Successfully!").show();
            //tblItems.getItems().add(new ItemTM(code, description, unitPrice, qtyOnHand));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.WARNING, "Error! Please Try Again...").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        /*Item i = new Item(
                txtItemCode.getText(),txtDescription.getText(),txtPackSize.getText(), BigDecimal.valueOf(Double.valueOf(txtUnitPrice.getText())), Integer.valueOf(txtQtyOnHand.getText())
        );
        try {
            if (CrudUtil.execute("INSERT INTO super_market.item VALUES (?,?,?,?,?)",i.getItemCode(),i.getDescription(),i.getPackSize(),i.getUnitPrice(),i.getQtyOnHand())){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved...").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Error! Please Try Again...").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }*/


        Parent parent= FXMLLoader.load(getClass().getResource("../views/dashBordAdministrator.fxml"));
        dashBordAdministratorContext.getChildren().add(parent);

    }

    public void deleteOnAction(ActionEvent actionEvent) throws IOException {

        try {
            itemBO.deleteItem(txtItemCode.getText());
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
            //tblItems.getItems().add(new ItemTM(code, description, unitPrice, qtyOnHand));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.WARNING,"Delete Fail!").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        /*try {
            if(CrudUtil.execute("DELETE FROM Item WHERE ItemCode=?",txtItemCode.getText())){
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted!").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Delete Fail!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }*/
        Parent parent= FXMLLoader.load(getClass().getResource("../views/dashBordAdministrator.fxml"));
        dashBordAdministratorContext.getChildren().add(parent);
    }

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) dashBordAdministratorContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/loginPageForm.fxml"))));

    }

    public void searchOnAction(ActionEvent actionEvent) {
        try {
            ResultSet result=CrudUtil.execute("SELECT * FROM item WHERE ItemCode=?",txtItemCode.getText());
            if(result.next()){
                txtDescription.setText(result.getString(2));
                txtPackSize.setText(result.getString(3));
                txtUnitPrice.setText(String.valueOf(result.getBigDecimal(4)));
                txtQtyOnHand.setText(String.valueOf(result.getInt(5)));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
