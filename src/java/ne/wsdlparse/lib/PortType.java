package ne.wsdlparse.lib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import ne.wsdlparse.lib.exception.WSDLException;

public class PortType {
    private String name;
    private WSDLManagerRetrieval manager;
    private ArrayList<Operation> operations;
    private Port port;
    private String prefix;

    public PortType(WSDLManagerRetrieval manager, Port port, String portTypeName) throws XPathExpressionException {
        this.setName(portTypeName);
        this.manager = manager;
        this.setPort(port);
    }

    private void loadOperations()
            throws XPathExpressionException, WSDLException, SAXException, IOException, ParserConfigurationException {
        NodeList operations = (NodeList) this.manager.getXPath()
                .compile(String.format(Locale.getDefault(), "/definitions/portType[@name='%s']/operation", this.name))
                .evaluate(this.manager.getWSDLFile(), XPathConstants.NODESET);
        this.operations = new ArrayList<Operation>();
        for (int i = 0; i < operations.getLength(); i++) {
            // getting opeartion
            Operation operation = new Operation(this.manager, this, operations.item(i));
            this.operations.add(operation);
        }
    }

    private void setName(String name) {
        String[] tmp = Utils.splitPrefixes(name);
        this.prefix = tmp[0];
        this.name = tmp[1];

    }

    public ArrayList<Operation> getOperations()
            throws XPathExpressionException, WSDLException, SAXException, IOException, ParserConfigurationException {
        if (this.operations == null) {
            this.loadOperations();
        }
        return this.operations;
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return WSDLManagerRetrieval return the manager
     */
    public WSDLManagerRetrieval getManager() {
        return manager;
    }

    /**
     * @param manager the manager to set
     */
    public void setManager(WSDLManagerRetrieval manager) {
        this.manager = manager;
    }

    /**
     * @param operations the operations to set
     */
    public void setOperations(ArrayList<Operation> operations) {
        this.operations = operations;
    }

    /**
     * @param port the port to set
     */
    public void setPort(Port port) {
        this.port = port;
    }

    /**
     * @return Port return the port
     */
    public Port getPort() {
        return this.port;
    }

}