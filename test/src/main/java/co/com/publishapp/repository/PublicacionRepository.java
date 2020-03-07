package co.com.publishapp.repository;

import co.com.publishapp.domain.Publicacion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Publicacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {

}
