package app.adapters.soap.buypackage;

import app.adapters.soap.BaseSoapAdapter;
import controllers.buypackage.IBuyPackageController;
import domain.dtos.buypackage.BaseBuyPackageDTO;
import domain.dtos.buypackage.BuyPackageDTO;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class BuyPackageSoapAdapter extends BaseSoapAdapter implements IBuyPackageController {

    private final IBuyPackageController controller;

    public BuyPackageSoapAdapter(IBuyPackageController controller) {
        this.controller = controller;
    }

    @Override
    protected String getServiceName() {
        return "buyPackageService";
    }

    @Override
    @WebMethod
    public BaseBuyPackageDTO createBuyPackage(String customerNickname, String flightRoutePackageName) {
        return controller.createBuyPackage(customerNickname, flightRoutePackageName);
    }

    @Override
    @WebMethod
    public BuyPackageDTO getBuyPackageDetailsById(Long id) {
        return controller.getBuyPackageDetailsById(id);
    }

    @Override
    @WebMethod
    public BaseBuyPackageDTO getBuyPackageSimpleDetailsById(Long id) {
        return controller.getBuyPackageSimpleDetailsById(id);
    }

    @Override
    @WebMethod
    public String getFlightNameById(Long id) {
        return controller.getFlightNameById(id);
    }
}
