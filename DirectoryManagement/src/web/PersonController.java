package web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import beans.Person;
import business.IDirectoryManager;
import business.User;
import business.exception.UserNotLoggedException;

@Controller()
@RequestMapping(value = "/persons")
public class PersonController {

	@Autowired
	IDirectoryManager directoryManager;

	private User getUser(HttpServletRequest r) {
		Object userSession = r.getSession().getAttribute("user");
		if (userSession instanceof User)
			return (User) userSession;
		return new User();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listPersons(@RequestParam(value = "id", required = true) long id,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "range", defaultValue = "7") int range, HttpServletRequest request,
			HttpServletResponse response) {
		User user = getUser(request);
		Map<String, Object> map = new HashMap<>();
		try {
			int size = (int) directoryManager.nbPersons(user, id);
			int nbPages = ((int) Math.ceil(((double) size) / (double) range)) - 1;
			map.put("nbPage", nbPages);
			map.put("id", id);
			map.put("page", page);
			map.put("persons", directoryManager.findPersons(user, id, page * range, page * range + range));
		} catch (UserNotLoggedException e) {
			return new ModelAndView("redirect:/login");
		}
		return new ModelAndView("listPersons", map);
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ModelAndView detailPerson(@RequestParam(value = "id", required = true) long id, HttpServletRequest request,
			HttpServletResponse response) {
		User user = getUser(request);
		Person p;
		try {
			p = directoryManager.findPerson(user, id);
		} catch (UserNotLoggedException e) {
			return new ModelAndView("redirect:/login");
		}
		return new ModelAndView("personDetail", "person", p);
	}
	
//	@ModelAttribute("person")
//	public Person newPerson(@RequestParam(value = "id", required = false) Integer id, HttpServletRequest request,
//			HttpServletResponse response) {
//		User user = getUser(request);
//		if (id != null) {
//	        try {
//				return directoryManager.findPerson(user, id);
//			} catch (UserNotLoggedException e) {
//				System.out.println("user not logged");
//			}
//	    }
//	    return new Person();
//	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editPerson(@RequestParam(value = "id", required = true) long id, HttpServletRequest request,
			HttpServletResponse response) {
		User user = getUser(request);
		Person p;
		try {
			p = directoryManager.findPerson(user, id);
		} catch (UserNotLoggedException e) {
			return new ModelAndView("redirect:/login");
		}
		return new ModelAndView("personEdit", "person", p);
	}
}
