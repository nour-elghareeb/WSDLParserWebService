package ne.wsdlparse.lib.xsd;

import java.io.IOException;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import ne.wsdlparse.lib.WSDLManagerRetrieval;
import ne.wsdlparse.lib.exception.WSDLException;

public class XSDComplexContent extends XSDComplexElement<XSDElement<?>> {
    public XSDComplexContent(WSDLManagerRetrieval manager, Node node)
            throws XPathExpressionException, SAXException, IOException, ParserConfigurationException, WSDLException {
        super(manager, node, XSDChoice.class);
    }

    @Override
    public String getNodeHelp() {
        return null;
    }

    @Override
    protected Boolean isESQLPrintable() {
        return true;
    }



}