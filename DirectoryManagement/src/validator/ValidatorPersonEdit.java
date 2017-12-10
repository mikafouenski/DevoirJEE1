package validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import web.connection.Connection;

public class ValidatorPersonEdit implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return Connection.class.isAssignableFrom(arg0);
	}
	/**
	 * Génère des erreurs si le nom ou le prenom de l object Person est vide  
	 * @param arg0 un object de type Person a tester
	 * @param errors L'endroid ou les erreurs seront stocké
	 * @author Bernardini Mickael De Barros Sylvain 
	 */
	@Override
	public void validate(Object arg0, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "person.name", "Le nom ne doit pas être vide");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "person.firstname",
				"Le prenom ne doit pas être vide");
	}

}
