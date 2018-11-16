/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ne.wsdlparse.service.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ne.utility.CompressionUtils;
import ne.utility.FileUtils;
import ne.wsdlparse.lib.FaultMessage;
import ne.wsdlparse.lib.WSDLOperation;
import ne.wsdlparse.lib.Port;
import ne.wsdlparse.lib.Service;
import ne.wsdlparse.lib.WSDLManager;
import ne.wsdlparse.lib.WSDLMessage;
import ne.wsdlparse.lib.exception.WSDLException;
import ne.wsdlparse.service.lib.WSDLParserService;
import wsdlparse.ne.GetAvailableWSDLsRequest;
import wsdlparse.ne.GetAvailableWSDLsResponse;
import wsdlparse.ne.GetWSDLOperationMessagesRequest;
import wsdlparse.ne.GetWSDLOperationMessagesResponse;
import wsdlparse.ne.GetWSDLOperationsRequest;
import wsdlparse.ne.GetWSDLOperationsResponse;
import wsdlparse.ne.GetWSDLPortsRequest;
import wsdlparse.ne.GetWSDLPortsResponse;

import wsdlparse.ne.UploadFileRequest;
import wsdlparse.ne.UploadFileResponse;
import wsdlparse.ne.WSDLParserFault;
import wsdlparse.ne.WSDLParserFaultDetails;

/**
 *
 * @author nour
 */
public class GetWSDLOpeartionMessagesHandler extends ServiceHandler<GetWSDLOperationMessagesRequest, GetWSDLOperationMessagesResponse> {

    @Override
    public GetWSDLOperationMessagesResponse handle(GetWSDLOperationMessagesRequest request) throws WSDLParserFault {
        try {
            GetWSDLOperationMessagesResponse response = new GetWSDLOperationMessagesResponse();
            List<GetWSDLOperationMessagesResponse.WSDLOperationMessage> messages = response.getWSDLOperationMessage();
            loadWSDL(request.getWSDLName());
            Service service = manager.loadService(request.getServiceName());
            Port port = service.loadPort(request.getWSDLPortName());
            WSDLOperation operation = port.getType().loadOperation(request.getWSDLOperation());
            GetWSDLOperationMessagesResponse.WSDLOperationMessage requestMsg = new GetWSDLOperationMessagesResponse.WSDLOperationMessage();
            requestMsg.setMessageName(operation.getRequest().getName());
            requestMsg.setMessageType("request");
            messages.add(requestMsg);
            GetWSDLOperationMessagesResponse.WSDLOperationMessage responseMsg = new GetWSDLOperationMessagesResponse.WSDLOperationMessage();
            responseMsg.setMessageName(operation.getResponse().getName());
            responseMsg.setMessageType("response");
            messages.add(responseMsg);
            for (WSDLMessage msg : operation.getFault()){
                GetWSDLOperationMessagesResponse.WSDLOperationMessage faultMsg = new GetWSDLOperationMessagesResponse.WSDLOperationMessage();
                faultMsg.setMessageName(msg.getName() == null ? "SOAPFault" : msg.getName());
                faultMsg.setMessageType("fault");
                messages.add(faultMsg);
            }
            return response;
        } catch (WSDLException ex) {
            Logger.getLogger(GetWSDLOpeartionMessagesHandler.class.getName()).log(Level.SEVERE, null, ex);
            throw handleFault(ex.getCode().ordinal(), ex.getMessage());
        }
    }

}
