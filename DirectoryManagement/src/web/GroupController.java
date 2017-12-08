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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import beans.Connection;
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
		return daoPerson.findGroups(0, 2);
	}
	
	@ModelAttribute(name = "searchGroup")
	Group groupSearch() {
		return new Group();
	}
	
	@ModelAttribute(name = "searchPerson")
	Person personSearch() {
		return new Person();
	}
	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listGroup(@RequestParam(value = "id", defaultValue = "-1") long id) {
		if (id == -1) return new ModelAndView("listGroups");
		Collection<Person> persons = daoPerson.findPersons(id, 0, 2);
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("persons", persons);
		return new ModelAndView("listPersons", map);
	}
	

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute Group g,@ModelAttribute Person p) {
		if(p.getName() == null) {
			System.out.println("oui");
		}
		return null;
	}
	
	
//	@RequestMapping(value = "/test", method = RequestMethod.GET)
//	public void initDatabase() {
//		ArrayList<Group> groups = new ArrayList<>();
//		for (int i = 0; i < 200; i++) {
//			int j = (i % 10);
//			if (groups.size() <= j) {
//				Group g = new Group();
//				g.setName("group " + (j + 1));
//				daoPerson.saveGroup(g);
//				groups.add(g);
//			}
//			Person p = new Person();
//			p.setFirstname("f" + (i + 1));
//			p.setName("l" + (i + 1));
//			p.setBirthdate(Date.valueOf("2017-12-11"));
//			p.setMail("m" + (i + 1) + "@test.com");
//			p.setPassword(HachageSha3.digest("pass" + (i + 1)));
//			p.setWebsite("perdu.com");
//			p.setIdGroup(groups.get(j).getId());
//			daoPerson.savePerson(p);
//		}
//	}
    
}
