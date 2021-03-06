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
import ne.wsdlparse.lib.Port;
import ne.wsdlparse.lib.Service;
import ne.wsdlparse.lib.WSDLManager;
import ne.wsdlparse.lib.exception.WSDLException;
import ne.wsdlparse.service.lib.WSDLParserService;
import wsdlparse.ne.GetAvailableWSDLsRequest;
import wsdlparse.ne.GetAvailableWSDLsResponse;
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
public class GetWSDLPortsHandler extends ServiceHandler<GetWSDLPortsRequest, GetWSDLPortsResponse> {

    @Override
    public GetWSDLPortsResponse handle(GetWSDLPortsRequest request) throws WSDLParserFault {
        try {
            GetWSDLPortsResponse response = new GetWSDLPortsResponse();
            List<String> ports = response.getWSDLPortItem();
            loadWSDL(request.getWSDLName());
            Service service = manager.loadService(request.getServiceName());
            
            for (Port port : service.loadPorts()) {
                ports.add(port.getName());
            }
            
            return response;
        } catch (WSDLException ex) {
            Logger.getLogger(GetWSDLPortsHandler.class.getName()).log(Level.SEVERE, null, ex);
            throw handleFault(ex.getCode().ordinal(), ex.getMessage());
        }
    }

}
