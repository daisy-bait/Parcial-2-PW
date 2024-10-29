
package co.edu.usco.parcialPW.restcontroller;

import co.edu.usco.parcialPW.persistence.entity.Vehicle;
import co.edu.usco.parcialPW.service.impl.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vehicle")
@Tag(name = "ACOMODADOR ACCIONES", description = "Acciones que puede realizar el Acomodador, CRUD Básico")
public class adminRestController {
    
    @Autowired
    VehicleService vehService;
    
    @Operation(summary = "Añadir nuevo vehículo")
    @PostMapping("/add")
    public ResponseEntity<?> save (
            @Parameter(description = "Datos básicos para insertar estancia de vehículo")
            @RequestBody Vehicle vehicle) {
        
        Optional<Vehicle> optVehUbi = vehService.findByUbication(vehicle.getUbicacion());
        Optional<Vehicle> optVehPlaca = vehService.findByPlaca(vehicle.getPlaca());
        
        if (optVehUbi.isPresent() || optVehPlaca.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        
        vehService.save(vehicle);
        
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @Operation(summary = "Listar todos los vehículos")
    @GetMapping("/find")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(vehService.findAll());
    }
    
    @Operation(summary = "Eliminar vehículo")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        
        Optional<Vehicle> vehOptional = vehService.findById(id);
        
        if (!vehOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        } else if (id != null) {
            
            vehService.delete(id);
            
            return ResponseEntity.ok("Estancia de Vehículo eliminada");
        }
        
        return ResponseEntity.badRequest().build();
    }
    
}
