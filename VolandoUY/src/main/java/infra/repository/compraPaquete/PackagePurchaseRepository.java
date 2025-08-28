package infra.repository.compraPaquete;

import domain.models.packagePurchase.PackagePurchase;
import infra.repository.BaseRepository;

public class PackagePurchaseRepository extends BaseRepository<PackagePurchase>{
    public PackagePurchaseRepository() {
        super(PackagePurchase.class);
    }

    public boolean existsByCustomerAndPackage(String customerNickname, String packageName) {
        return findAll().stream().anyMatch(purchase ->
                purchase.getCustomer().getNickname().equalsIgnoreCase(customerNickname)
                        && purchase.getFlightRoutePackage().getName().equalsIgnoreCase(packageName)
        );
    }
}
