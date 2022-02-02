package com.developer.barber_user.models;

public class Booking_Detail {
    String booking_id;
    String booked_by;
    String booked_slot;
    String phone;

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getBooked_by() {
        return booked_by;
    }

    public void setBooked_by(String booked_by) {
        this.booked_by = booked_by;
    }

    public String getBooked_slot() {
        return booked_slot;
    }

    public void setBooked_slot(String booked_slot) {
        this.booked_slot = booked_slot;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
