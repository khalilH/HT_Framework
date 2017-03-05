package http;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import exception.TemplateVariableNotFoundException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
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

    public static String replaceAllObject(String template, HashMap<String, Object> env) throws TemplateVariableNotFoundException, NoSuchFieldException, NoSuchMethodException {
        Pattern pattern = Pattern.compile("%((\\w+)((\\.)(\\w+))?)%");
        Matcher matcher = pattern.matcher(template);
        String varName, value = null, expr, className, getMethod, attrName;
        String[] tab;

        Class methodClass;
        Field[] classFields;
        int i,j;
        boolean foundField;

        while (matcher.find()) {
            // Exemple point1.x
            expr = matcher.group(1);
            tab = expr.split("\\.");
            // varName = point1
            varName = tab[0];
            // On vérifie que les attributs existent bien

            if(tab.length == 1) {
                if(env.get(varName) == null) {
                    value = null;
                }
                else {
                    value = env.get(varName).toString();
                }
            }else {
                try {
                    attrName = tab[1];
                    // Récup les fields de Point
                    className = env.get(varName).getClass().getName();
                    methodClass = Class.forName(className);
                    classFields = methodClass.getFields();

                    // Check si dans les fields il y a "x"
                    foundField = false;
                    for(j=0; j<classFields.length; j++){
                        if(classFields[j].getName().equals(tab[1])){
                            foundField = true;
                            break;
                        }
                    }

                    if(!foundField) {
                        throw new NoSuchFieldException("Field "+ tab[1] + " not found in " + className + ".");
                    }

                    getMethod = "get"+attrName.toUpperCase().charAt(0)+attrName.substring(1);
                    Method method = methodClass.getMethod(getMethod);
                    Object classInstance = env.get(varName);
                    value = method.invoke(classInstance) + "";


                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

            if (value == null)
                throw new TemplateVariableNotFoundException("Variable name not found in environment");
            template = template.replace("%" + expr + "%", value);

        }

        return template;
    }

}
