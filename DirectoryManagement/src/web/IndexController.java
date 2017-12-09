package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller()
@RequestMapping(value = "/")
public class IndexController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String redirectLogin() {
		return "redirect:/login";
	}
}
