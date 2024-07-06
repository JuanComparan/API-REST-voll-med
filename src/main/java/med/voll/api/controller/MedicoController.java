package med.voll.api.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    @Operation(
            summary = "Resgistra un medico en la base de datos",
            description = "",
            tags = {"consulta, post"}
    )
    public ResponseEntity<DatoRespuestaMedico> registraMedico(@RequestBody @Valid DatoRegistroMedico datoRegistroMedico, UriComponentsBuilder uriComponentsBuilder){
        Medico medico = medicoRepository.save(new Medico(datoRegistroMedico));
        DatoRespuestaMedico datoRespuestaMedico = new DatoRespuestaMedico(medico.getId(),
                medico.getNombre(),medico.getEmail(), medico.getTelefono(), medico.getDocumento(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()), medico.getActivo());
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datoRespuestaMedico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaMedicos>> listaMedicos(@PageableDefault(size = 3) Pageable paginacion){
        //return medicoRepository.findAll(paginacion).map(DatosListaMedicos::new);
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListaMedicos::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatoActualizarMedico datoActualizarMedico){
        Medico medico = medicoRepository.getReferenceById(datoActualizarMedico.id());
        medico.actualizarDatos(datoActualizarMedico);
        return ResponseEntity.ok(new DatoRespuestaMedico(medico.getId(),
                medico.getNombre(),medico.getEmail(), medico.getTelefono(), medico.getDocumento(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()), medico.getActivo()));
    }

    // DELETE LOGICO

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatoRespuestaMedico> retornarDatosMedicos(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        DatoRespuestaMedico datosMedico = new DatoRespuestaMedico(medico.getId(),
                medico.getNombre(),medico.getEmail(), medico.getTelefono(), medico.getDocumento(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()), medico.getActivo());
        return ResponseEntity.ok(datosMedico);
    }


// DELETE EN BASE DE DATOS
//    public void eliminarMedico(@PathVariable Long id){
//        Medico medico = medicoRepository.getReferenceById(id);
//        medicoRepository.delete(medico);
//    }
}
