package br.com.jssfernandes.validator.validators.strategies;

import br.com.jssfernandes.validator.validators.Validator;


public abstract class SimpleValidatorStrategy<T> {
    private String message;
    private Object[] parameters;

    public SimpleValidatorStrategy(String message, Object...defaultParameters) {
        this.message = message;
        this.parameters = defaultParameters;
    }

    public SimpleValidatorStrategy<T> message(String message){
        this.message = message;
        return this;
    }

    public void addErrors(String category, T t, Validator validator) {
        if(shouldAddError(t)) validator.addError(category, this.message);
    }

    public Object[] getParameters() {
        return parameters;
    }

    protected abstract boolean shouldAddError(T t);

}
