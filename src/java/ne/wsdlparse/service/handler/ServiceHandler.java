/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ne.wsdlparse.service.handler;

import wsdlparse.ne.WSDLParserFault;


/**
 *
 * @author nour
 */
public interface ServiceHandler<RQ, RS> {
    public RS handle(RQ request) throws WSDLParserFault;
    public WSDLParserFault handleFault(int code, String message) ;
}
