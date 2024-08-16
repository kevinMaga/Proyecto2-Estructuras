module com.mycompany.mentedivina {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires javafx.media;
    requires org.eclipse.scout.json;
    requires java.desktop;

    opens com.mycompany.mentedivina to javafx.fxml;
    exports com.mycompany.mentedivina;
}
