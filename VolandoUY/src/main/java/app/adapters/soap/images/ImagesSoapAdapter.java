package app.adapters.soap.images;

import app.adapters.dto.constants.ImageConstantsDTO;
import app.adapters.mappers.ImageMapper;
import app.adapters.soap.BaseSoapAdapter;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import shared.constants.Images;
import shared.utils.ImageProcessor;

import java.io.File;
import java.io.IOException;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class ImagesSoapAdapter extends BaseSoapAdapter {

    @Override
    protected String getServiceName() {
        return "imagesService";
    }

    // ------------------------------------------------------------
    //  CONSTANTES DE IMÁGENES
    // ------------------------------------------------------------
//    @WebMethod
//    public ImageConstantsDTO getImageConstants() {
//        ImageConstantsDTO dto = new ImageConstantsDTO();
//
//        dto.imagesPath = Images.IMAGES_PATH;
//        dto.userDefault = Images.USER_DEFAULT;
//        dto.flightDefault = Images.FLIGHT_DEFAULT;
//        dto.flightRouteDefault = Images.FLIGHT_ROUTE_DEFAULT;
//        dto.flightRoutePackageDefault = Images.FLIGHT_ROUTE_PACKAGE_DEFAULT;
//        dto.errorDefault = Images.ERROR_DEFAULT;
//
//        dto.airlinesPath = Images.AIRLINES_PATH;
//        dto.customersPath = Images.CUSTOMERS_PATH;
//        dto.flightsPath = Images.FLIGHTS_PATH;
//        dto.flightRoutesPath = Images.FLIGHT_ROUTES_PATH;
//        dto.flightRoutesPackagesPath = Images.FLIGHT_ROUTES_PACKAGES_PATH;
//
//        dto.formatDefault = Images.FORMAT_DEFAULT;
//
//        return dto;
//    }


    @WebMethod
    public String getImageAbsolutePath(String resource, String key) {

        String image= ImageProcessor.getImageAbsolutePath(resource, key);
        if(image==null || image.isEmpty()){
            return Images.ERROR_DEFAULT;
        }
        return image;
    }

    @WebMethod
    public String getCreationPath(String resource, String key) {
        return ImageProcessor.getCreationPath(resource, key);
    }

    @WebMethod
    public String uploadImage(String base64Image, String creationPath) {
        try {
            File imageFile = null;
            imageFile = ImageMapper.fromBase64(base64Image, ".tmp");
            return ImageProcessor.uploadImage(imageFile, creationPath);
        } catch (IOException e) {
            throw new RuntimeException("Error al procesar la imagen base64", e);
        }
    }
}
