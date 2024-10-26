package lk.ijse.demokushan.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.demokushan.model.Customer;
import lk.ijse.demokushan.model.TM.MostAppointmentTM;
import lk.ijse.demokushan.repository.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static lk.ijse.demokushan.repository.SupplierRepo.getSupplierCount;

public class HomePageController {
    public AnchorPane root;
    public Label lblCount;
    public Label lblCustomer;
    public Label lblApCount;
    public Label lblAppointment;
    public AnchorPane root1;
    public AnchorPane root21;
    public Label lblCompleteCount;
    public Label lblCompleteAppointment;
    public AnchorPane root212;
    public Label lblInCompleteCount;
    public Label lblInCompleteAppointment2;
    public Label lblInCompleteAppointment;

    public AnchorPane rootNode;
    public Label lblEmployeeCount;
    public AnchorPane root211;
    public Label lblEmployee;
    public AnchorPane root21111;

    public Label lblFullPayment1;
    public Label lblFullPayment;
    public Label lblFullPaymentCount;
    public AnchorPane root2111;
    public Label lblSupplierCount;
    public javafx.scene.chart.PieChart PieChart;
    public AnchorPane rootNod25;
    public AnchorPane rootNod24;
    public AnchorPane rootNode2;
    public Label lblDate;
    public Label lblTime;


    private int cCount;
    private int aCount;
    private int eCount;

    private  int sCount;


    public void initialize() {
        setTime();
        setDate();
        pieChartConnect();

        int completeCount = 0;
        int incompleteCount = 0;
        double fullPaymentCount = 0;


        try {
            cCount = CustomerRepo.getCustomerCount();
            completeCount = AppointmentRepo.getCompleteAppointmentCount();
            incompleteCount = AppointmentRepo.getIncompleteAppointmentCount();
            aCount = completeCount + incompleteCount;
            eCount = EmployeeRepo.getEmployeeCount();
            sCount = SupplierRepo.getSupplierCount();
            fullPaymentCount = PaymentRepo.getAllPaymentCount();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setSupplierCount(sCount);
        setCount(cCount);
        setAppointmentCount(aCount);
        setEmployeeCount(eCount);
        setCompleteAppointmentCount(completeCount);
        setIncompleteAppointmentCount(incompleteCount);
        setFullPaymentCount(fullPaymentCount);
    }
    private void setCount(int cCount) {
        lblCount.setText(String.valueOf(cCount));
    }

    private void setSupplierCount(int sCount) {
        lblSupplierCount.setText(String.valueOf(sCount));
    }

    private void setAppointmentCount(int aCount) {
        lblApCount.setText(String.valueOf(aCount));
    }

    private void setCompleteAppointmentCount(int completeCount) {
        lblCompleteCount.setText(String.valueOf(completeCount));
    }

    private void setIncompleteAppointmentCount(int incompleteCount) {
        lblInCompleteCount.setText(String.valueOf(incompleteCount));
    }

    private void setEmployeeCount(int aCount) {
        lblEmployeeCount.setText(String.valueOf(eCount));

    }

    private void setFullPaymentCount(double fullPaymentCount) {

        lblFullPaymentCount.setText(String.valueOf(fullPaymentCount));
    }

    public void pieChartConnect() {

        List<MostAppointmentTM> itemList = null;
        try {
            itemList = DashBoardRepo.getMostSellItem();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Customer item;
        for (MostAppointmentTM sellItem : itemList) {
            try {
                item = CustomerRepo.searchById(sellItem.getCustomerId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            ObservableList<PieChart.Data> pieChartData =
                    FXCollections.observableArrayList(
                            new PieChart.Data(item.getCustomerId(), sellItem.getVisitCount())
                    );

            PieChart.getData().addAll(pieChartData);
        }
    }
    private void setDate() {
        LocalDate nowDate = LocalDate.now();
        lblDate.setText(String.valueOf(nowDate));
    }

    private void setTime() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    LocalTime nowTime = LocalTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    String formattedTime = nowTime.format(formatter);
                    lblTime.setText(formattedTime);
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


    public void btnProfileeOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("/view/profile_details.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        AnchorPane load = fxmlLoader.load();
        rootNode.getChildren().clear();
        rootNode.getChildren().add(load);
    }
}
