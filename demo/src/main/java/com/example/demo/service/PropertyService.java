package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Property;
import com.example.demo.repository.PropertyRepository;

import org.springframework.web.multipart.MultipartFile;
import com.example.demo.dto.PropertyForm;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

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

    public void saveProperty(PropertyForm form, List<MultipartFile> images, String hostEmail) {
        List<String> uploadedUrls = new ArrayList<>();
        String uploadDir = System.getProperty("user.dir") + "/uploads/";

        // save images to local folder
        File uploadFolder = new File(uploadDir);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                String uniqueFilename = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                try {
                    image.transferTo(new File(uploadDir + uniqueFilename));
                    uploadedUrls.add("/uploads/" + uniqueFilename);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to save image", e);
                }
            }
        }

        Property property = new Property();
        property.setName(form.getName());
        property.setLocation(form.getLocation());
        property.setPricePerNight(form.getPricePerNight());
        property.setMaxGuests(form.getMaxGuests());
        property.setPropertyType(form.getPropertyType());
        property.setDescription(form.getDescription());
        property.setBreakfastIncluded(form.isBreakfastIncluded());
        property.setAllowsPets(form.isAllowsPets());
        property.setParkingSpace(form.isParkingSpace());
        property.setHostEmail(hostEmail);

        property.setRating(0.0);

        if (!uploadedUrls.isEmpty()) {
            property.setImageUrl(uploadedUrls.get(0));
        }

        propertyRepository.save(property);
    }
}