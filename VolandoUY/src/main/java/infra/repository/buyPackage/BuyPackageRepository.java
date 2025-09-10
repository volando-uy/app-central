package infra.repository.buyPackage;

import app.DBConnection;
import domain.models.buypackage.BuyPackage;
import domain.models.category.Category;
import domain.models.flightRoutePackage.FlightRoutePackage;
import domain.models.user.Customer;
import jakarta.persistence.EntityManager;

public class BuyPackageRepository extends AbstractBuyPackageRepository implements IBuyPackageRepository {

    public void buyPackage(BuyPackage buyPackage, Customer customer, FlightRoutePackage flightRoutePackage) {
        // Persist the buyPackage, add it to customer and add it to flightRoutePackage
        EntityManager em = DBConnection.getEntityManager();
        try {
            em.getTransaction().begin();

            // Persist the BuyPackage entity
            em.persist(buyPackage);

            // Add the BuyPackage to the Customer's boughtPackages collection
            customer.getBoughtPackages().add(buyPackage);
            em.merge(customer); // Update the customer entity

            // Add the BuyPackage to the FlightRoutePackage's purchases collection
            flightRoutePackage.getBuyPackages().add(buyPackage);
            em.merge(flightRoutePackage); // Update the flightRoutePackage entity

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e; // Rethrow the exception after rollback
        } finally {
            em.close();
        }
    }

    public BuyPackage getFullBuyPackageById(Long id) {
        EntityManager em = DBConnection.getEntityManager();
        try (em) {
            BuyPackage bp = em.createQuery(
                            "SELECT bp FROM BuyPackage bp WHERE bp.id = :id", BuyPackage.class)
                    .setParameter("id", id)
                    .getSingleResult();

            // Load relationships to avoid lazy loading issues
            if (bp.getCustomer() != null) {
                bp.getCustomer();
                bp.getFlightRoutePackage();
                bp.getBookFlights().size();
            }

            return bp;
        } catch (Exception e) {
            return null;
        }
    }

    public BuyPackage getBuyPackageById(Long id) {
        return findByKey(id);
    }
}
