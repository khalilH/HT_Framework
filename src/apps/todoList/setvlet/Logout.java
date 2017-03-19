package apps.todoList.setvlet;

import apps.todoList.setvlet.model.Session;
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

            JSONObject logoutRes = new JSONObject();
            JSONObject jsonBody = new JSONObject(request.getBody());
            int userId = Integer.parseInt(jsonBody.getString("userId"));
            Session s;
            if (session != null) {
                if (session instanceof Session) {
                    s = (apps.todoList.setvlet.model.Session) session;
                    if (userId == s.getId()) {
                        SessionManager.delete(request.getUniqueId());
                        logoutRes.put("success", "Deconnexion");
                    } else {
                        logoutRes.put("error", "Vous n'etes pas connecte");
                    }
                } else {
                    logoutRes.put("error", "Vous n'etes pas connecte");
                }
            } else {
                logoutRes.put("error", "Vous n'etes pas connecte");
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
