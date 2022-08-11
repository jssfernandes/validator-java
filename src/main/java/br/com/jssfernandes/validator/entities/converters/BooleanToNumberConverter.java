package br.com.jssfernandes.validator.entities.converters;

import java.math.BigDecimal;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanToNumberConverter implements AttributeConverter<Boolean, BigDecimal> {

    @Override
    public BigDecimal convertToDatabaseColumn(Boolean value) {        
        return (value != null && value) ? new BigDecimal("1") : new BigDecimal("0");            
    }    

    @Override
    public Boolean convertToEntityAttribute(BigDecimal value) {
        return (value != null && value == new BigDecimal("1")) ? true : false;
    }
}
