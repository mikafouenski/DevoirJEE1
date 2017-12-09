package beans;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Person {

	private Long id;
	private String name;
	private String firstname;
	private String mail;
	private String website;
	private Date birthdate;
	private String password;
	private Long idGroup;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date;
		try {
			date = dateFormat.parse(birthdate);
			this.birthdate = new Date(date.getTime());
		} catch (ParseException e) {
			System.out.println("Error convertDate");
			e.printStackTrace();
		}
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(Long idGroup) {
		this.idGroup = idGroup;
	}

	public boolean equals(Person p) {
		return id.equals(p.getId()) && name.equals(p.getName()) && firstname.equals(p.getFirstname())
				&& mail.equals(p.getMail()) && website.equals(p.getWebsite()) && password.equals(p.getPassword())
				&& idGroup.equals(p.getIdGroup()) && birthdate.toString().equals(p.getBirthdate().toString());
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", firstname=" + firstname + ", mail=" + mail + ", website="
				+ website + ", birthdate=" + birthdate + ", password=" + password + ", idGroup=" + idGroup + "]";
	}

}
