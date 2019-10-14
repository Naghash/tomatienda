package com.tomatienda.myapp.service;

import com.tomatienda.myapp.domain.Empleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Empleado.
 */
public interface EmpleadoService {

    /**
     * Save a empleado.
     *
     * @param empleado the entity to save
     * @return the persisted entity
     */
    Empleado save(Empleado empleado);

    /**
     * Get all the empleados.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Empleado> findAll(Pageable pageable);

    /**
     * Get the "id" empleado.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Empleado findOne(Long id);

    /**
     * Delete the "id" empleado.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
