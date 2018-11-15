/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ne.wsdlparse.service.lib;

import javax.jws.WebService;
import ne.wsdlparse.service.handler.UploadFileHandler;
import wsdlparse.ne.WSDLParserFault;

/**
 *
 * @author nour
 */
@WebService(serviceName = "WSDLParser", portName = "WSDLParserSOAP", endpointInterface = "wsdlparse.ne.WSDLParser", targetNamespace = "ne.wsdlparse", wsdlLocation = "WEB-INF/wsdl/WSDLParserService/WSDLParser.wsdl")
public class WSDLParserService {

    public wsdlparse.ne.UploadFileResponse uploadFile(wsdlparse.ne.UploadFileRequest parameters) throws WSDLParserFault {
        //TODO implement this method
        UploadFileHandler handler = new UploadFileHandler();
        return handler.handle(parameters);
    }
    
}
