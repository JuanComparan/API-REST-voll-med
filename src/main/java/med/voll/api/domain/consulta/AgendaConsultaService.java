package med.voll.api.domain.consulta;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.validaciones.ValidadorDeCancelamientoConsulta;
import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaConsultaService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    List<ValidadorDeConsultas> validadores;

    @Autowired
    List<ValidadorDeCancelamientoConsulta> validadoresCancelamiento;

    public DatosDetalleConsulta agendarConsulta(DatosAgendarConsulta datosAgendarConsulta) {

        if(!pacienteRepository.findById(datosAgendarConsulta.idPaciente()).isPresent()) {
            throw new ValidacionDeIntegridad("este id para el paciente no fue encontrado");
        }

        if(datosAgendarConsulta.idMedico() != null && !medicoRepository.existsById(datosAgendarConsulta.idMedico())) {
            throw new ValidacionDeIntegridad("este id para el medico no fue encontrado");
        }

        validadores.forEach(v->v.validar(datosAgendarConsulta));

        var paciente = pacienteRepository.findById(datosAgendarConsulta.idPaciente()).get();

        var medico = seleccionarMedico(datosAgendarConsulta);

        if(medico==null) {
            throw new ValidacionDeIntegridad("No existen medicos disponibles para esta especialidad y horario");
        }

        var consulta = new Consulta(medico, paciente, datosAgendarConsulta.fecha());

        consultaRepository.save(consulta);

        return new DatosDetalleConsulta(consulta);
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datosAgendarConsulta) {
        if(datosAgendarConsulta.idMedico() != null) {
            return medicoRepository.getReferenceById(datosAgendarConsulta.idMedico());
        }
        if(datosAgendarConsulta.especialidad() == null) {
            throw new ValidacionDeIntegridad("debe seleccionarse una especialidad valida para el medico");
        }

        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datosAgendarConsulta.especialidad()
                , datosAgendarConsulta.fecha());
    }

    public void cancelar(DatosCancelamientoConsulta datosCancelamientoConsulta){
        if(!consultaRepository.existsById(datosCancelamientoConsulta.idConsulta())){
            throw new ValidacionDeIntegridad("Id de consulta no encontrado");
        }

        validadoresCancelamiento.forEach(v -> v.validarCancelamiento(datosCancelamientoConsulta));

        var consulta = consultaRepository.getReferenceById(datosCancelamientoConsulta.idConsulta());
        consulta.cancelar(datosCancelamientoConsulta.motivo());
    }

}
