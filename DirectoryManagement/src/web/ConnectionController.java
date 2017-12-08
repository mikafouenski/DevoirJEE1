package web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import business.IDirectoryManager;
import business.User;
import validor.ValidatorConnection;


@Controller()
@RequestMapping(value = "/connection")
public class ConnectionController {
	
	@Autowired
	IDirectoryManager manager; 
	
	ValidatorConnection validator = new ValidatorConnection();
	
	@ModelAttribute
	Connection connect() {
		Connection connect = new Connection();
		return connect;
	}
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView editProduct(@ModelAttribute Connection co,HttpServletRequest request, HttpServletResponse response) {
		Object userSession = request.getSession().getAttribute("user");
		User test = new User();
		if(userSession instanceof User)
			test = (User) userSession;
		if(test.isAnonymous())
			return new ModelAndView("connection");
		return new ModelAndView("redirect:/actions/groups/list");
		
	}
	
	@RequestMapping(value = "/display", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute Connection co,BindingResult result,HttpServletRequest request) {
		User user = new User();
		boolean connection = false;
		validator.validate(co, result);
		if(result.hasErrors()) {
			return "connection";
		}
		try {
			connection = manager.login(user, co.getId(), co.getPassword());
		} catch (Exception e) {
			result.rejectValue("id","connect.id", "Identifiant ou Mot de passe incorect");
			result.rejectValue("password","connect.password", "Identifiant ou Mot de passe incorect");
			return "connection";
		}
		if(connection) {
			request.getSession().setAttribute("user", user);
		}
		return "redirect:/actions/groups/list";
	}
}
