package beans;

import java.util.Collection;

public class Group {

	private Long id;
	private String name;

	public Group() {
		super();
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
	
	public boolean equals(Group p) {
		return this.getId().equals(p.getId()) && this.getName().equals(p.getName());
	}

}
