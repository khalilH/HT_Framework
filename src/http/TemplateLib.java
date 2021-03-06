package http;

import apps.todoList.setvlet.model.User;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import exception.TemplateVariableNotFoundException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateLib {

    /**
     * Replace toutes les String dans le template qui correspondent à des noms de variables, par les valeurs de ces variables
     * à conditions qu'elles soient des primitives
     * @param template Template dans lequel on veut remplacer les variables
     * @param env Hashmap mappant les noms des variables à leurs valeurs
     * @return Le template dont les variables ont été remplacées par leurs valeurs
     * @throws TemplateVariableNotFoundException lorsque le fichier de template n'a pas été trouvé
     */
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

    /**
     * Replace toutes les String dans le template qui correspondent à des noms de variables, par les valeurs de ces variables
     * même si elles sont des attributs d'objet
     * @param template Template dans lequel on veut remplacer les variables
     * @param env Hashmap mappant les noms des variables à leurs valeurs
     * @return Le template dont les variables ont été remplacées par leurs valeurs
     * @throws TemplateVariableNotFoundException lorsque le fichier de template n'a pas été trouvé
     * @throws NoSuchFieldException lorsqu'un attribut n'a pas pu être associé à un champ dans la classe de l'objet
     * @throws NoSuchMethodException lorsque le getter n'a pas pu être trouvé pour un attribut dans la classe de l'objet
     */
    public static String replaceAllObject(String template, Map<String, Object> env) throws TemplateVariableNotFoundException, NoSuchFieldException, NoSuchMethodException {
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
                    classFields = methodClass.getDeclaredFields();

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

    /**
     * Replace toutes les String dans le template qui correspondent à des noms de variables, par les valeurs de ces variables
     * même si elles sont des attributs d'objet. Cette fonction permet d'éviter à l'utilisateur de faire lui-même la
     * lecture de la template
     * @param path Path vers le template
     * @param env Hashmap mappant les noms des variables à leurs valeurs
     * @return Le template dont les variables ont été remplacées par leurs valeurs
     * @throws IOException lorsqu'il y a eu un soucis au niveau de la lecture du fichier
     * @throws NoSuchMethodException lorsque le getter n'a pas pu être trouvé pour un attribut dans la classe de l'objet
     * @throws NoSuchFieldException lorsqu'un attribut n'a pas pu être associé à un champ dans la classe de l'objet
     * @throws TemplateVariableNotFoundException lorsque le fichier de template n'a pas été trouvé
     */
    public static String replaceInTemplate(String path, Map<String, Object> env) throws IOException, NoSuchMethodException, NoSuchFieldException, TemplateVariableNotFoundException {
        Path templatePath = Paths.get(path);
        Charset charset = StandardCharsets.UTF_8;
        String content = new String(Files.readAllBytes(templatePath), charset);
        String res = TemplateLib.replaceAllObject(content, env);
        return res;
    }

}
