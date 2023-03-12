package isen.contact.entities;

import java.util.Date;
import java.time.LocalDate;
import java.util.Locale;

public class Person {
	
	private Integer id;
	private String lastname;
	private String firstname;
	private String nickname;
	private String phone_number;
	private String address;
	private String email_address;
	private LocalDate birth_date;
	private String category;
	
	public Person() {
	}
	
	public Person(Integer id, String lastname, String firstname, String nickname, String phone_number, String address,
			String email_address, LocalDate birth_date, String category) {
		super();
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
		this.nickname = nickname;
		this.phone_number = phone_number;
		this.address = address;
		this.email_address = email_address;
		this.birth_date = birth_date;
		this.category = category;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public String getPhone_number() {
		return phone_number;
	}


	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getEmail_address() {
		return email_address;
	}


	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}


	public LocalDate getBirth_date() {
		return birth_date;
	}


	public void setBirth_date(LocalDate birth_date) {
		this.birth_date = birth_date;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}


	@Override
	public String toString() {
		return String.format(Locale.FRANCE,
				"%s %s{id=%d, nickname=%s, phone_number='%s', category='%s', birthday='%s', email='%s', address='%s'}\n",
				this.firstname,this.lastname,this.id, this.nickname, this.phone_number,this.category,this.birth_date.toString(),this.email_address,this.address
		);
	}

	public String toVcard() {
		String birthDate = this.getBirth_date().toString().replace("-", "");
		return ("BEGIN:VCARD \rVERSION:4.0 \rN:%s;%s \rFN:%s \rNICKNAME:%s \rBDAY:%s \rTEL;TYPE=CELL:%s \rEMAIL:%s" +
				" \rADR;TYPE=HOME:%s \rCATEGORIES:%s \rEND:VCARD").formatted(this.getLastname(), this.getFirstname(),this.getFirstname() + " " + this.getLastname(),
				 this.getNickname(), birthDate, this.getPhone_number(), this.getEmail_address(), this.getAddress(), this.getCategory());
	}
}
