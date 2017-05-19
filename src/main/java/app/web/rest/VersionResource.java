package app.web.rest;

import com.codahale.metrics.annotation.Timed;
import app.domain.Version;
import app.service.VersionService;
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
 * REST controller for managing Version.
 */
@RestController
@RequestMapping("/api")
public class VersionResource {

    private final Logger log = LoggerFactory.getLogger(VersionResource.class);
        
    @Inject
    private VersionService versionService;

    /**
     * POST  /versions : Create a new version.
     *
     * @param version the version to create
     * @return the ResponseEntity with status 201 (Created) and with body the new version, or with status 400 (Bad Request) if the version has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/versions")
    @Timed
    public ResponseEntity<Version> createVersion(@Valid @RequestBody Version version) throws URISyntaxException {
        log.debug("REST request to save Version : {}", version);
        if (version.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("version", "idexists", "A new version cannot already have an ID")).body(null);
        }
        Version result = versionService.save(version);
        return ResponseEntity.created(new URI("/api/versions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("version", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /versions : Updates an existing version.
     *
     * @param version the version to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated version,
     * or with status 400 (Bad Request) if the version is not valid,
     * or with status 500 (Internal Server Error) if the version couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/versions")
    @Timed
    public ResponseEntity<Version> updateVersion(@Valid @RequestBody Version version) throws URISyntaxException {
        log.debug("REST request to update Version : {}", version);
        if (version.getId() == null) {
            return createVersion(version);
        }
        Version result = versionService.save(version);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("version", version.getId().toString()))
            .body(result);
    }

    /**
     * GET  /versions : get all the versions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of versions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/versions")
    @Timed
    public ResponseEntity<List<Version>> getAllVersions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Versions");
        Page<Version> page = versionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/versions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /versions/:id : get the "id" version.
     *
     * @param id the id of the version to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the version, or with status 404 (Not Found)
     */
    @GetMapping("/versions/{id}")
    @Timed
    public ResponseEntity<Version> getVersion(@PathVariable Long id) {
        log.debug("REST request to get Version : {}", id);
        Version version = versionService.findOne(id);
        return Optional.ofNullable(version)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /versions/:id : delete the "id" version.
     *
     * @param id the id of the version to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/versions/{id}")
    @Timed
    public ResponseEntity<Void> deleteVersion(@PathVariable Long id) {
        log.debug("REST request to delete Version : {}", id);
        versionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("version", id.toString())).build();
    }

}
