package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;
import med.voll.api.domain.medico.Especialidad;


@Table(name = "pacientes")
@Entity(name = "paciente")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean activo;
    private String nombre;
    private String email;
    private String telefono;
    @Enumerated(EnumType.STRING)
    private Especialidad consulta;
    @Embedded
    private Direccion direccion;

    public Paciente(DatosRegistroPaciente datosRegistroPaciente) {
        this.activo = true;
        this.nombre = datosRegistroPaciente.nombre();
        this.email = datosRegistroPaciente.email();
        this.telefono = datosRegistroPaciente.telefono();
        this.consulta = datosRegistroPaciente.consulta();
        this.direccion = new Direccion(datosRegistroPaciente.direccion());
    }

    public void actualizarPaciente(DatoActualizarPaciente datoActualizarPaciente) {
        if (datoActualizarPaciente.nombre() != null) {
            this.nombre = datoActualizarPaciente.nombre();
        }
        if (datoActualizarPaciente.direccion() != null) {
            this.direccion = direccion.actualizarDatos(datoActualizarPaciente.direccion());
        }
    }

    public void desactivarPaciente() {
        this.activo = false;
    }
}
