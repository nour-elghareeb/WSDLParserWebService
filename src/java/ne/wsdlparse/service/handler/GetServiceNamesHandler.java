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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ne.utility.CompressionUtils;
import ne.utility.FileUtils;
import ne.wsdlparse.lib.Service;
import ne.wsdlparse.lib.exception.WSDLException;
import ne.wsdlparse.service.lib.WSDLParserService;
import wsdlparse.ne.GetServiceNamesRequest;
import wsdlparse.ne.GetServiceNamesResponse;

import wsdlparse.ne.UploadFileRequest;
import wsdlparse.ne.UploadFileResponse;
import wsdlparse.ne.WSDLParserFault;
import wsdlparse.ne.WSDLParserFaultDetails;

/**
 *
 * @author nour
 */
public class GetServiceNamesHandler extends ServiceHandler<GetServiceNamesRequest, GetServiceNamesResponse> {

    private final static File EXTRACTING_DIR = new File(TEMP_DIR, "extracted");

    @Override
    public GetServiceNamesResponse handle(GetServiceNamesRequest request) throws WSDLParserFault {
        try {
            GetServiceNamesResponse response = new GetServiceNamesResponse();
            loadWSDL(request.getWSDLName());
            this.manager.loadServices();
            for (Service service : this.manager.getServices()){
                response.getService().add(service.getName());
            }
            return response;
        } catch (WSDLException ex) {
            Logger.getLogger(GetServiceNamesHandler.class.getName()).log(Level.SEVERE, null, ex);
            throw handleFault(ex.getCode().ordinal(), ex.getMessage());
        }
    }

}
