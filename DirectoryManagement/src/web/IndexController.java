package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import business.exception.UserNotLoggedException;

@Controller()
@RequestMapping(value = "/")
public class IndexController {

	/**
	 * Redirige la racine vers la page de login
	 * @author Bernardini Mickael De Barros Sylvain 
	 * @return Nom de la servelet de login
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String redirectLogin() {
		return "redirect:/login";
	}
}
