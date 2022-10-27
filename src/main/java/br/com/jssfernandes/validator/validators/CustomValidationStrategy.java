package br.com.jssfernandes.validator.validators;


public interface CustomValidationStrategy<T> {

    public void verify(T t, Validator validator);
    
}
