package ne.wsdlparse.lib.xsd;

import java.io.IOException;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import ne.wsdlparse.lib.Utils;
import ne.wsdlparse.lib.WSDLManagerRetrieval;
import ne.wsdlparse.lib.exception.WSDLException;

public class XSDChoice extends XSDComplexElement<XSDElement<?>> {

    public XSDChoice(WSDLManagerRetrieval manager, Node node)
            throws XPathExpressionException, SAXException, IOException, ParserConfigurationException, WSDLException {
        super(manager, node, XSDChoice.class);
    }

    @Override
    public String getNodeHelp() {
        return String.format(Locale.getDefault(), "You have a choice of the following %s parameters:",
                this.children.size());
    }

    @Override
    protected Boolean isESQLPrintable() {
        return true;
    }

    @Override
    protected void loadChildren()
            throws XPathExpressionException, SAXException, IOException, ParserConfigurationException, WSDLException {
        Node child = Utils.getFirstXMLChild(this.node);
        int i = 1;
        while (child != null) {
            child.setUserData("tns", this.node.getUserData("tns"), null);
            XSDElement element = XSDElement.getInstance(this.manager, child);
            element.setHelp(String.format(Locale.getDefault(), "Choice (%s) --------------", i));
            i++;
            this.children.add(element);
            child = Utils.getNextXMLSibling(child);
        }
    }
}