package web.person;

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

import beans.Person;
import business.IDirectoryManager;
import business.User;
import business.exception.UserNotLoggedException;
import validator.ValidatorPersonEdit;
import web.ControllerHelpers;

@Controller()
@RequestMapping(value = "/persons")
public class PersonEditController {

	@Autowired
	IDirectoryManager directoryManager;
	
	/**
	 * Servelet d'édition d'une personne (GET)
	 * @param id L'identifiant du group a modifier
	 * @param request La HttpServletRequest de la requete
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Redirige vers la page de login si non authentifié ou la page d'edition
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editPersonDetail(@RequestParam(value = "id", required = true) long id,
			HttpServletRequest request) {
		User user = ControllerHelpers.getUser(request);
		Person p;
		try {
			p = directoryManager.findPerson(user, id);
		} catch (UserNotLoggedException e) {
			return new ModelAndView("redirect:/login");
		}
		PersonFormBean pf = new PersonFormBean();
		pf.setId(p.getId().toString());
		pf.setName(p.getName());
		pf.setFirstname(p.getFirstname());
		pf.setMail(p.getMail());
		pf.setWebsite(p.getWebsite());
		Map<String, Object> map = new HashMap<>();
		map.put("personFormBean", pf);
		map.put("id", id);
		return new ModelAndView("person/personEdit", map);
	}

	/**
	 * Servelet d'édition d'une personne (POST)
	 * @param pf La PersonFormBean modifié par le formulaire
	 * @param result Le BindingResult de la requete
	 * @param request La HttpServletRequest de la requete
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Redirige vers la page de login si non authentifié ou la page de detail d'une personne
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView editPersonForm(@ModelAttribute("personFormBean") PersonFormBean pf, BindingResult result,
			HttpServletRequest request) {
		User user = ControllerHelpers.getUser(request);
		Person p;
		try {
			p = directoryManager.findPerson(user, Integer.parseInt(pf.getId()));
		} catch (UserNotLoggedException e1) {
			return new ModelAndView("person/personEdit");
		}
		p.setName(pf.getName());
		p.setFirstname(pf.getFirstname());
		p.setMail(pf.getMail());
		p.setWebsite(pf.getWebsite());
		ValidatorPersonEdit validator = new ValidatorPersonEdit();
		validator.validate(p, result);
		if (result.hasErrors()) {
			return new ModelAndView("person/personEdit");
		}
		try {
			directoryManager.savePerson(user, p);
		} catch (UserNotLoggedException e) {
			result.rejectValue("id", "person.id", "Erreur...");
			return new ModelAndView("person/personEdit");
		}
		return new ModelAndView("person/personDetail", "person", p);
	}
}
