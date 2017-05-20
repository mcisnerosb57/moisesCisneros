package app.service;

import app.domain.Artefacto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Artefacto.
 */
public interface ArtefactoService {

    /**
     * Save a artefacto.
     *
     * @param artefacto the entity to save
     * @return the persisted entity
     */
    Artefacto save(Artefacto artefacto);

    /**
     *  Get all the artefactos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Artefacto> findAll(Pageable pageable);

    /**
     *  Get the "id" artefacto.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Artefacto findOne(Long id);

    /**
     *  Delete the "id" artefacto.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
    
    Artefacto comprobarUno(Long id);
    List<Artefacto> findAllbyVersion(Long id);

	List<Artefacto> comprobarAllbyVersion(Long id);
}
