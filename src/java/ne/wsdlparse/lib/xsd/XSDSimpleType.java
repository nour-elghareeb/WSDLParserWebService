package ne.wsdlparse.lib.xsd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ne.wsdlparse.lib.Utils;
import ne.wsdlparse.lib.WSDLManagerRetrieval;
import ne.wsdlparse.lib.esql.ESQLLine;
import ne.wsdlparse.lib.exception.WSDLException;
import ne.wsdlparse.lib.utility.ConsoleStyle;
import ne.wsdlparse.lib.xsd.constant.XSDSimpleElementType;
import ne.wsdlparse.lib.xsd.restriction.XSDRestrictionParam;

public class XSDSimpleType extends XSDComplexElement<XSDElement<?>> {
    private String[] restrictionStringRepresntation;
    private XSDSimpleElementType simpleType;
    private boolean hasList;
    private boolean hasUnion;
    private XSDUnion union;

    public XSDSimpleType(WSDLManagerRetrieval manager, Node node)
            throws XPathExpressionException, SAXException, IOException, ParserConfigurationException, WSDLException {
        super(manager, node, XSDSimpleType.class);
    }

    @Override
    protected Boolean isESQLPrintable() {
        return true;
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    protected boolean validateChild(Node child, XSDElement element)
            throws XPathExpressionException, SAXException, IOException, ParserConfigurationException, WSDLException {
        // return super.validateChild(child, element);
        // child is restriction
        if (element instanceof XSDRestriction) {
            this.simpleType = ((XSDRestriction) element).getSimpleType();
            this.restriction = (XSDRestriction) element;
            this.hasRestriction = true;
        } else if (element instanceof XSDList) {
            this.hasList = true;
            this.simpleType = ((XSDList) element).getSimpleType();
        } else if (element instanceof XSDUnion) {
            this.hasUnion = true;
            // element.setName(Utils.getParamWithPrefix(this.prefix, this.name));
            this.simpleType = XSDSimpleElementType.UNION_CHILDREN;
            this.union = (XSDUnion) element;
        }
        return true;

    }

    protected boolean handleList() {
        // if (this.hasList)
        // TODO: fix list
        return this.hasRestriction;
    }

    @Override
    public void toESQL() {
        this.handleList();
        super.toESQL();
        // this.addHelpComment();
        String val = this.fixedValue == null ? this.defaultValue : this.fixedValue;
        this.manager.getESQLManager().addParam(this.prefix, this.name, this.simpleType, val);
    }

    @Override
    protected boolean hasPrintable() {
        return false;
    }

    public XSDSimpleElementType getSimpleType() {
        return this.simpleType;
    }

    public boolean hasRestriction() {
        return this.hasRestriction;
    }

    public boolean hasList() {
        return this.hasList;
    }
}