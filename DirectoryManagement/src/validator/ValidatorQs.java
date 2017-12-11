package validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import web.connection.Connection;

public class ValidatorQs implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Connection.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object arg0, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reponse","qs.reponse", "La reponse ne doit pas être vide");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPass","qs.newPass", "Le nouveau password ne doit pas être vide");
	}
}
