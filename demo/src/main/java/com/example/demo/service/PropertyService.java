package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Property;
import com.example.demo.repository.PropertyRepository;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public List<Property> searchProperties(String location) {
        if (location == null || location.isBlank()) {
            return propertyRepository.findAll();
        }
        return propertyRepository.findByLocationContainingIgnoreCase(location);
    }

    public Property getPropertyById(Long id) {
        return propertyRepository.findById(id).orElse(null);
    }

    public List<Property> getPropertiesForHost(String hostEmail) {
        return propertyRepository.findByHostEmail(hostEmail);
    }
}