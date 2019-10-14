package com.tomatienda.myapp.service.impl;

import com.tomatienda.myapp.service.CfisicaService;
import com.tomatienda.myapp.domain.Cfisica;
import com.tomatienda.myapp.repository.CfisicaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Cfisica.
 */
@Service
@Transactional
public class CfisicaServiceImpl implements CfisicaService {

    private final Logger log = LoggerFactory.getLogger(CfisicaServiceImpl.class);

    private final CfisicaRepository cfisicaRepository;

    public CfisicaServiceImpl(CfisicaRepository cfisicaRepository) {
        this.cfisicaRepository = cfisicaRepository;
    }

    /**
     * Save a cfisica.
     *
     * @param cfisica the entity to save
     * @return the persisted entity
     */
    @Override
    public Cfisica save(Cfisica cfisica) {
        log.debug("Request to save Cfisica : {}", cfisica);
        return cfisicaRepository.save(cfisica);
    }

    /**
     * Get all the cfisicas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Cfisica> findAll(Pageable pageable) {
        log.debug("Request to get all Cfisicas");
        return cfisicaRepository.findAll(pageable);
    }

    /**
     * Get one cfisica by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Cfisica findOne(Long id) {
        log.debug("Request to get Cfisica : {}", id);
        return cfisicaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the cfisica by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cfisica : {}", id);
        cfisicaRepository.delete(id);
    }
}
