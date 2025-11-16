package app;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.jaxws.EndpointImpl;
import jakarta.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class Publicador {

    @WebMethod
    public int suma(int a, int b) {
        return a + b;
    }

    @WebMethod(exclude = true)
    public void publicar() {

        Bus bus = BusFactory.getDefaultBus();

        EndpointImpl endpoint = new EndpointImpl(bus, this);
        endpoint.publish("http://localhost:8086/publicador");

        System.out.println("🟢 Servicio CXF publicado en:");
        System.out.println("http://localhost:8086/publicador?wsdl");
    }
}