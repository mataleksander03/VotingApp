module com.example.schoolvotingapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;


    opens com.example.schoolvotingapp to javafx.fxml;
    exports com.example.schoolvotingapp;
}