package com.tomatienda.myapp.service.impl;

import com.tomatienda.myapp.service.ConlineService;
import com.tomatienda.myapp.domain.Conline;
import com.tomatienda.myapp.repository.ConlineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Conline.
 */
@Service
@Transactional
public class ConlineServiceImpl implements ConlineService {

    private final Logger log = LoggerFactory.getLogger(ConlineServiceImpl.class);

    private final ConlineRepository conlineRepository;

    public ConlineServiceImpl(ConlineRepository conlineRepository) {
        this.conlineRepository = conlineRepository;
    }

    /**
     * Save a conline.
     *
     * @param conline the entity to save
     * @return the persisted entity
     */
    @Override
    public Conline save(Conline conline) {
        log.debug("Request to save Conline : {}", conline);
        return conlineRepository.save(conline);
    }

    /**
     * Get all the conlines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Conline> findAll(Pageable pageable) {
        log.debug("Request to get all Conlines");
        return conlineRepository.findAll(pageable);
    }

    /**
     * Get one conline by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Conline findOne(Long id) {
        log.debug("Request to get Conline : {}", id);
        return conlineRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the conline by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Conline : {}", id);
        conlineRepository.delete(id);
    }
}
