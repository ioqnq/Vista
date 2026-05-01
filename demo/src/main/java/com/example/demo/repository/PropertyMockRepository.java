package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.Property;

@Repository
public class PropertyMockRepository {

    private final List<Property> properties = new ArrayList<>();

    public PropertyMockRepository() {
        properties.add(new Property(
                1L, "Cosy loft in city center", "Rome", 200, 4.8,
                "/images/property-1.jpg", 4, "Apartment",
                true, true, false,
                "A cozy apartment in central Rome."
        ));

        properties.add(new Property(
                2L, "Classic double apartment", "Rome", 200, 4.7,
                "/images/property-2.jpg", 4, "Apartment",
                false, false, true,
                "Comfortable apartment close to tourist attractions."
        ));

        properties.add(new Property(
                3L, "Authentic Villa Romana", "Rome", 500, 4.9,
                "/images/property-3.jpg", 8, "Villa",
                true, true, true,
                "Large villa ideal for families and groups."
        ));

        properties.add(new Property(
                4L, "2 bedroom apartment", "Rome", 217, 4.6,
                "/images/property-4.jpg", 5, "Apartment",
                false, false, true,
                "Two-bedroom apartment with modern amenities."
        ));
    }

    public List<Property> findAll() {
        return new ArrayList<>(properties);
    }

    public Optional<Property> findById(Long id) {
        return properties.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public List<Property> findByLocation(String location) {
        if (location == null || location.isBlank()) {
            return findAll();
        }

        return properties.stream()
                .filter(p -> p.getLocation().toLowerCase().contains(location.toLowerCase()))
                .toList();
    }
}
