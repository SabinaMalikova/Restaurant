package peaksoft.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PriceValidator implements ConstraintValidator<PriceValidation, Double> {

    @Override
    public boolean isValid(Double price, ConstraintValidatorContext constraintValidatorContext) {
        return price > 0;
    }
}
