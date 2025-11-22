package app.adapters.mappers;

import app.adapters.dto.bookflight.SoapBaseBookFlightDTO;
import domain.dtos.bookflight.BaseBookFlightDTO;

public class BookFlightSoapMapper {

    public static BaseBookFlightDTO fromSoap(SoapBaseBookFlightDTO soapDto) {
        BaseBookFlightDTO dto = new BaseBookFlightDTO();
        dto.setId(soapDto.getId());
        dto.setCreatedAt(DateTimeMapper.fromSoapLocalDateTime(soapDto.getCreatedAt()));
        dto.setTotalPrice(soapDto.getTotalPrice());
        dto.setSeatType(soapDto.getSeatType());
        return dto;
    }

    public static SoapBaseBookFlightDTO toSoap(BaseBookFlightDTO dto) {
        SoapBaseBookFlightDTO soapDto = new SoapBaseBookFlightDTO();
        soapDto.setId(dto.getId());
        soapDto.setCreatedAt(DateTimeMapper.toSoapLocalDateTime(dto.getCreatedAt()));
        soapDto.setTotalPrice(dto.getTotalPrice());
        soapDto.setSeatType(dto.getSeatType());
        return soapDto;
    }
}
