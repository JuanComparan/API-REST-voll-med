package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoConConsulta implements ValidadorDeConsultas{

    @Autowired
    private ConsultaRepository repository;

    public void validar(DatosAgendarConsulta datosAgendarConsulta){
        if(datosAgendarConsulta.idMedico()==null){
            return;
        }
        var medicoConConsulta = repository.existsByMedicoIdAndFecha(datosAgendarConsulta.idMedico(), datosAgendarConsulta.fecha());
        if(medicoConConsulta){
            throw new ValidationException("Este medico ya tiene consulta en ese horario");
        }
    }

}
