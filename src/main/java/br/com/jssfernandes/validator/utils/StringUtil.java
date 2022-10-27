package br.com.jssfernandes.validator.utils;

import java.text.Normalizer;
import org.apache.commons.lang3.StringUtils;

public class StringUtil {

    private StringUtil() {}

    public static String accentsRemove(String str) {
        if (!StringUtils.isEmpty(str)) {
            return Normalizer.normalize(str.toUpperCase().trim(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        }
        return null;
    }

    public static String onlyNumbers(String value) {
        if (!StringUtils.isEmpty(value)) {
            return value.replaceAll("[^0-9]", "");
        }
        return null;
    }
}
