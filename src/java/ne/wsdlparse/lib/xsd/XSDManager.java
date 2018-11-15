package ne.wsdlparse.lib.xsd;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ne.wsdlparse.lib.WSDLManagerRetrieval;
import ne.wsdlparse.lib.exception.WSDLException;

public class XSDManager {
    private WSDLManagerRetrieval wsdlManager;
    private String targetNS;
    private String name;
    private Node inlineSchema;
    private String workingdir;
    private XSDFile xsd;

    public XSDManager(WSDLManagerRetrieval wsdlManager, String workingdir, NodeList schemas)
            throws SAXException, IOException, ParserConfigurationException, XPathExpressionException, FileNotFoundException, WSDLException {
        this.workingdir = workingdir;
        // this.targetNS = Utils.getAttrValueFromNode(schemas, "targetNamespace");

        this.xsd = new XSDFile(workingdir, schemas);

        // load includes if any
        // NodeList includes = (NodeList)
        // wsdlManager.getXPath().compile("include").evaluate(schema,
        // XPathConstants.NODESET);
        // for (int i = 0; i < includes.getLength(); i++) {
        // Node include = includes.item(i);
        // this.include(Utils.getAttrValueFromNode(include, "schemaLocation"));
        // }
        // // load imports if any
        // NodeList imports = (NodeList)
        // wsdlManager.getXPath().compile("import").evaluate(schema,
        // XPathConstants.NODESET);

        // for (int i = 0; i < imports.getLength(); i++) {
        // Node _import = imports.item(i);
        // String ns = Utils.getAttrValueFromNode(_import, "namespace");
        // String schemaLocation = Utils.getAttrValueFromNode(_import,
        // "schemaLocation");
        // if (schemaLocation == null) {
        // Node _schema = (Node) wsdlManager
        // .getXPath().compile(String.format(Locale.getDefault(),
        // "/definitions/types/schema[@targetNamespace='%s']", ns))
        // .evaluate(wsdlManager.getWSDLFile(), XPathConstants.NODE);
        // if (this.inlineImports == null)
        // this.inlineImports = new HashMap<String, Node>();
        // this.inlineImports.put(ns, _schema);
        // } else {
        // if (this.imports == null)
        // this.imports = new ArrayList<XSDFile>();

        // }

        // }
        // this.inlineSchema = schema;
        // this.xPath = wsdlManager.getXPath();

    }

    public Object find(String xpath, Object source, QName returnType) throws XPathExpressionException {
        return this.xsd.find(xpath, source, returnType);

    }

    public Object find(String xpath, QName returnType) throws XPathExpressionException {
        return this.xsd.find(xpath, returnType);
        // Object node = null;
        // // search inline schema if any
        // node = this.find(xpath, this.inlineSchema, returnType);
        // // if not found, search includes if any..
        // if (node == null)
        // for (XSDFile file : this.includes) {
        // node = file.find(xpath, returnType);
        // if (node != null)
        // return node;
        // }
        // // if not found search inline imports if any...
        // for (Entry<String, Node> entry : this.inlineImports.entrySet()) {
        // Node schema = entry.getValue();
        // node = this.find(xpath, schema, returnType);
        // if (node != null)
        // return node;
        // }
        // // if not foumd, search external imports if any...
        // for (XSDFile file : this.imports) {
        // node = file.find(xpath, returnType);
        // if (node != null)
        // return node;
        // }
        // // return null.
        // return node;
    }

    public String getNamespaceURI(String prefix) {
        return this.xsd.getNamespaceURI(prefix);
    }

    public String getPrefix(String ns) {
        return this.xsd.getPrefix(ns);
    }
}