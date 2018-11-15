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
import ne.wsdlparse.lib.xsd.constant.XSDSimpleElementType;

public class XSDExtention extends XSDComplexElement<XSDElement<?>> {
    private String base;
    private XSDSimpleElementType simpleType;

    public XSDExtention(WSDLManagerRetrieval manager, Node node)
            throws XPathExpressionException, SAXException, IOException, ParserConfigurationException, WSDLException {
        super(manager, node, XSDExtention.class);

    }

    @Override
    public String getNodeHelp() {
        return null;
    }

    /**
     * @param base the base to set
     */
    public void setBase(String base) {
        this.base = Utils.splitPrefixes(base)[1];
    }

    @Override
    protected void loadChildren()
            throws XPathExpressionException, SAXException, IOException, ParserConfigurationException, WSDLException {
        this.setBase(Utils.getAttrValueFromNode(node, "base"));
        try {
            this.simpleType = XSDSimpleElementType.parse(this.base);
        } catch (WSDLException e) {
            Node base = (Node) this.manager.getXSDManager()
                    .find(String.format(Locale.getDefault(), "/schema/*[@name='%s']", this.base), XPathConstants.NODE);
            XSDComplexElement baseElement = (XSDComplexElement) ((XSDComplexElement) XSDElement
                    .getInstance(this.manager, base)).getChildren().get(0);
            // this.node.setUserData("tns", base.getUserData("tns"), null);
            super.loadChildren();
            XSDComplexElement baseChild;
            try {
                baseChild = (XSDComplexElement) getChildren().get(0);
                for (XSDElement el : (ArrayList<XSDComplexElement>) baseChild.getChildren()) {
                    el.explicitlySetTargetNameSpace(this.getTargetTamespace());
                    baseElement.addChild(el);
                }
            } catch (IndexOutOfBoundsException e2) {

            }

            this.children = new ArrayList<XSDElement<XSDElement<?>>>();
            this.children.add(baseElement);
        }

    }

    @Override
    protected Boolean isESQLPrintable() {
        return true;
    }

    public XSDSimpleElementType getSimpleType() {
        return this.simpleType;
    }
}