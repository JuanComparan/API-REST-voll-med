package med.voll.api.domain.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private Boolean activo;
    private String documento;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    private Direccion direccion;

    public Medico(DatoRegistroMedico datoRegistroMedico) {
        this.activo = true;
        this.nombre = datoRegistroMedico.nombre();
        this.email = datoRegistroMedico.email();
        this.telefono = datoRegistroMedico.telefono();
        this.documento = datoRegistroMedico.documento();
        this.especialidad = datoRegistroMedico.especialidad();
        this.direccion = new Direccion(datoRegistroMedico.direccion());
    }

    public void actualizarDatos(DatoActualizarMedico datoActualizarMedico) {
        if (datoActualizarMedico.nombre() != null) {
            this.nombre = datoActualizarMedico.nombre();
        }
        if (datoActualizarMedico.documento() != null) {
            this.documento = datoActualizarMedico.documento();
        }
        if (datoActualizarMedico.direccion() != null) {
            this.direccion = direccion.actualizarDatos(datoActualizarMedico.direccion());
        }
    }

    public void desactivarMedico() {
        this.activo = false;
    }
}
