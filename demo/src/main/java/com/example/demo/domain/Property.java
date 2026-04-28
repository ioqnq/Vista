package com.example.demo.domain;

public class Property {
    private Long id;
    private String name;
    private String location;
    private double pricePerNight;
    private double rating;
    private String imageUrl;
    private int maxGuests;
    private String propertyType;
    private boolean breakfastIncluded;
    private boolean allowsPets;
    private boolean parkingSpace;
    private String description;

    public Property() {
    }

    public Property(Long id, String name, String location, double pricePerNight, double rating,
                    String imageUrl, int maxGuests, String propertyType,
                    boolean breakfastIncluded, boolean allowsPets, boolean parkingSpace,
                    String description) {
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
        this.description = description;
    }

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
    public String getDescription() { return description; }

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
    public void setDescription(String description) { this.description = description; }
}
