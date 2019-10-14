package com.tomatienda.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tomatienda.myapp.domain.Conline;
import com.tomatienda.myapp.service.ConlineService;
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
 * REST controller for managing Conline.
 */
@RestController
@RequestMapping("/api")
public class ConlineResource {

    private final Logger log = LoggerFactory.getLogger(ConlineResource.class);

    private static final String ENTITY_NAME = "conline";

    private final ConlineService conlineService;

    public ConlineResource(ConlineService conlineService) {
        this.conlineService = conlineService;
    }

    /**
     * POST  /conlines : Create a new conline.
     *
     * @param conline the conline to create
     * @return the ResponseEntity with status 201 (Created) and with body the new conline, or with status 400 (Bad Request) if the conline has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/conlines")
    @Timed
    public ResponseEntity<Conline> createConline(@RequestBody Conline conline) throws URISyntaxException {
        log.debug("REST request to save Conline : {}", conline);
        if (conline.getId() != null) {
            throw new BadRequestAlertException("A new conline cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Conline result = conlineService.save(conline);
        return ResponseEntity.created(new URI("/api/conlines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /conlines : Updates an existing conline.
     *
     * @param conline the conline to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated conline,
     * or with status 400 (Bad Request) if the conline is not valid,
     * or with status 500 (Internal Server Error) if the conline couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/conlines")
    @Timed
    public ResponseEntity<Conline> updateConline(@RequestBody Conline conline) throws URISyntaxException {
        log.debug("REST request to update Conline : {}", conline);
        if (conline.getId() == null) {
            return createConline(conline);
        }
        Conline result = conlineService.save(conline);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, conline.getId().toString()))
            .body(result);
    }

    /**
     * GET  /conlines : get all the conlines.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of conlines in body
     */
    @GetMapping("/conlines")
    @Timed
    public ResponseEntity<List<Conline>> getAllConlines(Pageable pageable) {
        log.debug("REST request to get a page of Conlines");
        Page<Conline> page = conlineService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/conlines");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /conlines/:id : get the "id" conline.
     *
     * @param id the id of the conline to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the conline, or with status 404 (Not Found)
     */
    @GetMapping("/conlines/{id}")
    @Timed
    public ResponseEntity<Conline> getConline(@PathVariable Long id) {
        log.debug("REST request to get Conline : {}", id);
        Conline conline = conlineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(conline));
    }

    /**
     * DELETE  /conlines/:id : delete the "id" conline.
     *
     * @param id the id of the conline to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/conlines/{id}")
    @Timed
    public ResponseEntity<Void> deleteConline(@PathVariable Long id) {
        log.debug("REST request to delete Conline : {}", id);
        conlineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
