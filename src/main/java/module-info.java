module com.example.promynarzece {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.promynarzece to javafx.fxml;
    exports com.example.promynarzece;
}