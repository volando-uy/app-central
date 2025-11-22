package app.adapters.mappers;


import app.adapters.dto.customer.SoapBaseCustomerDTO;
import domain.dtos.user.BaseCustomerDTO;

public class CustomerSoapMapper {

    public static BaseCustomerDTO fromSoap(SoapBaseCustomerDTO soapDto) {
        BaseCustomerDTO dto = new BaseCustomerDTO();
        dto.setNickname(soapDto.getNickname());
        dto.setName(soapDto.getName());
        dto.setMail(soapDto.getMail());
        dto.setPassword(soapDto.getPassword());
        dto.setImage(soapDto.getImage());
        dto.setSurname(soapDto.getSurname());
        dto.setCitizenship(soapDto.getCitizenship());
        dto.setBirthDate(DateMapper.fromSoapLocalDate(soapDto.getBirthDate()));
        dto.setNumDoc(soapDto.getNumDoc());
        dto.setDocType(soapDto.getDocType());
        return dto;
    }

    public static SoapBaseCustomerDTO toSoap(BaseCustomerDTO dto) {
        SoapBaseCustomerDTO soapDto = new SoapBaseCustomerDTO();
        soapDto.setNickname(dto.getNickname());
        soapDto.setName(dto.getName());
        soapDto.setMail(dto.getMail());
        soapDto.setPassword(dto.getPassword());
        soapDto.setImage(dto.getImage());
        soapDto.setSurname(dto.getSurname());
        soapDto.setCitizenship(dto.getCitizenship());
        soapDto.setBirthDate(DateMapper.toSoapLocalDate(dto.getBirthDate()));
        soapDto.setNumDoc(dto.getNumDoc());
        soapDto.setDocType(dto.getDocType());
        return soapDto;
    }
}
