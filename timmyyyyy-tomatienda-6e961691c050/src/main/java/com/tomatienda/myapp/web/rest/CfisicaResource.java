package com.tomatienda.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tomatienda.myapp.domain.Cfisica;
import com.tomatienda.myapp.service.CfisicaService;
import com.tomatienda.myapp.web.rest.errors.BadRequestAlertException;
import com.tomatienda.myapp.web.rest.util.HeaderUtil;
import com.tomatienda.myapp.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Cfisica.
 */
@RestController
@RequestMapping("/api")
public class CfisicaResource {

    private final Logger log = LoggerFactory.getLogger(CfisicaResource.class);

    private static final String ENTITY_NAME = "cfisica";

    private final CfisicaService cfisicaService;

    public CfisicaResource(CfisicaService cfisicaService) {
        this.cfisicaService = cfisicaService;
    }

    /**
     * POST  /cfisicas : Create a new cfisica.
     *
     * @param cfisica the cfisica to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cfisica, or with status 400 (Bad Request) if the cfisica has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cfisicas")
    @Timed
    public ResponseEntity<Cfisica> createCfisica(@RequestBody Cfisica cfisica) throws URISyntaxException {
        log.debug("REST request to save Cfisica : {}", cfisica);
        if (cfisica.getId() != null) {
            throw new BadRequestAlertException("A new cfisica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cfisica result = cfisicaService.save(cfisica);
        return ResponseEntity.created(new URI("/api/cfisicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cfisicas : Updates an existing cfisica.
     *
     * @param cfisica the cfisica to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cfisica,
     * or with status 400 (Bad Request) if the cfisica is not valid,
     * or with status 500 (Internal Server Error) if the cfisica couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cfisicas")
    @Timed
    public ResponseEntity<Cfisica> updateCfisica(@RequestBody Cfisica cfisica) throws URISyntaxException {
        log.debug("REST request to update Cfisica : {}", cfisica);
        if (cfisica.getId() == null) {
            return createCfisica(cfisica);
        }
        Cfisica result = cfisicaService.save(cfisica);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cfisica.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cfisicas : get all the cfisicas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cfisicas in body
     */
    @GetMapping("/cfisicas")
    @Timed
    public ResponseEntity<List<Cfisica>> getAllCfisicas(Pageable pageable) {
        log.debug("REST request to get a page of Cfisicas");
        Page<Cfisica> page = cfisicaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cfisicas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cfisicas/:id : get the "id" cfisica.
     *
     * @param id the id of the cfisica to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cfisica, or with status 404 (Not Found)
     */
    @GetMapping("/cfisicas/{id}")
    @Timed
    public ResponseEntity<Cfisica> getCfisica(@PathVariable Long id) {
        log.debug("REST request to get Cfisica : {}", id);
        Cfisica cfisica = cfisicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cfisica));
    }

    /**
     * DELETE  /cfisicas/:id : delete the "id" cfisica.
     *
     * @param id the id of the cfisica to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cfisicas/{id}")
    @Timed
    public ResponseEntity<Void> deleteCfisica(@PathVariable Long id) {
        log.debug("REST request to delete Cfisica : {}", id);
        cfisicaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
