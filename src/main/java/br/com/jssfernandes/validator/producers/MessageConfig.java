package br.com.jssfernandes.validator.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;


@Component
public class MessageConfig {
    @Autowired
    private MessageSource messageSource;
    
    public String get(String key){
        if (key != null){
            return this.messageSource.getMessage(key, null, LocaleContextHolder.getLocale()); 
        }else{
            return null;
        }
    }
    
    public String get(String key, String... params ){
        if (key != null){
            return this.messageSource.getMessage(key, params, LocaleContextHolder.getLocale()); 
        }else{
            return null;
        }
    }
}
