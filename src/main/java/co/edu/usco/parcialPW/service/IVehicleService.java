
package co.edu.usco.parcialPW.service;

import co.edu.usco.parcialPW.persistence.entity.Vehicle;
import java.util.List;
import java.util.Optional;

public interface IVehicleService {
    
    Optional<Vehicle> findById(Long id);
    
    List<Vehicle> findAll();
    
    void save (Vehicle vehicle);
    
    void delete (Long id);
    
    Optional<Vehicle> findByUbication(String ubication);
    
    Optional<Vehicle> findByPlaca(String placa);
}
