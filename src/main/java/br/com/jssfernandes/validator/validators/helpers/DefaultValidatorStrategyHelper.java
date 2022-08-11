package br.com.jssfernandes.validator.validators.helpers;

import br.com.jssfernandes.validator.models.ErrorDefault;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;


@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DefaultValidatorStrategyHelper implements ValidatorStrategyHelper {

    protected List<ErrorDefault> errorDefaults = new ArrayList<>();

    @Override
    public void addError(String category, String message) {
        this.errorDefaults.add(new ErrorDefault(category, message));
    }

    @Override
    public List<ErrorDefault> getErrors() {
        return this.errorDefaults;
    }

    @Override
    public void clear() {
        this.errorDefaults = new ArrayList<>();
    }
}