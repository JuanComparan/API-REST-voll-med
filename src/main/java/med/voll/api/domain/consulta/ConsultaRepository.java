package med.voll.api.domain.consulta;

import aj.org.objectweb.asm.commons.Remapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {


    boolean existsByPacienteIdAndFechaBetween(Long idPaciente, LocalDateTime primerHorario, LocalDateTime ultimoHorario);

    boolean existsByMedicoIdAndFecha(Long idMedico, LocalDateTime fecha);

    Page<Consulta> findByActivoTrue(Pageable paginacion);
}
