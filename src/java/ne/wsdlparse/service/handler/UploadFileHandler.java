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
public class UploadFileHandler extends ServiceHandler<UploadFileRequest, UploadFileResponse> {

    private final static File EXTRACTING_DIR = new File(TEMP_DIR, "extracted");

    @Override
    public UploadFileResponse handle(UploadFileRequest request) throws WSDLParserFault {
        try {
            UploadFileResponse response = new UploadFileResponse();
            if (request.getMimeType()
                    .equals("application/zip")) {

                String filename = request.getFileName() + "." + request.getFileExtension();
                File compressedFile = new File(TEMP_DIR, "compressed.zip");
                OutputStream stream = new FileOutputStream(compressedFile);
                stream.write(request.getFileContents());
                stream.close();
                if (!EXTRACTING_DIR.exists()) {
                    EXTRACTING_DIR.mkdir();
                }
                compressedFile.deleteOnExit();
                if (!CompressionUtils.Unzip(compressedFile, EXTRACTING_DIR, true)) {
                    throw handleFault(1001, "Error Unzipping file.");
                }

                File fileToBeStored = new File(WORKING_DIR, request.getFileName());
                if (fileToBeStored.exists() && !request.isOverwrite()) {
                    throw handleFault(1002, "A wsdl with same name already exists. Set overwrite option to true to replace.");
                } else if (fileToBeStored.exists() && request.isOverwrite()) {
                    FileUtils.deleteDirectoryStream(fileToBeStored.toPath());
                }
                if (!EXTRACTING_DIR.renameTo(fileToBeStored)) {
                    throw handleFault(1003, "Error moving file");
                }
                response.setWSDLName(request.getFileName());
                response.setStatus(true);
                return response;

            } else if (request.getMimeType().equals("text/xml")) {
                File wsdlDir = new File(WORKING_DIR, request.getFileName());
                File wsdlFile = new File(wsdlDir, request.getFileName() + "." + request.getFileExtension());
                OutputStream stream = new FileOutputStream(wsdlFile);
                stream.write(request.getFileContents());
                stream.close();
                
                response.setWSDLName(request.getFileName());
                response.setStatus(true);
                
            }
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

}
