package infra.repository.compraPaquete;

import app.DBConnection;
import domain.models.packagePurchase.PackagePurchase;
import jakarta.persistence.EntityManager;

public class PackagePurchaseRepository extends AbstractPackagePurchaseRepository implements IPackagePurchaseRepository {

    public PackagePurchaseRepository() {
        super(PackagePurchase.class);
    }

    @Override
    public boolean existsByCustomerAndPackage(String customerNickname, String packageName) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            Long count = em.createQuery(
                            "SELECT COUNT(pp) FROM PackagePurchase pp " +
                                    "WHERE LOWER(pp.customer.nickname) = :nickname " +
                                    "AND LOWER(pp.flightRoutePackage.name) = :packageName",
                            Long.class
                    )
                    .setParameter("nickname", customerNickname.toLowerCase())
                    .setParameter("packageName", packageName.toLowerCase())
                    .getSingleResult();

            return count > 0;
        }
    }

    @Override
    public void save(PackagePurchase purchase) {
        super.save(purchase);
    }
}