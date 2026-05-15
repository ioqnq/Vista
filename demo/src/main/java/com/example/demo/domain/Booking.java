package com.example.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long propertyId;

    @Column(nullable = false)
    private String propertyName;

    @Column(nullable = false)
    private String guestFirstName;

    @Column(nullable = false)
    private String guestLastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String checkIn;

    @Column(nullable = false)
    private String checkOut;

    @Column(nullable = false)
    private int guests;

    @Column(nullable = false)
    private double totalPrice;

    @Column(nullable = false)
    private String status;

    public Booking() {
    }

    public Booking(Long id, Long propertyId, String propertyName, String guestFirstName,
                   String guestLastName, String email, String phone,
                   String checkIn, String checkOut, int guests,
                   double totalPrice, String status) {
        this.id = id;
        this.propertyId = propertyId;
        this.propertyName = propertyName;
        this.guestFirstName = guestFirstName;
        this.guestLastName = guestLastName;
        this.email = email;
        this.phone = phone;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guests = guests;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Long getId() { return id; }
    public Long getPropertyId() { return propertyId; }
    public String getPropertyName() { return propertyName; }
    public String getGuestFirstName() { return guestFirstName; }
    public String getGuestLastName() { return guestLastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getCheckIn() { return checkIn; }
    public String getCheckOut() { return checkOut; }
    public int getGuests() { return guests; }
    public double getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }

    public void setId(Long id) { this.id = id; }
    public void setPropertyId(Long propertyId) { this.propertyId = propertyId; }
    public void setPropertyName(String propertyName) { this.propertyName = propertyName; }
    public void setGuestFirstName(String guestFirstName) { this.guestFirstName = guestFirstName; }
    public void setGuestLastName(String guestLastName) { this.guestLastName = guestLastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setCheckIn(String checkIn) { this.checkIn = checkIn; }
    public void setCheckOut(String checkOut) { this.checkOut = checkOut; }
    public void setGuests(int guests) { this.guests = guests; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public void setStatus(String status) { this.status = status; }
}