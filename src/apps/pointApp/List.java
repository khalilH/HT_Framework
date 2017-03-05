package apps.pointApp;

import exception.TemplateVariableNotFoundException;
import http.ApplicationResponse;
import http.TemplateLib;
import http.interfaces.ApplicationInterface;
import http.interfaces.ApplicationResponseInterface;
import http.interfaces.RequestInterface;
import http.interfaces.SessionInterface;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Khalil on 14/02/2017.
 */
public class List implements ApplicationInterface{


    public static Map<Integer, java.awt.Point> points;
    static {
        points = new HashMap<>();
        points.put(1, new Point(1,1));
        points.put(2, new Point(2,2));
        points.put(3, new Point(3,3));
        points.put(4, new Point(4,4));
        points.put(5, new Point(5,5));
    }

    @Override
    public ApplicationResponseInterface doGet(RequestInterface request, SessionInterface session) {
        ApplicationResponseInterface response = new ApplicationResponse();

        HashMap<String, Object> env = new HashMap<>();
        Point toz = new Point(7,7);
        env.put("Tz1", points.get(1).toString());
        env.put("Toz2", points.get(2).toString());
        env.put("Toz3", points.get(3).toString());
        env.put("toz", toz);
        String template = "<html><head></head><body><ul><li>%toz.x%</li><li>%Toz2%</li><li>%Toz3%</li></ul></body></html>";
        try {
            template = TemplateLib.replaceAllObject(template, env);
        } catch (TemplateVariableNotFoundException e) {
            template = "Template not well formed.";
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            template = e.toString();
        } catch (NoSuchMethodException e) {
            template = e.toString();
        }
        response.setBody(template);
        return response;
    }
}
