package app.service.impl;

import app.service.AplicacionService;
import app.domain.Aplicacion;
import app.repository.AplicacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Aplicacion.
 */
@Service
@Transactional
public class AplicacionServiceImpl implements AplicacionService{

    private final Logger log = LoggerFactory.getLogger(AplicacionServiceImpl.class);
    
    @Inject
    private AplicacionRepository aplicacionRepository;

    /**
     * Save a aplicacion.
     *
     * @param aplicacion the entity to save
     * @return the persisted entity
     */
    public Aplicacion save(Aplicacion aplicacion) {
        log.debug("Request to save Aplicacion : {}", aplicacion);
        Aplicacion result = aplicacionRepository.save(aplicacion);
        return result;
    }

    /**
     *  Get all the aplicacions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Aplicacion> findAll(Pageable pageable) {
        log.debug("Request to get all Aplicacions");
        Page<Aplicacion> result = aplicacionRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one aplicacion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Aplicacion findOne(Long id) {
        log.debug("Request to get Aplicacion : {}", id);
        Aplicacion aplicacion = aplicacionRepository.findOne(id);
        return aplicacion;
    }

    /**
     *  Delete the  aplicacion by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Aplicacion : {}", id);
        aplicacionRepository.delete(id);
    }
}
