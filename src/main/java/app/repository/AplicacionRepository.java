package app.repository;

import app.domain.Aplicacion;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Aplicacion entity.
 */
@SuppressWarnings("unused")
public interface AplicacionRepository extends JpaRepository<Aplicacion,Long> {

}
