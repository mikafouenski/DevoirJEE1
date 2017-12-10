package validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import web.connection.Connection;

@Service
public class ValidatorConnection implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Connection.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object arg0, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"id","connect.id", "Identifiant ne doit pas être vide");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password","connect.password", "Mot de passe ne doit pas être vide");
		
	}

}
