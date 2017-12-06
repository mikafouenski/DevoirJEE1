package business;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beans.Group;
import beans.Person;
import business.exception.PersonNotFoundException;
import business.exception.UserNotLoggedException;
import database.IDaoPerson;
import hachage.HachageSha3;

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

	@Override
	public Person findPerson(User user, long personId) throws UserNotLoggedException {
		isLogged(user);
		return dao.findPerson(personId);
	}

	@Override
	public Group findGroup(User user, long groupId) throws UserNotLoggedException {
		isLogged(user);
		return dao.findGroup(groupId);
	}

	@Override
	public Collection<Person> findPersons(User user, long groupId) throws UserNotLoggedException {
		isLogged(user);
		return dao.findPersons(groupId);
	}

	@Override
	public boolean login(User user, long personId, String password) throws PersonNotFoundException {
		Person person = dao.findPerson(personId);
		if (person == null)
			throw new PersonNotFoundException();
		if (!HachageSha3.digest(password).equals(person.getPassword()))
			return false;
		user.setAnonymous(false);
		user.setPersonId(personId);
		user.setGroupId(person.getIdGroup());
		return true;
	}

	@Override
	public void logout(User user) {
		user.setAnonymous(true);
	}

	@Override
	public void savePerson(User user, Person p) throws UserNotLoggedException {
		isLogged(user);
		dao.savePerson(p);
	}

}
