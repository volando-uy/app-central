package casosdeuso;

import domain.models.enums.EnumTipoAsiento;
import domain.models.enums.EnumTipoDocumento;
import domain.models.packagePurchase.PackagePurchase;
import domain.models.user.Customer;
import domain.models.flightRoutePackage.FlightRoutePackage;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PackagePurchaseEntityTest {

    private static EntityManagerFactory emf;
    private EntityManager em;

    @BeforeAll
    static void init() {
        emf = Persistence.createEntityManagerFactory("Volando"); // persistence.xml
    }

    @AfterAll
    static void close() {
        emf.close();
    }

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
    }

    @AfterEach
    void tearDown() {
        if (em.isOpen()) em.close();
    }

    @Test
    void persistPackagePurchase_directly() {
        em.getTransaction().begin();

        // ----------- Customer único -----------
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID().toString()); // 🔹 seteo obligatorio
        customer.setNickname("clienteTest_" + UUID.randomUUID());
        customer.setName("Cliente Test");
        customer.setSurname("ApellidoTest");
        customer.setMail("cliente@test.com");
        customer.setCitizenship("UY");
        customer.setIdType(EnumTipoDocumento.CI);
        customer.setBirthDate(LocalDate.of(2000, 1, 1));

        // ----------- FlightRoutePackage único -----------
        FlightRoutePackage frPackage = new FlightRoutePackage();
        frPackage.setName("Paquete Test_" + UUID.randomUUID());
        frPackage.setCreationDate(LocalDate.now());
        frPackage.setValidityPeriodDays(7);
        frPackage.setDescription("Paquete de prueba");
        frPackage.setSeatType(EnumTipoAsiento.EJECUTIVO);

        em.persist(customer);
        em.persist(frPackage);

        // ----------- PackagePurchase -----------
        PackagePurchase purchase = new PackagePurchase();
        purchase.setCustomer(customer);
        purchase.setFlightRoutePackage(frPackage);
        purchase.setPurchaseDate(LocalDate.now());
        purchase.setExpirationDate(LocalDateTime.now().plusDays(frPackage.getValidityPeriodDays()));
        em.persist(purchase);

        em.getTransaction().commit();

        // ----------- Verificación -----------
        var results = em.createQuery(
                        "SELECT p FROM PackagePurchase p WHERE p.customer.nickname = :nick",
                        PackagePurchase.class
                )
                .setParameter("nick", customer.getNickname())
                .getResultList();

        assertEquals(1, results.size());
        System.out.println("✅ Persistencia OK, ID generado: " + results.get(0).getId());
    }
}