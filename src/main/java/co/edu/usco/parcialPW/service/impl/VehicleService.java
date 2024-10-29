
package co.edu.usco.parcialPW.service.impl;

import co.edu.usco.parcialPW.persistence.entity.Vehicle;
import co.edu.usco.parcialPW.persistence.repository.TypeRepository;
import co.edu.usco.parcialPW.persistence.repository.VehicleRepository;
import co.edu.usco.parcialPW.service.IVehicleService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService implements IVehicleService{
    
    @Autowired
    private VehicleRepository vehRepo;
    
    @Autowired
    private TypeRepository typeRepo;
    
    @Override
    public Optional<Vehicle> findByUbication(String ubication) {
        return vehRepo.findByUbication(ubication);
    }
    
    public Optional<Vehicle> findByPlaca(String placa) {
        return vehRepo.findByPlaca(placa);
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
        return vehRepo.findById(id);
    }

    @Override
    public List<Vehicle> findAll() {
        return vehRepo.findAll();
    }

    @Override
    public void save(Vehicle vehicle) {
        
        vehicle.setType(typeRepo.findByName(vehicle.getType().getName()));
        vehRepo.save(vehicle);
    }

    @Override
    public void delete(Long id) {
        vehRepo.deleteById(id);
    }
    
    
    
}
