package med.voll.api.domain.consulta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;

import java.time.LocalDateTime;

@Table(name = "consultas")
@Entity(name = "Consulta")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private LocalDateTime fecha;

    private Boolean activo;

    @Column(name = "motivo")
    @Enumerated(EnumType.STRING)
    private MotivoCancelamiento motivoCancelamiento;


    public Consulta(Medico medico, Paciente paciente, LocalDateTime fecha){
        this.medico = medico;
        this.paciente = paciente;
        this.fecha = fecha;
    }

    public void cancelar(MotivoCancelamiento motivo){
        this.motivoCancelamiento = motivo;
    }

    public String desactivarConsulta() {
        this.activo = false;
        if (!this.medico.getActivo()) {
            return ("Consulta desactivada, motivo: el medico se dio de baja");
        }
        if (!this.paciente.getActivo()) {
            return ("Consulta desactivada, motivo: el paciente se dio de baja");
        }
        else {
            return ("Consulta desactivada, motivo: personales u otros");
        }
    }
}
