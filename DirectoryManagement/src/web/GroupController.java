package web;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import beans.Group;
import beans.Person;
import database.IDaoPerson;

@Controller()
@RequestMapping(value = "/groups")
public class GroupController {

	@Autowired
	IDaoPerson daoPerson;

	@ModelAttribute(name = "groups")
	Collection<Group> groups() {
		return daoPerson.findAllGroups();
	}
	
//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	public String listGroups() {
//		return "listGroups";
//	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listGroup(@RequestParam(value = "id", defaultValue = "-1") long id) {
		if (id == -1) return new ModelAndView("listGroups");
		Collection<Person> persons = daoPerson.findAllPersons(id);
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("persons", persons);
		return new ModelAndView("listPersons", map);
	}
}
