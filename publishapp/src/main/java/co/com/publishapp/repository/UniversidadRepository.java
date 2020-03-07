package co.com.publishapp.repository;

import co.com.publishapp.domain.Universidad;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Universidad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UniversidadRepository extends JpaRepository<Universidad, Long> {

}
