package apps.pointApp;

import http.ApplicationResponse;
import http.Url;
import http.interfaces.ApplicationInterface;
import http.interfaces.ApplicationResponseInterface;
import http.interfaces.RequestInterface;
import http.interfaces.SessionInterface;

/**
 * Created by Khalil on 14/02/2017.
 */
public class PointId implements ApplicationInterface {

    @Override
    public ApplicationResponseInterface doGet(RequestInterface request, SessionInterface session) {
        ApplicationResponseInterface response = new ApplicationResponse();
        Url url = request.getUrl();
        String[] tab = url.getPath().split("/");
        int id = Integer.parseInt(tab[2]);
        String coord = tab[3];
        if(coord.equals("x")){
            response.setBody(new Integer(List.points.get(id).x));
        }else if(coord.equals("y")){
            response.setBody(new Integer(List.points.get(id).y));
        }
        return response;
    }
 }
