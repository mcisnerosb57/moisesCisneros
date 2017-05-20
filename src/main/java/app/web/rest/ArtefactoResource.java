package app.web.rest;

import com.codahale.metrics.annotation.Timed;
import app.domain.Artefacto;
import app.service.ArtefactoService;
import app.web.rest.util.HeaderUtil;
import app.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Artefacto.
 */
@RestController
@RequestMapping("/api")
public class ArtefactoResource {

    private final Logger log = LoggerFactory.getLogger(ArtefactoResource.class);
        
    @Inject
    private ArtefactoService artefactoService;

    /**
     * POST  /artefactos : Create a new artefacto.
     *
     * @param artefacto the artefacto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new artefacto, or with status 400 (Bad Request) if the artefacto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/artefactos")
    @Timed
    public ResponseEntity<Artefacto> createArtefacto(@Valid @RequestBody Artefacto artefacto) throws URISyntaxException {
        log.debug("REST request to save Artefacto : {}", artefacto);
        if (artefacto.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("artefacto", "idexists", "A new artefacto cannot already have an ID")).body(null);
        }
        Artefacto result = artefactoService.save(artefacto);
        return ResponseEntity.created(new URI("/api/artefactos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("artefacto", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /artefactos : Updates an existing artefacto.
     *
     * @param artefacto the artefacto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated artefacto,
     * or with status 400 (Bad Request) if the artefacto is not valid,
     * or with status 500 (Internal Server Error) if the artefacto couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/artefactos")
    @Timed
    public ResponseEntity<Artefacto> updateArtefacto(@Valid @RequestBody Artefacto artefacto) throws URISyntaxException {
        log.debug("REST request to update Artefacto : {}", artefacto);
        if (artefacto.getId() == null) {
            return createArtefacto(artefacto);
        }
        Artefacto result = artefactoService.save(artefacto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("artefacto", artefacto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /artefactos : get all the artefactos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of artefactos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/artefactos")
    @Timed
    public ResponseEntity<List<Artefacto>> getAllArtefactos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Artefactos");
        Page<Artefacto> page = artefactoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/artefactos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /artefactos/:id : get the "id" artefacto.
     *
     * @param id the id of the artefacto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the artefacto, or with status 404 (Not Found)
     */
    @GetMapping("/artefactos/{id}")
    @Timed
    public ResponseEntity<Artefacto> getArtefacto(@PathVariable Long id) {
        log.debug("REST request to get Artefacto : {}", id);
        Artefacto artefacto = artefactoService.findOne(id);
        return Optional.ofNullable(artefacto)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/artefactos/comprobar/{id}")
    @Timed
    public ResponseEntity<Artefacto> comprobarArtefacto(@PathVariable Long id) {
    	log.debug("REST request to get Artefacto : {}", id);
    	Artefacto artefacto = artefactoService.comprobarUno(id);
    	return Optional.ofNullable(artefacto)
    			.map(result -> new ResponseEntity<>(
    					result,
    					HttpStatus.OK))
    			.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/artefactos/version/{id}")
    @Timed
    public ResponseEntity<List<Artefacto>> getArtefactoxVersionId(@PathVariable Long id) {
    	log.debug("REST request to get Artefacto : {}", id);
    	 return new ResponseEntity<>(this.artefactoService.findAllbyVersion(id), HttpStatus.OK);
    }
    @GetMapping("/artefactos/versionComprobar/{id}")
    @Timed
    public ResponseEntity<List<Artefacto>> comprobarArtefactoxVersionId(@PathVariable Long id) {
    	log.debug("REST request to get Artefacto : {}", id);
    	return new ResponseEntity<>(this.artefactoService.comprobarAllbyVersion(id), HttpStatus.OK);
    }

    /**
     * DELETE  /artefactos/:id : delete the "id" artefacto.
     *
     * @param id the id of the artefacto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/artefactos/{id}")
    @Timed
    public ResponseEntity<Void> deleteArtefacto(@PathVariable Long id) {
        log.debug("REST request to delete Artefacto : {}", id);
        artefactoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("artefacto", id.toString())).build();
    }

}
