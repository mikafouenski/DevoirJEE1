package web;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
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

import beans.Group;
import beans.Person;
import business.IDirectoryManager;
import business.User;
import business.exception.UserNotLoggedException;
import database.IDaoPerson;
import database.JDBC;
import hachage.HachageSha3;

@Controller()
@RequestMapping(value = "/groups")
public class GroupController {

	@Autowired
	IDirectoryManager directoryManager;
	
	@Autowired
	IDaoPerson daoPerson;
		
	private User getUser(HttpServletRequest r) {
		Object userSession = r.getSession().getAttribute("user");
		if (userSession instanceof User) return (User) userSession;
		return new User();
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listGroup(@RequestParam(value = "id", defaultValue = "-1") long id, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "range", defaultValue = "7") int range, HttpServletRequest request, HttpServletResponse response) {
		User user = getUser(request);
		if (id == -1) {
			Map<String, Object> map = new HashMap<>();
			try {
				int size = (int) directoryManager.nbGroups(user);
				int  nbPages = ((int) Math.ceil( ((double) size) / (double) range)) -1;
				map.put("nbPage", nbPages);
				map.put("page", page);
				map.put("groups", directoryManager.findGroups(user, page * range, page * range + range));
			} catch (UserNotLoggedException e) {
				return new ModelAndView("redirect:/login");
			}
			return new ModelAndView("listGroups", map);
		}
		Map<String, Object> map = new HashMap<>();
		try {
			int size = (int) directoryManager.nbPersons(user, id);
			int  nbPages = ((int) Math.ceil( ((double) size) / (double) range)) -1;
			map.put("nbPage", nbPages);
			map.put("id", id);
			map.put("page", page);
			map.put("persons", directoryManager.findPersons(user, id, page * range, page * range + range));
		} catch (UserNotLoggedException e) {
			return new ModelAndView("redirect:/login");
		}
		return new ModelAndView("listPersons", map);
	}
	

	
//	@RequestMapping(value = "/test", method = RequestMethod.GET)
//	public void initDatabase() {
//		new JDBC(true);
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
//    
}
