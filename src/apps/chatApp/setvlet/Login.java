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
public class Login implements ApplicationInterface {

    @Override
    public ApplicationResponseInterface doPost(RequestInterface request, SessionInterface session) {
        ApplicationResponseInterface response = new ApplicationResponse();
        try {

            String login, password, reqBody;
            Session s;
            reqBody = request.getBody();
            JSONObject jsonBody = new JSONObject(reqBody);
            JSONObject loginRes = null;
            if (session == null) {
                loginRes = UserServices.login(jsonBody.getString("login"), jsonBody.getString("password"));
                int id = loginRes.getInt("repsonse"); //TODO mettre constante ou faire attention
                if (id != -1) {
                    s = new Session();
                    s.setId(id);
                    SessionManager.save(request.getUniqueId(), s);
                }
            }
            else {
                s = (Session) session;
                loginRes = new JSONObject();
                loginRes.put("repsonse", s.getId());
            }
            response.setBody(loginRes);
            response.setContentType(Headers.APPLICATION_JSON);
        } catch (JSONException e) {
            response.setBody(ResponseBuilder.serverResponse(StatusCode.INTERNAL_SERVER_ERROR, request.getUrl().getPath()));
            response.setContentType(Headers.TEXT_HTML);
            e.printStackTrace();
        }
        return response;
    }



}
