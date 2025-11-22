package app.adapters.mappers;

import app.adapters.dto.user.SoapUserDTO;

public class UserSoapMapper {

    public static SoapUserDTO toSoap(domain.dtos.user.UserDTO dto) {
        SoapUserDTO soap = new SoapUserDTO();
        soap.setNickname(dto.getNickname());
        soap.setName(dto.getName());
        soap.setMail(dto.getMail());
        soap.setImage(dto.getImage());
        return soap;
    }

}