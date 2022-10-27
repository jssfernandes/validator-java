package br.com.jssfernandes.validator.validators;

import br.com.jssfernandes.validator.models.ErrorDefault;
import br.com.jssfernandes.validator.producers.MessageConfig;
import br.com.jssfernandes.validator.validators.strategies.SimpleValidatorStrategy;
import br.com.jssfernandes.validator.validators.exceptions.ValidationException;
import br.com.jssfernandes.validator.validators.helpers.DefaultValidatorStrategyHelper;
import br.com.jssfernandes.validator.validators.helpers.ValidatorStrategyHelper;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode; 
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Validator {
    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Autowired
    private DefaultValidatorStrategyHelper strategy;

    @Autowired
    private MessageConfig messageConfig;

    private HttpStatus status = HttpStatus.BAD_REQUEST;

    public void validate() {
        if (hasErrors()) {
            ValidationException ex = new ValidationException(this);
            ex.setStackTrace( Thread.currentThread().getStackTrace() );
//            logger.error("validate", ex);
            throw ex;
        }
    }

    public <T> Validator verify(String category, T t, SimpleValidatorStrategy<T> classe) {
        classe.addErrors(category, t, this);
        return this;
    }

    public <T> Validator verify(T t, Class<? extends CustomValidationStrategy<T>> clazz) {
        CustomValidationStrategy<T> st = null;

        try {
            st = beanFactory.createBean(clazz);
        } catch (IllegalStateException ex) {
            try {
                st = clazz.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex1) {
                //do nothing
            }
        }

        if (st != null) {
            st.verify(t, this);
        }

        return this;
    }

    public Validator withResponseStatus(HttpStatus status) {

        if (status != null) {
            this.status = status;
        }

        return this;
    }

    public Validator addError(String category, String message) {

        String result = this.messageConfig.get(message);

        if (result != null) {
            message = result;
        }

        this.strategy.addError(category, message);
        return this;
    }

    public List<ErrorDefault> getErrors() {
        return this.strategy.getErrors();
    }

    public boolean hasErrors() {
        return !this.strategy.getErrors().isEmpty();
    }

    public void clear() {
        this.strategy.clear();
    }

    public ValidatorStrategyHelper getStrategy() {
        return this.strategy;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorsToString() {
        return getErrorsToString(";\r\n", this.getErrors().size());
    }

    public String getErrorsToString(Integer untilIndex) {
        return getErrorsToString(";\r\n", untilIndex);
    }

    public String getErrorsToString(String delimiter, Integer untilIndex) {
        return this.getErrors().subList(0, untilIndex).stream().map(e -> e.getCategory() + ": " + e.getMessage())
                .collect(Collectors.joining(delimiter));
    }

}