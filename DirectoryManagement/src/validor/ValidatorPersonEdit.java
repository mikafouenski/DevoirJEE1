package validor;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import web.Connection;

public class ValidatorPersonEdit implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return Connection.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "person.name", "Le nom ne doit pas être vide");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "person.firstname",
				"Le prenom ne doit pas être vide");
	}

}
