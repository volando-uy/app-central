    package domain.dtos.airport;

    import domain.dtos.city.CityDTO;
    import domain.models.city.City;
    import lombok.*;

    @Getter
    @Setter
    @NoArgsConstructor
    public class AirportDTO extends BaseAirportDTO {
        private String cityName;

        public AirportDTO(String name, String code) {
            super(name, code);
            this.cityName = null;
        }
    }
