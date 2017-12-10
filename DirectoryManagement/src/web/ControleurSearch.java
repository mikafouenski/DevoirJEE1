package web;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import beans.Group;
import beans.Person;
import business.IDirectoryManager;
import business.User;
import business.exception.UserNotLoggedException;
import validator.ValidatorConnection;


@Controller()
@RequestMapping(value = "/")
public class ControleurSearch {
	
	@Autowired
	IDirectoryManager manager; 
	
	@Autowired
	ValidatorConnection validator;
	
	/**
	 * Servelet de recherche de groupes (POST uniquement)
	 * @param request La HttpServletRequest de la requete
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Redirige vers la page de login si non authentifié ou la page de liste des groupes trouvés
	 */
	@RequestMapping(value = "/searchGroup", method = RequestMethod.POST)
	public ModelAndView searchGroup(HttpServletRequest request) {
		User user = ControllerHelpers.getUser(request);
		String name = request.getParameter("name");
		Collection<Group> groups;
		try {
			groups = manager.findGroups(user,name);
		} catch (UserNotLoggedException e) 	{
			return new ModelAndView("redirect:/login");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("groups", groups);
		
		return new ModelAndView("listGroupSearch", map);
	}
	
	/**
	 * Servelet de recherche de personnes (POST uniquement)
	 * @param request La HttpServletRequest de la requete
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Redirige vers la page de login si non authentifié ou la page de liste des personnes trouvées
	 */
	@RequestMapping(value = "/searchPerson", method = RequestMethod.POST)
	public ModelAndView searchPerson(HttpServletRequest request) {
		User user = ControllerHelpers.getUser(request);
		String name = request.getParameter("name");
		String firstname = request.getParameter("firstname");
		Collection<Person> persons;
		try {
			persons = manager.findPersons(user,name,firstname);
		} catch (UserNotLoggedException e) 	{
			return new ModelAndView("redirect:/login");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("persons", persons);
		
		return new ModelAndView("listPersonSearch", map);
	}
	
	
}
