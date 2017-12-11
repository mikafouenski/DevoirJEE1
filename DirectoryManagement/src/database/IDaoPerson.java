package database;

import java.util.Collection;

import beans.Group;
import beans.Person;

public interface IDaoPerson {

	/**
	 * Recherche Toute les instances de Groupe en base de données 
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Une Collection contenant les groupes trouvés 
	 * @exception DaoException si la requete n'a pas fonctionée
	 */
	Collection<Group> findGroups() throws DaoException;
	
	/**
	 * Recherche Toute les instances de Groupe entre la borne start et end en base de données 
	 * @param start indice de la première colonne selectionné de recherche 
	 * @param range la taille du record
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Une Collection contenant les groupes trouvés 
	 * @exception DaoException si la requete n'a pas fonctionée
	 */
	Collection<Group> findGroups(int start, int range) throws DaoException;
	
	/**
	 * Recherche un Groupe en base de données 
	 * @param id L'identifiant du groupe
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Le groupe trouvé 
	 * @exception DaoException si la requete n'a pas fonctionée
	 */
	Group findGroup(long id) throws DaoException;
	
	/**
	 * Recherche les Groupe dont le nom commence par "..." en base de données 
	 * @param name Nom pouur la recherche
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Une Collection contenant les groupes trouvés 
	 * @exception DaoException si la requete n'a pas fonctionée
	 */
	Collection<Group> findGroups(String name) throws DaoException;
	
	/**
	 * calcule le nombre de groupes en base de données 
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return le nombre de groupes en base de données 
	 * @exception DaoException si la requete n'a pas fonctionée
	 */
	long getNbGroups() throws DaoException;
	
	/**
	 * calcule le nombre de personnes dans le groupe 
	 * @param id L'identifiant du groupe
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return le nombre de personnes dans le groupe
	 * @exception DaoException si la requete n'a pas fonctionée
	 */
	long getNbPersons(long id) throws DaoException;
	
	/**
	 * Recherche Toute les instances de personnes du groupe "..." en base de données 
	 * @param id L'identifiant du groupe
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Une Collection contenant les personnes trouvées 
	 * @exception DaoException si la requete n'a pas fonctionée
	 */
	Collection<Person> findPersons(long groupId) throws DaoException;
	
	/**
	 * Recherche Toute les instances de personnes entre la borne start et end en base de données 
	 * @param start indice de la première colonne selectionné de recherche 
	 * @param range la taille du record
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Une Collection contenant les personnes trouvées 
	 * @exception DaoException si la requete n'a pas fonctionée
	 */
	Collection<Person> findPersons(long groupId, int start, int range) throws DaoException;
	
	/**
	 * Recherche les personnes dont le nom ou prenom commence par "..." en base de données 
	 * @param name Nom pouur la recherche
	 * @param firstname Pernom pouur la recherche
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Une Collection contenant les personnes trouvées 
	 * @exception DaoException si la requete n'a pas fonctionée
	 */
	Collection<Person> findPersons(String name, String firstname) throws DaoException;
	
	/**
	 * Recherche une personne en base de données 
	 * @param id L'identifiant de la personne
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Le groupe trouvé 
	 * @exception DaoException si la requete n'a pas fonctionée
	 */
	Person findPerson(long id) throws DaoException;
	
	/**
	 * Recherche une personne en base de données 
	 * @param id L'identifiant de la personne
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Le groupe trouvé 
	 * @exception DaoException si la requete n'a pas fonctionée
	 */
	Person findPerson(String email) throws DaoException;
	
	/**
	 * Sauvegarde une personne en base de données 
	 * @param p La personne
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @exception DaoException si la requete n'a pas fonctionée
	 */
	void savePerson(Person p) throws DaoException;

	/**
	 * Sauvegarde un groupe en base de données 
	 * @param g Le groupe
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @exception DaoException si la requete n'a pas fonctionée
	 */
	void saveGroup(Group g) throws DaoException;
	
	
	
}
