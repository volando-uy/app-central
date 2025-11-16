package app.adapters.soap;

import app.config.ConfigProperties;
import jakarta.jws.WebMethod;
import jakarta.xml.ws.Endpoint;

public abstract class BaseSoapAdapter {
    private final String ip = ConfigProperties.get("soap.ip");
    private final String port = ConfigProperties.get("soap.port");
    private final String path = ConfigProperties.get("soap.path");

    @WebMethod(exclude = true)
    public void publish() {
        String endpointUrl = getEndpointUrl();

        Endpoint.publish(endpointUrl, this);


        System.out.printf("🟢 Servicio SOAP %s publicado en: %s?wsdl%n", getServiceName(), endpointUrl);

    }

    public String getEndpointUrl() {
        return String.format("http://%s:%s%s/%s", ip, port, path, getServiceName());
    }

    protected abstract String getServiceName();
}
