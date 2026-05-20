package com.example.demo.controller;

import java.util.List; // Added this import for propertyTypes!

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.PropertyService;

@Controller
public class HomeController {

    private final PropertyService propertyService;

    public HomeController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/")
    public String home(@RequestParam(required = false) String location, Model model) {
        model.addAttribute("properties", propertyService.searchProperties(location));
        return "results";
    }

    @GetMapping("/results")
    public String results(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer minGuests,
            @RequestParam(required = false) List<String> propertyTypes,
            @RequestParam(required = false) Boolean allowsPets,
            @RequestParam(required = false) Boolean smokingArea,
            @RequestParam(required = false) Boolean breakfastIncluded,
            @RequestParam(required = false) Boolean parkingSpace,
            @RequestParam(required = false) Boolean restaurant,
            @RequestParam(required = false) Boolean frontDesk,
            Model model) {

        // Passing all 10 arguments directly to your updated Service!
        model.addAttribute("properties", propertyService.getFilteredProperties(
                location, maxPrice, minGuests, propertyTypes, allowsPets,
                smokingArea, breakfastIncluded, parkingSpace, restaurant, frontDesk));

        return "results";
    }
}