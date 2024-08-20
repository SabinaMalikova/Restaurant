package peaksoft.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberValidation, String> {

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        return phoneNumber.matches("\\+996\\d{9}");
    }
}
