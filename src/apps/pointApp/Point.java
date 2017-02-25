package apps.pointApp;

import http.ApplicationResponse;
import http.interfaces.ApplicationInterface;
import http.interfaces.ApplicationResponseInterface;
import http.interfaces.RequestInterface;
import http.interfaces.SessionInterface;

import static apps.pointApp.List.points;

/**
 * Created by Khalil on 14/02/2017.
 */
public class Point implements ApplicationInterface{

    @Override
    public ApplicationResponseInterface doPost(RequestInterface request, SessionInterface session) {
        ApplicationResponseInterface response = new ApplicationResponse();
        String body = request.getBody();
        String[] point = body.split(",");
        int x = Integer.parseInt(point[0]);
        int y = Integer.parseInt(point[1]);
        Integer id = points.size()+1;
        points.put(id, new java.awt.Point(x,y));
        response.setBody(id);
        return response;
    }

}
