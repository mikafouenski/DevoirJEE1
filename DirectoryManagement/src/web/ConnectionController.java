package web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import beans.Connection;
import business.IDirectoryManager;
import business.User;


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
	public ModelAndView editProduct(@ModelAttribute Connection co,HttpServletRequest request, HttpServletResponse response) {
		Object userSession = request.getSession().getAttribute("user");
		User test = new User();
		if(userSession instanceof User)
			test = (User) userSession;
		if(test.isAnonymous())
			return new ModelAndView("connection");
		return new ModelAndView("redirect:/actions/groups/list");
		
	}
	
	@RequestMapping(value = "/display", method = RequestMethod.POST)
	public ModelAndView saveProduct(@ModelAttribute Connection co, HttpServletRequest request) {
		User user = new User();
		boolean connection = false;
		try {
			System.out.println(co.getId());
			System.out.println(co.getPassword());
			connection = manager.login(user, co.getId(), co.getPassword());
			System.out.println(co.getPassword());
		} catch (Exception e) {
			return new ModelAndView("connection");
		}
		if(connection) {
			request.getSession().setAttribute("user", user);
		}
		return new ModelAndView("redirect:/actions/groups/list");
	}
}
