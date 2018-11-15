package ne.wsdlparse.lib;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import ne.wsdlparse.lib.esql.ESQLManager;
import ne.wsdlparse.lib.xsd.XSDManager;

public interface WSDLManagerRetrieval {
    Document getWSDLFile();

    XSDManager getXSDManager() throws XPathExpressionException, SAXException, IOException, ParserConfigurationException;

    XPath getXPath();

    String getTargetNameSpace();

    ESQLManager getESQLManager();

    String getNamespaceURI(String prefix);

    String getPrefix(String targetTamespace);

}