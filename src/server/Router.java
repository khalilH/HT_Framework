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
import java.util.regex.Pattern;

public class Router {

    // Pour plus tard verifier que le fichier xml fourni est valide, pour l'instant on suppose qu'il est tjr valide
    // Construire la map des routes au lancement du server ?

    private static final String MAPPING = "mapping";
    private static final String CLASS = "class";
    private static final String URL_PATTERN = "url-pattern";


    private Map<String, String> mapping;
    private String xmlFilename;
    private Document document;

    public Router() {
        mapping = new HashMap<>();
        xmlFilename = "web.xml";
        document = null;
    }

    public void parseXML() throws ParserConfigurationException, IOException, SAXException {
        File file = new File(xmlFilename);

        if(!file.exists())
            throw new FileNotFoundException("web.xml is missing");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(file);

    }

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
    }

    public Set<String> getPatterns() {
        return mapping.keySet();
    }

    public String getMapping(String urlPattern) {
        return mapping.get(urlPattern);
    }

}
