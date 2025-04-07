package models;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private String name;
    private String description;
    private String customer;
    private int startDay;
    private String startMonth;
    private int endDay;
    private String endMonth;
    private int year; // Year is the same for the entire event
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    private String room;
    private int discount;

    public Event(String name, String description, String customer, int startDay, String startMonth, int endDay,
                 String endMonth, int year, int startHour, int startMinute, int endHour, int endMinute,String room,int discount) {
        this.name = name;
        this.description = description;
        this.customer = customer;
        this.startDay = startDay;
        this.startMonth = startMonth;
        this.endDay = endDay;
        this.endMonth = endMonth;
        this.year = year;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.room = room;
        this.discount = discount;
    }

    public String getEventName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCustomerName() {
        return customer;
    }

    public int getStartDay() {
        return startDay;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public int getEndDay() {
        return endDay;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public int getYear() {
        return year;
    }

    public String getStartTime() {
        return String.format("%02d:%02d", startHour, startMinute);
    }

    public String getEndTime() {
        return String.format("%02d:%02d", endHour, endMinute);
    }

    public String getRoom() {
        return room;
    }
    public int getDiscount() {
        return discount;
    }



    public boolean isWithinTimeSlot(int hour) {
        return hour >= startHour && hour < endHour;
    }

    /**
     * Checks if the event is visible on a specific day and month.
     */
    public boolean isVisibleOn(int day, String month) {
        if (!startMonth.equals(endMonth)) {
            // Handle multi-month events
            if (month.equals(startMonth) && day >= startDay) {
                return true;
            } else if (month.equals(endMonth) && day <= endDay) {
                return true;
            }
        } else {
            // Single-month event
            if (month.equals(startMonth) && day >= startDay && day <= endDay) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

    }
}

// checking if commits still work