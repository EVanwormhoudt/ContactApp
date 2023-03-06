package isen.contact.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import isen.contact.entities.Person;


public class PersonDao {
	
	
	/**
     * This method retrieves the list of all persons from the database.
     * @return A List of person objects representing all persons in the database.
     */
	public List<Person> listAllPersons() {
		List<Person> listOfPersons = new ArrayList<>();
	    try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
	        try (Statement statement = connection.createStatement()) {
	            try (ResultSet results = statement.executeQuery("SELECT * FROM person")) {
	                while (results.next()) {


	                    Person Person = new Person(results.getInt("idperson"),
	                                            results.getString("lastname"),
	                                            results.getString("firstname"),
	                                            results.getString("nickname"),
	                                            results.getString("phone_number"),
	                                            results.getString("address"),
	                                            results.getString("email_address"),
												LocalDate.parse(results.getString("birth_date")),
								                results.getString("category")
											);
	                    listOfPersons.add(Person);
	                }
	            }
	        }
	    } catch (SQLException e) {
	        // Manage Exception
	        e.printStackTrace();
	    }
	    return listOfPersons;
	}
	
	
	/**
	 * Adds a new person to the database
	 * @param person 
	 * 			the person to be added
	 * @return the person object that was added to the database, or null if there was an error
	 */
	public Person addPerson(Person person) {
		try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
	        String sqlQuery = "INSERT INTO person(lastname,firstname,nickname,phone_number,address,email_address, birth_date,category) VALUES(?,?,?,?,?,?,?,?)";
	        try (PreparedStatement statement = connection.prepareStatement(
	                        sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
				prepare_statement(person, statement);
				statement.executeUpdate();
	            ResultSet ids = statement.getGeneratedKeys();
	            if (ids.next()) {
	                return new Person(ids.getInt(1), person.getLastname(), person.getFirstname(), person.getNickname(), 
	                		person.getPhone_number(), person.getAddress(), person.getEmail_address(), person.getBirth_date(), person.getCategory());
	            }
	        }
	    }catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	/**
	 * Prepare a statement to be executed
	 * @param person
	 * 			the person to be updated
	 * 		  statement
	 * 			the statement to be prepared
	 *
	 */
	private void prepare_statement(Person person, PreparedStatement statement) throws SQLException {
		statement.setString(1, person.getLastname());
		statement.setString(2, person.getFirstname());
		statement.setString(3, person.getNickname());
		if (person.getPhone_number() != null) {
			statement.setString(4, person.getPhone_number());
		} else {
			statement.setNull(4, java.sql.Types.VARCHAR);
		}
		if (person.getAddress() != null) {
			statement.setString(5, person.getAddress());
		} else {
			statement.setNull(5, java.sql.Types.VARCHAR);
		}
		if (person.getEmail_address() != null) {
			statement.setString(6, person.getEmail_address());
		} else {
			statement.setNull(6, java.sql.Types.VARCHAR);
		}
		if (person.getBirth_date() != null) {
			statement.setString(7, person.getBirth_date().toString());
		} else {
			statement.setNull(7, java.sql.Types.DATE);
		}
		if (person.getCategory() != null) {
			statement.setString(8, person.getCategory());
		} else {
			statement.setNull(8, java.sql.Types.VARCHAR);
		}


	}

	/**
	 * Delete a person in the database
	 * @param personId
	 * 			Id of the person we want to delete
	 */
	public void delete(Integer personId) {
	    try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
	        try (PreparedStatement statement = 
	                    connection.prepareStatement("delete from person where idperson=?")) {
	            statement.setInt(1, personId);
	            statement.executeUpdate();
	        }
	    }catch (SQLException e) {
	        // Manage Exception
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Update the data of an existing person
	 *
	 * @param person The person that we want to update in the database
	 */
	public void updatePerson(Person person) {
		try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
	        String sqlQuery = "UPDATE person SET lastname = ?,firstname = ?,nickname = ?,phone_number = ?,address = ?,email_address = ?, birth_date = ?,category=? WHERE idperson = ?";
	        try (PreparedStatement statement = connection.prepareStatement(
	                        sqlQuery)) {
				prepare_statement(person, statement);
				statement.setInt(9, person.getId());
				statement.executeUpdate();
			}
	    }catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	
}
