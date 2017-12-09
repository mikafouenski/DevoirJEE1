package web.person;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import beans.Person;
import business.IDirectoryManager;
import business.User;
import business.exception.UserNotLoggedException;
import web.ControllerHelpers;

@Controller()
@RequestMapping(value = "/persons")
public class PersonDetailController {

	@Autowired
	IDirectoryManager directoryManager;

	/**
	 * Servelet detaillant une personne
	 * @param id Le numéro de la personne a affichée
	 * @param request La HttpServletRequest de la requete
	 * @param response La HttpServletResponse de la requete
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Redirige vers la page de login si non authentifié ou la page de detail d'une personne
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ModelAndView detailPerson(@RequestParam(value = "id", required = true) long id, HttpServletRequest request,
			HttpServletResponse response) {
		User user = ControllerHelpers.getUser(request);
		Person p;
		try {
			p = directoryManager.findPerson(user, id);
		} catch (UserNotLoggedException e) {
			return new ModelAndView("redirect:/login");
		}
		return new ModelAndView("person/personDetail", "person", p);
	}
}
