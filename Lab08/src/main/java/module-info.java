module com.example.lab08 {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencsv;


    opens com.example.lab08 to javafx.fxml;
    exports com.example.lab08;
}