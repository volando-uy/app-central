package domain.models.city;

import domain.models.airport.Airport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {
    private List<Airport> airports;
    private String name;
    private String country;
    private double latitude;
    private double longitude;

    public boolean isAirportInCity(String airportName) {
        if (airports == null || airports.isEmpty()) {
            return false;
        }
        for (Airport airport : airports) {
            if (airport.getName().equalsIgnoreCase(airportName)) {
                return true;
            }
        }
        return false;
    }

    public void addAirport(String airportName, String airportCode) {
        if (airports == null) {
            airports = new ArrayList<>();
        }
        Airport newAirport = new Airport(this, airportName, airportCode);
        airports.add(newAirport);
    }
}
