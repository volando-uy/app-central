package app.adapters.soap.countries;

import app.adapters.soap.BaseSoapAdapter;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import shared.utils.CountryUtil;

import java.util.List;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class SoapCountriesAdapter extends BaseSoapAdapter {
    @Override
    protected String getServiceName() {
        return "countriesService";
    }

    @WebMethod
    public List<String> getAllCountries() {
        return CountryUtil.getAllCountries();
    }

}
