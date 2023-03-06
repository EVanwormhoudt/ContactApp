package isen.contact.view;

import isen.contact.daos.PersonDao;
import isen.contact.entities.Person;
import isen.contact.utils.ContactCellFactory;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;
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
    }

    @FXML
    private void handleDeleteButton() {
        if (selectedPerson==null){
            return;
        }
        personDao.delete(selectedPerson.getId());
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
        newPerson.setNickname("nickname");
        newPerson.setBirth_date(LocalDate.now());
        personDao.addPerson(newPerson);
        refreshList();



    }
}
