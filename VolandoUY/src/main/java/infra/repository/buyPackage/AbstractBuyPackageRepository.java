package infra.repository.buyPackage;

import domain.models.buypackage.BuyPackage;
import domain.models.category.Category;
import infra.repository.BaseRepository;

public abstract class AbstractBuyPackageRepository extends BaseRepository<BuyPackage> {
    public AbstractBuyPackageRepository() {
        super(BuyPackage.class);
    }
}
