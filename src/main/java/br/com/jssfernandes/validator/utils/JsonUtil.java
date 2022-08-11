package br.com.jssfernandes.validator.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;

public class JsonUtil {

    private JsonUtil() {
        super();
    }

    public static String toJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            return null;
        }
//        return null;
    }

    public static <T> T toObject(String json, Class<T> c) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.registerModule(new JavaTimeModule());
        if (json != null) {
            try {
                return mapper.readValue(json, c);
            } catch (JsonProcessingException ex) {
                return null;
            }
        }
        return null;
    }
    
    public static <T> T toObject(String json, TypeReference valueTypeRef) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.registerModule(new JavaTimeModule());
        if (json != null) {
            try {
                return mapper.readValue(json, valueTypeRef);
            } catch (JsonProcessingException ex) {
                return null;
            }
        }
        return null;
    }

}
