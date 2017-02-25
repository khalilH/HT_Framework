package http;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by patrick.tran on 14/02/2017.
 */
public class Url {

    private String host;

    /**
     * Chemin de la ressource demandee
     */

    private String path;

    /**
     * Lien absolu
     */
    private String entirePath;

    private Map<String, String> arguments;


    /**
     * Permet de recuperer les differentes parties d'une URL
     * @param pathString l'URL a parser
     */
    public void parseUrl(String pathString){
        String[] pathWithoutFragment = pathString.split("#");
        entirePath = pathWithoutFragment[0];
        String[] tmp = pathWithoutFragment[0].split("\\?");
        setPath(tmp[0]);
            arguments = new HashMap<>();
        if(tmp.length > 1){
            String[] args = tmp[1].split("&");
            String[] entry;
            for(String arg : args){
                entry = arg.split("=");
                arguments.put(entry[0], entry[1]);
            }
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getArguments() {
        return arguments;
    }

    public String getEntirePath() { return entirePath; }

    public String toString(){
        return host + entirePath;
    }

    public String getParameter(String param) {
        return arguments.get(param);
    }
}
