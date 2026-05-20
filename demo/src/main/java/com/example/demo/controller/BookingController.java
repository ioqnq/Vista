package com.example.demo.controller;

import org.springframework.security.core.Authentication;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String submitBooking(@ModelAttribute BookingForm bookingForm,
                                Authentication authentication,
                                RedirectAttributes redirectAttributes) {

        bookingService.createBooking(bookingForm, authentication.getName());

        redirectAttributes.addFlashAttribute("bookingForm", bookingForm);

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

        if (!model.containsAttribute("bookingForm")) {
            BookingForm fallbackForm = new BookingForm();
            fallbackForm.setPropertyId(propertyId);
            model.addAttribute("bookingForm", fallbackForm);
        }

        return "payment";
    }

    @GetMapping("/payment-success")
    public String paymentSuccessPage() {
        return "payment-success";
    }

    @GetMapping("/guest-bookings")
    public String guestBookings(Model model, Authentication authentication) {
        model.addAttribute("bookings", bookingService.getBookingsForUser(authentication.getName()));
        return "guest-bookings";
    }

    @GetMapping("/host-properties")
    public String hostProperties(Model model, Authentication authentication) {
        model.addAttribute("properties", propertyService.getPropertiesForHost(authentication.getName()));
        return "host-properties";
    }
}