package web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import beans.Connection;
import business.IDirectoryManager;
import business.User;
import business.exception.PersonNotFoundException;


@Controller()
@RequestMapping(value = "/connection")
public class ConnectionController {
	
	@Autowired
	IDirectoryManager manager;
	
	@ModelAttribute(name = "userInformation")
	Connection connect() {
		return new Connection();
	}
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public String editProduct(@ModelAttribute Connection co,HttpServletRequest request, HttpServletResponse response) {
		Object userSession = request.getSession().getAttribute("user");
		User test = new User();
		test.setAnonymous(true);
		if(userSession instanceof User)
			test = (User) userSession;
		if(test.isAnonymous())
			return "connection";
		return null;
		
	}
	
	@RequestMapping(value = "/display", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute Connection co, HttpServletRequest request) {
		User user = new User();
		boolean connection = false;
		try {
			connection = manager.login(user, co.getId(), co.getPassword());
		} catch (PersonNotFoundException e) {
			// TODO Auto-generated catch block
			return "connection";
		}
		if(connection) {
			request.getSession().setAttribute("user", user);
		}
		return "accueil";
	}
}
