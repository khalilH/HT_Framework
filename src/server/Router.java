package server;

import exception.MapperFileException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Router {

    private static final String MAPPING = "mapping";
    private static final String CLASS = "class";
    private static final String URL_PATTERN = "url-pattern";

    /**
     * Table contenant la classe associee a un chemin pour acceder a une ressource
     */
    private Map<String, String> mapping;

    /**
     * Nom du fichier de mapping
     */
    private String xmlFilename;

    /**
     * Objet contenant le resultat du fichier de mapping parse
     */
    private Document document;

    public Router() {
        mapping = new HashMap<>();
        xmlFilename = "web.xml";
        document = null;
    }

    /**
     * Permet de parser le fichier de mapping, le resultat est mis dans document
     * @throws ParserConfigurationException lorsqu'il y a eu un probleme a la creation du parser
     * @throws IOException lorsqu'il y a eu un probleme lors de l'analyse syntaxique
     * @throws SAXException lorsqu'il y a eu un probleme lors de l'analyse syntaxique
     */
    public void parseXML() throws ParserConfigurationException, IOException, SAXException {
        File file = new File(xmlFilename);

        if(!file.exists())
            throw new FileNotFoundException("web.xml is missing");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(file);

    }

    /**
     * Permet d'etablir la table des routes, une fois le fichier de mapping analyse
     * @throws NullPointerException si le fichier de mapping n'a pas ete analyse
     * @throws MapperFileException si le fichier de mapping a des donnees dupliquees
     */
    public void route() throws NullPointerException, MapperFileException {
        if (document == null) throw new NullPointerException("Document not parsed");

        // Getting all class associated to an url pattern
        NodeList mappingsNodes = document.getElementsByTagName(MAPPING);
        for (int i = 0; i < mappingsNodes.getLength(); i++) {
            Node m = mappingsNodes.item(i);
            if (m.getNodeType() == Node.ELEMENT_NODE) {
                NodeList childs = m.getChildNodes();
                String urlPattern = null;
                String _class = null;
                for (int j = 0; j < childs.getLength(); j++) {
                    Node c = childs.item(j);
                    if (c.getNodeType() == Node.ELEMENT_NODE) {
                        if (c.getNodeName().equals(CLASS)) {
                            _class = c.getTextContent();
                        }
                        if (c.getNodeName().equals(URL_PATTERN)) {
                            urlPattern = c.getTextContent();
                        }
                    }
                }
                if(mapping.containsKey(urlPattern))
                    throw new MapperFileException("Invalid mapper file, duplicated path: " + urlPattern);
                if(mapping.containsValue(_class))
                    throw new MapperFileException("Invalid mapper file, duplicated classPath: " + _class);
                mapping.put(urlPattern, _class);
            }
        }
        // Ici on ajoutera les application de gestion manuellement
        mapping.put("/", "apps.managerApp.Manager");
//        mapping.put("/help", "apps.managerApp.Help");
//        mapping.put("/", "apps.managerApp.Manager");
    }

    public Set<String> getPatterns() {
        return mapping.keySet();
    }

    public String getMapping(String urlPattern) {
        return mapping.get(urlPattern);
    }

}
