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
        GetWSDLPortsResponse response = new GetWSDLPortsResponse();
        List<String> ports = response.getWSDLPortItem();
        File dir = new File(WORKING_DIR, request.getWSDLName());
        File wsdl = null;
        
        for (File file : dir.listFiles()) {
            if (file.getName().endsWith(".wsdl")) {
                wsdl = file;
                break;
            }
        }
        if (wsdl == null) {
            throw handleFault(1001, "Could not load wsdl file!");
        }
        try {
            WSDLManager manager = new WSDLManager(wsdl.getAbsolutePath());
            Service service = manager.getServices().get(0);
            for (Port port : service.getPorts()) {
                ports.add(port.getName());
            }
        } catch (WSDLException ex) {
            throw handleFault(9999, ex.getMessage());
        } catch (Exception ex) {
            throw handleFault(9999, ex.getMessage());
        }

        return response;
    }

}