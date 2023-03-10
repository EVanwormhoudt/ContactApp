package isen.ca.daos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import isen.contact.daos.DataSourceFactory;
import org.junit.Before;
import org.junit.Test;

import isen.contact.entities.Person;
import isen.contact.daos.PersonDao;


public class PersonDaoTestCase {
	private final PersonDao personDao = new PersonDao();
	
	@Before
	public void initDb() throws Exception {
		Connection connection = DataSourceFactory.getDataSource().getConnection();
		Statement stmt = connection.createStatement();
		stmt.executeUpdate(
				"""
						CREATE TABLE IF NOT EXISTS person (
										idperson INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\s
										lastname VARCHAR(45) NOT NULL, \s
										firstname VARCHAR(45) NOT NULL,
										nickname VARCHAR(45) NOT NULL,
										phone_number VARCHAR(15) NULL,
										address VARCHAR(200) NULL,
										category VARCHAR(150) NULL,
										email_address VARCHAR(150) NULL,
										birth_date DATE NULL
      
										);
						""");
		stmt.executeUpdate("DELETE FROM person");
		stmt.executeUpdate("Update SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='person'");
		stmt.executeUpdate("INSERT INTO person(lastname, firstname, nickname, phone_number, address, email_address, birth_date,category) "
				+ "VALUES ('Contois', 'Gabriel', 'G', '012345678910121', 'x rue des y', 'email_address_1', '2000-05-05','friend')");
		stmt.executeUpdate("INSERT INTO person(lastname, firstname, nickname, phone_number, address, email_address, birth_date,category) "
				+ "VALUES ('Vanwormhoudt', 'Elliott', 'E', '012345678910121', 'x rue des z', 'email_address_2', '2001-08-05','family')");
		stmt.executeUpdate("INSERT INTO person(lastname, firstname, nickname, phone_number, address, email_address, birth_date,category) "
				+ "VALUES ('Lefebvre', 'Julien', 'J', '012345678910121', 'z rue des x', 'email_address_3', '2001-06-06','family')");
		stmt.close();
		connection.close();
	}
	
	/**
	 * Test the {@link PersonDao#listAllPersons()} method.
	 * This test checks if the list of persons returned by the method has the expected size,
	 * and if its content matches the expected values for each person's lastname, firstname, nickname, phone number
	 * , address, email_address, birthdate and category.
	 */
	 @Test
	 public void shouldListPersons() {
		// WHEN
		List<Person> persons = personDao.listAllPersons();
		// THEN
		assertThat(persons).hasSize(3);
		assertThat(persons).extracting("id","lastname", "firstname", "nickname", "phone_number", "address", "email_address", "birth_date", "category")
		.containsOnly(tuple(1, "Contois", "Gabriel", "G", "012345678910121", "x rue des y", "email_address_1", LocalDate.of(2000, 5, 5), "friend"),
				tuple(2,"Vanwormhoudt", "Elliott", "E", "012345678910121", "x rue des z", "email_address_2", LocalDate.of(2001, 8, 5), "family"),
				tuple(3,"Lefebvre", "Julien", "J", "012345678910121", "z rue des x", "email_address_3", LocalDate.of(2001, 6, 6), "family"));
		assertThat(persons.get(0).getId()).isEqualTo(1);
		assertThat(persons.get(1).getId()).isEqualTo(2);
		assertThat(persons.get(2).getId()).isEqualTo(3);
	}
	
	
	 /**
	  * Test the {@link PersonDao#addPerson(Person)} method.
	  * This test checks if a person is correctly inserted into the database by creating a person object,
	  * passing it to the method, and then checking the contents of the database to see if the person has been correctly added.
	  * @throws Exception if an error occurs while accessing the database.
	  */
	 @Test
	 public void shouldAddPerson() throws Exception {
		 // GIVEN
		 Person person = new Person();
		 person.setLastname("lastname");
		 person.setFirstname("firstname");
		 person.setNickname("nickname");
		 person.setPhone_number("phone_number");
		 person.setAddress("address");
		 person.setEmail_address("email_address");
		 person.setBirth_date(LocalDate.of(2000, 5, 5));
		 person.setCategory("work");
		 // WHEN
		 personDao.addPerson(person);
		 // THEN
		 Connection connection = DataSourceFactory.getDataSource().getConnection();
		 Statement stmt = connection.createStatement();
		 ResultSet results = stmt.executeQuery("SELECT * FROM person WHERE idperson = 4");
		 assertThat(results.next()).isTrue();
		 assertThat(results.getInt("idperson")).isEqualTo(4);
		 assertThat(results.getString("lastname")).isEqualTo("lastname");
		 assertThat(results.getString("firstname")).isEqualTo("firstname");
		 assertThat(results.getString("nickname")).isEqualTo("nickname");
		 assertThat(results.getString("phone_number")).isEqualTo("phone_number");
		 assertThat(results.getString("address")).isEqualTo("address");
		 assertThat(results.getString("email_address")).isEqualTo("email_address");
		 assertThat(results.getString("birth_date")).isEqualTo("2000-05-05");
		 assertThat(results.getString("category")).isEqualTo("work");
		 assertThat(results.next()).isFalse();
		 results.close();
		 stmt.close();
		 connection.close();
	 }


