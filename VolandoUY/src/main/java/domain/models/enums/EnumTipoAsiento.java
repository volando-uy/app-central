package domain.models.enums;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "EnumTipoAsiento")
@XmlEnum
public enum EnumTipoAsiento {
    TURISTA, EJECUTIVO
}
