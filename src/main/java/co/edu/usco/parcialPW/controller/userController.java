package co.edu.usco.parcialPW.controller;

import co.edu.usco.parcialPW.persistence.entity.UserEntity;
import co.edu.usco.parcialPW.persistence.entity.Vehicle;
import co.edu.usco.parcialPW.persistence.repository.UserRepository;
import co.edu.usco.parcialPW.service.impl.VehicleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
@Tag(name = "CLIENTE ACCIONES", description = "Acciones que puede realizar el Cliete, menos listar los clientes, Sólo READ")
public class userController {

    @Autowired
    VehicleService vehService;

    @Autowired
    UserRepository userRepo;

    @GetMapping
    public String showIndex() {

        return "index";
    }

    @GetMapping("/vehicle/list")
    public String showVehicleList(Model model) {

        List<Vehicle> vehicles = vehService.findAll();

        model.addAttribute("vehicles", vehicles);
        return "VehicleList";
    }

    @GetMapping("/loginPage")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/user/list")
    public String listTeachers(Model model) {

        List<UserEntity> users = userRepo.findUsuarios();

        model.addAttribute("users", users);
        return "userList";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/loginPage?logout"; 

    }
}
