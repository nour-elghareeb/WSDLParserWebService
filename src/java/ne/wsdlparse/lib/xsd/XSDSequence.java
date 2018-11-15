package ne.wsdlparse.lib.xsd;

import java.io.IOException;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import ne.wsdlparse.lib.WSDLManagerRetrieval;
import ne.wsdlparse.lib.exception.WSDLException;

public class XSDSequence extends XSDComplexElement {
    public XSDSequence(WSDLManagerRetrieval manager, Node node)
            throws XPathExpressionException, SAXException, IOException, ParserConfigurationException, WSDLException {
        super(manager, node, XSDSequence.class);
    }

    @Override
    public String getNodeHelp() {
        return String.format(Locale.getDefault(), "The following %s children must appear in a sequence (in order)",
                this.children.size());
    }

    @Override
    protected Boolean isESQLPrintable() {
        return true;
    }

}