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
import ne.wsdlparse.service.lib.WSDLParserService;
import wsdlparse.ne.GetAvailableWSDLsRequest;
import wsdlparse.ne.GetAvailableWSDLsResponse;


import wsdlparse.ne.UploadFileRequest;
import wsdlparse.ne.UploadFileResponse;
import wsdlparse.ne.WSDLParserFault;
import wsdlparse.ne.WSDLParserFaultDetails;

/**
 *
 * @author nour
 */
public class GetAvailableWSDLsHandler extends ServiceHandler<GetAvailableWSDLsRequest, GetAvailableWSDLsResponse> {

 
    @Override
    public GetAvailableWSDLsResponse handle(GetAvailableWSDLsRequest request) throws WSDLParserFault{
        GetAvailableWSDLsResponse response = new GetAvailableWSDLsResponse();
        List<String> wsdls = response.getWSDLItem();
        for (File dir : WORKING_DIR.listFiles()){
            if (!dir.isDirectory()) continue;
            wsdls.add(dir.getName());
        }
        return response;
    }    

}
