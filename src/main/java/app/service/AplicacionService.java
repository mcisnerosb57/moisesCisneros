package app.service;

import app.domain.Aplicacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Aplicacion.
 */
public interface AplicacionService {

    /**
     * Save a aplicacion.
     *
     * @param aplicacion the entity to save
     * @return the persisted entity
     */
    Aplicacion save(Aplicacion aplicacion);

    /**
     *  Get all the aplicacions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Aplicacion> findAll(Pageable pageable);

    /**
     *  Get the "id" aplicacion.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Aplicacion findOne(Long id);

    /**
     *  Delete the "id" aplicacion.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
