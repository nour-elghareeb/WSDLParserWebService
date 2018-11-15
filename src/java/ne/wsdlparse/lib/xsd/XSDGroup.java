package ne.wsdlparse.lib.xsd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import ne.wsdlparse.lib.Utils;
import ne.wsdlparse.lib.WSDLManagerRetrieval;
import ne.wsdlparse.lib.exception.WSDLException;

public class XSDGroup extends XSDComplexElement<XSDElement> {
    private String reference;

    public XSDGroup(WSDLManagerRetrieval manager, Node node)
            throws XPathExpressionException, SAXException, IOException, ParserConfigurationException, WSDLException {
        super(manager, node, XSDExtention.class);
    }

    @Override
    public String getNodeHelp() {
        return String.format(Locale.getDefault(),
                "The following % children can appear in any order and each of them can appear once or not at all",
                this.children.size());
    }

    // TODO: fetch reference and load children...
    @Override
    protected void loadChildren()
            throws XPathExpressionException, SAXException, IOException, ParserConfigurationException, WSDLException {
        this.setReference(Utils.getAttrValueFromNode(this.node, "ref"));
        Node refElementNode = (Node) this.manager.getXSDManager()
                .find(String.format(Locale.getDefault(), "/schema/*[@name='%s']", this.reference), XPathConstants.NODE);
        XSDElement xsdRefElement = XSDElement.getInstance(this.manager, refElementNode);
        this.children = new ArrayList<XSDElement<XSDElement>>();
        this.children.add(xsdRefElement);
    }

    private void setReference(String ref) {
        this.reference = Utils.splitPrefixes(ref)[1];
    }

    @Override
    protected Boolean isESQLPrintable() {
        return true;
    }
}