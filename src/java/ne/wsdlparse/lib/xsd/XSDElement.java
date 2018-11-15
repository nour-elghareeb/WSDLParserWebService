package ne.wsdlparse.lib.xsd;

import java.io.IOException;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import ne.wsdlparse.lib.Utils;
import ne.wsdlparse.lib.WSDLManagerRetrieval;
import ne.wsdlparse.lib.constant.ESQLVerbosity;
import ne.wsdlparse.lib.exception.WSDLException;
import ne.wsdlparse.lib.exception.WSDLExceptionCode;
import ne.wsdlparse.lib.xsd.constant.XSDSimpleElementType;

public abstract class XSDElement<T> {
    protected String help;
    protected String name;
    protected T value;
    protected int maxOccurs = -1;
    protected int minOccurs = -1;
    protected String defaultValue;
    protected boolean nillable;
    protected Class<?> type;
    protected String prefix = "";
    protected Node node;
    protected WSDLManagerRetrieval manager;
    protected String xPath = "";
    protected boolean isSkippable = false;
    private String targetNamespace;
    protected boolean nullifyChildrenName;
    private boolean printable;
    private String fixed;
    public abstract String getNodeHelp();
    protected String fixedValue;
    public XSDElement(WSDLManagerRetrieval manager, Node node, Class<?> type) {
        this.type = type;
        this.node = node;
        this.manager = manager;
        loadAttributes();
    }

