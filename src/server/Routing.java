package server;

import http.Url;
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

public class Routing {

    // Pour plus tard verifier que le fichier xml fourni est valide, pour l'instant on suppose qu'il est tjr valide

    private static final String MAPPING = "mapping";
    private static final String CLASS = "class";
    private static final String URL_PATTERN = "url-pattern";


    private Map<Pattern, String> mapping;
    private String xmlFilename;
    private Document document;

    public Routing() {
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

    public void route() {
        if (document == null) throw new NullPointerException("Document not parsed");

        NodeList mappingsNodes = document.getElementsByTagName(MAPPING);
        for (int i = 0; i < mappingsNodes.getLength(); i++) {
            Node m = mappingsNodes.item(i);
            if (m.getNodeType() == Node.ELEMENT_NODE) {
                NodeList childs = m.getChildNodes();
                Pattern pattern = null;
                String _class = null;
                for (int j = 0; j < childs.getLength(); j++) {
                    Node c = childs.item(j);
                    if(c.getNodeType() == Node.ELEMENT_NODE && c.getNodeName().equals(CLASS)) {
                        _class = c.getTextContent();
                    }
                    if(c.getNodeType() == Node.ELEMENT_NODE && c.getNodeName().equals(URL_PATTERN)) {
                        pattern = Pattern.compile(c.getTextContent());
                    }
                }
                mapping.put(pattern,_class);
            }
        }
    }



    public Set<Pattern> getPatterns() {
        return mapping.keySet();
    }

    public String getMapping(Pattern pattern) {
        return mapping.get(pattern);
    }

}
