package com.tomatienda.myapp.service.impl;

import com.tomatienda.myapp.service.EmpleadoService;
import com.tomatienda.myapp.domain.Empleado;
import com.tomatienda.myapp.repository.EmpleadoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Empleado.
 */
@Service
@Transactional
public class EmpleadoServiceImpl implements EmpleadoService {

    private final Logger log = LoggerFactory.getLogger(EmpleadoServiceImpl.class);

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    /**
     * Save a empleado.
     *
     * @param empleado the entity to save
     * @return the persisted entity
     */
    @Override
    public Empleado save(Empleado empleado) {
        log.debug("Request to save Empleado : {}", empleado);
        return empleadoRepository.save(empleado);
    }

    /**
     * Get all the empleados.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Empleado> findAll(Pageable pageable) {
        log.debug("Request to get all Empleados");
        return empleadoRepository.findAll(pageable);
    }

    /**
     * Get one empleado by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Empleado findOne(Long id) {
        log.debug("Request to get Empleado : {}", id);
        return empleadoRepository.findOne(id);
    }

    /**
     * Delete the empleado by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Empleado : {}", id);
        empleadoRepository.delete(id);
    }
}
