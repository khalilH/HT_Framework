package apps.todoList.setvlet;

import apps.todoList.setvlet.model.Session;
import apps.todoList.setvlet.services.UserServices;
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
            JSONObject jsonResponse = null;
            jsonResponse = UserServices.login(jsonBody.getString("login"), jsonBody.getString("password"));

            if (jsonResponse.has("id")) {
                int id = jsonResponse.getInt("id");
                if (session != null) {
                    if (session instanceof Session) {
                        s = (apps.todoList.setvlet.model.Session) session;
                        if (id == s.getId()) {
                            System.out.println(">>>> La session existe et c'est celle de l'utilisateur");
                        } else {
                            System.out.println(">>>> La session existe mais ce n'est pas celle de l'utilisateur");
                            System.out.println(">>>> on ecrase la session precedente");
                            s.setId(id);
                            SessionManager.save(request.getUniqueId(), s);
                        }
                    }
                } else {
                    System.out.println(">>>> La session n'existe pas");
                    s = new Session();
                    s.setId(id);
                    SessionManager.save(request.getUniqueId(), s);
                }
            }

            response.setBody(jsonResponse);
            response.setContentType(Headers.APPLICATION_JSON);
        } catch (JSONException e) {
            response.setBody(ResponseBuilder.serverResponse(StatusCode.INTERNAL_SERVER_ERROR, request.getUrl().getPath()));
            response.setContentType(Headers.TEXT_HTML);
            e.printStackTrace();
        }
        return response;
    }


}
