package database;

import java.util.Collection;

import beans.Group;
import beans.Person;

public interface IDaoPerson {

	// récupérer les groupes
	Collection<Group> findAllGroups() throws DaoException;
	
	Group findGroup(long id) throws DaoException;

	// récupérer les personnes
	Collection<Person> findAllPersons(long groupId) throws DaoException;

	// lire une personne
	Person findPerson(long id) throws DaoException;

	// modification ou ajout d'une nouvelle personne
	void savePerson(Person p) throws DaoException;

	// modification ou ajout d'un nouveau groupe
	void saveGroup(Group g) throws DaoException;

}
