package ne.wsdlparse.lib.exception;

public enum WSDLExceptionCode {
    INVALID_OPERATION_STYLE("Invalid operation style! Valid styles are document and rpc."),
    EMPTY_MESSAGE_PARAMS("Message does not seem to have any parts"),
    XSD_NOT_SIMPLE_ELEMENT("The element is not simple."), XSD_ELEMENT_NOT_FOUND("XSD element could not be found!"),
    XSD_NOT_COMPLEX_TYPE("The element is not complex"), XSD_NODE_IS_ELEMENT("the node is element not type"),
    XSD_RESTRICT_PARAM_INVALID("Invalid restrict param"), XSD_SCHEMA_FILE_NOT_FOUND("XSD Schema file cannot be loaded (not found)");

    private String message;

    WSDLExceptionCode(String message) {
        this.message = message;
    }

    /**
     * @return String return the message
     */
    String getMessage() {
        return message;
    }
}