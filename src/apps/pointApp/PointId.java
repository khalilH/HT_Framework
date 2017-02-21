package apps.pointApp;

import http.Url;
import http.interfaces.ApplicationInterface;
import http.interfaces.RequestInterface;
import http.interfaces.SessionInterface;

import java.awt.*;
import java.awt.Point;

/**
 * Created by Khalil on 14/02/2017.
 */
public class PointId implements ApplicationInterface {

    @Override
    public Object doGet(RequestInterface request, SessionInterface session) {
        Url url = request.getUrl();
        String[] tab = url.getPath().split("/");
        int id = Integer.parseInt(tab[2]);
        String coord = tab[3];
        Integer res = null;
        if(coord.equals("x")){
            res = new Integer(List.points.get(id).x);
        }else if(coord.equals("y")){
            res = new Integer(List.points.get(id).y);
        }
        return res;
    }
 }
