package med.voll.api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatoActualizarPaciente(@NotNull Long id, String nombre, DatosDireccion direccion) {
}
