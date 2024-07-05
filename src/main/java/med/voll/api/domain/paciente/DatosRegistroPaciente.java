package med.voll.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.Especialidad;

public record DatosRegistroPaciente(

        @NotBlank(message = "{nombre.obligatorio}")
        String nombre,

        @NotBlank(message = "{email.obligatorio}")
        @Email(message = "{email.invalido}")
        String email,

        @NotBlank(message = "El numero es obligatorio")
        String telefono,

        @NotNull(message = "La consulta es obligatoria")
        Especialidad consulta,

        @NotNull(message = "Los datos de la dirección son obligatorios")
        @Valid
        DatosDireccion direccion

) {
}
