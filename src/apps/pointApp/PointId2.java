package apps.pointApp;

import http.ApplicationResponse;
import http.interfaces.ApplicationInterface;
import http.interfaces.ApplicationResponseInterface;
import http.interfaces.RequestInterface;
import http.interfaces.SessionInterface;

import java.awt.Point;

/**
 * Created by Khalil on 14/02/2017.
 */
public class PointId2 implements ApplicationInterface {

    @Override
    public ApplicationResponseInterface doPut(RequestInterface request, SessionInterface session) {
        ApplicationResponseInterface response = new ApplicationResponse();
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
                response.setBody("true");
                return response;
            } else {
                response.setBody("false");
                return response;
            }
        }
        // mieux gerer les erreur avec des exception et les codes erreurs http
        response.setBody("Point "+id+" does not exists");
        return response;
    }

    @Override
    public ApplicationResponseInterface doDelete(RequestInterface request, SessionInterface session) {
        ApplicationResponseInterface response = new ApplicationResponse();
        String[] tab = request.getUrl().getPath().split("/");
        int id = Integer.parseInt(tab[2]);
        if (List.points.containsKey(id)) {
            response.setBody(List.points.remove(id));
            return response;
        } else {
            response.setBody("false");
            return response;
        }
    }

}
