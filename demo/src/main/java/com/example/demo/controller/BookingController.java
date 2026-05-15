package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.BookingForm;
import com.example.demo.domain.Property;
import com.example.demo.service.BookingService;
import com.example.demo.service.PropertyService;

@Controller
public class BookingController {

    private final BookingService bookingService;
    private final PropertyService propertyService;

    public BookingController(BookingService bookingService, PropertyService propertyService) {
        this.bookingService = bookingService;
        this.propertyService = propertyService;
    }

    @GetMapping("/booking-details/{propertyId}")
    public String bookingDetails(@PathVariable Long propertyId, Model model) {
        Property property = propertyService.getPropertyById(propertyId);

        if (property == null) {
            return "redirect:/results";
        }

        BookingForm bookingForm = new BookingForm();
        bookingForm.setPropertyId(propertyId);
        bookingForm.setCheckIn("12 Sept 2026");
        bookingForm.setCheckOut("17 Sept 2026");
        bookingForm.setGuests(4);

        model.addAttribute("property", property);
        model.addAttribute("bookingForm", bookingForm);

        return "booking-details";
    }

    @PostMapping("/booking-details")
    public String submitBooking(@ModelAttribute BookingForm bookingForm) {
        bookingService.createBooking(bookingForm);
        return "redirect:/payment/" + bookingForm.getPropertyId();
    }

    @GetMapping("/payment/{propertyId}")
    public String paymentPage(@PathVariable Long propertyId, Model model) {
        Property property = propertyService.getPropertyById(propertyId);

        if (property == null) {
            return "redirect:/results";
        }

        model.addAttribute("property", property);
        model.addAttribute("total", property.getPricePerNight() * 5);

        return "payment";
    }

    @GetMapping("/guest-bookings")
    public String guestBookings(Model model) {
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "guest-bookings";
    }

    @GetMapping("/host-properties")
    public String hostProperties(Model model) {
        model.addAttribute("properties", propertyService.getAllProperties());
        return "host-properties";
    }
}