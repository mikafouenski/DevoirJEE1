package web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import business.IDirectoryManager;
import validor.ValidatorConnection;


@Controller()
@RequestMapping(value = "/searchPerson")
public class ConnectionSearchPerson {
	
	@Autowired
	IDirectoryManager manager; 
	
	ValidatorConnection validator = new ValidatorConnection();
	
	
	
	
}
