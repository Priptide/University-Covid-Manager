/**
 * The driver of the app
 */
public class BookingApp {
    public static void main(String[] args) {
        // Create a main menu
        MainMenu mainMenu = new MainMenu();

        // Pre load resources
        UniversityResources resources = mainMenu.preLoadResources();
        BookingSystem booking = mainMenu.preLoadBooking(resources);

        // Run the main menu
        mainMenu.mainScreen(booking, resources);
    }
}
