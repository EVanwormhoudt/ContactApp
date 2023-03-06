module isen.contact {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens isen.contact to javafx.fxml;

    exports isen.contact;

    exports isen.contact.entities;

    exports isen.contact.daos;

    exports isen.contact.view;

    opens isen.contact.view to javafx.fxml;
}