package com.hitler.core.validation;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<Username, String> {
	
	private static final String PATTERN = "[a-zA-Z0-9_]{3,16}";

	private Pattern pattern;
	
	@Override
	public void initialize(Username constraintAnnotation) {
		pattern = Pattern.compile(PATTERN);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return pattern.matcher(value).matches();
	}


}
