
package wsdlparse.ne;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.11-b150120.1832
 * Generated source version: 2.2
 * 
 */
@WebService(name = "WSDLParser", targetNamespace = "ne.wsdlparse")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface WSDLParser {


    /**
     * 
     * @param parameters
     * @return
     *     returns wsdlparse.ne.UploadFileResponse
     * @throws WSDLParserFault
     */
    @WebMethod(operationName = "UploadFile", action = "ne.wsdlparse/UploadFile")
    @WebResult(name = "UploadFileResponse", targetNamespace = "ne.wsdlparse", partName = "parameters")
    public UploadFileResponse uploadFile(
        @WebParam(name = "UploadFileRequest", targetNamespace = "ne.wsdlparse", partName = "parameters")
        UploadFileRequest parameters)
        throws WSDLParserFault
    ;

    /**
     * 
     * @param parameters
     * @return
     *     returns wsdlparse.ne.GetServiceNamesResponse
     * @throws WSDLParserFault
     */
    @WebMethod(operationName = "GetServiceNames", action = "ne.wsdlparse/GetServiceNames")
    @WebResult(name = "GetServiceNamesResponse", targetNamespace = "ne.wsdlparse", partName = "parameters")
    public GetServiceNamesResponse getServiceNames(
        @WebParam(name = "GetServiceNamesRequest", targetNamespace = "ne.wsdlparse", partName = "parameters")
        GetServiceNamesRequest parameters)
        throws WSDLParserFault
    ;

    /**
     * 
     * @param parameters
     * @return
     *     returns wsdlparse.ne.GetAvailableWSDLsResponse
     * @throws WSDLParserFault
     */
    @WebMethod(operationName = "GetAvailableWSDLs", action = "ne.wsdlparse/GetAvailableWSDLs")
    @WebResult(name = "GetAvailableWSDLsResponse", targetNamespace = "ne.wsdlparse", partName = "parameters")
    public GetAvailableWSDLsResponse getAvailableWSDLs(
        @WebParam(name = "GetAvailableWSDLsRequest", targetNamespace = "ne.wsdlparse", partName = "parameters")
        GetAvailableWSDLsRequest parameters)
        throws WSDLParserFault
    ;

    /**
     * 
     * @param parameters
     * @return
     *     returns wsdlparse.ne.GetWSDLPortsResponse
     * @throws WSDLParserFault
     */
    @WebMethod(operationName = "GetWSDLPorts", action = "ne.wsdlparse/GetWSDLPorts")
    @WebResult(name = "GetWSDLPortsResponse", targetNamespace = "ne.wsdlparse", partName = "parameters")
    public GetWSDLPortsResponse getWSDLPorts(
        @WebParam(name = "GetWSDLPortsRequest", targetNamespace = "ne.wsdlparse", partName = "parameters")
        GetWSDLPortsRequest parameters)
        throws WSDLParserFault
    ;

    /**
     * 
     * @param parameters
     * @return
     *     returns wsdlparse.ne.GetWSDLOperationsResponse
     * @throws WSDLParserFault
     */
    @WebMethod(operationName = "GetWSDLOperations", action = "ne.wsdlparse/GetWSDLOperations")
    @WebResult(name = "GetWSDLOperationsResponse", targetNamespace = "ne.wsdlparse", partName = "parameters")
    public GetWSDLOperationsResponse getWSDLOperations(
        @WebParam(name = "GetWSDLOperationsRequest", targetNamespace = "ne.wsdlparse", partName = "parameters")
        GetWSDLOperationsRequest parameters)
        throws WSDLParserFault
    ;

    /**
     * 
     * @param parameters
     * @return
     *     returns wsdlparse.ne.GetWSDLOperationMessagesResponse
     * @throws WSDLParserFault
     */
    @WebMethod(operationName = "GetWSDLOperationMessages", action = "ne.wsdlparse/GetWSDLOperationMessages")
    @WebResult(name = "GetWSDLOperationMessagesResponse", targetNamespace = "ne.wsdlparse", partName = "parameters")
    public GetWSDLOperationMessagesResponse getWSDLOperationMessages(
        @WebParam(name = "GetWSDLOperationMessagesRequest", targetNamespace = "ne.wsdlparse", partName = "parameters")
        GetWSDLOperationMessagesRequest parameters)
        throws WSDLParserFault
    ;

    /**
     * 
     * @param parameters
     * @return
     *     returns wsdlparse.ne.GetWSDLOperationMessageAsESQLResponse
     * @throws WSDLParserFault
     */
    @WebMethod(operationName = "GetWSDLOperationMessageAsESQL", action = "ne.wsdlparse/GetWSDLOperationMessageAsESQL")
    @WebResult(name = "GetWSDLOperationMessageAsESQLResponse", targetNamespace = "ne.wsdlparse", partName = "parameters")
    public GetWSDLOperationMessageAsESQLResponse getWSDLOperationMessageAsESQL(
        @WebParam(name = "GetWSDLOperationMessageAsESQLRequest", targetNamespace = "ne.wsdlparse", partName = "parameters")
        GetWSDLOperationMessageAsESQLRequest parameters)
        throws WSDLParserFault
    ;

}
