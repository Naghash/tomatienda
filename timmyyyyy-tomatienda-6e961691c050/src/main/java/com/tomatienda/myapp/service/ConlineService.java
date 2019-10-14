package com.tomatienda.myapp.service;

import com.tomatienda.myapp.domain.Conline;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Conline.
 */
public interface ConlineService {

    /**
     * Save a conline.
     *
     * @param conline the entity to save
     * @return the persisted entity
     */
    Conline save(Conline conline);

    /**
     * Get all the conlines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Conline> findAll(Pageable pageable);

    /**
     * Get the "id" conline.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Conline findOne(Long id);

    /**
     * Delete the "id" conline.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
