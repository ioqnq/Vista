package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.Property;
import com.example.demo.domain.PropertyImage;
import com.example.demo.dto.PropertyForm;
import com.example.demo.repository.PropertyImageRepository;
import com.example.demo.repository.PropertyRepository;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyImageRepository propertyImageRepository;

    public PropertyService(PropertyRepository propertyRepository,
                           PropertyImageRepository propertyImageRepository) {
        this.propertyRepository = propertyRepository;
        this.propertyImageRepository = propertyImageRepository;
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

    public Property save(Property property) {
        return propertyRepository.save(property);
    }

    public void saveProperty(PropertyForm form, List<MultipartFile> images, String hostEmail) {
        String uploadDir = System.getProperty("user.dir") + "/uploads/";

        File uploadFolder = new File(uploadDir);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        if (images == null || images.isEmpty()) {
            throw new RuntimeException("At least one image is required");
        }

        List<String> uploadedUrls = new ArrayList<>();

        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                String uniqueFilename = UUID.randomUUID() + "_" + image.getOriginalFilename();
                try {
                    image.transferTo(new File(uploadDir + uniqueFilename));
                    uploadedUrls.add("/uploads/" + uniqueFilename);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to save image", e);
                }
            }
        }

        if (uploadedUrls.isEmpty()) {
            throw new RuntimeException("At least one valid image is required");
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

        
        property.setImageUrl(uploadedUrls.get(0));

        Property savedProperty = propertyRepository.save(property);

        for (String imageUrl : uploadedUrls) {
            PropertyImage propertyImage = new PropertyImage();
            propertyImage.setImageUrl(imageUrl);
            propertyImage.setProperty(savedProperty);
            propertyImageRepository.save(propertyImage);
        }
    }
}