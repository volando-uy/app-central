package infra.repository.buypackage;

import domain.models.buypackage.BuyPackage;
import infra.repository.BaseRepository;

public abstract class AbstractBuyPackageRepository extends BaseRepository<BuyPackage> {
    public AbstractBuyPackageRepository() {
        super(BuyPackage.class);
    }
}
