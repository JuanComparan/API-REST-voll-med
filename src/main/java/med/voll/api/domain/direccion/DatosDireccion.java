package med.voll.api.domain.direccion;

import jakarta.validation.constraints.NotBlank;

public record DatosDireccion(

        @NotBlank(message = "{calle.obligatorio}")
        String calle,

        @NotBlank(message = "{distrito.obligatorio}")
        String distrito,

        @NotBlank(message = "{ciudad.obligatorio}")
        String ciudad,

        @NotBlank(message = "{numero.obligatorio}")
        String numero,

        @NotBlank(message = "{complemento.obligatorio}")
        String complemento

) {
}
