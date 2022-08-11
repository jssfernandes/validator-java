package br.com.jssfernandes.validator.validators.exceptions;

import br.com.jssfernandes.validator.validators.Validator;

public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final transient Validator validator;

    public ValidationException(Validator validator) {
        super(validator.getErrorsToString());
        this.validator = validator;
    }

    public Validator getValidator() {
        return validator;
    }

}
