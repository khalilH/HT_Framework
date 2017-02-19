package http;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by patrick.tran on 07/02/2017.
 */
public class RequestAnalyser {

    public static Request analyse(String request){
        Request res;
        Method method = null;
        Url url = new Url();
        HashMap<String, String> headers = new HashMap<>();
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
                headers.put(tab[0], tab[1]);
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

        // Getting the cookies ??

        res = new Request(url, method, headers, null);
        if (body.length() > 0)
            res.setBody(body);

        return res;
    }


}
