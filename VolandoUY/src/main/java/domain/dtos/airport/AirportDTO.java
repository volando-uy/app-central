    package domain.dtos.airport;

    import domain.dtos.city.CityDTO;
    import domain.models.city.City;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class AirportDTO {
        private String name;
        private String code;
        private String cityName;
    }
