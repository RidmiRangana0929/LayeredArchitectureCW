package lk.ijse.pos.controller;

import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.custom.CustomerBO;
import lk.ijse.pos.bo.custom.PurchaseOrderBO;
import lk.ijse.pos.db.DBConnection;
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
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.OrderDTO;
import lk.ijse.pos.dto.OrderDetailsDTO;
import lk.ijse.pos.model.Customer;
import lk.ijse.pos.model.Order;
import lk.ijse.pos.model.OrderDetails;
import lk.ijse.pos.util.CrudUtil;
import lk.ijse.pos.views.Cart.CartTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DashBordCashierController {
    public TextField txtCustomerId;
    public TextField txtCustomerName;
    public TextField txtAddress;
    public TextField txtCity;
    public TextField txtProvince;
    public TextField txtPostalCode;
    public AnchorPane cashierContext;
    public AnchorPane dashBordCashierContext;
    public Label lblTime;
    public Label lblDate;
    public TextField txtTitle;
    public TextField txtOrderId;
    public TextField txtItemCode;
    public TextField txtQty;
    public TextField txtDiscount;
    public TextField txtPaymentMethod;
    public TextField txtTotal;
    public TableView tblCart;
    public TableColumn colItemCode;
    public TableColumn colQty;
    public TableColumn colPrice;
    public TextField txtSubTotal;
    public TextField txtUnitPrice;
    public TextField txtItemTotal;
    public AnchorPane itemDetailsContext;
    public AnchorPane listOfItemsContext;
    public TableColumn colDiscount;
    public TableColumn colOption;
    public TextField txtQtyOnHand;
    public ComboBox<String> cmbItemCode;

    private final CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PURCHASE_ORDER);

    public void initialize(){
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        setDateTime();
        setItemCodes();
    }

    private void setItemCodes() {
        try {

            cmbItemCode.setItems(FXCollections.observableArrayList(ItemCrudController.getItemIds()));

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*private void setItemDetails(String selectedItemCode) {
        try {

            Item i = ItemCrudController.getItem(selectedItemCode);
            if (i != null) {
                txtUnitPrice.setText(String.valueOf(i.getUnitPrice()));
                txtQtyOnHand.setText(String.valueOf(i.getQtyOnHand()));
            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/

    private void setDateTime() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            lblTime.setText(LocalDateTime.now().format(formatter));

            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            lblDate.setText(formatter2.format(date));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }


    public void updateOnAction(ActionEvent actionEvent) {

        try {
            customerBO.updateCustomer(new CustomerDTO(txtCustomerId.getText(),txtTitle.getText(),txtCustomerName.getText(),txtAddress.getText(),txtCity.getText(),txtProvince.getText(),txtPostalCode.getText()));
            new Alert(Alert.AlertType.CONFIRMATION,"Updated!").show();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.WARNING,"Update Fail!").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        /*Customer c=new Customer(
                txtCustomerId.getText(),txtTitle.getText(),txtCustomerName.getText(),txtAddress.getText(),txtCity.getText(),txtProvince.getText(),txtPostalCode.getText()
        );

        try {
            if(CrudUtil.execute("UPDATE customer SET CustTitle=?,CustName=?,CustAddress=?,City=?,Province=?,PostalCode=? WHERE CustID=? ",c.getCustTitle(),c.getCustName(),c.getCustAddress(),c.getCustName(),c.getProvince(),c.getPostalCode(),c.getCustID())){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated!").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Update Fail!").show();
            }

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }*/
    }

    public void saveOnAction(ActionEvent actionEvent) throws IOException {

        try {
            customerBO.saveCustomer(new CustomerDTO(txtCustomerId.getText(),txtTitle.getText(),txtCustomerName.getText(),txtAddress.getText(),txtCity.getText(),txtProvince.getText(),txtPostalCode.getText()));
            new Alert(Alert.AlertType.CONFIRMATION, "Customer Information saved Successfully!").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.WARNING, "Error! Please Try Again...").show();
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        /*Customer c=new Customer(
                txtCustomerId.getText(),txtTitle.getText(),txtCustomerName.getText(),txtAddress.getText(),txtCity.getText(),txtProvince.getText(),txtPostalCode.getText()
        );

        try {
            if(CrudUtil.execute("INSERT INTO super_market.customer VALUES (?,?,?,?,?,?,?)",c.getCustID(),c.getCustTitle(),c.getCustName(),c.getCustAddress(),c.getCity(),c.getProvince(),c.getPostalCode())){

                new Alert(Alert.AlertType.CONFIRMATION, "Customer Information saved Successfully!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Error! Please Try Again...").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }*/

        Parent parent= FXMLLoader.load(getClass().getResource("../views/dashBordCashier.fxml"));
        dashBordCashierContext.getChildren().add(parent);

    }

    public void deleteOnAction(ActionEvent actionEvent) throws IOException {

        try {
            customerBO.deleteCustomer(txtCustomerId.getText());
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted!").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.WARNING,"Delete Fail!").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        /*try {
            if(CrudUtil.execute("DELETE FROM customer WHERE CustID=?",txtCustomerId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted!").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Delete Fail!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }*/
        Parent parent= FXMLLoader.load(getClass().getResource("../views/dashBordCashier.fxml"));
        dashBordCashierContext.getChildren().add(parent);
    }

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) dashBordCashierContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/loginPageForm.fxml"))));
    }

    public void searchOnAction(ActionEvent actionEvent) {
        try {



            ResultSet result=CrudUtil.execute("SELECT * FROM customer WHERE CustID=?",txtCustomerId.getText());
            if(result.next()){
                txtTitle.setText(result.getString(2));
                txtCustomerName.setText(result.getString(3));
                txtAddress.setText(result.getString(4));
                txtCity.setText(result.getString(5));
                txtProvince.setText(result.getString(6));
                txtPostalCode.setText(result.getString(7));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void deleteItemOnAction(ActionEvent actionEvent) {
    }

    ObservableList<CartTM> tmList = FXCollections.observableArrayList();

    private CartTM isExists(String itemCode) {
        for (CartTM tm : tmList
        ) {
            if (tm.getItemCode().equals(itemCode)) {
                return tm;
            }
        }
        return null;
    }

    public void addToListOnAction(ActionEvent actionEvent) throws IOException {

        /*String itemCode = cmbItemCode.getSelectionModel().getSelectedItem();
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText()).setScale(2);
        int qty = Integer.parseInt(txtQty.getText());
        BigDecimal discount = new BigDecimal(txtDiscount.getText()).setScale(2);

        boolean exists = tblOrderDetails.getItems().stream().anyMatch(detail -> detail.getCode().equals(itemCode));

        if (exists) {
            OrderDetailTM orderDetailTM = tblOrderDetails.getItems().stream().filter(detail -> detail.getCode().equals(itemCode)).findFirst().get();

            if (btnSave.getText().equalsIgnoreCase("Update")) {
                orderDetailTM.setQty(qty);
                orderDetailTM.setTotal(total);
                tblOrderDetails.getSelectionModel().clearSelection();
            } else {
                orderDetailTM.setQty(orderDetailTM.getQty() + qty);
                total = new BigDecimal(orderDetailTM.getQty()).multiply(unitPrice).setScale(2);
                orderDetailTM.setTotal(total);
            }
            tblOrderDetails.refresh();
        } else {
            tblOrderDetails.getItems().add(new OrderDetailTM(itemCode, description, qty, unitPrice, total));
        }
        cmbItemCode.getSelectionModel().clearSelection();
        cmbItemCode.requestFocus();
        calculateTotal();
        enableOrDisablePlaceOrderButton();*/


        CartTM isExists = isExists((String) cmbItemCode.getValue());

        if (isExists != null) {
            for (CartTM temp : tmList
            ) {
                if (temp.equals(isExists)) {
                    temp.setQty((temp.getQty() + Integer.parseInt(txtQty.getText())));
                    temp.setDiscount(BigDecimal.valueOf(Double.valueOf(txtDiscount.getText())));
                }
            }
        } else {
            Button btn = new Button("Delete");

            CartTM tm = new CartTM(
                    cmbItemCode.getValue(),
                    Integer.parseInt(txtQty.getText()),
                    BigDecimal.valueOf(Double.valueOf(txtDiscount.getText())),
                    btn
            );

            btn.setOnAction(e -> {
                tmList.remove(tm);
            });

            tmList.add(tm);
            tblCart.setItems(tmList);
        }

        tblCart.refresh();

    }



    public void confirmOnAction(ActionEvent actionEvent, String orderId, String orderDate, String customerId, List<OrderDetailsDTO> orderDetails) throws SQLException {

        /*try {
            purchaseOrderBO.purchaseOrder(new OrderDTO(orderId,orderDate,customerId,orderDetails));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/

        /*boolean b = saveOrder(txtOrderId.getText(), lblDate.getText(), txtCustomerId.getText(),
                tblOrderDetails.getItems().stream().map(tm -> new OrderDetailDTO(orderId, tm.getCode(), tm.getQty(), tm.getUnitPrice())).collect(Collectors.toList()));
        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
        }*/

        /*try {
            txtOrderId.setText(OrderCrudController.getOrderId(1));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        Order order = new Order(
                txtOrderId.getText(),
                lblDate.getText(),
                txtCustomerId.getText()
        );
        ArrayList<OrderDetails> details = new ArrayList<>();
        for (CartTM tm : tmList
        ) {
            details.add(
                    new OrderDetails(
                            txtOrderId.getText(),
                            tm.getItemCode(),
                            tm.getQty(),
                            tm.getDiscount()
                    )
            );
        }

        //----------------------------

        Connection connection= null;

        try {
            connection= DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean isOrderSaved = new OrderCrudController().saveOrder(order);
            if (isOrderSaved) {
                boolean isDetailsSaved=new OrderCrudController().saveOrderDetails(details);
                if (isDetailsSaved){
                    connection.commit();
                    new Alert(Alert.AlertType.CONFIRMATION,"Saved!").show();
                }else{
                    connection.rollback();
                    new Alert(Alert.AlertType.ERROR,"Error!").show();
                }
            }else{
                connection.rollback();
                new Alert(Alert.AlertType.ERROR,"Error!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }finally {
            connection.setAutoCommit(true);
        }*/

    }

    public void cancelOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("../views/dashBordCashier.fxml"));
        dashBordCashierContext.getChildren().add(parent);
    }

    public void updateItemOnAction(ActionEvent actionEvent) {

        /*for (CartTM tm : tmList
        ) {
            if(cmbItemCode.getValue()==tm.getItemCode()){

                tm.setQty(Integer.parseInt(txtQty.getText()));
                tm.setDiscount(BigDecimal.valueOf(Double.parseDouble(txtDiscount.getText())));

            }
            tmList.add(tm);

        }
        tblCart.setItems(tmList);
        tblCart.refresh();*/

    }

    public void placeOrderOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("../views/dashBordCashier.fxml"));
        dashBordCashierContext.getChildren().add(parent);
    }

    public void manageOrderOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("../views/manageOrder.fxml"));
        cashierContext.getChildren().add(parent);
    }

    public void searchItemsOnAction(ActionEvent actionEvent) {
        try {
            ResultSet result= CrudUtil.execute("SELECT * FROM item WHERE itemCode=?",cmbItemCode.getValue());
            if(result.next()){
                txtUnitPrice.setText(result.getString(4));
                txtQtyOnHand.setText(String.valueOf(result.getBigDecimal(5)));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void calculateSubTotalOnAction(ActionEvent actionEvent) {
        int a= Integer.parseInt(txtQty.getText());
        double b=Double.parseDouble(txtUnitPrice.getText());
        double ab=a*b;
        BigDecimal c=BigDecimal.valueOf(ab);
        txtSubTotal.setText(String.valueOf(c));
    }

    public void setDiscountOnAction(ActionEvent actionEvent) {
        double a= Double.parseDouble(txtSubTotal.getText());
        double b= Double.parseDouble(txtDiscount.getText());
        double ab=a-b;
        BigDecimal c=BigDecimal.valueOf(ab);
        txtItemTotal.setText(String.valueOf(c));
    }

    public boolean saveOrder(String orderId, String orderDate, String customerId, List<OrderDetailsDTO> orderDetails) {
        try {
            return purchaseOrderBO.purchaseOrder(new OrderDTO(orderId, orderDate, customerId, orderDetails));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void confirmOrderOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            txtOrderId.setText(OrderCrudController.getOrderId(1));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        Order order = new Order(
                txtOrderId.getText(),
                lblDate.getText(),
                txtCustomerId.getText()
        );
        ArrayList<OrderDetails> details = new ArrayList<>();
        for (CartTM tm : tmList
        ) {
            details.add(
                    new OrderDetails(
                            txtOrderId.getText(),
                            tm.getItemCode(),
                            tm.getQty(),
                            tm.getDiscount()
                    )
            );
        }

        //----------------------------

        Connection connection= null;

        try {
            connection= DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean isOrderSaved = new OrderCrudController().saveOrder(order);
            if (isOrderSaved) {
                boolean isDetailsSaved=new OrderCrudController().saveOrderDetails(details);
                if (isDetailsSaved){
                    connection.commit();
                    new Alert(Alert.AlertType.CONFIRMATION,"Saved!").show();
                }else{
                    connection.rollback();
                    new Alert(Alert.AlertType.ERROR,"Error!").show();
                }
            }else{
                connection.rollback();
                new Alert(Alert.AlertType.ERROR,"Error!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }finally {
            connection.setAutoCommit(true);
        }
    }
}
