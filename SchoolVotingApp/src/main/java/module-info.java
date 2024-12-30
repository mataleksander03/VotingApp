module com.example.schoolvotingapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.schoolvotingapp to javafx.fxml;
    exports com.example.schoolvotingapp;
}