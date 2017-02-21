package apps.pointApp;

import http.Url;
import http.interfaces.ApplicationInterface;
import http.interfaces.RequestInterface;
import http.interfaces.SessionInterface;

import java.awt.Point;

/**
 * Created by Khalil on 14/02/2017.
 */
public class PointId2 implements ApplicationInterface {

    @Override
    public Object doPut(RequestInterface request, SessionInterface session) {
        String[] tab = request.getUrl().getPath().split("/");
        int id = Integer.parseInt(tab[2]);
        String xx = request.getParameter("x");
        String yy = request.getParameter("y");
        if (List.points.containsKey(id)) {

            int x = List.points.get(id).x, y = y = List.points.get(id).y;;
            boolean change = false;

            if (xx != null && !xx.equals("")) {
                x = Integer.parseInt(xx);
                change = true;
            }

            if (yy != null && !yy.equals("")) {
                y = Integer.parseInt(yy);
                change = true;
            }

            if (List.points.containsKey(id)) {
                List.points.put(id, new Point(x,y));
                String ret = change ? "true" : "false";
                return ret;
            } else {
                return "false";
            }
        }
        // mieux gerer les erreur avec des exception et les codes erreurs http
        return "Point "+id+" does not exists";
    }

    @Override
    public Object doDelete(RequestInterface request, SessionInterface session) {
        String[] tab = request.getUrl().getPath().split("/");
        int id = Integer.parseInt(tab[2]);
        if (List.points.containsKey(id)) {
            return List.points.remove(id);
        } else {
            return "false";
        }
    }

}
