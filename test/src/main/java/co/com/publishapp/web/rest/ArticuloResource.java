package co.com.publishapp.web.rest;

import co.com.publishapp.domain.Articulo;
import co.com.publishapp.repository.ArticuloRepository;
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
 * REST controller for managing {@link co.com.publishapp.domain.Articulo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ArticuloResource {

    private final Logger log = LoggerFactory.getLogger(ArticuloResource.class);

    private static final String ENTITY_NAME = "articulo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArticuloRepository articuloRepository;

    public ArticuloResource(ArticuloRepository articuloRepository) {
        this.articuloRepository = articuloRepository;
    }

    /**
     * {@code POST  /articulos} : Create a new articulo.
     *
     * @param articulo the articulo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new articulo, or with status {@code 400 (Bad Request)} if the articulo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/articulos")
    public ResponseEntity<Articulo> createArticulo(@RequestBody Articulo articulo) throws URISyntaxException {
        log.debug("REST request to save Articulo : {}", articulo);
        if (articulo.getId() != null) {
            throw new BadRequestAlertException("A new articulo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Articulo result = articuloRepository.save(articulo);
        return ResponseEntity.created(new URI("/api/articulos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /articulos} : Updates an existing articulo.
     *
     * @param articulo the articulo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articulo,
     * or with status {@code 400 (Bad Request)} if the articulo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the articulo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/articulos")
    public ResponseEntity<Articulo> updateArticulo(@RequestBody Articulo articulo) throws URISyntaxException {
        log.debug("REST request to update Articulo : {}", articulo);
        if (articulo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Articulo result = articuloRepository.save(articulo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, articulo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /articulos} : get all the articulos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articulos in body.
     */
    @GetMapping("/articulos")
    public List<Articulo> getAllArticulos(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Articulos");
        return articuloRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /articulos/:id} : get the "id" articulo.
     *
     * @param id the id of the articulo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the articulo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/articulos/{id}")
    public ResponseEntity<Articulo> getArticulo(@PathVariable Long id) {
        log.debug("REST request to get Articulo : {}", id);
        Optional<Articulo> articulo = articuloRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(articulo);
    }

    /**
     * {@code DELETE  /articulos/:id} : delete the "id" articulo.
     *
     * @param id the id of the articulo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/articulos/{id}")
    public ResponseEntity<Void> deleteArticulo(@PathVariable Long id) {
        log.debug("REST request to delete Articulo : {}", id);
        articuloRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
