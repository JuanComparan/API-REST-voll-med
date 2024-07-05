package med.voll.api.domain.paciente;

import med.voll.api.domain.direccion.DatosDireccion;

public record DatoRespuestaPaciente(Long id, String nombre, String email, String telefono, DatosDireccion direccion) {
}
