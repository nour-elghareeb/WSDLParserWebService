/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ne.wsdlparse.service.lib;

import javax.jws.WebService;
import ne.wsdlparse.service.handler.GetAvailableWSDLsHandler;
import ne.wsdlparse.service.handler.GetServiceNamesHandler;
import ne.wsdlparse.service.handler.GetWSDLOpeartionMessageAsESQLHandler;
import ne.wsdlparse.service.handler.GetWSDLOpeartionMessagesHandler;
import ne.wsdlparse.service.handler.GetWSDLOperationsHandler;
import ne.wsdlparse.service.handler.GetWSDLPortsHandler;
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

    public wsdlparse.ne.GetServiceNamesResponse getServiceNames(wsdlparse.ne.GetServiceNamesRequest parameters) throws WSDLParserFault {
        //TODO implement this method
        GetServiceNamesHandler handler = new GetServiceNamesHandler();
        return handler.handle(parameters);
    }

    public wsdlparse.ne.GetAvailableWSDLsResponse getAvailableWSDLs(wsdlparse.ne.GetAvailableWSDLsRequest parameters) throws WSDLParserFault {
        //TODO implement this method
        GetAvailableWSDLsHandler handler = new GetAvailableWSDLsHandler();
        return handler.handle(parameters);
    }

    public wsdlparse.ne.GetWSDLPortsResponse getWSDLPorts(wsdlparse.ne.GetWSDLPortsRequest parameters) throws WSDLParserFault {
        //TODO implement this method
        GetWSDLPortsHandler handler = new GetWSDLPortsHandler();
        return handler.handle(parameters);
    }

    public wsdlparse.ne.GetWSDLOperationsResponse getWSDLOperations(wsdlparse.ne.GetWSDLOperationsRequest parameters) throws WSDLParserFault {
        //TODO implement this method
        GetWSDLOperationsHandler handler = new GetWSDLOperationsHandler();
        return handler.handle(parameters);
    }

    public wsdlparse.ne.GetWSDLOperationMessagesResponse getWSDLOperationMessages(wsdlparse.ne.GetWSDLOperationMessagesRequest parameters) throws WSDLParserFault {
        //TODO implement this method
        GetWSDLOpeartionMessagesHandler handler = new GetWSDLOpeartionMessagesHandler();
        return handler.handle(parameters);
    }

    public wsdlparse.ne.GetWSDLOperationMessageAsESQLResponse getWSDLOperationMessageAsESQL(wsdlparse.ne.GetWSDLOperationMessageAsESQLRequest parameters) throws WSDLParserFault {
        //TODO implement this method
        GetWSDLOpeartionMessageAsESQLHandler handler = new GetWSDLOpeartionMessageAsESQLHandler();
        return handler.handle(parameters);
    }

}
