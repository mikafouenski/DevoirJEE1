package database;

import java.util.Collection;

import group.Group;
import person.Person;

public interface DaoPerson {

	// récupérer les groupes
	Collection<Group> findAllGroups();

	// récupérer les personnes
	Collection<Person> findAllPersons(long groupId);

	// lire une personne
	Person findPerson(long id);

	// modification ou ajout d'une nouvelle personne
	void savePerson(Person p);

	// modification ou ajout d'un nouveau groupe
	void saveGroup(Group g);

}
