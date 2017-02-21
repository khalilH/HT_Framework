package apps.pointApp;

import http.Session;
import http.SessionManager;
import http.interfaces.ApplicationInterface;
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
    public Object doGet(RequestInterface request, SessionInterface session) {
        Session s = new Session(points);
        SessionManager.save(request.getUniqueId(), s);
        return points.keySet();

    }
}
