package beans;

import java.util.Collection;

public class Group {

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + "]";
	}

	private Long id;
	private String name;
	private Collection<Person> persons;

	public Group() {
		super();
	}

	public Group(Long id, String name, Collection<Person> persons) {
		this.id = id;
		this.name = name;
		this.persons = persons;
	}

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

	public Collection<Person> getPersons() {
		return persons;
	}

	public void setPersons(Collection<Person> persons) {
		this.persons = persons;
	}
	
	public boolean equals(Group p) {
		// TODO Auto-generated method stub
		return this.getId().equals(p.getId()) && this.getName().equals(p.getName());
	}

}
