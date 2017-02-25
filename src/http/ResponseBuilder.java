package http;

/**
 * Created by ladislas on 21/02/2017.
 */

/**
 * Permet de renvoyer des reponses toutes faites
 */
public class ResponseBuilder {

//    public static Response internalServerError(int statusCode, String templateFileName, string path)

    public static Response serverResponse(int statusCode) {
        Response response = new Response();
        response.setStatusCode(statusCode);
        String msg = StatusCode.getDescription(statusCode);
        response.setBody(msg);
        response.addHeader(Headers.CONTENT_TYPE, Headers.TEXT_PLAIN); //HTML ?
        response.addHeader(Headers.CONTENT_LENGTH, msg.length()+"");
        return response;
    }
}
