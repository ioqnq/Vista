package com.example.demo.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private double pricePerNight;

    @Column(nullable = false)
    private double rating;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private int maxGuests;

    @Column(nullable = false)
    private String propertyType;

    @Column(nullable = false)
    private boolean breakfastIncluded;

    @Column(nullable = false)
    private boolean allowsPets;

    @Column(nullable = false)
    private boolean parkingSpace;

    // --- NEW: Added the 3 missing Figma sidebar filters ---
    @Column(nullable = false)
    private boolean smokingArea;

    @Column(nullable = false)
    private boolean restaurant;

    @Column(nullable = false)
    private boolean frontDesk;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String hostEmail;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PropertyImage> images = new ArrayList<>();

    public Property() {
    }

    public Property(Long id, String name, String location, double pricePerNight, double rating,
                    String imageUrl, int maxGuests, String propertyType,
                    boolean breakfastIncluded, boolean allowsPets, boolean parkingSpace,
                    boolean smokingArea, boolean restaurant, boolean frontDesk,
                    String description, String hostEmail) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.pricePerNight = pricePerNight;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.maxGuests = maxGuests;
        this.propertyType = propertyType;
        this.breakfastIncluded = breakfastIncluded;
        this.allowsPets = allowsPets;
        this.parkingSpace = parkingSpace;
        this.smokingArea = smokingArea;
        this.restaurant = restaurant;
        this.frontDesk = frontDesk;
        this.description = description;
        this.hostEmail = hostEmail;
    }

    public Property(String name, String location, double pricePerNight, double rating,
                    String imageUrl, int maxGuests, String propertyType,
                    boolean breakfastIncluded, boolean allowsPets, boolean parkingSpace,
                    boolean smokingArea, boolean restaurant, boolean frontDesk,
                    String description, String hostEmail) {
        this.name = name;
        this.location = location;
        this.pricePerNight = pricePerNight;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.maxGuests = maxGuests;
        this.propertyType = propertyType;
        this.breakfastIncluded = breakfastIncluded;
        this.allowsPets = allowsPets;
        this.parkingSpace = parkingSpace;
        this.smokingArea = smokingArea;
        this.restaurant = restaurant;
        this.frontDesk = frontDesk;
        this.description = description;
        this.hostEmail = hostEmail;
    }

    public void addImage(PropertyImage image) {
        images.add(image);
        image.setProperty(this);
    }

    // --- Getters ---
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public double getPricePerNight() { return pricePerNight; }
    public double getRating() { return rating; }
    public String getImageUrl() { return imageUrl; }
    public int getMaxGuests() { return maxGuests; }
    public String getPropertyType() { return propertyType; }
    public boolean isBreakfastIncluded() { return breakfastIncluded; }
    public boolean isAllowsPets() { return allowsPets; }
    public boolean isParkingSpace() { return parkingSpace; }
    public boolean isSmokingArea() { return smokingArea; }
    public boolean isRestaurant() { return restaurant; }
    public boolean isFrontDesk() { return frontDesk; }
    public String getDescription() { return description; }
    public String getHostEmail() { return hostEmail; }
    public List<PropertyImage> getImages() { return images; }

    // --- Setters ---
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setLocation(String location) { this.location = location; }
    public void setPricePerNight(double pricePerNight) { this.pricePerNight = pricePerNight; }
    public void setRating(double rating) { this.rating = rating; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setMaxGuests(int maxGuests) { this.maxGuests = maxGuests; }
    public void setPropertyType(String propertyType) { this.propertyType = propertyType; }
    public void setBreakfastIncluded(boolean breakfastIncluded) { this.breakfastIncluded = breakfastIncluded; }
    public void setAllowsPets(boolean allowsPets) { this.allowsPets = allowsPets; }
    public void setParkingSpace(boolean parkingSpace) { this.parkingSpace = parkingSpace; }
    public void setSmokingArea(boolean smokingArea) { this.smokingArea = smokingArea; }
    public void setRestaurant(boolean restaurant) { this.restaurant = restaurant; }
    public void setFrontDesk(boolean frontDesk) { this.frontDesk = frontDesk; }
    public void setDescription(String description) { this.description = description; }
    public void setHostEmail(String hostEmail) { this.hostEmail = hostEmail; }
    public void setImages(List<PropertyImage> images) { this.images = images; }
}