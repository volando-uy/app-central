package app.adapters.mappers;

import app.adapters.dto.bookflight.SoapBaseBookFlightDTO;
import app.adapters.dto.bookflight.SoapBookFlightDTO;
import domain.dtos.bookflight.BaseBookFlightDTO;
import domain.dtos.bookflight.BookFlightDTO;

public class BookFlightSoapMapper {

    public static SoapBookFlightDTO toSoapBookFlightDTO(BookFlightDTO bookFlightDTO){
        SoapBookFlightDTO soapDto = new SoapBookFlightDTO();
        soapDto.setId(bookFlightDTO.getId());
        soapDto.setCreatedAt(DateTimeMapper.toSoapLocalDateTime(bookFlightDTO.getCreatedAt()));
        soapDto.setTotalPrice(bookFlightDTO.getTotalPrice());
        soapDto.setSeatType(bookFlightDTO.getSeatType());
        soapDto.setCustomerNickname(bookFlightDTO.getCustomerNickname());
        soapDto.setTicketIds(bookFlightDTO.getTicketIds());
        return soapDto;
    }

    public static BookFlightDTO toBookFlightDTO(SoapBookFlightDTO soapDto){
        BookFlightDTO dto = new BookFlightDTO();
        dto.setId(soapDto.getId());
        dto.setCreatedAt(DateTimeMapper.fromSoapLocalDateTime(soapDto.getCreatedAt()));
        dto.setTotalPrice(soapDto.getTotalPrice());
        dto.setSeatType(soapDto.getSeatType());
        dto.setCustomerNickname(soapDto.getCustomerNickname());
        dto.setTicketIds(soapDto.getTicketIds());
        return dto;
    }

    public static BaseBookFlightDTO toBaseBookFlightDTO(SoapBaseBookFlightDTO soapDto) {
        BaseBookFlightDTO dto = new BaseBookFlightDTO();
        dto.setId(soapDto.getId());
        dto.setCreatedAt(DateTimeMapper.fromSoapLocalDateTime(soapDto.getCreatedAt()));
        dto.setTotalPrice(soapDto.getTotalPrice());
        dto.setSeatType(soapDto.getSeatType());
        return dto;
    }

    public static SoapBaseBookFlightDTO toSoapBaseBookFlightDTO(BaseBookFlightDTO dto) {
        SoapBaseBookFlightDTO soapDto = new SoapBaseBookFlightDTO();
        soapDto.setId(dto.getId());
        soapDto.setCreatedAt(DateTimeMapper.toSoapLocalDateTime(dto.getCreatedAt()));
        soapDto.setTotalPrice(dto.getTotalPrice());
        soapDto.setSeatType(dto.getSeatType());
        return soapDto;
    }
}
