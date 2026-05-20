package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.Property;
import com.example.demo.domain.User;
import com.example.demo.dto.PropertyForm;
import com.example.demo.service.BookingService;
import com.example.demo.service.PropertyService;
import com.example.demo.service.UserService;


@Controller
public class HostController {

    private final UserService userService;
    private final PropertyService propertyService;
    private final BookingService bookingService;

    public HostController(UserService userService,
                      PropertyService propertyService,
                      BookingService bookingService) {
        this.userService = userService;
        this.propertyService = propertyService;
        this.bookingService = bookingService;
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
        model.addAttribute("bookings", bookingService.getBookingsForProperty(id));
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
    public String addProperty(
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

        propertyService.saveProperty(form, images, authentication.getName());
        return "redirect:/properties";
    }

    @GetMapping("/host/properties/edit/{id}")
    public String editPropertyPage(@PathVariable Long id, Authentication authentication, Model model) {
        if (authentication == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(authentication.getName());
        if (user == null || !"HOST".equalsIgnoreCase(user.getRole())) {
            return "redirect:/properties";
        }

        Property property = propertyService.getPropertyById(id);

        if (property == null || !user.getEmail().equals(property.getHostEmail())) {
            return "redirect:/properties";
        }

        model.addAttribute("property", property);
        return "edit-property";
    }

    @PostMapping("/host/properties/edit/{id}")
    public String editProperty(@PathVariable Long id,
                               @ModelAttribute("property") Property updatedProperty,
                               Authentication authentication) {

        if (authentication == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(authentication.getName());
        if (user == null || !"HOST".equalsIgnoreCase(user.getRole())) {
            return "redirect:/properties";
        }

        Property existingProperty = propertyService.getPropertyById(id);

        if (existingProperty == null || !user.getEmail().equals(existingProperty.getHostEmail())) {
            return "redirect:/properties";
        }

        existingProperty.setName(updatedProperty.getName());
        existingProperty.setLocation(updatedProperty.getLocation());
        existingProperty.setPricePerNight(updatedProperty.getPricePerNight());
        existingProperty.setMaxGuests(updatedProperty.getMaxGuests());
        existingProperty.setPropertyType(updatedProperty.getPropertyType());
        existingProperty.setDescription(updatedProperty.getDescription());
        existingProperty.setBreakfastIncluded(updatedProperty.isBreakfastIncluded());
        existingProperty.setAllowsPets(updatedProperty.isAllowsPets());
        existingProperty.setParkingSpace(updatedProperty.isParkingSpace());

        propertyService.save(existingProperty);

        return "redirect:/host/properties/" + id;
    }
}
