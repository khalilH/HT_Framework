package server;

import http.Headers;
import http.RequestAnalyser;
import http.StatusCode;
import http.interfaces.RequestInterface;
import http.interfaces.ResponseInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite d'un serveur generique qui ecoute sur un port donne
 * et effectue la boucle suivante
 * lecture et analyse syntaxique de la requete
 * traitement de la requete
 * envoie de la reponse au client
 */
public abstract class AbstractServer implements ServerInterface{

    /**
     * Port sur lequel ecoute le serveur
     */
    protected int port;
    /**
     * Socket du serveur
     */
    protected ServerSocket serverSocket;
    /**
     * Liste des sockets clientes
     */
    protected List<Socket> clientSockets;

    public AbstractServer(int port) {
        StatusCode.init();
        this.port = port;
        clientSockets = new ArrayList<>();
    }

    /**
     * Methode abstraite du traitement d'une requete
     * @param request la requete a traiter
     * @return un objet ResponseInterface
     */
    public abstract ResponseInterface handleRequest(RequestInterface request);

    /**
     * Methode demarrant l'ecoute du serveur sur son port.
     * Boucle de lecture de la requete / traitement de la requete / ecriture de
     * la reponse au client
     * @throws IOException
     */
    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Listening on port "+port+"...");
        while (true) {
            // TODO avant d'ajouter dans la liste des socket, verifier que la liste ne contient pas plus de 100 elelemts
            // TODO valeur choisie comme ca, ptet prendre autre chose
            if(clientSockets.size() < 100)
                clientSockets.add(serverSocket.accept());
            else
                continue;
            System.out.print("Connection opened from ");
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // Recuperation de l'adresse IP
                        Socket clientSocket = clientSockets.remove(0);
                        String _ip = clientSocket.getRemoteSocketAddress().toString();
                        String[] tab = _ip.split(":");
                        String ip = tab[0].substring(1);
                        System.out.println(ip);

                        // Ouverture des flux
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        String inputLine, httpRequestText = "";
                        boolean hasBody = false, isReadingBody = false;
                        int contentLength = 0;

                        //Lecture de requete HTTP
                        inputLine = in.readLine();
                        while (!inputLine.equals("") || (inputLine.equals("") && hasBody == true)) {
                            if(inputLine.contains(Headers.CONTENT_LENGTH)) {
                                hasBody = true;
                                contentLength = Integer.parseInt(inputLine.split(": ")[1]);
                            }
                            if(inputLine.equals("")) {
                                hasBody = false;
                                isReadingBody = true;
                            }
                            if(isReadingBody){
                                char[] params = new char[contentLength];
                                isReadingBody = false;
                                in.read(params, 0, contentLength);
                                inputLine = new String(params);
                                httpRequestText += System.lineSeparator() + inputLine + System.lineSeparator();
                                break;
                            }else{
                                httpRequestText += inputLine + System.lineSeparator();
                                inputLine = in.readLine();
                            }
                        }
                        System.out.println("=============");
                        System.out.println(httpRequestText);

                        // Analyse syntaxique de la requete HTTP
                        RequestInterface request = RequestAnalyser.analyse(httpRequestText);
                        request.setIp(ip);

                        // Traitement de la requete HTTP
                        ResponseInterface response = handleRequest(request);

                        out.println(response.toString());
                        out.close();
                        in.close();
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
            t.start();
        }
    }

    /**
     * Permet d'arreter le serveur
     * @throws IOException
     */
    public void shutdown() throws IOException {
        serverSocket.close();
    }




}
