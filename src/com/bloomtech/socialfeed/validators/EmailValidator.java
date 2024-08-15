package com.bloomtech.socialfeed.validators;

import com.bloomtech.socialfeed.exceptions.EmailValidationException;
import java.util.regex.Pattern;

public class EmailValidator implements Validator {
    public EmailValidator() {
    }

    private boolean isValidEmail(String email) {
        /*TODO: Validate that email begins with a letter or number, contains only letters, numbers, "." and "_", and
        *that it follows the pattern of name@domain.identifier
        */
        String emailRule = "^[A-Za-z0-9._%+-]+@[A-Za-z]+\\.[A-Za-z]{2,}$";

        return Pattern.matches(emailRule, email);
    }

    @Override
    public void validate(Object emailData) {
        String email = (String) emailData;
        if (!isValidEmail(email)) {
            throw new EmailValidationException("Invalid Email: Email address must include '@' before domain and a domain identifier after a '.'!");
        }
    }
}
