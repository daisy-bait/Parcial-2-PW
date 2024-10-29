
package co.edu.usco.parcialPW.persistence.repository;

import co.edu.usco.parcialPW.persistence.entity.Vehicle;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>{
    
    @Query(value = "SELECT v FROM Vehicle v WHERE v.ubicacion = :ubicacion AND v.salida IS NULL", nativeQuery = false)
    Optional<Vehicle> findByUbication(@Param("ubicacion") String ubicacion);
    
    @Query(value = "SELECT v FROM Vehicle v WHERE v.placa = :placa AND v.salida IS NULL", nativeQuery = false)
    Optional<Vehicle> findByPlaca(@Param("placa") String placa);
}
