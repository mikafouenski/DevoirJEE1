package web;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import beans.Group;
import beans.Person;
import business.IDirectoryManager;
import business.User;
import business.exception.UserNotLoggedException;
import validor.ValidatorConnection;


@Controller()
@RequestMapping(value = "/")
public class ControleurSearch {
	
	@Autowired
	IDirectoryManager manager; 
	
	@Autowired
	ValidatorConnection validator;
	
	@RequestMapping(value = "/searchGroup", method = RequestMethod.POST)
	public ModelAndView searchGroup(HttpServletRequest request) {
		Object userSession = request.getSession().getAttribute("user");
		User test = new User();
		if (userSession instanceof User)
			test = (User) userSession;
		String name = request.getParameter("name");
		Collection<Group> groups;
		try {
			groups = manager.findGroups(test,name);
		} catch (UserNotLoggedException e) 	{
			return new ModelAndView("redirect:/login");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("groups", groups);
		
		return new ModelAndView("listGroupSearch", map);
	}
	
	@RequestMapping(value = "/searchPerson", method = RequestMethod.POST)
	public ModelAndView searchPerson(HttpServletRequest request) {
		Object userSession = request.getSession().getAttribute("user");
		User test = new User();
		if (userSession instanceof User)
			test = (User) userSession;
		String name = request.getParameter("name");
		String firstname = request.getParameter("firstname");
		Collection<Person> persons;
		try {
			persons = manager.findPersons(test,name,firstname);
		} catch (UserNotLoggedException e) 	{
			return new ModelAndView("redirect:/login");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("persons", persons);
		
		return new ModelAndView("listPersonSearch", map);
	}
	
	
}
