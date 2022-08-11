package br.com.jssfernandes.validator.validators.helpers;

import br.com.jssfernandes.validator.models.ErrorDefault;

import java.util.List;

public interface ValidatorStrategyHelper {
    public void addError(String category, String message);

    public List<ErrorDefault> getErrors();

    public void clear();

}
