module com.pa.pa_ternak {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens com.pa.pa_ternak to javafx.fxml;
    exports com.pa.pa_ternak;
    exports com.pa.pa_ternak.controller;
    opens com.pa.pa_ternak.controller to javafx.fxml;
}