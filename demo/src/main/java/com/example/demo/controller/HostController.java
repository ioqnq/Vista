package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.domain.User;
import com.example.demo.service.PropertyService;
import com.example.demo.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import com.example.demo.dto.PropertyForm;

@Controller
public class HostController {

    private final UserService userService;
    private final PropertyService propertyService;

    public HostController(UserService userService, PropertyService propertyService) {
        this.userService = userService;
        this.propertyService = propertyService;
    }

    @GetMapping("/properties")
    public String propertiesEntry(Authentication authentication, Model model) {
        if (authentication == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(authentication.getName());

        if (user == null) {
            return "redirect:/login";
        }

        if (!"HOST".equalsIgnoreCase(user.getRole())) {
            return "redirect:/become-host";
        }

        model.addAttribute("properties", propertyService.getPropertiesForHost(user.getEmail()));
        return "host-properties";
    }

    @GetMapping("/become-host")
    public String becomeHostPage() {
        return "become-host";
    }


    @GetMapping("/host/properties/{id}")
    public String hostPropertyDetails(@PathVariable Long id, Authentication authentication, Model model) {
        if (authentication == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(authentication.getName());

        if (user == null) {
            return "redirect:/login";
        }

        if (!"HOST".equalsIgnoreCase(user.getRole())) {
            return "redirect:/become-host";
        }

        var property = propertyService.getPropertyById(id);

        if (property == null) {
            return "redirect:/properties";
        }

        if (!user.getEmail().equals(property.getHostEmail())) {
            return "redirect:/properties";
        }

        model.addAttribute("property", property);
        return "host-property-details";
    }

    @GetMapping("/properties/add")
    public String showAddPropertyForm(Authentication authentication, Model model) {
        if (authentication == null) {
            return "redirect:/login";
        }
        User user = userService.findByEmail(authentication.getName());
        if (user == null || !"HOST".equalsIgnoreCase(user.getRole())) {
            return "redirect:/properties";
        }

        model.addAttribute("propertyForm", new PropertyForm());
        return "propertyForm";
    }

    @PostMapping("/properties/add")
    public String processAddProperty(
            @ModelAttribute("propertyForm") PropertyForm form,
            @RequestParam("images") List<MultipartFile> images,
            Authentication authentication) {

        if (authentication == null) {
            return "redirect:/login";
        }
        User user = userService.findByEmail(authentication.getName());
        if (user == null || !"HOST".equalsIgnoreCase(user.getRole())) {
            return "redirect:/properties";
        }

        propertyService.saveProperty(form, images, user.getEmail());

        return "redirect:/properties";
    }
}
