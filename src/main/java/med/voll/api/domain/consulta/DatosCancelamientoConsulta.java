package med.voll.api.domain.consulta;

import jakarta.validation.constraints.NotNull;

public record DatosCancelamientoConsulta(
        @NotNull Long idConsulta,
        @NotNull MotivoCancelamiento motivo
) {

    public DatosCancelamientoConsulta(Consulta consulta) {
        this(consulta.getId(), consulta.getMotivoCancelamiento());
    }
}
