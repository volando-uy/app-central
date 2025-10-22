package controllers.airport;

import controllers.city.ICityController;
import domain.dtos.airport.AirportDTO;
import domain.dtos.airport.BaseAirportDTO;
import domain.dtos.city.CityDTO;
import domain.services.airport.AirportService;
import domain.services.airport.IAirportService;
import factory.ControllerFactory;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import shared.constants.ErrorMessages;
import utils.TestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AirportControllerTest {

    private IAirportController airportController;

    @Mock
    private IAirportService airportService = Mockito.mock(AirportService.class);


    @BeforeAll
    void setUp() {
        airportController = new AirportController(airportService);
    }

    @Test
    @DisplayName("GIVEN valid AirportDTO and city WHEN createairport is called THEN it should return created airport")
    void createAirport_shouldReturnCreatedAirport() {
        BaseAirportDTO dto = new BaseAirportDTO();
        dto.setName("Carrasco");
        dto.setCode("MVD");

        // Mock of service
        Mockito.when(airportService.createAirport(dto, "Montevideo")).thenReturn(dto);

        BaseAirportDTO created = airportController.createAirport(dto, "Montevideo");

        assertNotNull(created);
        assertEquals("MVD", created.getCode());
        assertEquals("Carrasco", created.getName());
    }

    @Test
    @DisplayName("GIVEN existing code WHEN getAirportByCode is called THEN correct airport is returned")
    void getAirportByCode_shouldReturnCorrectDTO() {
        AirportDTO dto = new AirportDTO();
        dto.setName("Carrasco");
        dto.setCode("MVD");
        dto.setCityName("Montevideo");

        // Mock of createairport
        Mockito.when(airportService.createAirport(dto, "Montevideo")).thenReturn(dto);

        airportController.createAirport(dto, "Montevideo");

        // Mock of getAirport
        Mockito.when(airportService.getAirportDetailsByCode("MVD", true)).thenReturn(dto);

        AirportDTO result = airportController.getAirportDetailsByCode("MVD");

        assertNotNull(result);
        assertEquals("MVD", result.getCode());
        assertEquals("Carrasco", result.getName());
        assertEquals("Montevideo", result.getCityName());
    }

    @Test
    @DisplayName("GIVEN existing code WHEN getAirportDetailsByCode is called THEN correct airport is returned")
    void getAirportByCodeDetails_shouldReturnCorrectDTO() {
        AirportDTO dto = new AirportDTO();
        dto.setName("Carrasco");
        dto.setCode("MVD");

        // Mock of createairport
        Mockito.when(airportService.createAirport(dto, "Montevideo")).thenReturn(dto);

        airportController.createAirport(dto, "Montevideo");

        // Mock of getAirport
        Mockito.when(airportService.getAirportDetailsByCode("MVD", false)).thenReturn(dto);

        BaseAirportDTO result = airportController.getAirportSimpleDetailsByCode("MVD");

        assertNotNull(result);
        assertEquals("MVD", result.getCode());
        assertEquals("Carrasco", result.getName());
    }

    @Test
    @DisplayName("GIVEN airport exists WHEN airportExists is called THEN return true")
    void airportExists_shouldReturnTrueIfExists() {
        AirportDTO dto = new AirportDTO();
        dto.setName("Carrasco");
        dto.setCode("MVD");
        dto.setCityName("Montevideo");

        // Mock of createairport
        Mockito.when(airportService.createAirport(dto, "Montevideo")).thenReturn(dto);

        airportController.createAirport(dto, "Montevideo");

        // Mock of airportExists
        Mockito.when(airportService.airportExists("MVD")).thenReturn(true);

        assertTrue(airportController.airportExists("MVD"));
    }

    @Test
    @DisplayName("GIVEN airport does not exist WHEN airportExists is called THEN return false")
    void airportExists_shouldReturnFalseIfNotExists() {
        // Mock of airportExists
        Mockito.when(airportService.airportExists("XYZ")).thenReturn(false);

        assertFalse(airportController.airportExists("XYZ"));
    }

    @Test
    @DisplayName("GIVEN duplicate code WHEN createairport is called THEN throw exception")
    void createAirport_shouldFailOnDuplicateCode() {
        BaseAirportDTO dto = new BaseAirportDTO();
        dto.setName("Carrasco");
        dto.setCode("MVD");

        // Mock of airportExists
        Mockito.when(airportService.createAirport(dto, "Montevideo")).thenReturn(dto);

        airportController.createAirport(dto, "Montevideo");

        // Mock of createairport with throw
        Mockito.when(airportService.createAirport(dto, "Montevideo")).thenThrow(new IllegalArgumentException(String.format(ErrorMessages.ERR_AIRPORT_CODE_ALREADY_EXISTS, "MVD")));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            airportController.createAirport(dto, "Montevideo");
        });

        assertTrue(ex.getMessage().contains("MVD"));
    }

    @Test
    @DisplayName("GIVEN various airports created WHEN getAllAirportsSimpleDetails is called THEN return airports created")
    void getAllAirportsSimpleDetails_shouldReturnAllAirports() {
        AirportDTO dto1 = new AirportDTO();
        dto1.setName("Aero San José");
        dto1.setCode("SJM");
        dto1.setCityName("San José");

        AirportDTO dto2 = new AirportDTO();
        dto2.setName("Aero Montevideo");
        dto2.setCode("MVD");
        dto2.setCityName("Montevideo");

        // Mock of getAllAirportsSimpleDetails with throw
        Mockito.when(airportService.getAllAirportsDetails(false)).thenReturn(new ArrayList<>() {{ add(dto1); add(dto2); }});

        List<BaseAirportDTO> returned = airportController.getAllAirportsSimpleDetails();

        assertTrue(returned.size() == 2);
        assertTrue(returned.get(0).getCode().equals("SJM"));
        assertTrue(returned.get(1).getCode().equals("MVD"));
    }

    @Test
    @DisplayName("GIVEN various airports created WHEN getAllAirportsDetails is called THEN return airports created")
    void getAllAirportsDetails_shouldReturnAllAirports() {
        AirportDTO dto1 = new AirportDTO();
        dto1.setName("Aero San José");
        dto1.setCode("SJM");
        dto1.setCityName("San José");

        AirportDTO dto2 = new AirportDTO();
        dto2.setName("Aero Montevideo");
        dto2.setCode("MVD");
        dto2.setCityName("Montevideo");

        // Mock of getAllAirportsSimpleDetails with throw
        Mockito.when(airportService.getAllAirportsDetails(true)).thenReturn(new ArrayList<>() {{ add(dto1); add(dto2); }});

        List<AirportDTO> returned = airportController.getAllAirportsDetails();

        assertTrue(returned.size() == 2);
        assertTrue(returned.get(0).getCode().equals("SJM"));
        assertTrue(returned.get(0).getCityName().equals("San José"));
        assertTrue(returned.get(1).getCode().equals("MVD"));
        assertTrue(returned.get(1).getCityName().equals("Montevideo"));
    }
}
