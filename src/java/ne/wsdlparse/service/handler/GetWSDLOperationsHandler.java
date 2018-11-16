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
import ne.wsdlparse.lib.WSDLOperation;
import ne.wsdlparse.lib.Port;
import ne.wsdlparse.lib.Service;
import ne.wsdlparse.lib.WSDLManager;
import ne.wsdlparse.lib.exception.WSDLException;
import ne.wsdlparse.service.lib.WSDLParserService;
import wsdlparse.ne.GetAvailableWSDLsRequest;
import wsdlparse.ne.GetAvailableWSDLsResponse;
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
public class GetWSDLOperationsHandler extends ServiceHandler<GetWSDLOperationsRequest, GetWSDLOperationsResponse> {

    @Override
    public GetWSDLOperationsResponse handle(GetWSDLOperationsRequest request) throws WSDLParserFault {
        GetWSDLOperationsResponse response = new GetWSDLOperationsResponse();
        List<String> operations = response.getWSDLOperationName();
        loadWSDL(request.getWSDLName());
        try {
            Service service = manager.loadService(request.getServiceName());
            Port port = service.loadPort(request.getWSDLPortName());
            
            for (WSDLOperation op : port.getType().loadOperations()) {
                operations.add(op.getName());
            }
        } catch (WSDLException ex) {
            Logger.getLogger(GetWSDLOperationsHandler.class.getName()).log(Level.SEVERE, null, ex);
            throw handleFault(ex.getCode().ordinal(), ex.getMessage());
        }

        return response;
    }

}
