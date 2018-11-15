/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ne.wsdlparse.service.handler;

import java.io.File;
import wsdlparse.ne.WSDLParserFault;
import wsdlparse.ne.WSDLParserFaultDetails;


/**
 *
 * @author nour
 */
public abstract class ServiceHandler<RQ, RS> {
    protected final static File WORKING_DIR = new File("/usr/share/wsdlparser/files");
    protected final static File TEMP_DIR = new File("/usr/share/wsdlparser/tmp");
    
    
    public abstract RS handle(RQ request) throws WSDLParserFault;
    public WSDLParserFault handleFault(int code, String message){
        WSDLParserFault fault = new WSDLParserFault(message, new WSDLParserFaultDetails());
        fault.getFaultInfo().setErrorCode(code);
        fault.getFaultInfo().setErrorMessage(message);
        return fault;
    }
}
