package validor;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import web.Connection;

@Service
public class ValidatorConnection implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Connection.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
//		Connection connect = (Connection) arg0;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"id","connect.id", "Identifiant ne doit pas être vide");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password","connect.password", "Mot de passe ne doit pas être vide");
		
	}

}
