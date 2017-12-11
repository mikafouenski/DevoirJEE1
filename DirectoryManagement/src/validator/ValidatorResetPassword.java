package validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import web.connection.Connection;

public class ValidatorResetPassword implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Connection.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mail","qs.mail", "La reponse ne doit pas être vide");
	}
}
