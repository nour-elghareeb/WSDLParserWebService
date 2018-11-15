package ne.wsdlparse.lib;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        try {
            

            // WSDLManager wsdl = new WSDLManager(args[0]);
            // for (Service service : wsdl.getServices()) {
            //     for (Port port : service.getPorts()) {
            //         PortType portType = port.getType();
            //         for (Operation operation : portType.getOperations()) {
            //             System.out.println("Operation: " + operation.getName());
            //             System.out.println("Request: ---------------");
            //             wsdl.getESQLManager().getESQLBlock().clear();
            //             // if (operation.getRequest().getName().equals("addVASRequest"))
            //                 operation.getRequest().generateESQL();
            //             // operation.getFault()[1].generateESQL();

            //             // operation.getResponse().generateESQL();
            //             // operation.getFault().generateESQL();
            //             //
            //             // break;

            //             wsdl.getESQLManager().getESQLBlock().printOutputSetters();
            //         }
            //         break;
            //     }
            // }
            // // wsdl.getESQLManager().getESQLBlock().printInputReferences();
            // // wsdl.getESQLManager().getESQLBlock().printInputVariables();

        } catch (

        Exception e) {
            e.printStackTrace();
        }

    }

}
