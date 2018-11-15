package ne.wsdlparse.lib;

import java.util.ArrayList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Service {
    private String name;
    private WSDLManagerRetrieval manager;
    private ArrayList<Port> ports;
    private Node node;

    public Service(WSDLManagerRetrieval manager, Node node) throws XPathExpressionException {
        this.name = Utils.getAttrValueFromNode(node, "name");
        this.manager = manager;
        this.node = node;
        this.loadPorts();
    }

    private void loadPorts() throws XPathExpressionException {
        final NodeList ports = (NodeList) this.manager.getXPath().compile("port").evaluate(this.node,
                XPathConstants.NODESET);
        this.ports = new ArrayList<Port>() {
            {
                for (int i = 0; i < ports.getLength(); i++) {
                    Node portNode = ports.item(i);
                    Port port = new Port(Service.this.manager, portNode);
                    add(port);
                }
            }
        };
    }

    public ArrayList<Port> getPorts() {
        return this.ports;
    }

    public Port getPort(String portName) {
        if (this.ports == null)
            return null;
        for (Port port : this.ports) {
            if (port.getName().equals(portName))
                return port;
        }
        return null;
    }

    public String getName() {
        return this.name;
    }
}