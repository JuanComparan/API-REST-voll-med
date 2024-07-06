package med.voll.api.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    public ResponseEntity<DatoRespuestaPaciente> registroPaciente(@RequestBody @Valid DatosRegistroPaciente datosRegistroPaciente,
                                                                  UriComponentsBuilder uriComponentsBuilder){
        Paciente paciente = pacienteRepository.save(new Paciente(datosRegistroPaciente));
        DatoRespuestaPaciente datoRespuestaPaciente = new DatoRespuestaPaciente(paciente.getId(), paciente.getNombre(),
                paciente.getEmail(), paciente.getTelefono(), new DatosDireccion(paciente.getDireccion().getCalle(),
                paciente.getDireccion().getDistrito(), paciente.getDireccion().getCiudad(),
                paciente.getDireccion().getNumero(), paciente.getDireccion().getComplemento()));
        URI url = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(url).body(datoRespuestaPaciente);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaPacientes>> listarPacientes(@PageableDefault(size = 2, sort = "nombre") Pageable paginacion) {
        //return pacienteRepository.findAll(paginacion).map(DatosListaPacientes::new);
        return ResponseEntity.ok(pacienteRepository.findByActivoTrue(paginacion).map(DatosListaPacientes::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarPaciente(@RequestBody @Valid DatoActualizarPaciente datoActualizarPaciente){
        Paciente paciente = pacienteRepository.getReferenceById(datoActualizarPaciente.id());
        paciente.actualizarPaciente(datoActualizarPaciente);
        return ResponseEntity.ok(new DatoRespuestaPaciente(paciente.getId(), paciente.getNombre(),
                paciente.getEmail(), paciente.getTelefono(), new DatosDireccion(paciente.getDireccion().getCalle(),
                        paciente.getDireccion().getDistrito(), paciente.getDireccion().getCiudad(),
                        paciente.getDireccion().getNumero(), paciente.getDireccion().getComplemento())));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarPaciente(@PathVariable Long id){
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.desactivarPaciente();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatoRespuestaPaciente> retornarDatosPaciente(@PathVariable Long id){
        Paciente paciente = pacienteRepository.getReferenceById(id);
        DatoRespuestaPaciente datosPaciente = new DatoRespuestaPaciente(paciente.getId(), paciente.getNombre(),
                paciente.getEmail(), paciente.getTelefono(), new DatosDireccion(paciente.getDireccion().getCalle(),
                paciente.getDireccion().getDistrito(), paciente.getDireccion().getCiudad(),
                paciente.getDireccion().getNumero(), paciente.getDireccion().getComplemento()));
        return ResponseEntity.ok(datosPaciente);
    }
}
