module lk.ijse.stitchwave1stsemesterfinalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.ikonli.javafx;
    requires com.jfoenix;
    requires lombok;
    requires java.sql;
    requires net.sf.jasperreports.core;

    opens lk.ijse.stitchwave1stsemesterfinalproject.dto.tm to javafx.base;
    opens lk.ijse.stitchwave1stsemesterfinalproject.controller to javafx.fxml;
    exports lk.ijse.stitchwave1stsemesterfinalproject;
}