    public static boolean isComplex(XSDElement element) {
        Boolean val = element instanceof XSDComplexElement;
        return val;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    protected abstract Boolean isESQLPrintable();

    public String getTargetTamespace() {
        if (this.node == null) return null;
        String ns = (String) this.node.getUserData("tns");
        return ns;
    }

    public String getExplicitlySetTargetTamespace() {
        if (this.node == null) return null;
        String ns = (String) this.node.getUserData("EX_tns");
        return ns;
    }

    public static XSDElement<?> getInstance(WSDLManagerRetrieval manager, Node node)
            throws XPathExpressionException, SAXException, IOException, ParserConfigurationException, WSDLException {
        XSDElement xsdElement;
        if (node == null)
            return null;

        String nodeNameWithPrefix = node.getNodeName();
        String nodeName = Utils.splitPrefixes(node.getNodeName())[1];
        String name = Utils.getAttrValueFromNode(node, "name");
        String type = Utils.getAttrValueFromNode(node, "type");
        // Check if node is element

        Node element = node;
        Node elementTypeNode = null;
        if (nodeName.equals("attribute"))
            return null;
        String tns = (String) element.getUserData("tns");
        if (type != null) {
            try {
                xsdElement = XSDElement.getInstanceForSimpleElement(manager, element, Utils.splitPrefixes(type)[1]);
                xsdElement.setName(name);
                return xsdElement;
            } catch (WSDLException e) {
                if (e.getCode().equals(WSDLExceptionCode.XSD_NOT_SIMPLE_ELEMENT)) {

                    element = (Node) manager.getXSDManager()
                            .find(String.format(Locale.getDefault(), "/schema/*[name() != '%s' and @name = '%s']",
                                    nodeNameWithPrefix, Utils.splitPrefixes(type)[1]), XPathConstants.NODE);
                    xsdElement = XSDElement.getInstance(manager, element);
                    tns = (String) element.getUserData("tns");
                    xsdElement.setName(name);
                    return xsdElement;
                }
                throw e;
            }

        }
        try {
            xsdElement = XSDElement.getInstanceForSimpleElement(manager, element, nodeName);
            xsdElement.setName(name);
            return xsdElement;
        } catch (WSDLException e) {
            // check if node name is already a type..
            try {
                xsdElement = XSDElement.getInstanceForComplexElement(manager, element);
                xsdElement.setName(name);
                return xsdElement;
            } catch (WSDLException e2) {
                // not a complex element
                if (e2.getCode().equals(WSDLExceptionCode.XSD_NODE_IS_ELEMENT)) {
                    element = Utils.getFirstXMLChild(element);
                    element.setUserData("tns", tns, null);
                    xsdElement = XSDElement.getInstance(manager, element);
                    xsdElement.setName(name);
                    // String tns = (String) element.getUserData("tns");
                    xsdElement.setTargetNamespace(tns);
                    return xsdElement;
                }
                throw e;
            }
        }

        // check if type is null, then grap first child..
        // if (nodeName.equals("element")) {
        // if (type == null) {
        // elementTypeNode = Utils.getFirstXMLChild(node);
        // } else {
        // try {
        // xsdElement = XSDElement.getInstanceForSimpleElement(manager, element,
        // Utils.splitPrefixes(type)[1]);
        // xsdElement.setName(name);
        // return xsdElement;
        // } catch (WSDLException e) {
        // elementTypeNode = (Node) manager.getXSDManager().find(
        // String.format(Locale.getDefault(), "/schema/*[@name='%s']",
        // Utils.splitPrefixes(type)[1]),
        // XPathConstants.NODE);
        // }

        // }
        // xsdElement = XSDElement.getInstanceForComplexElement(manager,
        // elementTypeNode);
        // xsdElement.setName(name);
        // return xsdElement;
        // }
        // try {
        // xsdElement = XSDElement.getInstanceForSimpleElement(manager, element,
        // Utils.splitPrefixes(type)[1]);
        // xsdElement.setName(name);
        // return xsdElement;
        // } catch (Exception e) {
        // elementTypeNode = (Node) manager.getXSDManager().find(
        // String.format(Locale.getDefault(), "/schema/*[@name='%s']",
        // Utils.splitPrefixes(type)[1]),
        // XPathConstants.NODE);
        // xsdElement = XSDElement.getInstanceForComplexElement(manager,
        // elementTypeNode);
        // xsdElement.setName(name);
        // return xsdElement;
        // }

        // if (type[1] == null) {
        // xsdElement = XSDElement.getInstance(manager, Utils.getFirstXMLChild(node));
        // xsdElement.setName(name);
        // return xsdElement;
        // } else {
        // try {
        // xsdElement = XSDElement.getInstanceForSimpleElement(manager, node, type[1]);
        // xsdElement.setName(name);
        // return xsdElement;
        // } catch (WSDLException e) {
        // if (e.getCode().equals(WSDLExceptionCode.XSD_NOT_SIMPLE_ELEMENT)) {
        // Node targetElementType = (Node) manager.getXSDManager().find(
        // String.format(Locale.getDefault(), "/schema/element[@name='%s']", type[1]),
        // XPathConstants.NODE);

        // if (targetElementType != null) {
        // xsdElement = XSDElement.getInstanceForComplexElement(manager,
        // targetElementType);
        // xsdElement.setName(name);
        // return xsdElement;
        // }
        // }
        // }

        // }

        // if (node == null)
        // return null;
        // String nodeName = Utils.splitPrefixes(node.getNodeName())[1];
        // if (nodeName.equals("element"))
        // nodeName = Utils.splitPrefixes(Utils.getAttrValueFromNode(node, "type"))[1];
        // if (nodeName.equals("string"))
        // return new XSDString(manager, node);
        // else if (nodeName.equals("int") || nodeName.equals("integer"))
        // return new XSDInteger(manager, node);
        // else if (nodeName.equals("boolean"))
        // return new XSDBoolean(manager, node);
        // else if (nodeName.equals("complexType"))
        // return XSDElement.getInstance(manager, Utils.getFirstXMLChild(node));
        // else if (nodeName.equals("all"))
        // return new XSDAll(manager, node);
        // else if (nodeName.equals("sequence"))
        // return new XSDSequence(manager, node);
        // else if (nodeName.equals("choice"))
        // return new XSDChoice(manager, node);
        // else
        // return null;
    }

    private void setTargetNamespace(String tns) {
        this.targetNamespace = tns;
    }

    private static XSDComplexElement getInstanceForComplexElement(WSDLManagerRetrieval manager, Node node)
            throws WSDLException, XPathExpressionException, SAXException, IOException, ParserConfigurationException {
        String nodeName = Utils.splitPrefixes(node.getNodeName())[1];

        if (nodeName.equals("element"))
            throw new WSDLException(WSDLExceptionCode.XSD_NODE_IS_ELEMENT);
        else if (nodeName.equals("all"))
            return new XSDAll(manager, node);
        else if (nodeName.equals("annotation"))
            return new XSDAnnotation(manager, node);

        else if (nodeName.equals("choice"))
            return new XSDChoice(manager, node);
        else if (nodeName.equals("complexContent"))
            return new XSDComplexContent(manager, node);
        else if (nodeName.equals("complexType"))
            return new XSDComplexType(manager, node);
        else if (nodeName.equals("simpleType"))
            return new XSDSimpleType(manager, node);
        else if (nodeName.equals("simpleContent"))
            return new XSDSimpleContent(manager, node);
        else if (nodeName.equals("extension"))
            return new XSDExtention(manager, node);
        else if (nodeName.equals("group"))
            return new XSDGroup(manager, node);
        else if (nodeName.equals("restriction"))
            return new XSDRestriction(manager, node);
        else if (nodeName.equals("sequence"))
            return new XSDSequence(manager, node);
        else if (nodeName.equals("union"))
            return new XSDUnion(manager, node);
        else {
            throw new WSDLException(WSDLExceptionCode.XSD_NOT_COMPLEX_TYPE);
        }
    }

    private static XSDSimpleElement getInstanceForSimpleElement(WSDLManagerRetrieval manager, Node node, String type)
            throws WSDLException {
        XSDSimpleElement element;

        try {
            XSDSimpleElementType simpleType = XSDSimpleElementType.parse(type);
            if (simpleType.equals(XSDSimpleElementType.LIST))
                element = new XSDList(manager, node);
            else if (simpleType.equals(XSDSimpleElementType.ANY))
                element = new XSDAny(manager, node);
            else
                element = new XSDSimpleElement(manager, node, simpleType);
            return element;
        } catch (WSDLException e) {
            throw new WSDLException(WSDLExceptionCode.XSD_NOT_SIMPLE_ELEMENT);
        }

        // try {
        // XSDNumericType numericType = XSDNumericType.parse(type);
        // element = new XSDNumeric(manager, node);
        // ((XSDNumeric) element).setNumericType(numericType);
        // return element;
        // } catch (WSDLException e) {
        // if (type.equals("string"))
        // return new XSDString(manager, node);

        // else if (type.equals("list")) {
        // String[] itemType = Utils.splitPrefixes(Utils.getAttrValueFromNode(node,
        // "itemType"));
        // XSDElement itemTypeCLS = XSDElement.getInstanceForSimpleElement(manager,
        // node, itemType[1]);

        // element = new XSDList(manager, node);
        // } else if (type.equals("boolean"))

        // return new XSDBoolean(manager, node);
        // else
        // throw new WSDLException(WSDLExceptionCode.XSD_NOT_SIMPLE_ELEMENT);
        // }
    }

    private void loadAttributes() {
        this.setNillable(Utils.getAttrValueFromNode(this.node, "nillable"));
        this.setName(Utils.getAttrValueFromNode(this.node, "name"));
        this.setMaxOccurs(Utils.getAttrValueFromNode(this.node, "maxOccurs"));
        this.setMinOccurs(Utils.getAttrValueFromNode(this.node, "minOccurs"));
        this.setDefaultValue(Utils.getAttrValueFromNode(this.node, "default"));
        this.setFixedValue((T) Utils.getAttrValueFromNode(this.node, "fixed"));
    }

    protected abstract void setFixedValue(T fixedValue);

    /**
     * return if the element is nillable..
     */
    public boolean isNillable() {
        return this.nillable;
    }

    /**
     * @param value nillable
     */
    public void setNillable(String value) {
        if (value == null)
            return;
        this.nillable = Boolean.parseBoolean(value);
    }

    /**
     * return maximum occurrance..
     */
    public int getMaxOccurs() {
        return this.maxOccurs;
    }

    /**
     * @param value the maximum occur value to set
     */
    public void setMaxOccurs(String value) {
        if (value == null)
            return;
        if (value.equals("unbounded")) {
            this.maxOccurs = -1;
            return;
        }
        this.maxOccurs = Integer.parseInt(value);
    }

    /**
     * return minmum occurrance..
     */
    public int getMinOccurs() {
        return this.minOccurs;
    }

    /**
     * @param value the minumum occur value to set
     */
    public void setMinOccurs(String value) {
        if (value == null)
            return;

        this.minOccurs = Integer.parseInt(value);
    }

    /**
     * @param value the name to set
     */
    public void setDefaultValue(String value) {
        // this.defaultValue = value;
    }

    /**
     * @return the default value
     */
    public T getDefaultValue() {
        return this.value;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        if (name == null && this.name != null)
            return;
        String[] tmp = Utils.splitPrefixes(name);
        this.prefix = tmp[0];
        this.name = tmp[1];
    }

    public String getNameWithPrefix() {
        StringBuilder builder = new StringBuilder();
        if (this.prefix != null) {
            builder.append(this.prefix);
            builder.append(":");
        }
        builder.append(this.name);
        return builder.toString();
    }

    /**
     * @return the value
     */
    public T getValue() {
        return this.value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(T value) {
        this.value = value;
    }

    public Class<?> getType() {
        return this.type;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void toESQL() {
        this.addHelpComment();
    }

    protected void addHelpComment() {
        this.manager.getESQLManager().addComment(ESQLVerbosity.VALUE_HELP, this.getNodeHelp());
        if (this.minOccurs == 0) {
            this.manager.getESQLManager().addComment(ESQLVerbosity.MULTIPLICITY, "Optional");
        }
    }

    public void explicitlySetTargetNameSpace(String targetTamespace) {
        this.node.setUserData("EX_tns", targetTamespace, null);
    }

    public void nullifyChildrenName() {
        this.name = null;
        this.prefix = null;
    }

    protected boolean hasPrintable() {
        return false;
    }

}