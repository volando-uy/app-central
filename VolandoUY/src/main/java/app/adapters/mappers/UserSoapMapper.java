package app.adapters.mappers;

import app.adapters.dto.user.SoapBaseCustomerDTO;
import app.adapters.dto.user.SoapAirlineDTO;
import app.adapters.dto.user.SoapBaseAirlineDTO;
import app.adapters.dto.user.SoapCustomerDTO;
import app.adapters.dto.user.SoapUserDTO;
import domain.dtos.user.*;
import shared.constants.Values;

public class UserSoapMapper {

    // --------------------------------------------------------
    // USERDTO → SOAPUSERDTO (polimórfico)
    // --------------------------------------------------------
    public static SoapUserDTO toSoapUserDTO(UserDTO userDTO) {
        if (userDTO == null) return null;

        SoapUserDTO base = new SoapUserDTO();
        base.setNickname(userDTO.getNickname());
        base.setName(userDTO.getName());
        base.setMail(userDTO.getMail());
        base.setImage(userDTO.getImage());
        base.setPassword(userDTO.getPassword());
        System.out.println(userDTO.getClass().getSimpleName());
        if (userDTO instanceof BaseAirlineDTO airlineDTO) {
            base.setUserType(Values.USER_TYPE_AIRLINE);
            return _toSoapAirlineDTO(airlineDTO, base);
        }

        if (userDTO instanceof BaseCustomerDTO customerDTO) {
            base.setUserType(Values.USER_TYPE_CUSTOMER);
            return _toSoapCustomerDTO(customerDTO, base);
        }

        base.setUserType("unknown");
        return base;
    }


    // --------------------------------------------------------
    // SOAPUSERDTO → USERDTO (polimórfico)
    // --------------------------------------------------------
    public static UserDTO toUserDTO(SoapUserDTO soapDto) {
        if (soapDto == null) return null;

        if (soapDto instanceof SoapAirlineDTO airlineSoap) {
            return toAirlineDTO(airlineSoap);
        }

        if (soapDto instanceof SoapCustomerDTO customerSoap) {
            return toCustomerDTO(customerSoap);
        }
        if (soapDto instanceof SoapBaseCustomerDTO baseCustomer) {
            return toBaseCustomerDTO(baseCustomer);
        }

        if (soapDto instanceof SoapBaseAirlineDTO baseAirline) {
            return toBaseAirlineDTO(baseAirline);
        }

        // Fallback genérico
        UserDTO dto = new UserDTO() {};
        _copyBaseFields(soapDto, dto);
        return dto;
    }

    // --------------------------------------------------------
    // SOAP ⇄ DTO AIRLINE
    // --------------------------------------------------------
    public static AirlineDTO toAirlineDTO(SoapAirlineDTO soapDto) {
        if (soapDto == null) return null;

        AirlineDTO dto = new AirlineDTO();
        _copyBaseFields(soapDto, dto);
        dto.setDescription(soapDto.getDescription());
        dto.setWeb(soapDto.getWeb());
        return dto;
    }

    private static SoapAirlineDTO _toSoapAirlineDTO(AirlineDTO dto, SoapUserDTO base) {
        SoapAirlineDTO soap = new SoapAirlineDTO();
        _copyBaseFields(base, soap);
        soap.setDescription(dto.getDescription());
        soap.setWeb(dto.getWeb());
        return soap;
    }

    private static BaseAirlineDTO _toBaseAirlineDTO(AirlineDTO soapDto, UserDTO base) {
        BaseAirlineDTO dto = new BaseAirlineDTO();
        _copyBaseFields(base, dto);
        dto.setDescription(soapDto.getDescription());
        dto.setWeb(soapDto.getWeb());
        return dto;
    }



    private static SoapAirlineDTO _toSoapAirlineDTO(BaseAirlineDTO dto, SoapUserDTO base) {
        SoapAirlineDTO soap = new SoapAirlineDTO();
        _copyBaseFields(base, soap);
        soap.setDescription(dto.getDescription());
        soap.setWeb(dto.getWeb());
        return soap;
    }

    // --------------------------------------------------------
    // SOAPBASEAIRLINE → BASEAIRLINEDTO
    // --------------------------------------------------------
    public static SoapBaseAirlineDTO toSoapBaseAirlineDTO(BaseAirlineDTO dto) {
        if (dto == null) return null;

        SoapBaseAirlineDTO soap = new SoapBaseAirlineDTO();
        soap.setNickname(dto.getNickname());
        soap.setName(dto.getName());
        soap.setMail(dto.getMail());
        soap.setPassword(dto.getPassword());
        soap.setImage(dto.getImage());
        soap.setDescription(dto.getDescription());
        soap.setWeb(dto.getWeb());

        return soap;
    }

    public static BaseAirlineDTO toBaseAirlineDTO(SoapBaseAirlineDTO soap) {
        if (soap == null) return null;

        BaseAirlineDTO dto = new BaseAirlineDTO();
        _copyBaseFields(soap, dto);
        dto.setDescription(soap.getDescription());
        dto.setWeb(soap.getWeb());

        return dto;
    }

