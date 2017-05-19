package app.repository;

import app.domain.Artefacto;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Artefacto entity.
 */
@SuppressWarnings("unused")
public interface ArtefactoRepository extends JpaRepository<Artefacto,Long> {
	List<Artefacto> findByVersionId(Long id);

}
