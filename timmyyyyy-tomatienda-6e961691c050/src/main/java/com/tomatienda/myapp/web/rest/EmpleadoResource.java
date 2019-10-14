package com.tomatienda.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tomatienda.myapp.domain.Empleado;
import com.tomatienda.myapp.service.EmpleadoService;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Empleado.
 */
@RestController
@RequestMapping("/api")
public class EmpleadoResource {

    private final Logger log = LoggerFactory.getLogger(EmpleadoResource.class);

    private static final String ENTITY_NAME = "empleado";

    private final EmpleadoService empleadoService;

    public EmpleadoResource(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    /**
     * POST  /empleados : Create a new empleado.
     *
     * @param empleado the empleado to create
     * @return the ResponseEntity with status 201 (Created) and with body the new empleado, or with status 400 (Bad Request) if the empleado has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/empleados")
    @Timed
    public ResponseEntity<Empleado> createEmpleado(@Valid @RequestBody Empleado empleado) throws URISyntaxException {
        log.debug("REST request to save Empleado : {}", empleado);
        if (empleado.getId() != null) {
            throw new BadRequestAlertException("A new empleado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Empleado result = empleadoService.save(empleado);
        return ResponseEntity.created(new URI("/api/empleados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /empleados : Updates an existing empleado.
     *
     * @param empleado the empleado to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated empleado,
     * or with status 400 (Bad Request) if the empleado is not valid,
     * or with status 500 (Internal Server Error) if the empleado couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/empleados")
    @Timed
    public ResponseEntity<Empleado> updateEmpleado(@Valid @RequestBody Empleado empleado) throws URISyntaxException {
        log.debug("REST request to update Empleado : {}", empleado);
        if (empleado.getId() == null) {
            return createEmpleado(empleado);
        }
        Empleado result = empleadoService.save(empleado);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, empleado.getId().toString()))
            .body(result);
    }

    /**
     * GET  /empleados : get all the empleados.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of empleados in body
     */
    @GetMapping("/empleados")
    @Timed
    public ResponseEntity<List<Empleado>> getAllEmpleados(Pageable pageable) {
        log.debug("REST request to get a page of Empleados");
        Page<Empleado> page = empleadoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/empleados");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /empleados/:id : get the "id" empleado.
     *
     * @param id the id of the empleado to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the empleado, or with status 404 (Not Found)
     */
    @GetMapping("/empleados/{id}")
    @Timed
    public ResponseEntity<Empleado> getEmpleado(@PathVariable Long id) {
        log.debug("REST request to get Empleado : {}", id);
        Empleado empleado = empleadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(empleado));
    }

    /**
     * DELETE  /empleados/:id : delete the "id" empleado.
     *
     * @param id the id of the empleado to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/empleados/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmpleado(@PathVariable Long id) {
        log.debug("REST request to delete Empleado : {}", id);
        empleadoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
