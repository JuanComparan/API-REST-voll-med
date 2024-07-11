package med.voll.api.domain.medico;

import jakarta.persistence.EntityManager;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.DatosRegistroPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("Deberia retornar nulo cuando el medico se encuentre con otro paciente en ese horario")
    void seleccionarMedicoConEspecialidadEnFechaEscenario1() {

        var proximoLunes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var medico= registrarMedico("JUAN", "juan.22@gmail.com","123456",Especialidad.ORTOPEDIA);
        var paciente= registrarPaciente("ANAHI", "anahi.22@gmail.com", Especialidad.ORTOPEDIA);

        registrarConsulta(medico, paciente, proximoLunes10H);

        var medicoLibre = medicoRepository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.ORTOPEDIA, proximoLunes10H);

        assertThat(medicoLibre).isNull();
    }

    @Test
    @DisplayName("Deberia retornar un medico cuando realice la consulta en la base de datos para ese horario")
    void seleccionarMedicoConEspecialidadEnFechaEscenario2() {

        var proximoLunes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var medico= registrarMedico("JUAN", "juan.22@gmail.com","123456",Especialidad.ORTOPEDIA);

        var medicoLibre = medicoRepository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.ORTOPEDIA, proximoLunes10H);

        assertThat(medicoLibre).isEqualTo(medico);
    }

    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
        em.persist(new Consulta(null, medico, paciente, fecha, null, null));
    }

    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad) {
        var medico = new Medico(datosMedico(nombre, email, documento, especialidad));
        em.persist(medico);
        return medico;
    }

    private Paciente registrarPaciente(String nombre, String email, Especialidad consulta){
        var paciente = new Paciente(datoPaciente(nombre, email, consulta));
        em.persist(paciente);
        return paciente;
    }

    private DatoRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad){
        return new DatoRegistroMedico(
                nombre,
                email,
                "654654812",
                documento,
                especialidad,
                datosDireccion()
        );
    }

    private DatosRegistroPaciente datoPaciente(String nombre, String email, Especialidad consulta){
        return new DatosRegistroPaciente(
                nombre,
                email,
                "54685487",
                consulta,
                datosDireccion()
        );
    }

    private DatosDireccion datosDireccion(){
        return new DatosDireccion(
            "azul",
                "2",
                "dos",
                "165",
                "12"
        );
    }
}

