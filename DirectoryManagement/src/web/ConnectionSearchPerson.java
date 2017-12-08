package web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import beans.Connection;
import beans.Group;
import beans.Person;
import business.IDirectoryManager;
import business.User;
import validor.ValidatorConnection;


@Controller()
@RequestMapping(value = "/searchPerson")
public class ConnectionSearchPerson {
	
	@Autowired
	IDirectoryManager manager; 
	
	ValidatorConnection validator = new ValidatorConnection();
	
	
	
	
}
