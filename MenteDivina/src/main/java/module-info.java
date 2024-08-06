module com.mycompany.mentedivina {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires javafx.media;

    opens com.mycompany.mentedivina to javafx.fxml;
    exports com.mycompany.mentedivina;
}
