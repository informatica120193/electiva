package co.com.publishapp.web.rest;

import co.com.publishapp.domain.Publicacion;
import co.com.publishapp.repository.PublicacionRepository;
import co.com.publishapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link co.com.publishapp.domain.Publicacion}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PublicacionResource {

    private final Logger log = LoggerFactory.getLogger(PublicacionResource.class);

    private static final String ENTITY_NAME = "publicacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PublicacionRepository publicacionRepository;

    public PublicacionResource(PublicacionRepository publicacionRepository) {
        this.publicacionRepository = publicacionRepository;
    }

    /**
     * {@code POST  /publicacions} : Create a new publicacion.
     *
     * @param publicacion the publicacion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new publicacion, or with status {@code 400 (Bad Request)} if the publicacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/publicacions")
    public ResponseEntity<Publicacion> createPublicacion(@RequestBody Publicacion publicacion) throws URISyntaxException {
        log.debug("REST request to save Publicacion : {}", publicacion);
        if (publicacion.getId() != null) {
            throw new BadRequestAlertException("A new publicacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Publicacion result = publicacionRepository.save(publicacion);
        return ResponseEntity.created(new URI("/api/publicacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /publicacions} : Updates an existing publicacion.
     *
     * @param publicacion the publicacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicacion,
     * or with status {@code 400 (Bad Request)} if the publicacion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the publicacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/publicacions")
    public ResponseEntity<Publicacion> updatePublicacion(@RequestBody Publicacion publicacion) throws URISyntaxException {
        log.debug("REST request to update Publicacion : {}", publicacion);
        if (publicacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Publicacion result = publicacionRepository.save(publicacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publicacion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /publicacions} : get all the publicacions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of publicacions in body.
     */
    @GetMapping("/publicacions")
    public List<Publicacion> getAllPublicacions() {
        log.debug("REST request to get all Publicacions");
        return publicacionRepository.findAll();
    }

    /**
     * {@code GET  /publicacions/:id} : get the "id" publicacion.
     *
     * @param id the id of the publicacion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the publicacion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/publicacions/{id}")
    public ResponseEntity<Publicacion> getPublicacion(@PathVariable Long id) {
        log.debug("REST request to get Publicacion : {}", id);
        Optional<Publicacion> publicacion = publicacionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(publicacion);
    }

    /**
     * {@code DELETE  /publicacions/:id} : delete the "id" publicacion.
     *
     * @param id the id of the publicacion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/publicacions/{id}")
    public ResponseEntity<Void> deletePublicacion(@PathVariable Long id) {
        log.debug("REST request to delete Publicacion : {}", id);
        publicacionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
