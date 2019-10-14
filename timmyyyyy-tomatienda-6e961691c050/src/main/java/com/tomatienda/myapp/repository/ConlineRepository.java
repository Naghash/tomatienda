package com.tomatienda.myapp.repository;

import com.tomatienda.myapp.domain.Conline;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Conline entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConlineRepository extends JpaRepository<Conline, Long> {
    @Query("select distinct conline from Conline conline left join fetch conline.productos")
    List<Conline> findAllWithEagerRelationships();

    @Query("select conline from Conline conline left join fetch conline.productos where conline.id =:id")
    Conline findOneWithEagerRelationships(@Param("id") Long id);

}
