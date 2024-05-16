module lk.ijse.demokushan {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.jfoenix;
    requires jasperreports;


    opens lk.ijse.demokushan to javafx.fxml;
    exports lk.ijse.demokushan;
    exports lk.ijse.demokushan.controller;
    opens lk.ijse.demokushan.controller to javafx.fxml;
    opens lk.ijse.demokushan.model.TM to javafx.base;

}