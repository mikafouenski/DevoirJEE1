package web.person;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import business.IDirectoryManager;
import business.User;
import business.exception.UserNotLoggedException;
import web.ControllerHelpers;

@Controller()
@RequestMapping(value = "/persons")
public class PersonListController {
	
	@Autowired
	IDirectoryManager directoryManager;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listPersons(@RequestParam(value = "id", required = true) long id,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "range", defaultValue = "7") int range, HttpServletRequest request,
			HttpServletResponse response) {
		User user = ControllerHelpers.getUser(request);
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
		return new ModelAndView("person/listPersons", map);
	}
}
