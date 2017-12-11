package web.connection;

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
import beans.QuestionSecrete;
import business.IDirectoryManager;
import business.User;
import business.exception.PersonNotFoundException;
import hashage.HashageSha3;
import validator.ValidatorConnection;
import validator.ValidatorQs;
import validator.ValidatorResetPassword;
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
			connection = manager.login(user, co.getMail(), co.getPassword());
		} catch (Exception e) {
			result.rejectValue("mail", "connect.mail", "Identifiant ou Mot de passe incorect");
			result.rejectValue("password", "connect.password", "Identifiant ou Mot de passe incorect");
			return "connection/connection";
		}
		if (connection) {
			request.getSession().setAttribute("user", user);
			return "redirect:/groups/list";
		}
		result.rejectValue("mail", "connect.mail", "Identifiant ou Mot de passe incorect");
		result.rejectValue("password", "connect.password", "Identifiant ou Mot de passe incorect");
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
	
	@ModelAttribute("passid")
	PassId passid() {
		return new PassId();
	}
	@ModelAttribute("passqs")
	PassQs passqs() {
		return new PassQs();
	}
	@RequestMapping(value = "/resetpassword", method = RequestMethod.GET)
	public String resetpassword(HttpServletRequest request) {
		return "connection/passwordreset";
	}
	@RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
	public ModelAndView displayResetFormID(@ModelAttribute PassId ps, BindingResult result, HttpServletRequest request) {
		Person p;
		ValidatorResetPassword validatorResetPassword = new ValidatorResetPassword();
		validatorResetPassword.validate(ps, result);
		if (result.hasErrors())
			return new ModelAndView("connection/passwordreset");
		try {
			p = manager.resetPasswordMail(ps.getMail());
		} catch (PersonNotFoundException e) {
			result.rejectValue("mail", "ps.mail", "La personne n'existe pas !");
			return new ModelAndView("connection/passwordreset");
		}
		return new ModelAndView("connection/passordresetQS", "qs", manager.resetPassword(p.getId()));
	}
	@RequestMapping(value = "/resetpasswordid", method = RequestMethod.POST)
	public ModelAndView displayResetFormQS(@RequestParam(value = "id", required = true) long id,
			@ModelAttribute PassQs ps, BindingResult result, HttpServletRequest request) {
		ValidatorQs validatorQs = new ValidatorQs();
		validatorQs.validate(ps, result);
		QuestionSecrete qsBDD = manager.resetPassword(id);
		if (result.hasErrors())
			return new ModelAndView("connection/passordresetQS", "qs", qsBDD);
		if (! HashageSha3.digest(ps.getReponse()).equals(qsBDD.getReponse())) {
			result.rejectValue("reponse", "ps.reponse", "Reponse non valide");
			return new ModelAndView("connection/passordresetQS", "qs", qsBDD);
		}
		Person p = manager.resetPasswordId(id);
		p.setPassword(HashageSha3.digest(ps.getNewPass()));
		manager.savePerson(p);
		return new ModelAndView("redirect:/login");
	}
}
