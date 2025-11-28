package app.adapters.soap;

import app.config.ConfigProperties;
import jakarta.jws.WebMethod;
import org.apache.cxf.Bus;
import org.apache.cxf.BusException;
import org.apache.cxf.BusFactory;
import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.binding.soap.SoapBindingFactory;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.jaxws.WrapperClassGenerator;
import org.apache.cxf.jaxws.spi.WrapperClassCreator;
import org.apache.cxf.transport.DestinationFactoryManager;
import org.apache.cxf.transport.http.DestinationRegistry;
import org.apache.cxf.transport.http.DestinationRegistryImpl;
import org.apache.cxf.transport.http.HTTPTransportFactory;
import org.apache.cxf.transport.http.HttpDestinationFactory;
import org.apache.cxf.transport.servlet.ServletDestinationFactory;
import org.apache.cxf.wsdl.WSDLManager;
import org.apache.cxf.wsdl11.WSDLManagerImpl;

public abstract class BaseSoapAdapter {
    private final String ip = ConfigProperties.get("soap.ip");
    private final String port = ConfigProperties.get("soap.port");
    private final String path = ConfigProperties.get("soap.path");
    @WebMethod(exclude = true)
    public void publish() {
        String endpointUrl = getEndpointUrl();

        // Crear y registrar el Bus
        Bus bus = BusFactory.newInstance().createBus();
        BusFactory.setDefaultBus(bus);

        // Registrar WrapperClassCreator manualmente
        bus.setExtension(new WrapperClassGenerator(bus), WrapperClassCreator.class);

        // Registrar WSDLManager
        try {
            bus.setExtension(new WSDLManagerImpl(), WSDLManager.class);
        } catch (BusException e) {
            throw new RuntimeException(e);
        }

        // Registrar SoapBindingFactory
        SoapBindingFactory sbf = new SoapBindingFactory();
        sbf.setBus(bus); // Este sí tiene setBus
        bus.getExtension(BindingFactoryManager.class)
                .registerBindingFactory("http://schemas.xmlsoap.org/wsdl/soap/", sbf);

        // Crear DestinationRegistry e instanciar la transport factory
        DestinationRegistry registry = new DestinationRegistryImpl();
        HTTPTransportFactory transportFactory = new HTTPTransportFactory(registry);

        // Registrar como DestinationFactory y HttpDestinationFactory
        bus.getExtension(DestinationFactoryManager.class)
                .registerDestinationFactory("http://schemas.xmlsoap.org/soap/http", transportFactory);
        HttpDestinationFactory proxyFactory = new ServletDestinationFactory();
        bus.setExtension(proxyFactory, HttpDestinationFactory.class);

        // Publicar servicio
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        factory.setBus(bus);
        factory.setServiceClass(this.getClass());
        factory.setServiceBean(this);
        factory.setAddress(endpointUrl);
        factory.setBindingId("http://schemas.xmlsoap.org/wsdl/soap/");
        factory.create();

        System.out.printf("Servicio SOAP %s publicado en: %s?wsdl%n", getServiceName(), endpointUrl);
    }


    public String getEndpointUrl() {
        return String.format("http://%s:%s%s/%s", ip, port, path, getServiceName());
    }

    protected abstract String getServiceName();
}

