package app.adapters.mappers;

import app.adapters.dto.flightroute.SoapBaseFlightRouteDTO;
import app.adapters.dto.flightroute.SoapFlightRouteDTO;
import domain.dtos.flightroute.BaseFlightRouteDTO;
import domain.dtos.flightroute.FlightRouteDTO;

public class FlightRouteMapper {

    // -----------------------------------
    // BaseFlightRouteDTO ↔ SoapBaseFlightRouteDTO
    // -----------------------------------

    public static SoapBaseFlightRouteDTO toSoapBaseFlightRouteDTO(BaseFlightRouteDTO dto) {
        if (dto == null) return null;

        SoapBaseFlightRouteDTO soap = new SoapBaseFlightRouteDTO();
        soap.setName(dto.getName());
        soap.setDescription(dto.getDescription());
        soap.setCreatedAt(LocalDateMapper.toString(dto.getCreatedAt()));
        soap.setPriceTouristClass(dto.getPriceTouristClass());
        soap.setPriceBusinessClass(dto.getPriceBusinessClass());
        soap.setPriceExtraUnitBaggage(dto.getPriceExtraUnitBaggage());
        soap.setStatus(dto.getStatus());
        soap.setImage(dto.getImage());
        soap.setVideoURL(dto.getVideoURL());
        soap.setVisitCount(dto.getVisitCount());

        return soap;
    }

    public static BaseFlightRouteDTO toBaseFlightRouteDTO(SoapBaseFlightRouteDTO soap) {
        if (soap == null) return null;

        BaseFlightRouteDTO dto = new BaseFlightRouteDTO();
        dto.setName(soap.getName());
        dto.setDescription(soap.getDescription());
        dto.setCreatedAt(LocalDateMapper.toLocalDate(soap.getCreatedAt()));
        dto.setPriceTouristClass(soap.getPriceTouristClass());
        dto.setPriceBusinessClass(soap.getPriceBusinessClass());
        dto.setPriceExtraUnitBaggage(soap.getPriceExtraUnitBaggage());
        dto.setStatus(soap.getStatus());
        dto.setImage(soap.getImage());
        dto.setVideoURL(soap.getVideoURL());
        dto.setVisitCount(soap.getVisitCount() != null ? soap.getVisitCount() : 0.0);

        return dto;
    }

    // -----------------------------------
    // FlightRouteDTO ↔ SoapFlightRouteDTO
    // -----------------------------------

    public static SoapFlightRouteDTO toSoapFlightRouteDTO(FlightRouteDTO dto) {
        if (dto == null) return null;

        SoapFlightRouteDTO soap = new SoapFlightRouteDTO();
        // Heredados
        SoapBaseFlightRouteDTO base = toSoapBaseFlightRouteDTO(dto);
        copyBaseFields(base, soap);

        // Específicos
        soap.setOriginAeroCode(dto.getOriginAeroCode());
        soap.setDestinationAeroCode(dto.getDestinationAeroCode());
        soap.setAirlineNickname(dto.getAirlineNickname());

        soap.setCategoriesNames(dto.getCategoriesNames());
        soap.setFlightsNames(dto.getFlightsNames());
        soap.setInPackagesNames(dto.getInPackagesNames());

        return soap;
    }

    public static FlightRouteDTO toFlightRouteDTO(SoapFlightRouteDTO soap) {
        if (soap == null) return null;

        FlightRouteDTO dto = new FlightRouteDTO();
        // Heredados
        BaseFlightRouteDTO base = toBaseFlightRouteDTO(soap);
        copyBaseFields(base, dto);

        // Específicos
        dto.setOriginAeroCode(soap.getOriginAeroCode());
        dto.setDestinationAeroCode(soap.getDestinationAeroCode());
        dto.setAirlineNickname(soap.getAirlineNickname());

        dto.setCategoriesNames(soap.getCategoriesNames());
        dto.setFlightsNames(soap.getFlightsNames());
        dto.setInPackagesNames(soap.getInPackagesNames());

        return dto;
    }

    // -----------------------------------
    // Helpers
    // -----------------------------------

    private static void copyBaseFields(BaseFlightRouteDTO source, BaseFlightRouteDTO target) {
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setCreatedAt(source.getCreatedAt());
        target.setPriceTouristClass(source.getPriceTouristClass());
        target.setPriceBusinessClass(source.getPriceBusinessClass());
        target.setPriceExtraUnitBaggage(source.getPriceExtraUnitBaggage());
        target.setStatus(source.getStatus());
        target.setImage(source.getImage());
        target.setVideoURL(source.getVideoURL());
        target.setVisitCount(source.getVisitCount());
    }

    private static void copyBaseFields(SoapBaseFlightRouteDTO source, SoapBaseFlightRouteDTO target) {
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setCreatedAt(source.getCreatedAt());
        target.setPriceTouristClass(source.getPriceTouristClass());
        target.setPriceBusinessClass(source.getPriceBusinessClass());
        target.setPriceExtraUnitBaggage(source.getPriceExtraUnitBaggage());
        target.setStatus(source.getStatus());
        target.setImage(source.getImage());
        target.setVideoURL(source.getVideoURL());
        target.setVisitCount(source.getVisitCount());
    }
}
