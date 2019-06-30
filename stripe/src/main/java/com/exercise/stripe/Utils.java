package com.exercise.stripe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Utils {
	//Regex expressions for validators
	private static final String EMAIL_VALIDATOR =  "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
	private static final String DECIMAL_VALIDATOR =  "[0-9]+(\\.[0-9][0-9]?)?";
	
	//Validator for email fields
    public static boolean emailValidator( String value){
		Pattern pattern = Pattern.compile(EMAIL_VALIDATOR);
		Matcher m = pattern.matcher(value);
		return m.matches();
    }
    
    //Validotr for decimal fields
    public static boolean decimalValidator(String value){
		Pattern pattern = Pattern.compile(DECIMAL_VALIDATOR);
		Matcher m = pattern.matcher(value);
		return m.matches();
    }
    
}