    public static SoapCustomerDTO toSoapCustomerDTO(CustomerDTO dto) {
        if (dto == null) return null;

        SoapCustomerDTO soap = new SoapCustomerDTO();
        soap.setNickname(dto.getNickname());
        soap.setName(dto.getName());
        soap.setMail(dto.getMail());
        soap.setPassword(dto.getPassword());
        soap.setImage(dto.getImage());
        soap.setUserType(Values.USER_TYPE_CUSTOMER);

        soap.setSurname(dto.getSurname());
        soap.setCitizenship(dto.getCitizenship());
        soap.setBirthDate(LocalDateMapper.toString(dto.getBirthDate()));
        soap.setNumDoc(dto.getNumDoc());
        soap.setDocType(dto.getDocType());

        soap.setBoughtPackagesIds(dto.getBoughtPackagesIds());
        soap.setBookFlightsIds(dto.getBookFlightsIds());

        return soap;
    }
    private static SoapUserDTO _toSoapCustomerDTO(BaseCustomerDTO customerDTO, SoapUserDTO base) {
        SoapCustomerDTO soap = new SoapCustomerDTO();
        _copyBaseFields(base, soap);
        soap.setSurname(customerDTO.getSurname());
        soap.setCitizenship(customerDTO.getCitizenship());
        soap.setBirthDate(LocalDateMapper.toString(customerDTO.getBirthDate()));
        soap.setNumDoc(customerDTO.getNumDoc());
        soap.setDocType(customerDTO.getDocType());
        return soap;
    }


    public static SoapAirlineDTO toSoapAirlineDTO(AirlineDTO dto) {
        if (dto == null) return null;

        SoapAirlineDTO soap = new SoapAirlineDTO();
        soap.setNickname(dto.getNickname());
        soap.setName(dto.getName());
        soap.setMail(dto.getMail());
        soap.setPassword(dto.getPassword());
        soap.setImage(dto.getImage());
        soap.setUserType(Values.USER_TYPE_AIRLINE);

        soap.setDescription(dto.getDescription());
        soap.setWeb(dto.getWeb());
        soap.setFlightRoutesNames(dto.getFlightRoutesNames());
        soap.setFlightsNames(dto.getFlightsNames());
        return soap;
    }


    // --------------------------------------------------------
    // SOAP ⇄ DTO CUSTOMER
    // --------------------------------------------------------
    public static CustomerDTO toCustomerDTO(SoapCustomerDTO soapDto) {
        if (soapDto == null) return null;

        CustomerDTO dto = new CustomerDTO();
        _copyBaseFields(soapDto, dto);
        dto.setSurname(soapDto.getSurname());
        dto.setCitizenship(soapDto.getCitizenship());
        dto.setBirthDate(LocalDateMapper.toLocalDate(soapDto.getBirthDate()));
        dto.setNumDoc(soapDto.getNumDoc());
        dto.setDocType(soapDto.getDocType());
        dto.setBoughtPackagesIds(soapDto.getBoughtPackagesIds());
        dto.setBookFlightsIds(soapDto.getBookFlightsIds());

        return dto;
    }

    private static SoapCustomerDTO _toSoapCustomerDTO(CustomerDTO dto, SoapUserDTO base) {
        SoapCustomerDTO soap = new SoapCustomerDTO();
        _copyBaseFields(base, soap);
        soap.setSurname(dto.getSurname());
        soap.setCitizenship(dto.getCitizenship());
        soap.setBirthDate(LocalDateMapper.toString(dto.getBirthDate()));
        soap.setNumDoc(dto.getNumDoc());
        soap.setDocType(dto.getDocType());
        soap.setBoughtPackagesIds(dto.getBoughtPackagesIds());
        soap.setBookFlightsIds(dto.getBookFlightsIds());
        return soap;
    }

    // --------------------------------------------------------
    // SOAPBASE CUSTOMER → BASE CUSTOMER DTO
    // --------------------------------------------------------
    public static SoapBaseCustomerDTO toSoapBaseCustomerDTO(BaseCustomerDTO dto) {
        if (dto == null) return null;

        SoapBaseCustomerDTO soap = new SoapBaseCustomerDTO();
        _copyBaseFields(dto, soap);
        soap.setSurname(dto.getSurname());
        soap.setCitizenship(dto.getCitizenship());
        soap.setBirthDate(LocalDateMapper.toString(dto.getBirthDate()));
        soap.setNumDoc(dto.getNumDoc());
        soap.setDocType(dto.getDocType());


        return soap;
    }

    public static BaseCustomerDTO toBaseCustomerDTO(SoapBaseCustomerDTO soap) {
        if (soap == null) return null;

        BaseCustomerDTO dto = new BaseCustomerDTO();
        _copyBaseFields(soap, dto);
        dto.setSurname(soap.getSurname());
        dto.setCitizenship(soap.getCitizenship());
        dto.setBirthDate(LocalDateMapper.toLocalDate(soap.getBirthDate()));
        dto.setNumDoc(soap.getNumDoc());
        dto.setDocType(soap.getDocType());

        return dto;
    }

    // --------------------------------------------------------
    // HELPERS
    // --------------------------------------------------------
    private static void _copyBaseFields(SoapUserDTO source, UserDTO target) {
        target.setNickname(source.getNickname());
        target.setName(source.getName());
        target.setMail(source.getMail());
        target.setPassword(source.getPassword());
        target.setImage(source.getImage());
    }

    private static void _copyBaseFields(SoapUserDTO source, SoapUserDTO target) {
        target.setNickname(source.getNickname());
        target.setName(source.getName());
        target.setMail(source.getMail());
        target.setPassword(source.getPassword());
        target.setImage(source.getImage());
        target.setUserType(source.getUserType());
    }

    private static void _copyBaseFields(UserDTO source, SoapUserDTO target) {
        target.setNickname(source.getNickname());
        target.setName(source.getName());
        target.setMail(source.getMail());
        target.setPassword(source.getPassword());
        target.setImage(source.getImage());
    }
    private static void _copyBaseFields(UserDTO base, BaseAirlineDTO dto) {
        dto.setNickname(base.getNickname());
        dto.setName(base.getName());
        dto.setMail(base.getMail());
        dto.setPassword(base.getPassword());
        dto.setImage(base.getImage());
    }
}
