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
import validor.ValidatorConnection;

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

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPrompt(@ModelAttribute Connection co, HttpServletRequest request) {
		Object userSession = request.getSession().getAttribute("user");
		User test = new User();
		if (userSession instanceof User)
			test = (User) userSession;
		if (test.isAnonymous())
			return "connection";
		return "redirect:/groups/list";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute Connection co, BindingResult result, HttpServletRequest request) {
		User user = new User();
		boolean connection = false;
		validator.validate(co, result);
		if (result.hasErrors()) {
			return "connection";
		}
		try {
			connection = manager.login(user, co.getId(), co.getPassword());
		} catch (Exception e) {
			result.rejectValue("id", "connect.id", "Identifiant ou Mot de passe incorect");
			result.rejectValue("password", "connect.password", "Identifiant ou Mot de passe incorect");
			return "connection";
		}
		if (connection) {
			request.getSession().setAttribute("user", user);
			return "redirect:/groups/list";
		} else 
			return "connection";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(@ModelAttribute Connection co, BindingResult result, HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/login";
	}
}