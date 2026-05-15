package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Booking;
import com.example.demo.domain.BookingForm;
import com.example.demo.domain.Property;
import com.example.demo.repository.BookingRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final PropertyService propertyService;

    public BookingService(BookingRepository bookingRepository, PropertyService propertyService) {
        this.bookingRepository = bookingRepository;
        this.propertyService = propertyService;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingsForProperty(Long propertyId) {
        return bookingRepository.findByPropertyId(propertyId);
    }

    public List<Booking> getBookingsForUser(String email) {
        return bookingRepository.findByEmail(email);
    }

    public Booking createBooking(BookingForm form) {
        Property property = propertyService.getPropertyById(form.getPropertyId());

        if (property == null) {
            throw new IllegalArgumentException("Property not found");
        }

        double totalPrice = property.getPricePerNight() * 5;

        Booking booking = new Booking();
        booking.setPropertyId(property.getId());
        booking.setPropertyName(property.getName());
        booking.setGuestFirstName(form.getFirstName());
        booking.setGuestLastName(form.getLastName());
        booking.setEmail(form.getEmail());
        booking.setPhone(form.getPhone());
        booking.setCheckIn(form.getCheckIn());
        booking.setCheckOut(form.getCheckOut());
        booking.setGuests(form.getGuests());
        booking.setTotalPrice(totalPrice);
        booking.setStatus("Pending");

        return bookingRepository.save(booking);
    }
}