package web;

import javax.servlet.http.HttpServletRequest;

import business.User;

public class ControllerHelpers {
	
	public static User getUser(HttpServletRequest r) {
		Object userSession = r.getSession().getAttribute("user");
		if (userSession instanceof User)
			return (User) userSession;
		return new User();
	}
	
}
