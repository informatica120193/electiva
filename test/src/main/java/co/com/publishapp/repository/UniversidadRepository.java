package co.com.publishapp.repository;

import co.com.publishapp.domain.Universidad;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Universidad entity.
 */
@Repository
public interface UniversidadRepository extends JpaRepository<Universidad, Long> {

    @Query(value = "select distinct universidad from Universidad universidad left join fetch universidad.autors",
        countQuery = "select count(distinct universidad) from Universidad universidad")
    Page<Universidad> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct universidad from Universidad universidad left join fetch universidad.autors")
    List<Universidad> findAllWithEagerRelationships();

    @Query("select universidad from Universidad universidad left join fetch universidad.autors where universidad.id =:id")
    Optional<Universidad> findOneWithEagerRelationships(@Param("id") Long id);

}
