package domain.models.user;


import domain.dtos.user.ClienteDTO;
import domain.dtos.user.UsuarioDTO;
import domain.models.user.enums.EnumTipoDocumento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends Usuario {
    private String apellido;
    private LocalDate fechaNacimiento;
    private String nacionalidad;
    private EnumTipoDocumento tipoDocumento;
    private String numDocumento;


    @Override
    public void actualizarDatosDesde(UsuarioDTO nuevosDatos) {
        if (!(nuevosDatos instanceof ClienteDTO nuevo)) return;

        this.setNombre(nuevo.getNombre());
        this.setApellido(nuevo.getApellido());
        this.setFechaNacimiento(nuevo.getFechaNacimiento());
        this.setNumDocumento(nuevo.getNumDocumento());
        this.setTipoDocumento(nuevo.getTipoDocumento());
        this.setNacionalidad(nuevo.getNacionalidad());

    }

}
