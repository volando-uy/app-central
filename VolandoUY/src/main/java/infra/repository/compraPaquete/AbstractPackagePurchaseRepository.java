package infra.repository.compraPaquete;

import domain.models.packagePurchase.PackagePurchase;
import infra.repository.BaseRepository;

public abstract class AbstractPackagePurchaseRepository extends BaseRepository<PackagePurchase> {
    public AbstractPackagePurchaseRepository(Class<PackagePurchase> packagePurchaseClass) {
        super(PackagePurchase.class);
    }
}