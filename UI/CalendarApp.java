package UI;

import java.awt.*;
import javax.swing.*;

public class CalendarApp {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    // References to views
    private MonthView monthView;
    private DayView dayView;
    private EventView eventView;
    private Menu menu; // Add a reference to the Menu class

    public CalendarApp() {
        // Create the main frame
        frame = new JFrame("Calendar App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Set up CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize views
        menu = new Menu(this); // Initialize the Menu
        monthView = new MonthView(this);
        dayView = new DayView(this);
        eventView = new EventView(this);

        // Add views to the CardLayout
        mainPanel.add(menu.getPanel(), "Menu"); // Add the Menu to the CardLayout
        mainPanel.add(monthView.getPanel(), "MonthView");
        mainPanel.add(dayView.getPanel(), "DayView");
        mainPanel.add(eventView.getPanel(), "EventView");


        cardLayout.show(mainPanel, "MonthView");

        // Add the main panel to the frame
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // Methods to navigate between views
    public void showMenu() {
        cardLayout.show(mainPanel, "Menu");
    }

    public void showMonthView() {
        cardLayout.show(mainPanel, "MonthView");
    }

    public void showDayView(String monthYear) {
        try {
            if (monthYear == null || monthYear.trim().isEmpty()) {
                System.err.println("Invalid monthYear format: " + monthYear);
                return; // Do nothing if the monthYear is invalid
            }
            String[] parts = monthYear.split(" ");
            if (parts.length < 2) {
                throw new IllegalArgumentException("Invalid monthYear format: " + monthYear);
            }
            String month = parts[0];
            int year = Integer.parseInt(parts[1]);
            dayView.setMonthAndYear(month, year); // Update DayView with the selected month and year
            cardLayout.show(mainPanel, "DayView");
        } catch (Exception e) {
            System.err.println("Error in showDayView: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void showEventView(String month, int day, int year) {
        eventView.setDay(month, day, year); // Pass the year dynamically
        cardLayout.show(mainPanel, "EventView");
    }

    public static void main(String[] args) {
        new CalendarApp();
    }

}
