/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ne.wsdlparse.service.handler;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ne.wsdlparse.lib.WSDLOperation;
import ne.wsdlparse.lib.Port;
import ne.wsdlparse.lib.Service;
import ne.wsdlparse.lib.WSDLManager;
import ne.wsdlparse.lib.WSDLMessage;
import ne.wsdlparse.lib.constant.ESQLVerbosity;
import ne.wsdlparse.lib.esql.constant.ESQLSource;
import ne.wsdlparse.lib.exception.WSDLException;
import wsdlparse.ne.GetWSDLOperationMessageAsESQLRequest;
import wsdlparse.ne.GetWSDLOperationMessageAsESQLResponse;
import wsdlparse.ne.WSDLParserFault;

/**
 *
 * @author nour
 */
public class GetWSDLOpeartionMessageAsESQLHandler extends ServiceHandler<GetWSDLOperationMessageAsESQLRequest, GetWSDLOperationMessageAsESQLResponse> {

    @Override
    public GetWSDLOperationMessageAsESQLResponse handle(GetWSDLOperationMessageAsESQLRequest request) throws WSDLParserFault {
        try {
            GetWSDLOperationMessageAsESQLResponse response = new GetWSDLOperationMessageAsESQLResponse();
            List<String> lines = response.getESQLLine();

            loadWSDL(request.getWSDLName());
            Service service = manager.loadService(request.getServiceName());       
            WSDLOperation operation = service.loadPort(request.getWSDLPortName())
                    .getType().loadOperation(request.getWSDLOperationName());
            WSDLMessage message = operation.getMessageByName(request.getWSDLOperationMessageName());
            ESQLSource source;
            String s = request.getESQLSource().toUpperCase();
            try {
                source = ESQLSource.valueOf(s);
            } catch (IllegalArgumentException e) {
                throw handleFault(1004, "Invalid ESQLSource Value");
            }
            ESQLVerbosity[] verbosities = new ESQLVerbosity[request.getESQLVerboisty().size()];
            try {
                int i = 0;
                for (String verbosity : request.getESQLVerboisty()) {
                    verbosities[i] = ESQLVerbosity.valueOf(verbosity);
                    i++;
                }
                if (verbosities.length != 0) {
                    manager.getESQLManager().setVerbosity(verbosities);
                }

            } catch (IllegalArgumentException e) {
                throw handleFault(1004, "Invalid ESQLVerbosity Value");
            }
            message.generateESQL();
            lines.addAll(manager.getESQLManager().getESQLBlock().getLinesAsList(source, request.isUseReferenceAsVariable(), false));

            return response;
        } catch (WSDLException ex) {
            Logger.getLogger(GetWSDLOpeartionMessageAsESQLHandler.class.getName()).log(Level.SEVERE, null, ex);
            throw handleFault(ex.getCode().ordinal(), ex.getMessage());
        }
    }

}
