package beans;

import java.util.Collection;

public class Group {

	private int id;
	private String name;
	private Collection<Person> persons;

	public Group() {
		super();
	}

	public Group(int id, String name, Collection<Person> persons) {
		this.id = id;
		this.name = name;
		this.persons = persons;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Person> getPersons() {
		return persons;
	}

	public void setPersons(Collection<Person> persons) {
		this.persons = persons;
	}

}
