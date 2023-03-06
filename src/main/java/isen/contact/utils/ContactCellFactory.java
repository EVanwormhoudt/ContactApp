package isen.contact.utils;

public class ContactCellFactory implements javafx.util.Callback<javafx.scene.control.TableColumn.CellDataFeatures<isen.contact.entities.Person, java.lang.String>, javafx.beans.value.ObservableValue<java.lang.String>> {
    @Override
    public javafx.beans.value.ObservableValue<java.lang.String> call(javafx.scene.control.TableColumn.CellDataFeatures<isen.contact.entities.Person, java.lang.String> cellData) {
        return new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFirstname() + " " + cellData.getValue().getLastname());
    }
}

