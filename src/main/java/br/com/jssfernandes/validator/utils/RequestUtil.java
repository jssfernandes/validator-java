package br.com.jssfernandes.validator.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestUtil {

    public static String getPathVariable(HttpServletRequest request, String pathVariable) {
        if (request == null) return null;

        Pattern pattern = Pattern.compile("(\\/"+pathVariable+")(\\/[\\w]+)");
        Matcher matcher = pattern.matcher(request.getRequestURI());

        if (matcher.find() && !matcher.group(2).isEmpty()) {
            return matcher.group(2).replace("/","");
        }
        return null;
    }
}
