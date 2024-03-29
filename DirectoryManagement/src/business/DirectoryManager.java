package business;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beans.Group;
import beans.Person;
import beans.QuestionSecrete;
import business.exception.PersonNotFoundException;
import business.exception.UserNotLoggedException;
import database.IDaoPerson;
import hashage.HashageSha3;

@Service
public class DirectoryManager implements IDirectoryManager {

	@Autowired
	IDaoPerson dao;

	@Override
	public User newUser() {
		return new User();
	}

	private void isLogged(User u) throws UserNotLoggedException {
		if (u.isAnonymous())
			throw new UserNotLoggedException();
	}

	/**
	 * Recherche une instance de Person en base de données
	 * @param user utilisateur de l'application 
	 * @param personId l'id de la classe Person à trouver 
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Une instance de Person ou null si non trouvé
	 * @exception UserNotLoggedException si l'utilisateur n'est pas authentifié	
	 */
	@Override
	public Person findPerson(User user, long personId) throws UserNotLoggedException {
		isLogged(user);
		return dao.findPerson(personId);
	}

	/**
	 * Recherche une instance de Group en base de données
	 * @param user utilisateur de l'application 
	 * @param groupId l'id de la classe Group à trouver 
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Une instance de Group ou null si non trouvé
	 * @exception UserNotLoggedException si l'utilisateur n'est pas authentifié	
	 */
	@Override
	public Group findGroup(User user, long groupId) throws UserNotLoggedException {
		isLogged(user);
		return dao.findGroup(groupId);
	}
	
	/**
	 * Recherche Toute les instances de Group entre la borne start et end en base de données 
	 * @param user utilisateur de l'application 
	 * @param start indice de la première colonne selectionné de recherche 
	 * @param range la taille du record
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Une Collection contenant les groupes trouvé 
	 * @exception UserNotLoggedException si l'utilisateur n'est pas authentifié	
	 */
	@Override
	public Collection<Group> findGroups(User user, int start, int range) throws UserNotLoggedException {
		isLogged(user);
		return dao.findGroups(start, range);
	}
	
	/**
	 * Recherche Toute les instances de Group dont leur nom commence ou est egal à l'attribut name 
	 * @param user utilisateur de l'application 
	 * @param name le nom ou début du nom des group cherchés
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Une Collection contenant toute les instances de Group dont leur nom commence ou est egal à l'attribut name   
	 * @exception UserNotLoggedException si l'utilisateur n'est pas authentifié	
	 */
	public Collection<Group> findGroups(User user, String name) throws UserNotLoggedException {
		isLogged(user);
		return dao.findGroups(name);
	}
	
	/**
	 * Calcul le nombre de group dans la base de donnée 
	 * @param user utilisateur de l'application 
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return long egal au nombre de group dans la base de donnée 
	 * @exception UserNotLoggedException si l'utilisateur n'est pas authentifié	
	 */
	@Override
	public long nbGroups(User user) throws UserNotLoggedException {
		isLogged(user);
		return dao.getNbGroups();
	}
	
	/**
	 * Calcul le nombre de Person dans la base de donnée 
	 * @param user utilisateur de l'application 
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return long egal au nombre de Person dans la base de donnée 
	 * @exception UserNotLoggedException si l'utilisateur n'est pas authentifié	
	 */
	@Override
	public long nbPersons(User user, long groupId) throws UserNotLoggedException {
		isLogged(user);
		return dao.getNbPersons(groupId);
	}

	/**
	 * Recherche Toute les instances de Person entre la borne start et end en base de données 
	 * @param user utilisateur de l'application 
	 * @param start indice de la première colonne selectionné de recherche 
	 * @param range la taille du record
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Une Collection contenant les Peron trouvé 
	 * @exception UserNotLoggedException si l'utilisateur n'est pas authentifié	
	 */
	@Override
	public Collection<Person> findPersons(User user, long groupId, int start, int range) throws UserNotLoggedException {
		isLogged(user);
		return dao.findPersons(groupId, start, range);
	}
	
	/**
	 * Recherche Toute les instances de Person dont leur nom commence ou est égal à l'attribut name 
	 * et leur prénom est égal ou commence par l'attribut firstname
	 * @param user utilisateur de l'application 
	 * @param name le nom ou début du nom des Person cherchés
	 * @param firstname le prénom ou début du prénom des Person cherchés
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Une Collection contenant toute les instances de Person valide
	 * @exception UserNotLoggedException si l'utilisateur n'est pas authentifié	
	 */
	@Override
	public Collection<Person> findPersons(User user, String name, String firstname) throws UserNotLoggedException {
		isLogged(user);
		return dao.findPersons(name,firstname);
	}
	
	/**
	 * Teste si le personId et le password est correct 
	 * @param user utilisateur de l'applicationa authentifié
	 * @param personId L'identifiant base de donné
	 * @param password le mot de passe correspondant à l'id.
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return True si l'authentification à marché et changer l'attribut anonymous de user a false sinon renvoie false
	 * @exception PersonNotFoundException si l'id est introuvable en base de donné 	
	 */
	@Override
	public boolean login(User user, String mail, String password) throws PersonNotFoundException {
		Person person = dao.findPerson(mail);
		if (person == null)
			throw new PersonNotFoundException();
		if (!HashageSha3.digest(password).equals(person.getPassword()))
			return false;
		user.setAnonymous(false);
		user.setPersonId(person.getId());
		user.setGroupId(person.getIdGroup());
		return true;
	}

	/**
	 * Rend l'user anonyme 
	 * @param user utilisateur de l'applicationa authentifié
	 * @author Bernardini Mickael De Barros Sylvain 
	 */
	@Override
	public void logout(User user) {
		user.setAnonymous(true);
	}
	
	
	/**
	 * Enregistre une Person dans la base de donné
	 * @param user utilisateur de l'applicationa authentifié
	 * @param p La Person à sauvegarder 
	 * @param password le mot de passe correspondant à l'id.
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @exception UserNotLoggedException si l'utilisateur n'est pas authentifié		
	 */
	@Override
	public void savePerson(User user, Person p) throws UserNotLoggedException {
		isLogged(user);
		dao.savePerson(p);
	}
	
	 /**
	 * Enregistre une Person dans la base de donné
	 * @param p La Person à sauvegarder 
	 * @param password le mot de passe correspondant à l'id.
	 * @author Bernardini Mickael De Barros Sylvain 
	 */
    public void savePerson(Person p) {
    	dao.savePerson(p);
    }
	
	/**
	 * Enregistre un Group dans la base de donné
	 * @param user utilisateur de l'applicationa authentifié
	 * @param g Le Group à sauvegarder 
	 * @param password le mot de passe correspondant à l'id.
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @exception UserNotLoggedException si l'utilisateur n'est pas authentifié		
	 */
	@Override
	public void saveGroup(User user, Group g) throws UserNotLoggedException {
		isLogged(user);
		dao.saveGroup(g);
	}

	@Override
	public Person resetPasswordMail(String mail) throws PersonNotFoundException {
		Person p = dao.findPerson(mail);
		if (p == null)
			throw new PersonNotFoundException();
		return p;
	}

	@Override
	public QuestionSecrete resetPassword(long id) {
		return dao.findQuestionSecrete(id);
	}

	@Override
	public Person resetPasswordId(long id) {
		return dao.findPerson(id);
	}
	
}
