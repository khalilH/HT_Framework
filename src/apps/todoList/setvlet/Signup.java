package apps.todoList.setvlet;

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
public class Signup implements ApplicationInterface {

    @Override
    public ApplicationResponseInterface doPost(RequestInterface request, SessionInterface session) {
        ApplicationResponseInterface response = new ApplicationResponse();
        try {
            String login, password, reqBody;
            reqBody = request.getBody();
            JSONObject jsonBody = new JSONObject(reqBody);
            JSONObject signupRes = UserServices.signup(jsonBody.getString("login"), jsonBody.getString("password"),
                    jsonBody.getString("prenom"),jsonBody.getString("nom"),jsonBody.getInt("age"));
            response.setBody(signupRes);
            response.setContentType(Headers.APPLICATION_JSON);
        } catch (JSONException e) {
            response.setBody(ResponseBuilder.serverResponse(StatusCode.INTERNAL_SERVER_ERROR, request.getUrl().getPath()));
            response.setContentType(Headers.TEXT_HTML);
            e.printStackTrace();
        }
        return response;
    }

//    @Override
//    public ApplicationResponseInterface doGet(RequestInterface request, SessionInterface session) {
//        ApplicationResponseInterface response = new ApplicationResponse();
//        String login, password, reqBody;
//        JSONObject signupRes = UserServices.signup(request.getParameter("login"), request.getParameter("password"));
//        response.setBody(signupRes);
//        response.setContentType(Headers.APPLICATION_JSON);
//        return response;
//    }

}