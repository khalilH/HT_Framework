package apps.PointApp;

import http.interfaces.ApplicationInterface;
import http.interfaces.RequestInterface;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Khalil on 14/02/2017.
 */
public class List implements ApplicationInterface{

    public static Map<Integer, java.awt.Point> points_2 = new HashMap<>();

    @Override
    public Object doGet(RequestInterface request) {
        return points_2.keySet();
    }
}
