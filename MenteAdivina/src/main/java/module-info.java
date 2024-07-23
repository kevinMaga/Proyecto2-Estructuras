module com.mycompany.menteadivina {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.menteadivina to javafx.fxml;
    exports com.mycompany.menteadivina;
}
