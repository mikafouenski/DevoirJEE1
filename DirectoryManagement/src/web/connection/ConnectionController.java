package web.connection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import business.IDirectoryManager;
import business.User;
import validator.ValidatorConnection;
import web.ControllerHelpers;

@Controller()
@RequestMapping(value = "/")
public class ConnectionController {

	@Autowired
	IDirectoryManager manager;
	
	@Autowired
	ValidatorConnection validator;

	@ModelAttribute
	Connection connect() {
		Connection connect = new Connection();
		return connect;
	}

	/**
	 * Servelet de login (GET)
	 * @param co La Connection de la requete
	 * @param request La HttpServletRequest de la requete
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Redirige vers le jsp de connection ou la liste de group si deja authentifié
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPrompt(@ModelAttribute Connection co, HttpServletRequest request) {
		User user = ControllerHelpers.getUser(request);
		if (user.isAnonymous())
			return "connection/connection";
		return "redirect:/groups/list";
	}

	/**
	 * Servelet de login (POST)
	 * @param co La Connection de la requete
	 * @param result Le BindingResult de la requete
	 * @param request La HttpServletRequest de la requete
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Redirige vers la liste de groups en cas succes
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute Connection co, BindingResult result, HttpServletRequest request) {
		User user = new User();
		boolean connection = false;
		validator.validate(co, result);
		if (result.hasErrors()) {
			return "connection/connection";
		}
		try {
			connection = manager.login(user, co.getId(), co.getPassword());
		} catch (Exception e) {
			result.rejectValue("id", "connect.id", "Identifiant ou Mot de passe incorect");
			result.rejectValue("password", "connect.password", "Identifiant ou Mot de passe incorect");
			return "connection/connection";
		}
		if (connection) {
			request.getSession().setAttribute("user", user);
			return "redirect:/groups/list";
		} else 
			return "connection/connection";
	}

	/**
	 * Servelet de logout (POST uniquement) detruit la session
	 * @param co La Connection de la requete
	 * @param result Le BindingResult de la requete
	 * @param request La HttpServletRequest de la requete
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Redirige vers la page de login
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(@ModelAttribute Connection co, BindingResult result, HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/login";
	}
}
