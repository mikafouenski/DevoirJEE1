package database;

import java.util.Collection;

import beans.Group;
import beans.Person;

public interface IDaoPerson {

	// récupérer les groupes
	Collection<Group> findGroups() throws DaoException;
	Collection<Group> findGroups(int start, int end) throws DaoException;
	
	Group findGroup(long id) throws DaoException;
	Collection<Group> findGroups(String name) throws DaoException;
	
	long getNbGroups() throws DaoException;
	long getNbPersons(long id) throws DaoException;
	
	// récupérer les personnes
	Collection<Person> findPersons(long groupId) throws DaoException;
	Collection<Person> findPersons(long groupId, int start, int end) throws DaoException;
	Collection<Person> findPersons(String name, String firstname) throws DaoException;
	Person findPerson(long id) throws DaoException;

	// modification ou ajout d'une nouvelle personne
	void savePerson(Person p) throws DaoException;

	// modification ou ajout d'un nouveau groupe
	void saveGroup(Group g) throws DaoException;
	
	

}
