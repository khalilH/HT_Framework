package http;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import exception.TemplateVariableNotFoundException;

import java.lang.reflect.Field;
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

    public static String replaceAllObject(String template, HashMap<String, Object> env) throws TemplateVariableNotFoundException {
        Pattern pattern = Pattern.compile("%(\\w+)((\\.)?(\\w+)*)*%");
        Matcher matcher = pattern.matcher(template);
        String varName, value, expr, className;
        String[] tab;

        Class methodClass;
        Field[] classFields;
        int i,j;
        boolean foundField;

        while (matcher.find()) {
            // Exemple point1.x
            expr = matcher.group(0);
            tab = expr.split(".");
            // varName = point1
            varName = tab[0];
            // Ici on fait un for pcq on peut avoir objet.objet.attr...
            // On vérifie que les attributs existent bien
            for(i=1; i<tab.length; i++){
                try {
                    // Récup les fields de Point
                    className = env.get(varName).getClass().getName();
                    methodClass = Class.forName(className);
                    classFields = methodClass.getFields();

                    // Check si dans les fields il y a "x"
                    foundField = false;
                    for(j=0; j<classFields.length; j++){
                        if(classFields[j].getName().equals(tab[i])){
                            foundField = true;
                            break;
                        }
                    }

                    if(!foundField) {
                        // throw exception ?
                    }

                    //Reattribuer a className la classe du field en question pour itérer dessus si besoin

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            // Remplacer par varName.getX (cela suppose que les getters et setters sont tous définis)

            value = env.get(varName).toString();
            if (value == null)
                throw new TemplateVariableNotFoundException("Variable name not found in environment");
            template = template.replace("%" + varName + "%", value);
        }
        return template;
    }

}
