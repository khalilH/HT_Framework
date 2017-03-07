package apps.chatApp.setvlet;

import apps.chatApp.setvlet.model.Session;
import apps.chatApp.setvlet.services.UserServices;
import http.*;
import http.interfaces.ApplicationInterface;
import http.interfaces.ApplicationResponseInterface;
import http.interfaces.RequestInterface;
import http.interfaces.SessionInterface;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ladislas on 07/03/2017.
 */
public class Logout implements ApplicationInterface {

    @Override
    public ApplicationResponseInterface doPost(RequestInterface request, SessionInterface session) {
        ApplicationResponseInterface response = new ApplicationResponse();
        try {

            String reqBody;
            Session s;
            reqBody = request.getBody();
            JSONObject logoutRes = null;
            if (session == null) {
                logoutRes = new JSONObject();
                logoutRes.put("repsonse", "No active session"); //TODO trouver msg
            }
            else {
                s = (Session) session;
                SessionManager.delete(request.getUniqueId());
                logoutRes.put("repsonse", "Deconnexion");
            }
            response.setBody(logoutRes);
            response.setContentType(Headers.APPLICATION_JSON);
        } catch (JSONException e) {
            response.setBody(ResponseBuilder.serverResponse(StatusCode.INTERNAL_SERVER_ERROR, request.getUrl().getPath()));
            response.setContentType(Headers.TEXT_HTML);
            e.printStackTrace();
        }
        return response;
    }

}
