package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    // Your existing methods (untouched)
    List<Property> findByLocationContainingIgnoreCase(String location);
    List<Property> findByHostEmail(String hostEmail);

    // Our new dynamic filter query
    @Query("SELECT p FROM Property p WHERE " +
            "(:location IS NULL OR LOWER(p.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
            "(:maxPrice IS NULL OR p.pricePerNight <= :maxPrice) AND " +
            "(:minGuests IS NULL OR p.maxGuests <= :minGuests) AND " +
            "(p.propertyType IN :propertyTypes) AND " +
            "(:allowsPets IS NULL OR p.allowsPets = true) AND " +
            "(:smokingArea IS NULL OR p.smokingArea = true) AND " +
            "(:breakfast IS NULL OR p.breakfastIncluded = true) AND " +
            "(:parking IS NULL OR p.parkingSpace = true) AND " +
            "(:restaurant IS NULL OR p.restaurant = true) AND " +
            "(:frontDesk IS NULL OR p.frontDesk = true)")
    List<Property> findWithFilters(
            @Param("location") String location,
            @Param("maxPrice") Double maxPrice,
            @Param("minGuests") Integer minGuests,
            @Param("propertyTypes") List<String> propertyTypes,
            @Param("allowsPets") Boolean allowsPets,
            @Param("smokingArea") Boolean smokingArea,
            @Param("breakfast") Boolean breakfast,
            @Param("parking") Boolean parking,
            @Param("restaurant") Boolean restaurant,
            @Param("frontDesk") Boolean frontDesk
    );
}