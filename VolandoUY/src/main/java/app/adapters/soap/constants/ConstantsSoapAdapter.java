package app.adapters.soap.constants;

import app.adapters.dto.constants.ImageConstantsDTO;
import app.adapters.dto.constants.ValuesConstantsDTO;
import app.adapters.soap.BaseSoapAdapter;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import shared.constants.Images;
import shared.constants.Values;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class ConstantsSoapAdapter extends BaseSoapAdapter {
    @Override
    protected String getServiceName() {
        return "constantsService";
    }

    @WebMethod
    public ImageConstantsDTO getImageConstants() {
        ImageConstantsDTO dto = new ImageConstantsDTO();

        dto.imagesPath = Images.IMAGES_PATH;
        dto.userDefault = Images.USER_DEFAULT;
        dto.flightDefault = Images.FLIGHT_DEFAULT;
        dto.flightRouteDefault = Images.FLIGHT_ROUTE_DEFAULT;
        dto.flightRoutePackageDefault = Images.FLIGHT_ROUTE_PACKAGE_DEFAULT;
        dto.errorDefault = Images.ERROR_DEFAULT;

        dto.airlinesPath = Images.AIRLINES_PATH;
        dto.customersPath = Images.CUSTOMERS_PATH;
        dto.flightsPath = Images.FLIGHTS_PATH;
        dto.flightRoutesPath = Images.FLIGHT_ROUTES_PATH;
        dto.flightRoutesPackagesPath = Images.FLIGHT_ROUTES_PACKAGES_PATH;

        dto.formatDefault = Images.FORMAT_DEFAULT;

        return dto;
    }

    @WebMethod
    public ValuesConstantsDTO getValueConstants() {
        ValuesConstantsDTO dto = new ValuesConstantsDTO();
        dto.USER_TYPE_AIRLINE = Values.USER_TYPE_AIRLINE;
        dto.USER_TYPE_CUSTOMER = Values.USER_TYPE_CUSTOMER;

        return dto;
    }

}
