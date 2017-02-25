package http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Created by patrick.tran on 07/02/2017.
 */
public class RequestAnalyser {

    /**
     * Permet de parser une requete HTTP a partir d'une chaine de caracteres
     * @param request la chaine de caracteres correspondant a une requete HTTP
     * @return un objet Request contenant toutes les informations de la requete
     */
    public static Request analyse(String request){
        Request res;
        Method method = null;
        Url url = new Url();
        HashMap<String, String> headers = new HashMap<>();
        List<Cookie> cookies = new ArrayList<>();
        Scanner scanner = new Scanner(request);
        String line, body = "";
        String[] tab;
        boolean readBody = false;

        // Getting the METHOD
        if(scanner.hasNextLine()) {
            line = scanner.nextLine();
            tab = line.split(" ");
            if (tab != null) {
                method = Method.valueOf(tab[0]);
                url.parseUrl(tab[1]);
            }
        }

        // Getting the headers & body if present
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            tab = line.split(": ");
            if (tab.length == 2) {
                if(tab[0].equals(Headers.COOKIE)){
                    String[] tmp = tab[1].split("=");
                    cookies.add(new Cookie(tmp[0],tmp[1]));
                }else {
                    headers.put(tab[0], tab[1]);
                }
            }
            else {
                if (readBody) // lecture du body
                    body = line;
                else // lecture de la ligne vide, la prochaine ligne attendu est le body
                    readBody = true;
            }
        }


        // Getting the URL
        url.setHost(headers.get(Headers.HOST));


        res = new Request(url, method, headers, cookies);
        res.setCookies(cookies);
        if (body.length() > 0)
            res.setBody(body);

        return res;
    }



}
