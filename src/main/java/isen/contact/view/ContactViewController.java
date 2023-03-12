package isen.contact.view;

import isen.contact.daos.PersonDao;
import isen.contact.entities.Person;
import isen.contact.utils.ContactCellFactory;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class ContactViewController {

    PersonDao personDao = new PersonDao();

    Person selectedPerson;

    @FXML
    TextField lastnameField;
    @FXML
    TextField firstnameField;
    @FXML
    TextField nicknameField;
    @FXML
    TextField phoneField;
    @FXML
    TextField emailField;
    @FXML
    DatePicker birthDateField;
    @FXML
    ChoiceBox<String> categoryChoiceBox;
    @FXML
    TextField addressField;
    @FXML
    TableView<Person> contactTable;
    @FXML
    TableColumn<Person, String> nameColumn;

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(new ContactCellFactory());
        populateList();
        contactTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));

    }

    @FXML
    private void refreshList() {
        contactTable.refresh();
        contactTable.getSelectionModel().clearSelection();
        contactTable.getItems().clear();
        populateList();

    }

    @FXML
    private void populateList() {
        List<Person> persons = personDao.listAllPersons();
        contactTable.getItems().addAll(persons);
    }

    @FXML
    private void showPersonDetails(Person person) {
        if (person == null) {
            return;
        }
        lastnameField.setText(person.getLastname());
        firstnameField.setText(person.getFirstname());
        nicknameField.setText(person.getNickname());
        phoneField.setText(person.getPhone_number());
        emailField.setText(person.getEmail_address());
        birthDateField.setValue(person.getBirth_date());
        categoryChoiceBox.setValue(person.getCategory());
        addressField.setText(person.getAddress());
        selectedPerson = person;

    }

    @FXML
    private void handleSaveButton() {
        if (selectedPerson==null){
            return;
        }
        selectedPerson.setLastname(lastnameField.getText());
        selectedPerson.setFirstname(firstnameField.getText());
        selectedPerson.setNickname(nicknameField.getText());
        selectedPerson.setPhone_number(phoneField.getText());
        selectedPerson.setEmail_address(emailField.getText());
        selectedPerson.setBirth_date(birthDateField.getValue());
        selectedPerson.setCategory(categoryChoiceBox.getValue());
        selectedPerson.setAddress(addressField.getText());

        personDao.updatePerson(selectedPerson);
        refreshList();
    }

    @FXML
    private void handleDeleteButton() {
        if (selectedPerson==null){
            return;
        }
        personDao.deletePerson(selectedPerson.getId());
        categoryChoiceBox.setValue(null);
        lastnameField.setText("");
        firstnameField.setText("");
        nicknameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        birthDateField.setValue(null);
        addressField.setText("");


        refreshList();
        selectedPerson = null;

    }

    @FXML
    private void handleNewButton() {
        Person newPerson = new Person();
        selectedPerson = newPerson;
        newPerson.setLastname("lastname");
        newPerson.setFirstname("firstname");
        newPerson.setNickname("");
        newPerson.setBirth_date(LocalDate.now());
        selectedPerson = personDao.addPerson(newPerson);
        refreshList();
        showPersonDetails(selectedPerson);



    }

    @FXML
    private void handleExportButton(){
        List<Person> listToExport = personDao.listAllPersons();
        try {
            // create Vcard for each person
            for (Person person : listToExport) {
                String vcard = "BEGIN:VCARD \rVERSION:3.0 \rN:" + person.getLastname() + ";" + person.getFirstname() + " \rFN:" + person.getFirstname() + " " + person.getLastname() + " \rTEL;TYPE=CELL:" + person.getPhone_number() + " \rEMAIL:" + person.getEmail_address() + " \rADR;TYPE=HOME:" + person.getAddress() + " \rEND:VCARD";

                // write vcard to file by creating a new file
                File file = new File("src/main/resources/isen/contact/vcards/" + person.getFirstname() + "_" + person.getLastname() + ".vcf");

                // if file doesn't exists, then create it
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(vcard);
                bw.close();


            }


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Database exported");
            alert.setHeaderText(null);
            alert.setContentText("The data base has been exported successfully.");
            alert.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
