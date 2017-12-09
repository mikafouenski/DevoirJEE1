package business;

import java.util.Collection;

import beans.Group;
import beans.Person;
import business.exception.PersonNotFoundException;
import business.exception.UserNotLoggedException;

public interface IDirectoryManager {

    // créer un utilisateur anonyme
    User newUser();

    // chercher une personne
    Person findPerson(User user, long personId) throws UserNotLoggedException;
    Collection<Person> findPersons(User user, long groupId, int start, int end) throws UserNotLoggedException;
    long nbPersons(User user, long groupId) throws UserNotLoggedException;
    
    // chercher un groupe
    Group findGroup(User user, long groupId) throws UserNotLoggedException;
    Collection<Group> findGroups(User user, int start, int end) throws UserNotLoggedException;
    long nbGroups(User user) throws UserNotLoggedException;


    // identifier un utilisateur
    boolean login(User user, long personId, String password) throws PersonNotFoundException;

    // oublier l'utilisateur
    void logout(User user);

    // enregistrer une personne
    void savePerson(User user, Person p) throws UserNotLoggedException;

}