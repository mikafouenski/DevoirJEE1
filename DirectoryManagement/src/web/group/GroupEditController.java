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
import validor.ValidatorGroupEdit;
import web.ControllerHelpers;

@Controller()
@RequestMapping(value = "/groups")
public class GroupEditController {

	@Autowired
	IDirectoryManager directoryManager;
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editPersonDetail(@RequestParam(value = "id", required = true) long id,
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

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView editPersonForm(@ModelAttribute("group") Group g, BindingResult result,
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
			result.rejectValue("name", "group.name", "Erreur...");
			return new ModelAndView("group/groupEdit");
		}
		return new ModelAndView("redirect:/groups/list?id=" + g.getId());
	}
}
