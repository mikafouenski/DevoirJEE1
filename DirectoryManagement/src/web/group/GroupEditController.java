package web.group;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import beans.Group;
import business.IDirectoryManager;
import business.User;
import business.exception.UserNotLoggedException;
import validator.ValidatorGroupEdit;
import web.ControllerHelpers;

@Controller()
@RequestMapping(value = "/groups")
public class GroupEditController {

	@Autowired
	IDirectoryManager directoryManager;
	
	/**
	 * Servelet d'édition d'un groupe (GET)
	 * @param id L'identifiant du group a modifier
	 * @param request La HttpServletRequest de la requete
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Redirige vers la page de login si non authentifié ou la page d'edition
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editGroupDetail(@RequestParam(value = "id", required = true) long id,
			HttpServletRequest request) {
		User user = ControllerHelpers.getUser(request);
		Group g;
		try {
			g = directoryManager.findGroup(user, id);
		} catch (UserNotLoggedException e1) {
			return new ModelAndView("redirect:/login");
		}
		return new ModelAndView("group/groupEdit", "group", g);
	}

	/**
	 * Servelet d'édition d'un groupe (POST)
	 * @param g La Group modifié par le formulaire
	 * @param result Le BindingResult de la requete
	 * @param request La HttpServletRequest de la requete
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Redirige vers la page de login si non authentifié ou la page de detail d'un groupe
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView editGroupForm(@ModelAttribute("group") Group g, BindingResult result,
			HttpServletRequest request) {
		User user = ControllerHelpers.getUser(request);
		ValidatorGroupEdit validator = new ValidatorGroupEdit();
		validator.validate(g, result);
		if (result.hasErrors()) {
			return new ModelAndView("group/groupEdit");
		}
		try {
			directoryManager.saveGroup(user, g);
		} catch (UserNotLoggedException e) {
			return new ModelAndView("redirect:/login");
		}
		return new ModelAndView("redirect:/groups/list?id=" + g.getId());
	}
}
