
package wsdlparse.ne;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.11-b150120.1832
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "WSDLParserFaultDetails", targetNamespace = "ne.wsdlparse")
public class WSDLParserFault
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private WSDLParserFaultDetails faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public WSDLParserFault(String message, WSDLParserFaultDetails faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public WSDLParserFault(String message, WSDLParserFaultDetails faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: wsdlparse.ne.WSDLParserFaultDetails
     */
    public WSDLParserFaultDetails getFaultInfo() {
        return faultInfo;
    }

}
