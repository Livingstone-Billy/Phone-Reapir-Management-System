module com.phonecare.phonecaresystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires mongo.java.driver;
    requires org.apache.pdfbox;

    opens com.phonecare.phonecaresystem to javafx.fxml;
    exports com.phonecare.phonecaresystem;
}
