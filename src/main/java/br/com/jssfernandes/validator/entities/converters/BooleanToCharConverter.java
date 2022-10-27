package br.com.jssfernandes.validator.entities.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanToCharConverter implements AttributeConverter<Boolean, Character> {

    @Override
    public Character convertToDatabaseColumn(Boolean value) {        
        return (value != null && value) ? '1' : '0';            
    }    

    @Override
    public Boolean convertToEntityAttribute(Character value) {
        return (value != null && value == '1') ? true : false;
    }
}
