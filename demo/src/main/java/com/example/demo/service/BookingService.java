package com.example.demo.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public Booking createBooking(BookingForm form, String loggedInUserEmail) {
        Property property = propertyService.getPropertyById(form.getPropertyId());

        if (property == null) {
            throw new IllegalArgumentException("Property not found");
        }

        long nights = calculateNights(form.getCheckIn(), form.getCheckOut());

        if (nights <= 0) {
            throw new IllegalArgumentException("Check-out must be after check-in");
        }

        double totalPrice = property.getPricePerNight() * nights;

        Booking booking = new Booking();
        booking.setPropertyId(property.getId());
        booking.setPropertyName(property.getName());
        booking.setGuestFirstName(form.getFirstName());
        booking.setGuestLastName(form.getLastName());
        booking.setEmail(loggedInUserEmail);
        booking.setPhone(form.getPhone());
        booking.setCheckIn(form.getCheckIn());
        booking.setCheckOut(form.getCheckOut());
        booking.setGuests(form.getGuests());
        booking.setTotalPrice(totalPrice);
        booking.setStatus("Pending");

        return bookingRepository.save(booking);
    }

    public void markAsPaid(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);

        if (booking == null) {
            throw new RuntimeException("Booking not found");
        }

        booking.setStatus("Paid");
        bookingRepository.save(booking);
    }

    public long calculateNights(String checkIn, String checkOut) {
        LocalDate start = LocalDate.parse(checkIn);
        LocalDate end = LocalDate.parse(checkOut);

        return ChronoUnit.DAYS.between(start, end);
    }

    public List<Booking> getBookingsForUserWithProperty(String email) {
        List<Booking> bookings = bookingRepository.findByEmail(email);

        for (Booking booking : bookings) {
            Property property = propertyService.getPropertyById(booking.getPropertyId());
            booking.setProperty(property);
        }

        return bookings;
    }

    public void deleteBookingForUser(Long bookingId, String email) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);

        if (booking == null) {
            throw new RuntimeException("Booking not found");
        }

        if (email == null || !booking.getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized");
        }

        bookingRepository.delete(booking);
    }
}