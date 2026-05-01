package com.example.demo.service;

import com.example.demo.domain.Property;
import com.example.demo.repository.PropertyMockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {

    private final PropertyMockRepository propertyRepository;

    public PropertyService(PropertyMockRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public List<Property> searchProperties(String location) {
        return propertyRepository.findByLocation(location);
    }

    public Property getPropertyById(Long id) {
        return propertyRepository.findById(id).orElse(null);
    }
}
