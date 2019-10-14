package com.tomatienda.myapp.service;

import com.tomatienda.myapp.domain.Cfisica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Cfisica.
 */
public interface CfisicaService {

    /**
     * Save a cfisica.
     *
     * @param cfisica the entity to save
     * @return the persisted entity
     */
    Cfisica save(Cfisica cfisica);

    /**
     * Get all the cfisicas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Cfisica> findAll(Pageable pageable);

    /**
     * Get the "id" cfisica.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Cfisica findOne(Long id);

    /**
     * Delete the "id" cfisica.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
