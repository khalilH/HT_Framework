package http;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import exception.TemplateVariableNotFoundException;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ladislas on 28/02/2017.
 */
public class TemplateLib {

    public static String replaceAll(String template, HashMap<String, Object> env) throws TemplateVariableNotFoundException {
        Pattern pattern = Pattern.compile("%(\\w+)%");
        Matcher matcher = pattern.matcher(template);
        String varName, value;

        while (matcher.find()) {
            varName = matcher.group(1);
            value = env.get(varName).toString();
            if (value == null)
                throw new TemplateVariableNotFoundException("Variable name not found in environment");
            template = template.replace("%" + varName + "%", value);
        }
        return template;
    }

}
