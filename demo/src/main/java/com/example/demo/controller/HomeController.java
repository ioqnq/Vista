package com.example.demo.controller;

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
    public String results(@RequestParam(required = false) String location, Model model) {
        model.addAttribute("properties", propertyService.searchProperties(location));
        return "results";
    }
}