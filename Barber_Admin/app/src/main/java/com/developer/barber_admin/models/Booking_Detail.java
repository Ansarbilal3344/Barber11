package com.developer.barber_admin.models;

public class Booking_Detail {
    String booking_id;
    String booked_by;
    String booked_slot;
    String phone;
    String date;
    String status;
    String booked_user_id;


    public String getBooked_user_id() {
        return booked_user_id;
    }

    public void setBooked_user_id(String booked_user_id) {
        this.booked_user_id = booked_user_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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
