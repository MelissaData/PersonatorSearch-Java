module com.melissadata.personatorsearch {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires com.google.gson;
    requires jdk.crypto.ec;
    requires java.xml;

    opens com.melissadata.personatorsearch to javafx.fxml;
    opens com.melissadata.personatorsearch.model to javafx.fxml;
    opens com.melissadata.personatorsearch.view to javafx.fxml;
    exports com.melissadata.personatorsearch;
}
