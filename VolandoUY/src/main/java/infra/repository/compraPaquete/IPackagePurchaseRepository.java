package infra.repository.compraPaquete;

import domain.models.packagePurchase.PackagePurchase;

public interface IPackagePurchaseRepository {
    boolean existsByCustomerAndPackage(String customerNickname, String packageName);
    void save(PackagePurchase purchase);
}