package web;

import javax.servlet.http.HttpServletRequest;

import business.User;
import business.exception.UserNotLoggedException;

public class ControllerHelpers {
	
	/**
	 * Recherche l'user en session
	 * @param r L'HttpServletRequest de la requete
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return L'user en session ou un nouvel user
	 */
	public static User getUser(HttpServletRequest r) {
		Object userSession = r.getSession().getAttribute("user");
		if (userSession instanceof User)
			return (User) userSession;
		return new User();
	}
	
}