	 /**
	  * Test the {@link PersonDao#updatePerson(Person)} method.
	  * This test checks if a person is correctly updated in the database by creating a person object,
	  * passing it to the method, and then checking the contents of the database.
	  * @throws Exception if an error occurs while accessing the database.
	  */
	 @Test
	 public void shouldUpdatePerson() throws Exception {
		 // GIVEN
		 Person person = new Person();
		 person.setId(1);
		 person.setLastname("Contois");
		 person.setFirstname("Gabriel");
		 person.setNickname("G");
		 person.setPhone_number("phone_number");
		 person.setAddress("address");
		 person.setEmail_address("email_address");
		 person.setBirth_date(LocalDate.of(2000, 5, 5));
		 person.setCategory("friend");
		 // WHEN
		 personDao.updatePerson(person);
		 // THEN
		 Connection connection = DataSourceFactory.getDataSource().getConnection();
		 Statement stmt = connection.createStatement();
		 ResultSet results = stmt.executeQuery("SELECT * FROM person WHERE idperson = 1");
		 assertThat(results.next()).isTrue();
		 assertThat(results.getInt("idperson")).isEqualTo(1);
		 assertThat(results.getString("lastname")).isEqualTo("Contois");
		 assertThat(results.getString("firstname")).isEqualTo("Gabriel");
		 assertThat(results.getString("nickname")).isEqualTo("G");
		 assertThat(results.getString("phone_number")).isEqualTo("phone_number");
		 assertThat(results.getString("address")).isEqualTo("address");
		 assertThat(results.getString("email_address")).isEqualTo("email_address");
		 assertThat(results.getString("birth_date")).isEqualTo("2000-05-05");
		 assertThat(results.getString("category")).isEqualTo("friend");
		 assertThat(results.next()).isFalse();
		 results.close();
		 stmt.close();
		 connection.close();
	 }

	 /**
	  * Test the {@link PersonDao#deletePerson(Integer)} method.
	  * This test checks if a person is correctly deleted from the database by passing the id of the person to the method,
	  * and then checking the contents of the database to see if the person has been correctly deleted.
	  * @throws Exception if an error occurs while accessing the database.
	  */
	 @Test
	 public void shouldDeletePerson() throws Exception {
		 // WHEN
		 personDao.deletePerson(1);
		 // THEN
		 Connection connection = DataSourceFactory.getDataSource().getConnection();
		 Statement stmt = connection.createStatement();
		 ResultSet results = stmt.executeQuery("SELECT * FROM person WHERE idperson = 1");
		 assertThat(results.next()).isFalse();
		 results.close();
		 ResultSet results2 = stmt.executeQuery("SELECT * FROM person WHERE idperson = 2");
		 assertThat(results2.next()).isTrue();
		 results2.close();
		 stmt.close();
		 connection.close();
	 }
}

