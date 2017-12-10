package web.person;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
	
	/**
	 * Servelet listant les personnes
	 * @param id Le numéro du groupe dont font partie les personnes
	 * @param page Le numéro de page demandé
	 * @param range Le nombre de résultats demandé
	 * @param request La HttpServletRequest de la requete
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Redirige vers la page de login si non authentifié ou la page de liste des personnes
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listPersons(@RequestParam(value = "id", required = true) long id,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "range", defaultValue = "7") int range, HttpServletRequest request) {
		User user = ControllerHelpers.getUser(request);
		Map<String, Object> map = new HashMap<>();
		try {
			int size = (int) directoryManager.nbPersons(user, id);
			int nbPages = (size / range) + ((size % range != 0) ? 1 : 0);
			map.put("nbPage", nbPages);
			map.put("id", id);
			map.put("page", page);
			map.put("persons", directoryManager.findPersons(user, id, (page - 1) * range, range));
		} catch (UserNotLoggedException e) {
			return new ModelAndView("redirect:/login");
		}
		return new ModelAndView("person/listPersons", map);
	}
}
