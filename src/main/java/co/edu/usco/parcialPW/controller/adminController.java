package co.edu.usco.parcialPW.controller;

import co.edu.usco.parcialPW.persistence.entity.Type;
import co.edu.usco.parcialPW.persistence.entity.Vehicle;
import co.edu.usco.parcialPW.persistence.repository.UserRepository;
import co.edu.usco.parcialPW.service.impl.VehicleService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vehicle")
@Tag(name = "ACOMODADOR ACCIONES", description = "Acciones que puede realizar el Acomodador, CRUD Básico")
public class adminController {

    @Autowired
    VehicleService vehService;

    @Autowired
    UserRepository userRepo;

    @GetMapping("/add")
    public String addVehicle(Model model) {

        Vehicle newVeh = new Vehicle();
        newVeh.setType(new Type());

        model.addAttribute("newVeh", newVeh);
        model.addAttribute("valor", "Añadir");

        return "modifyVehicleForm";
    }

    @PostMapping("/insert")
    @Operation(summary = "Insertar un vehículo", description = "Inserta los datos de un nuevo vehículo")
    public String insertVehicle(
            @Valid
            @Parameter(description = "Datos básicos para insertar una Estancia de Vehículo")
            @ModelAttribute("newVeh") Vehicle vehicle, Errors error, Model model) {

        Optional<Vehicle> optVehUbi = vehService.findByUbication(vehicle.getUbicacion());
        Optional<Vehicle> optVehPlaca = vehService.findByPlaca(vehicle.getPlaca());

        //Verificar si es CREATE
        if (vehicle.getId() == null) {
            if (optVehUbi.isPresent() || optVehPlaca.isPresent()) {
                model.addAttribute("placaError", "Ya hay un vehículo aparcado con esa placa o en esa ubicación");
                model.addAttribute("valor", "Añadir");
                return "modifyVehicleForm";
            }
        } else { //Verificar si es UPDATE

            //Verificar si se quiere modificar placa o ubicación y que no sea de un carro ya estacionado
            if (optVehPlaca.isPresent()) {

                Vehicle oldVeh = optVehPlaca.get();

                if (vehicle.getId().equals(oldVeh.getId())) {
                    if (optVehUbi.isPresent()) {

                        oldVeh = optVehUbi.get();

                        if (!(oldVeh.getId().equals(vehicle.getId()))) {
                            model.addAttribute("ubicationError", "Ya hay un vehículo en esa ubicación");
                            model.addAttribute("valor", "Modificar");
                            return "modifyVehicleForm";
                        }
                    }
                } else {
                    model.addAttribute("placaError", "Ya hay un vehículo aparcado con esa placa");
                    model.addAttribute("valor", "Modificar");
                    return "modifyVehicleForm";
                }

            }

            if (optVehUbi.isPresent()) {

                Vehicle oldVeh = optVehUbi.get();

                if (!(oldVeh.getId().equals(vehicle.getId()))) {
                    model.addAttribute("ubicationError", "Ya hay un vehículo en esa ubicación");
                    model.addAttribute("valor", "Modificar");
                    return "modifyVehicleForm";
                }
            }

        }

        //Demás validaciones
        if (error.hasErrors()) {
            model.addAttribute("valor", vehicle.getId() == null ? "Añadir" : "Modificar");
            return "modifyVehicleForm";
        } else {
            vehService.save(vehicle);

            return "redirect:/vehicle/list";
        }
    }

    @GetMapping("/modify/{id}")
    public String modifyVehicle(@PathVariable Long id, Model model) {

        Vehicle modVehicle = vehService.findById(id).orElse(null);

        model.addAttribute("newVeh", modVehicle);
        model.addAttribute("valor", "Modificar");
        return "modifyVehicleForm";
    }

    @GetMapping("/delete/{id}")
    public String deleteVehicle(@PathVariable Long id) {

        vehService.delete(id);

        return "redirect:/vehicle/list";
    }

}
