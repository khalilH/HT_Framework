package apps.todoList.setvlet;

import apps.todoList.setvlet.model.Session;
import apps.todoList.setvlet.services.UserServices;
import http.ApplicationResponse;
import http.Headers;
import http.ResponseBuilder;
import http.StatusCode;
import http.interfaces.ApplicationInterface;
import http.interfaces.ApplicationResponseInterface;
import http.interfaces.RequestInterface;
import http.interfaces.SessionInterface;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ladislas on 15/03/2017.
 */
public class RemoveMemo implements ApplicationInterface {

    @Override
    public ApplicationResponseInterface doPost(RequestInterface request, SessionInterface session) {
        ApplicationResponseInterface response = new ApplicationResponse();
        try {
            String requestBody = request.getBody();
            Session s;
            int userId;
            JSONObject jsonRequestBody = new JSONObject(requestBody);
            JSONObject jsonResponse = new JSONObject();

            if (session != null) {
                s = (Session) session;
                userId = s.getId();
                jsonResponse = UserServices.removeMemo(userId+"", jsonRequestBody.getInt("memoId"));
            } else {
                jsonResponse.put("error", "Vous n'etes pas connecte");
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
