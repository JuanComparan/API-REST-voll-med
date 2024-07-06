package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.*;
import med.voll.api.domain.medico.DatosListaMedicos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private AgendaConsultaService service;

    @Autowired
    private ConsultaRepository consultaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity agendarConsulta(@RequestBody @Valid DatosAgendarConsulta datosAgendarConsulta) {
        var response = service.agendarConsulta(datosAgendarConsulta);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<DatosDetalleConsulta>> listaConsultas(@PageableDefault(size = 3) Pageable paginacion){
        return ResponseEntity.ok(consultaRepository.findByActivoTrue(paginacion).map(DatosDetalleConsulta::new));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelarConsulta(@RequestBody @Valid DatosCancelamientoConsulta datosCancelamientoConsulta) {
        service.cancelar(datosCancelamientoConsulta);

        return ResponseEntity.noContent().build();

    }
}
