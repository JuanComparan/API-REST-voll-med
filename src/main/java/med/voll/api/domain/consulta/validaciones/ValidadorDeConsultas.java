package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.DatosAgendarConsulta;

public interface ValidadorDeConsultas {
    public void validar(DatosAgendarConsulta datosAgendarConsulta);
}
