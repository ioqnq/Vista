package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.Booking;
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
    public String bookingDetails(@PathVariable Long propertyId,
                                 @RequestParam String checkIn,
                                 @RequestParam String checkOut,
                                 @RequestParam int guests,
                                 Model model) {
        Property property = propertyService.getPropertyById(propertyId);

        if (property == null) {
            return "redirect:/results";
        }

        BookingForm bookingForm = new BookingForm();
        bookingForm.setPropertyId(propertyId);
        bookingForm.setCheckIn(checkIn);
        bookingForm.setCheckOut(checkOut);
        bookingForm.setGuests(guests);

        long nights = bookingService.calculateNights(checkIn, checkOut);
        double total = property.getPricePerNight() * nights;

        model.addAttribute("property", property);
        model.addAttribute("bookingForm", bookingForm);
        model.addAttribute("nights", nights);
        model.addAttribute("total", total);

        return "booking-details";
    }

    @PostMapping("/booking-details")
    public String submitBooking(@ModelAttribute BookingForm bookingForm,
                                Authentication authentication) {
        Booking savedBooking = bookingService.createBooking(bookingForm, authentication.getName());
        return "redirect:/payment/" + savedBooking.getId();
    }

    @GetMapping("/payment/{bookingId}")
    public String paymentPage(@PathVariable Long bookingId, Model model) {
        Booking booking = bookingService.getBookingById(bookingId);

        if (booking == null) {
            return "redirect:/guest-bookings";
        }

        Property property = propertyService.getPropertyById(booking.getPropertyId());
        long nights = bookingService.calculateNights(booking.getCheckIn(), booking.getCheckOut());

        model.addAttribute("booking", booking);
        model.addAttribute("property", property);
        model.addAttribute("total", booking.getTotalPrice());
        model.addAttribute("nights", nights);

        return "payment";
    }

    @PostMapping("/payment/{bookingId}/complete")
    public String completePayment(@PathVariable Long bookingId) {
        bookingService.markAsPaid(bookingId);
        return "redirect:/payment-success";
    }

    @GetMapping("/payment-success")
    public String paymentSuccessPage() {
        return "payment-success";
    }

    @GetMapping("/guest-bookings")
    public String guestBookings(Model model, Authentication authentication) {
        model.addAttribute("bookings", bookingService.getBookingsForUserWithProperty(authentication.getName()));
        return "guest-bookings";
    }

    @GetMapping("/guest-bookings/{id}")
    public String guestBookingDetails(@PathVariable Long id,
                                    Authentication authentication,
                                    Model model) {
        Booking booking = bookingService.getBookingById(id);

        if (booking == null) {
            return "redirect:/guest-bookings";
        }

        if (authentication == null || !booking.getEmail().equals(authentication.getName())) {
            return "redirect:/guest-bookings";
        }

        Property property = propertyService.getPropertyById(booking.getPropertyId());

        model.addAttribute("booking", booking);
        model.addAttribute("property", property);

        return "guest-booking-details";
    }

    @PostMapping("/guest-bookings/{id}/delete")
    public String deleteBooking(@PathVariable Long id, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }

        bookingService.deleteBookingForUser(id, authentication.getName());
        return "redirect:/guest-bookings";
    }

    @GetMapping("/host-properties")
    public String hostProperties(Model model, Authentication authentication) {
        model.addAttribute("properties", propertyService.getPropertiesForHost(authentication.getName()));
        return "host-properties";
    }
}