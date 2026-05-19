package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.domain.Property;
import com.example.demo.service.PropertyService;

@Controller
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        Property property = propertyService.getPropertyById(id);

        if (property == null) {
            return "redirect:/results";
        }

        model.addAttribute("property", property);
        return "details";
    }

    
}
