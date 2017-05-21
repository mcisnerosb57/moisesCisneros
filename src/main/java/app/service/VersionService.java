package app.service;

import app.domain.Version;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Version.
 */
public interface VersionService {

    /**
     * Save a version.
     *
     * @param version the entity to save
     * @return the persisted entity
     */
    Version save(Version version);

    /**
     *  Get all the versions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Version> findAll(Pageable pageable);

    /**
     *  Get the "id" version.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Version findOne(Long id);

    /**
     *  Delete the "id" version.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
    
    
    List<Version> findAllVersion(Long id);
    List<Version> crearVersionbyVersion(Long id, String versionNueva);
}
