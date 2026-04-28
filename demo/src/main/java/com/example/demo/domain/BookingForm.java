package com.example.demo.domain;

public class BookingForm {
    private Long propertyId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String checkIn;
    private String checkOut;
    private int guests;

    public BookingForm() {
    }

    public Long getPropertyId() { return propertyId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getCheckIn() { return checkIn; }
    public String getCheckOut() { return checkOut; }
    public int getGuests() { return guests; }

    public void setPropertyId(Long propertyId) { this.propertyId = propertyId; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setCheckIn(String checkIn) { this.checkIn = checkIn; }
    public void setCheckOut(String checkOut) { this.checkOut = checkOut; }
    public void setGuests(int guests) { this.guests = guests; }
}
