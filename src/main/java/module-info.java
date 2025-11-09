module com.example.omagodooeste {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.omagodooeste to javafx.fxml;
    opens Controllers to javafx.fxml;
    exports com.example.omagodooeste;
}