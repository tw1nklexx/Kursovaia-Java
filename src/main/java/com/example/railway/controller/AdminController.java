package com.example.railway.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.servlet.ModelAndView;
import com.example.railway.entity.Train;
import com.example.railway.entity.Passenger;
import com.example.railway.entity.Role;
import com.example.railway.entity.User;
import com.example.railway.repository.RoleRepository;
import com.example.railway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.railway.service.TrainService;
import com.example.railway.service.PassengerService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TrainService trainService;

    @Autowired
    private PassengerService passengerService;

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/users/{id}/toggleRole")
    public String toggleRole(@PathVariable("id") Long id, @RequestParam("roleName") String roleName) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new IllegalArgumentException("Invalid role name: " + roleName));

        Set<Role> roles = new HashSet<>();
        if ("ADMIN".equals(roleName)) {
            roles.add(role);
        } else {
            roles.add(roleRepository.findByName("USER").orElseThrow(() -> new IllegalArgumentException("Role USER not found")));
        }
        user.setRoles(roles);
        userRepository.save(user);

        return "redirect:/admin/users";
    }

    @RequestMapping("/trains/new")
    public String showNewTrainForm(Model model) {
        Train train = new Train();
        model.addAttribute("train", train);
        return "new_train";
    }

    @RequestMapping("/passengers/new")
    public String showNewPassengerForm(Model model) {
        Passenger passenger = new Passenger();
        List<Train> trains = trainService.listAll();
        model.addAttribute("passenger", passenger);
        model.addAttribute("trains", trains);
        return "new_passenger";
    }

    @RequestMapping("/passengers")
    public String listPassengers(Model model, @Param("keyword") String keyword,
                                 @Param("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                 @Param("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                 @Param("sortOrder") String sortOrder) {
        List<Passenger> listPassengers;
        if (startDate != null && endDate != null) {
            listPassengers = passengerService.filterByDate(startDate, endDate);
        } else if (sortOrder != null) {
            listPassengers = passengerService.listAllSorted(sortOrder);
        } else {
            listPassengers = passengerService.listAll(keyword);
        }
        model.addAttribute("listPassengers", listPassengers);
        model.addAttribute("keyword", keyword);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("sortOrder", sortOrder);
        return "passengers";
    }

    @RequestMapping("/trains/{id}")
    public ModelAndView showEditTrainForm(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_train");
        Train train = trainService.get(id);
        mav.addObject("train", train);
        return mav;
    }

    @RequestMapping("/passengers/{id}")
    public ModelAndView showEditPassengerForm(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_passenger");
        Passenger passenger = passengerService.get(id);
        mav.addObject("passenger", passenger);
        return mav;
    }

    @RequestMapping("/statistics")
    public String showStatistics(Model model) {
        List<Map<String, Object>> trainsByDate = trainService.countTrainsByDate();
        List<Map<String, Object>> trainsByStation = trainService.countTrainsByStation();
        List<Map<String, Object>> passengersByTrainId = passengerService.countPassengersByTrainId();

        model.addAttribute("trainsByDate", trainsByDate);
        model.addAttribute("trainsByStation", trainsByStation);
        model.addAttribute("passengersByTrainId", passengersByTrainId);

        return "statistics";
    }
}
