package lk.ijse.demokushan.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.demokushan.Util.Regex;
import lk.ijse.demokushan.model.Customer;
import lk.ijse.demokushan.model.Supplier;
import lk.ijse.demokushan.model.TM.CustomerTM;
import lk.ijse.demokushan.repository.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {


    public ComboBox cmbStatus;
    public TableColumn colStatus;
    public TextField txtSearch;
    @FXML
    private TableView<CustomerTM> TbleCustomer;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colNumber;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhoneNumber;

    public void initialize() {

        setcellValues();
        loadAllCustomers();
        loadCmbStatus();
        genarateNextCustomerId();

        ObservableList<String> statusOptions = FXCollections.observableArrayList("Complete", "Incomplete");
        cmbStatus.setItems(statusOptions);

        showSelectedProductDetails();

    }

    private void showSelectedProductDetails() {
        CustomerTM selectedUser = TbleCustomer.getSelectionModel().getSelectedItem();
        TbleCustomer.setOnMouseClicked(event -> showSelectedProductDetails());
        if (selectedUser != null) {
            txtName.setText(selectedUser.getName());
            txtAddress.setText(selectedUser.getAddress());
            txtPhoneNumber.setText(selectedUser.getPhoneNumber());
            txtEmail.setText(selectedUser.getEmail());
            cmbStatus.setValue(selectedUser.getStatus());
            txtId.setText(selectedUser.getCustomerId());
        }
    }

    private void genarateNextCustomerId() {
        try {
            String currentId = CustomerRepo.getCurrentId();

            String nextOrderId = genarateNextCustomerId(currentId);
            txtId.setText(nextOrderId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private String genarateNextCustomerId(String currentId) {
        if (currentId != null && currentId.matches("C\\d{3}")) {
            int idNum = Integer.parseInt(currentId.substring(2));
            return "C" + String.format("%03d", idNum + 1);
        } else {
            return "C001";
        }
    }
    private void loadCmbStatus() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try {

            List<String> nameList = CustomerRepo.getStatus();

            for (String code : nameList) {
                obList.add(code);
            }
            cmbStatus.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadAllCustomers() {

        ObservableList<CustomerTM> obList = FXCollections.observableArrayList();

        try {
            List<Customer> cusList = CustomerRepo.getAll();
            for (Customer cusModle : cusList) {

                CustomerTM TM = new CustomerTM(cusModle.getCustomerId(), cusModle.getName(), cusModle.getPhoneNumber(), cusModle.getAddress(), cusModle.getEmail(),cusModle.getStatus());

                obList.add(TM);
                TbleCustomer.setItems(obList);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    private void setcellValues() {

        colId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        genarateNextCustomerId();

    }
    private void clearFields() {

        txtSearch.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtPhoneNumber.setText("");
        txtEmail.setText("");
        cmbStatus.setValue("");
    }

    public boolean isValied(){
        boolean idValied = Regex.setTextColor(lk.ijse.demokushan.Util.TextField.CUSID, txtId);
        boolean nameValid = Regex.setTextColor(lk.ijse.demokushan.Util.TextField.NAME, txtName);
        boolean validAddress = Regex.setTextColor(lk.ijse.demokushan.Util.TextField.ADDRESS, txtAddress);
        boolean validPhone = Regex.setTextColor(lk.ijse.demokushan.Util.TextField.PHONENUMBER, txtPhoneNumber);
        boolean email = Regex.setTextColor(lk.ijse.demokushan.Util.TextField.EMAIL, txtEmail);



        return nameValid && idValied && validAddress && validPhone && email;
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {

        if(isValied()){

            String id = txtId.getText();
            String name = txtName.getText();
            String phoneNumber = txtPhoneNumber.getText();
            String address = txtAddress.getText();
            String email = txtEmail.getText();
            String status = (String) cmbStatus.getValue();

            if (status == null) {
                new Alert(Alert.AlertType.ERROR, "Please select a status").show();
                return;
            }

            List<String>statusList = CustomerRepo.getStatus(status);
            System.out.println(statusList.get(0));

            Customer customer = new Customer(id, name, phoneNumber, address, email,status);

            try {
                boolean isSaved = CustomerRepo.save(customer);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();

                    initialize();
                    clearFields();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Validation Failed");
            alert.setContentText("Please fill in all fields correctly.");
            alert.showAndWait();
        }

    }


    public void idKeyReleaseOnAction(javafx.scene.input.KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.demokushan.Util.TextField.CUSID, txtId);
    }
    public void nameKeyReleaseAction(javafx.scene.input.KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.demokushan.Util.TextField.NAME, txtName);
    }
    public void numberKeyReleaseOnAction(javafx.scene.input.KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.demokushan.Util.TextField.PHONENUMBER, txtPhoneNumber);
    }
    public void addressKeyReleaseAction(javafx.scene.input.KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.demokushan.Util.TextField.ADDRESS, txtAddress);
    }
    public void emailKeyReleaseAction(javafx.scene.input.KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.demokushan.Util.TextField.EMAIL, txtEmail);
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) throws SQLException {

        if(isValidIde()){

        String id = txtId.getText();

        Customer customer = CustomerRepo.searchById(id);
        if (customer != null) {
            txtId.setText(customer.getCustomerId());
            txtName.setText(customer.getName());
            txtPhoneNumber.setText(customer.getPhoneNumber());
            txtAddress.setText(customer.getAddress());
            txtEmail.setText(customer.getEmail());
            cmbStatus.setValue(customer.getStatus());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
        }
        } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Validation Error");
                alert.setHeaderText("Validation Failed");
                alert.setContentText("Please enter valid Customer ID correctly.");
                alert.showAndWait();
            }
        }

        public boolean isValidIde(){
            boolean idValied = Regex.setTextColor(lk.ijse.demokushan.Util.TextField.CUSID, txtId);

            return idValied ;
        }


    @FXML
    void btnUpadateOnAction(ActionEvent event) {
        if(isValied()){

            String id = txtId.getText();
            String name = txtName.getText();
            String phoneNumber = txtPhoneNumber.getText();
            String address = txtAddress.getText();
            String email = txtEmail.getText();
            String status = (String) cmbStatus.getValue();

            Customer customer = new Customer(id, name, phoneNumber, address,email,status);

            try {
                boolean isUpdated = CustomerRepo.update(customer);
                if(isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();

                    initialize();
                    clearFields();

                }else {
                    new Alert(Alert.AlertType.CONFIRMATION, "customer not updated!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Validation Failed");
            alert.setContentText("Please fill in all fields correctly.");
            alert.showAndWait();
        }
    }


    public void btnDeleteOnAction(ActionEvent actionEvent) {
        if(isValidId()){

        String id = txtId.getText();

        try {
            boolean isDeleted = CustomerRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer deleted!").show();

                initialize();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        } else {
            // Show error message if validation fails
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Validation Failed");
            alert.setContentText("Please enter valid Customer ID correctly.");
            alert.showAndWait();
        }
    }

    public boolean isValidId(){
        boolean idValied = Regex.setTextColor(lk.ijse.demokushan.Util.TextField.CUSID, txtId);

        return idValied ;
    }

    public void btnSearchchOnAction(ActionEvent actionEvent) throws SQLException {
//        String id = txtId.getText();
        String id = txtSearch.getText();

        Customer customer = CustomerRepo.searchById(id);
        if (customer != null) {
            txtId.setText(customer.getCustomerId());
            txtName.setText(customer.getName());
            txtPhoneNumber.setText(customer.getPhoneNumber());
            txtAddress.setText(customer.getAddress());
            txtEmail.setText(customer.getEmail());
            cmbStatus.setValue(customer.getStatus());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
        }
    }
}