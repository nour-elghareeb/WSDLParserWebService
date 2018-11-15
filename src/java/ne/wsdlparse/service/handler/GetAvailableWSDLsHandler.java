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
import java.util.logging.Level;
import java.util.logging.Logger;
import ne.utility.CompressionUtils;
import ne.utility.FileUtils;
import ne.wsdlparse.service.lib.WSDLParserService;


import wsdlparse.ne.UploadFileRequest;
import wsdlparse.ne.UploadFileResponse;
import wsdlparse.ne.WSDLParserFault;
import wsdlparse.ne.WSDLParserFaultDetails;

/**
 *
 * @author nour
 */
public class UploadFileHandler implements ServiceHandler<UploadFileRequest, UploadFileResponse> {

    private final static String WORKING_DIR = "/usr/share/wsdlparser/files";
    private final static String TEMP_DIR = "/usr/share/wsdlparser/tmp";

    private final static File EXTRACTING_DIR = new File(TEMP_DIR, "extracted");

    @Override
    public UploadFileResponse handle(UploadFileRequest request) throws WSDLParserFault{

        if (request.getMimeType()
                .equals("application/zip")) {
            try {
                String filename = request.getFileName() + "." + request.getFileExtension();
                String filePath = TEMP_DIR + File.separator + "compressed.zip";
                OutputStream stream = new FileOutputStream(filePath);
                stream.write(request.getFileContents());
                stream.close();
                if (!EXTRACTING_DIR.exists()) {
                    EXTRACTING_DIR.mkdir();
                }
                File zippedFile = new File(filePath);
                zippedFile.deleteOnExit();
                if (!CompressionUtils.Unzip(zippedFile, EXTRACTING_DIR, true)) {
                    throw handleFault(1001, "Error Unzipping file.");
                }

                File fileToBeStored = new File(WORKING_DIR + File.separator + request.getFileName());
                if (fileToBeStored.exists() && !request.isOverwrite()) {
                    throw handleFault(1002, "A wsdl with same name already exists. Set overwrite option to true to replace.");
                } else if (fileToBeStored.exists() && request.isOverwrite()) {
                    FileUtils.deleteDirectoryStream(fileToBeStored.toPath());
                }
                if (!EXTRACTING_DIR.renameTo(fileToBeStored)) {
                    throw handleFault(1003, "Error moving file");
                }

                UploadFileResponse response = new UploadFileResponse();
                response.setWSDLName(request.getFileName());
                response.setStatus(true);
                return response;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(WSDLParserService.class.getName()).log(Level.SEVERE, null, ex);                
                throw handleFault(9999, "Error decoding uploaded file!");
            } catch (Exception e) {
                Logger.getLogger(WSDLParserService.class.getName()).log(Level.SEVERE, null, e);
                throw handleFault(9999, e.getMessage());
            } finally {
                EXTRACTING_DIR.delete();
            }
        }
        throw handleFault(9999, "Unexpected error occrred!");       
    }

    @Override
    public WSDLParserFault handleFault(int code, String message) {
        WSDLParserFault fault = new WSDLParserFault(message, new WSDLParserFaultDetails());
        fault.getFaultInfo().setErrorCode(code);
        fault.getFaultInfo().setErrorMessage(message);
        return fault;
    }

    

}
