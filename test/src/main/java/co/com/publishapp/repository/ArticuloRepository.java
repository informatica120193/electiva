package co.com.publishapp.repository;

import co.com.publishapp.domain.Articulo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Articulo entity.
 */
@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Long> {

    @Query(value = "select distinct articulo from Articulo articulo left join fetch articulo.publicacions left join fetch articulo.autors",
        countQuery = "select count(distinct articulo) from Articulo articulo")
    Page<Articulo> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct articulo from Articulo articulo left join fetch articulo.publicacions left join fetch articulo.autors")
    List<Articulo> findAllWithEagerRelationships();

    @Query("select articulo from Articulo articulo left join fetch articulo.publicacions left join fetch articulo.autors where articulo.id =:id")
    Optional<Articulo> findOneWithEagerRelationships(@Param("id") Long id);

}
