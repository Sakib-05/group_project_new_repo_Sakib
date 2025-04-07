package UI;

import models.Event;
import utils.FakeEventGenerator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.time.LocalDate;

public class EventView {
    private JPanel panel;
    private JLabel titleLabel;
    private JPanel eventsPanel;

    // Store the current month and year
    private String currentMonth;
    private int currentYear;

    public EventView(CalendarApp app) {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.decode("#142524")); // Aztec color (background)

        titleLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.decode("#30C142")); // Apple color

        eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));
        eventsPanel.setBackground(Color.decode("#142524"));

        // Add a back button
        JButton backButton = new JButton("Back to Days");
        backButton.setBackground(Color.decode("#30C142"));
        backButton.setForeground(Color.decode("#142524"));
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(e -> {
            // Pass the correct month and year to navigate back to DayView
            app.showDayView(currentMonth + " " + currentYear);
        });

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(new JScrollPane(eventsPanel), BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);
    }

    public void setDay(String month, int day, int year) {
        // Store the current month and year for navigation
        this.currentMonth = month;
        this.currentYear = year; // Dynamically update the year

        titleLabel.setText("Events for "+ day + " "  + month +  " " + year);

        // Clear previous events
        eventsPanel.removeAll();

        // Get all static events
        List<Event> allEvents = getAllStaticEvents();

        // Filter events for the selected date
        List<Event> filteredEvents = new ArrayList<>();
        for (Event event : allEvents) {
            if (event.isVisibleOn(day, month) && event.getYear() == year) {
                filteredEvents.add(event);
            }
        }

        // Display filtered events
        if (!filteredEvents.isEmpty()) {
            for (Event event : filteredEvents) {
                JPanel eventPanel = new JPanel();
                eventPanel.setLayout(new GridLayout(5, 1));
                eventPanel.setBackground(Color.decode("#30C142")); // Apple color
                eventPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the event
                // panel

                JLabel nameLabel = new JLabel("Name: " + event.getEventName());
                nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
                nameLabel.setForeground(Color.WHITE); // Set text color to white for better contrast

                JLabel startLabel = new JLabel("Start: " + event.getStartTime());
                startLabel.setForeground(Color.WHITE);

                JLabel endLabel = new JLabel("End: " + event.getEndTime());
                endLabel.setForeground(Color.WHITE);

                JLabel roomLabel = new JLabel("Room: " + event.getRoom());
                roomLabel.setForeground(Color.WHITE);

                JLabel discountLabel = new JLabel("Discount: " + event.getDiscount());
                discountLabel.setForeground(Color.WHITE);



                eventPanel.add(nameLabel);
                eventPanel.add(startLabel);
                eventPanel.add(endLabel);
                eventPanel.add(roomLabel);
                eventPanel.add(discountLabel);

                // Add spacing between events
                JPanel wrapperPanel = new JPanel(new BorderLayout());
                wrapperPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add vertical spacing
                wrapperPanel.setBackground(Color.decode("#142524")); // Match the background color of the main panel
                wrapperPanel.add(eventPanel, BorderLayout.CENTER);

                eventsPanel.add(wrapperPanel);
            }
        } else {
            // Show a message if no events are available
            JLabel noEventsLabel = new JLabel("No events scheduled for this day", SwingConstants.CENTER);
            noEventsLabel.setFont(new Font("Arial", Font.BOLD, 16));
            noEventsLabel.setForeground(Color.decode("#CCD1D2")); // Iron color
            eventsPanel.add(noEventsLabel);
        }

        // Refresh the panel
        eventsPanel.revalidate();
        eventsPanel.repaint();
    }

    private List<Event> getAllStaticEvents() {
        String URL = "jdbc:mysql://sst-stuproj.city.ac.uk:3306/in2033t20";
        String USER = "in2033t20_a";
        String PASSWORD = "seodztXABQo"; // Replace with your MySQL password

        // Updated SQL Query to join events with Room
        String sql = "SELECT e.*, r.room_name FROM events e JOIN Room r ON e.room_id = r.room_id";

        List<Event> events = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int eventID = resultSet.getInt("event_id");
                int bookingID = resultSet.getInt("booking_id");
                String eventName = resultSet.getString("event_name");
                Timestamp startTime = resultSet.getTimestamp("start_time");
                Timestamp endTime = resultSet.getTimestamp("end_time");
                String room = resultSet.getString("room_name"); // Fetch room name
                String description = resultSet.getString("desctiption"); // Matches the typo in the table
                int discount = resultSet.getInt("discount");

                // Extract date and time components from Timestamp
                LocalDate startDate = startTime.toLocalDateTime().toLocalDate();
                LocalDate endDate = endTime.toLocalDateTime().toLocalDate();
                int startHour = startTime.toLocalDateTime().getHour();
                int startMinute = startTime.toLocalDateTime().getMinute();
                int endHour = endTime.toLocalDateTime().getHour();
                int endMinute = endTime.toLocalDateTime().getMinute();

                // Create an Event object and add it to the list
                events.add(new Event(
                        eventName,
                        description != null ? description : "No description available", // Handle null descriptions
                        "Customer not available", // Placeholder for customer
                        startDate.getDayOfMonth(),
                        capitalizeFirstLetter(startDate.getMonth().name()),
                        endDate.getDayOfMonth(),
                        capitalizeFirstLetter(endDate.getMonth().name()),
                        startDate.getYear(),
                        startHour,
                        startMinute,
                        endHour,
                        endMinute,
                        room, // Pass the room name
                        discount
                ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return events; // Return the list of events after processing all records
    }

    // Helper method to capitalize the first letter of a month name
    private String capitalizeFirstLetter(String month) {
        return month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase();
    }

    public JPanel getPanel() {
        return panel;
    }
}