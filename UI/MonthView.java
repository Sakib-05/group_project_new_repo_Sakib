package UI;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class MonthView {
    private JPanel panel;
    private int currentYear;
    private JLabel yearLabel;

    public MonthView(CalendarApp app) {
        currentYear = 2025; // Default year
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.decode("#142524")); // Aztec color (background)

        // Year navigation bar
        JPanel yearPanel = new JPanel();
        yearPanel.setLayout(new BorderLayout());
        yearPanel.setBackground(Color.decode("#142524"));

        JButton backButton = new JButton("< Back");
        backButton.setBackground(Color.decode("#30C142"));
        backButton.setForeground(Color.decode("#142524"));
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(e -> {
            currentYear--;
            updateYearLabel();
        });

        JButton forwardButton = new JButton("Forward >");
        forwardButton.setBackground(Color.decode("#30C142"));
        forwardButton.setForeground(Color.decode("#142524"));
        forwardButton.setFont(new Font("Arial", Font.BOLD, 14));
        forwardButton.addActionListener(e -> {
            currentYear++;
            updateYearLabel();
        });

        yearLabel = new JLabel(String.valueOf(currentYear), SwingConstants.CENTER);
        yearLabel.setFont(new Font("Arial", Font.BOLD, 18));
        yearLabel.setForeground(Color.decode("#30C142"));

        yearPanel.add(backButton, BorderLayout.WEST);
        yearPanel.add(yearLabel, BorderLayout.CENTER);
        yearPanel.add(forwardButton, BorderLayout.EAST);

        // Month buttons
        JPanel monthsPanel = new JPanel();
        monthsPanel.setLayout(new GridLayout(3, 4, 10, 10)); // 3 rows, 4 columns for months
        monthsPanel.setBackground(Color.decode("#142524"));

        String[] months = {
                "January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December"
        };

        for (String month : months) {
            JButton monthButton = new JButton(month);
            monthButton.setBackground(Color.decode("#30C142")); // Apple color (button background)
            monthButton.setForeground(Color.decode("#142524")); // Aztec color (text color)
            monthButton.setFont(new Font("Arial", Font.BOLD, 16));
            monthButton.setFocusPainted(false);

            // Add action listener to navigate to DayView
            monthButton.addActionListener(e -> {
                String monthYear = month + " " + currentYear; // Properly format monthYear
                app.showDayView(monthYear); // Pass the formatted monthYear to showDayView
            });

            monthsPanel.add(monthButton);
        }

        // Bottom panel for navigation buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 2, 10, 10));
        bottomPanel.setBackground(Color.decode("#142524"));

        // Add a "Return to Menu" button
        JButton returnToMenuButton = new JButton("Return to Menu");
        returnToMenuButton.setBackground(Color.decode("#30C142"));
        returnToMenuButton.setForeground(Color.decode("#142524"));
        returnToMenuButton.setFont(new Font("Arial", Font.BOLD, 14));
        returnToMenuButton.addActionListener(e -> app.showMenu());
        bottomPanel.add(returnToMenuButton);

        // Add an "Events Today" button
        JButton todayButton = new JButton("Events Today");
        todayButton.setBackground(Color.decode("#30C142"));
        todayButton.setForeground(Color.decode("#142524"));
        todayButton.setFont(new Font("Arial", Font.BOLD, 14));
        todayButton.addActionListener(e -> {
            // Get today's date
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1; // Months are 0-based in Calendar
            String monthName = java.time.Month.of(month).name(); // Get month name
            monthName = monthName.substring(0, 1).toUpperCase() + monthName.substring(1).toLowerCase(); // Capitalize the first letter
            int year = calendar.get(Calendar.YEAR);

            // Show the EventView for today
            app.showEventView(monthName, day, year); // Pass today's date to showEventView
            System.out.println("Today's date: " + day + " " + monthName+ " " + year);
        });
        bottomPanel.add(todayButton);

        panel.add(yearPanel, BorderLayout.NORTH);
        panel.add(monthsPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void updateYearLabel() {
        yearLabel.setText(String.valueOf(currentYear));
    }

    public JPanel getPanel() {
        return panel;
    }
}