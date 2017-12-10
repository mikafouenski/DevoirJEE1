package business;

import java.util.Collection;

import beans.Group;
import beans.Person;
import business.exception.PersonNotFoundException;
import business.exception.UserNotLoggedException;

public interface IDirectoryManager {

    // créer un utilisateur anonyme
    User newUser();

    /**
	 * Recherche une instance de Person en base de données
	 * @param user utilisateur de l'application 
	 * @param personId l'id de la classe Person à trouver 
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Une instance de Person ou null si non trouvé
	 * @exception UserNotLoggedException si l'utilisateur n'est pas authentifié	
	 */
    Person findPerson(User user, long personId) throws UserNotLoggedException;
    
    /**
	 * Recherche Toute les instances de Person entre la borne start et end en base de données 
	 * @param user utilisateur de l'application 
	 * @param start indice de la première colonne selectionné de recherche 
	 * @param range la taille du record
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Une Collection contenant les Peron trouvé 
	 * @exception UserNotLoggedException si l'utilisateur n'est pas authentifié	
	 */
    Collection<Person> findPersons(User user, long groupId, int start, int range) throws UserNotLoggedException;
    
    /**
	 * Calcul le nombre de Person dans la base de donnée 
	 * @param user utilisateur de l'application 
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return long egal au nombre de Person dans la base de donnée 
	 * @exception UserNotLoggedException si l'utilisateur n'est pas authentifié	
	 */
    long nbPersons(User user, long groupId) throws UserNotLoggedException;
    
    /**
	 * Recherche une instance de Group en base de données
	 * @param user utilisateur de l'application 
	 * @param groupId l'id de la classe Group à trouver 
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Une instance de Group ou null si non trouvé
	 * @exception UserNotLoggedException si l'utilisateur n'est pas authentifié	
	 */
    Group findGroup(User user, long groupId) throws UserNotLoggedException;
    
    /**
	 * Recherche Toute les instances de Group entre la borne start et end en base de données 
	 * @param user utilisateur de l'application 
	 * @param start indice de la première colonne selectionné de recherche 
	 * @param range la taille du record
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Une Collection contenant les groupes trouvé 
	 * @exception UserNotLoggedException si l'utilisateur n'est pas authentifié	
	 */
    Collection<Group> findGroups(User user, int start, int range) throws UserNotLoggedException;
    
    /**
	 * Recherche Toute les instances de Group dont leur nom commence ou est egal à l'attribut name 
	 * @param user utilisateur de l'application 
	 * @param name le nom ou début du nom des group cherchés
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Une Collection contenant toute les instances de Group dont leur nom commence ou est egal à l'attribut name   
	 * @exception UserNotLoggedException si l'utilisateur n'est pas authentifié	
	 */
    Collection<Group> findGroups(User user, String name) throws UserNotLoggedException;
    
    /**
	 * Calcul le nombre de group dans la base de donnée 
	 * @param user utilisateur de l'application 
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return long egal au nombre de group dans la base de donnée 
	 * @exception UserNotLoggedException si l'utilisateur n'est pas authentifié	
	 */
    long nbGroups(User user) throws UserNotLoggedException;


    /**
	 * Teste si le personId et le password est correct 
	 * @param user utilisateur de l'applicationa authentifié
	 * @param personId L'identifiant base de donné
	 * @param password le mot de passe correspondant à l'id.
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return True si l'authentification à marché et changer l'attribut anonymous de user a false sinon renvoie false
	 * @exception PersonNotFoundException si l'id est introuvable en base de donné 	
	 */
    boolean login(User user, long personId, String password) throws PersonNotFoundException;

    /**
	 * Rend l'user anonyme 
	 * @param user utilisateur de l'applicationa authentifié
	 * @author Bernardini Mickael De Barros Sylvain 
	 */
    void logout(User user);

    /**
	 * Enregistre une Person dans la base de donné
	 * @param user utilisateur de l'applicationa authentifié
	 * @param p La Person à sauvegarder 
	 * @param password le mot de passe correspondant à l'id.
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @exception UserNotLoggedException si l'utilisateur n'est pas authentifié		
	 */
    void savePerson(User user, Person p) throws UserNotLoggedException;
    
    /**
	 * Enregistre un Group dans la base de donné
	 * @param user utilisateur de l'applicationa authentifié
	 * @param g Le Group à sauvegarder 
	 * @param password le mot de passe correspondant à l'id.
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @exception UserNotLoggedException si l'utilisateur n'est pas authentifié		
	 */
    void saveGroup(User user, Group g) throws UserNotLoggedException;

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
	Collection<Person> findPersons(User user, String name, String firstname) throws UserNotLoggedException;

}