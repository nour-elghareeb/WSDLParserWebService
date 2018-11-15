package ne.wsdlparse.lib.xsd;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import ne.wsdlparse.lib.WSDLManagerRetrieval;
import ne.wsdlparse.lib.exception.WSDLException;
import ne.wsdlparse.lib.xsd.constant.XSDSimpleElementType;

public class XSDAny extends XSDSimpleElement<Void> {
    protected boolean isSkippable = true;

    public XSDAny(WSDLManagerRetrieval manager, Node node){

        super(manager, node, XSDSimpleElementType.ANY);
    }

    @Override
    public String getNodeHelp() {
        return "You can add any custom element here";
    }

    @Override
    protected Boolean isESQLPrintable() {
        return false;
    }
    @Override
    public void toESQL() {
        // super.toESQL();
        this.manager.getESQLManager().addParam(this.prefix, "", XSDSimpleElementType.ANY, null);
    }
}