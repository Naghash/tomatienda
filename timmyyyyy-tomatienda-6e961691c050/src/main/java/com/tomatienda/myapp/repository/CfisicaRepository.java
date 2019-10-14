package com.tomatienda.myapp.repository;

import com.tomatienda.myapp.domain.Cfisica;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Cfisica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CfisicaRepository extends JpaRepository<Cfisica, Long> {
    @Query("select distinct cfisica from Cfisica cfisica left join fetch cfisica.productos")
    List<Cfisica> findAllWithEagerRelationships();

    @Query("select cfisica from Cfisica cfisica left join fetch cfisica.productos where cfisica.id =:id")
    Cfisica findOneWithEagerRelationships(@Param("id") Long id);

}
