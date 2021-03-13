import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Used for all the screens in the application
 */
public class MainMenu {

    /**
     * The main driver for the main menu
     * 
     * @param booking
     * @param resources
     */
    public void mainScreen(BookingSystem booking, UniversityResources resources) {
        // Add a menu header
        System.out.println(
                "University of Knowledge - COVID test\n\nManage Bookings\n\nPlease, enter the number to select your option:\n");
        System.out.println(
                "To manage Bookable Rooms: \n   1. List \n   2. Add \n   3. Remove \nTo manage Assistants on Shift:\n   4. List \n   5. Add\n   6. Remove\nTo manage Bookings:\n   7. List \n   8. Add\n   9. Remove\n   10. Conclude");

        System.out.println(
                "After selecting one the options above, you will be presented other screens.\nIf you press 0, you will be able to return to this main menu.\nPress -1 (or ctrl+c) to quit this application.\n");

        // Create a scanner object
        Scanner getNumberInput = new Scanner(System.in);
        String numericalInput = getNumberInput.nextLine();

        // Create a switch to check input
        switch (numericalInput) {
            case "0":
                mainScreen(booking, resources);
                break;
            case "-1":
                System.exit(0);
                break;
            case "1":
                bookableRoomManager(numericalInput, booking, resources);
                break;
            case "2":
                bookableRoomManager(numericalInput, booking, resources);
                break;
            case "3":
                bookableRoomManager(numericalInput, booking, resources);
                break;
            case "4":
                assistantsOnShiftManager(numericalInput, booking, resources);
                break;
            case "5":
                assistantsOnShiftManager(numericalInput, booking, resources);
                break;
            case "6":
                assistantsOnShiftManager(numericalInput, booking, resources);
                break;
            case "7":
                bookingManager(numericalInput, booking, resources);
                break;
            case "8":
                bookingManager(numericalInput, booking, resources);
                break;
            case "9":
                bookingManager(numericalInput, booking, resources);
                break;
            case "10":
                bookingManager(numericalInput, booking, resources);
                break;
            default:
                handleInvalidInput("Invalid main menu input, '" + numericalInput + "' is not a valid input.", booking,
                        resources);
        }

        getNumberInput.close();
    }

    /**
     * Used for the main menu to manage bookings
     * 
     * @param input
     * @param booking
     * @param resources
     */
    public void bookingManager(String input, BookingSystem bSystem, UniversityResources resources) {
        // Create this for later input
        Scanner getInput = new Scanner(System.in);
        // Check the input and sort into options
        switch (input) {
            case "7":
                // Listing booking
                System.out.println(
                        "University of Knowledge - COVID test\n\nSelect which booking to list:\n1. All\n2. Only bookings status:SCHEDULED\n3. Only bookings status:COMPLETED\n0. Back to main menu.\n-1. Quit application.\n");

                // Await input
                String inputLocal = getInput.nextLine();

                int index = 11;

                switch (inputLocal) {
                    case "2":
                        // Bookings with status scheduled
                        System.out.println("List of Scheduled Bookings ");

                        // Get the bookings from the booking system and cycle through them
                        for (Booking bookingLocal : bSystem.getBookings()) {
                            if (!bookingLocal.isCompleted()) {
                                System.out.println(index + ". " + bookingLocal.toString());
                                index += 1;
                            }
                        }
                        break;
                    case "3":
                        // Bookings with status completed
                        System.out.println("List of Completed Bookings");
                        for (Booking bookingLocal : bSystem.getBookings()) {
                            if (bookingLocal.isCompleted()) {
                                System.out.println(index + ". " + bookingLocal.toString());
                                index += 1;
                            }
                        }
                        break;
                    case "0":
                        mainScreen(bSystem, resources);
                        break;
                    case "-1":
                        System.exit(0);
                        break;
                    default:
                        // List all bookings
                        System.out.println("List of All Bookings");
                        for (Booking bookingLocal : bSystem.getBookings()) {
                            System.out.println(index + ". " + bookingLocal.toString());
                            index += 1;

                        }
                }

                // Add a footer
                System.out.println("0. Back to main menu.\n-1. Quit application.\n");

                String numericalInput = getInput.nextLine();

                // Create a switch to check input
                switch (numericalInput) {
                    case "0":
                        mainScreen(bSystem, resources);
                        break;
                    case "-1":
                        System.exit(0);
                        break;
                    default:
                        handleInvalidInput("Invalid menu input", bSystem, resources);
                }

                break;
            case "8":
                // Add booking
                System.out.println(
                        "University of Knowledge - COVID test\n\nAdding booking (appointment for a COVID test) to the system\n\nList of available time-slots:");

                index = 11;

                List<BookableRoom> bookableRooms = new ArrayList<>();

                List<AssistantOnShift> assistantOnShifts = new ArrayList<>();

                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

                List<LocalDateTime> timesInSystem = new ArrayList<>();

                // Find every room that is not bookable or
                for (BookableRoom bookableLocal : bSystem.getBookableRooms()) {
                    // Update it's status
                    bookableLocal.updateStatus();

                    // If it's not full we cycle through and add a new booking
                    if (!bookableLocal.isFull()) {
                        for (AssistantOnShift assistantOnShift : bSystem.getAssistantsOnShift()) {
                            if (assistantOnShift.isFree()
                                    && bookableLocal.getTimeSlot().equals(assistantOnShift.getTimeSlot())) {
                                if (!timesInSystem.contains(bookableLocal.getTimeSlot())) {
                                    System.out.println(
                                            index + ". " + bookableLocal.getTimeSlot().format(dateTimeFormatter));
                                    bookableRooms.add(bookableLocal);
                                    assistantOnShifts.add(assistantOnShift);
                                    timesInSystem.add(bookableLocal.getTimeSlot());
                                    index += 1;
                                }
                            }
                        }
                    }
                }

                System.out.println(
                        "\nPlease, enter one of the following:\n\nThe sequential ID of an available time-slot and the student email, separated by a white space.\n0. Back to main menu.\n-1. Quit application.\n");

                inputLocal = getInput.nextLine();

                // Create a switch to check input
                switch (inputLocal) {
                    case "0":
                        mainScreen(bSystem, resources);
                        break;
                    case "-1":
                        System.exit(0);
                        break;
                    default:
                        try {
                            String[] inputsStrings = inputLocal.split(" ");

                            int inputInt = Integer.parseInt(inputsStrings[0]);

                            try {
                                inputInt -= 11;
                                if (inputInt >= bookableRooms.size() || inputInt < 0) {
                                    bookingManager(input, bSystem, resources, true,
                                            "The sequential ID is not avaliable ");
                                } else {
                                    Booking localBooking = bSystem.addBookingWithObjects(bookableRooms.get(inputInt),
                                            assistantOnShifts.get(inputInt), false,
                                            bSystem.getBookings().get(bSystem.getBookings().size() - 1).getCode() + 2,
                                            inputsStrings[1]);
                                    System.out
                                            .println("Booking added successfully:\n" + localBooking.toString() + "\n");
                                    bookingManager(input, bSystem, resources, false, "");

                                }

                            } catch (Exception e) {
                                bookingManager(input, bSystem, resources, true, e.toString());
                            }
                        } catch (Exception e) {
                            bookingManager(input, bSystem, resources, true,
                                    "The input given was not an integer or the sequential ID is not avaliable");
                        }
                        break;
                }

                break;
            case "9":
                // Remove booking

                // Add the header
                System.out.println("University of Knowledge - COVID test\n");

                // Create a list for the usable bookings
                List<Booking> bookingsPossible = new ArrayList<>();

                // Bookings with status scheduled
                System.out.println("List of Scheduled Bookings ");

                index = 11;
                // Get the bookings from the booking system and cycle through them
                for (Booking bookingLocal : bSystem.getBookings()) {
                    if (!bookingLocal.isCompleted()) {
                        System.out.println(index + ". " + bookingLocal.toString());
                        bookingsPossible.add(bookingLocal);
                        index += 1;
                    }
                }

                // Add footer
                System.out.println(
                        "Removing booking from the system\n\nPlease, enter one of the following:\n\nThe sequential ID to select the booking to be removed from the listed bookings above.\n0. Back to main menu.\n-1. Quit application.\n");

                // Check for outputs
                inputLocal = getInput.nextLine();

                // Create a switch to check input
                switch (inputLocal) {
                    case "0":
                        mainScreen(bSystem, resources);
                        break;
                    case "-1":
                        System.exit(0);
                        break;
                    default:
                        try {
                            // Try parse the integer from the input string
                            int currentIndex = Integer.parseInt(inputLocal);
                            currentIndex -= 11;
                            if (currentIndex >= bookingsPossible.size() || currentIndex < 0) {
                                bookingManager(input, bSystem, resources, true,
                                        "The sequential ID is " + "not avaliable");
                            } else {
                                Booking localBooking = bookingsPossible.get(currentIndex);
                                bSystem.removeBooking(localBooking);
                                System.out.println("Booking removed successfully:   \n" + bookingsPossible.toString());
                                bookingManager(input, bSystem, resources, false, "");
                            }
                        } catch (Exception e) {
                            bookingManager(input, bSystem, resources, true, "The input given was not an integer ");
                        }
                        break;
                }
                break;
            case "10":
                // Conclude booking
                System.out.println("University of Knowledge - COVID test\n");

                List<Booking> bookingsScheduled = new ArrayList<>();
                index = 11;
                // Bookings with status scheduled
                System.out.println("List of Scheduled Bookings");
                for (Booking bookingLocal : bSystem.getBookings()) {
                    if (!bookingLocal.isCompleted()) {
                        System.out.println(index + ". " + bookingLocal.toString());
                        bookingsScheduled.add(bookingLocal);
                        index += 1;
                    }
                }

                // Add a footer
                System.out.println(
                        "Conclude booking\n\nPlease, enter one of the following:\n\nThe sequential ID to select the booking to be completed.\n0. Back to main menu.\n-1. Quit application.\n");

                inputLocal = getInput.nextLine();

                // Create a switch to check input
                switch (inputLocal) {
                    case "0":
                        mainScreen(bSystem, resources);
                        break;
                    case "-1":
                        System.exit(0);
                        break;
                    default:
                        try {
                            // Try parse the integer from the input string
                            int currentIndex = Integer.parseInt(inputLocal);
                            currentIndex -= 11;
                            if (currentIndex >= bookingsScheduled.size() || currentIndex < 0) {
                                bookingManager(input, bSystem, resources, true,
                                        "The sequential" + " ID is not avaliable");
                            } else {
                                Booking localBooking = bookingsScheduled.get(currentIndex);
                                localBooking.setCompleted(true);
                                System.out.println("Booking completed successfully:\n" + bookingsScheduled.toString());
                                bookingManager(input, bSystem, resources, false, "");
                            }
                        } catch (Exception e) {
                            bookingManager(input, bSystem, resources, true, "The input given was " + "not an integer");
                        }
                        break;
                }
                break;
            default:
                // Only here when there is an unkown error (this shouldn't be possible)
                handleInvalidInput("Unknown booking error", bSystem, resources);
        }
    }

    /**
     * Used for the menu to manage bookings when there is an error or repeated scren
     * 
     * @param input
     * @param bSystem
     * @param resources
     * @param isError
     * @param message
     */
    public void bookingManager(String input, BookingSystem bSystem, UniversityResources resources, boolean isError,
            String message) {
        // Create this for later input
        Scanner getInput = new Scanner(System.in);
        // Check the input and sort into options
        switch (input) {
            case "8":

                // Add header
                if (isError) {
                    System.out.println("Error!    \n" + message);
                } else {
                    System.out.println("\nList of available time-slots:");

                }

                // Add booking
                int index = 11;

                List<BookableRoom> bookableRooms = new ArrayList<>();

                List<AssistantOnShift> assistantOnShifts = new ArrayList<>();

                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

                List<LocalDateTime> timesInSystem = new ArrayList<>();

                // Find every room that is not bookable or
                for (BookableRoom bookableLocal : bSystem.getBookableRooms()) {
                    // Update it's status
                    bookableLocal.updateStatus();

                    // If it's not full we cycle through and add a new booking
                    if (!bookableLocal.isFull()) {
                        for (AssistantOnShift assistantOnShift : bSystem.getAssistantsOnShift()) {
                            if (assistantOnShift.isFree()
                                    && bookableLocal.getTimeSlot().equals(assistantOnShift.getTimeSlot())) {
                                if (!timesInSystem.contains(bookableLocal.getTimeSlot())) {
                                    if (!isError) {
                                        System.out.println(
                                                index + ". " + bookableLocal.getTimeSlot().format(dateTimeFormatter));
                                    }
                                    bookableRooms.add(bookableLocal);
                                    assistantOnShifts.add(assistantOnShift);
                                    timesInSystem.add(bookableLocal.getTimeSlot());
                                    index += 1;
                                }
                            }
                        }
                    }
                }

                if (!isError) {
                    System.out.println(" ");
                }

                System.out.println(
                        "Please, enter one of the following:\n\nThe sequential ID of an available time-slot and the student email, separated by a white space.\n0. Back to main menu.\n-1. Quit application.\n");

                String inputLocal = getInput.nextLine();

                // Create a switch to check input
                switch (inputLocal) {
                    case "0":
                        mainScreen(bSystem, resources);
                        break;
                    case "-1":
                        System.exit(0);
                        break;
                    default:
                        try {
                            String[] inputsStrings = inputLocal.split(" ");

                            int inputInt = Integer.parseInt(inputsStrings[0]);

                            try {
                                inputInt -= 11;
                                if (inputInt >= bookableRooms.size() || inputInt < 0) {
                                    bookingManager(input, bSystem, resources, true,
                                            "The sequential ID is not avaliable ");
                                } else {
                                    Booking localBooking = bSystem.addBookingWithObjects(bookableRooms.get(inputInt),
                                            assistantOnShifts.get(inputInt), false,
                                            bSystem.getBookings().get(bSystem.getBookings().size() - 1).getCode() + 2,
                                            inputsStrings[1]);
                                    System.out
                                            .println("Booking added successfully:\n" + localBooking.toString() + "\n");

                                }

                            } catch (Exception e) {
                                bookingManager(input, bSystem, resources, true, e.toString());
                            }
                        } catch (Exception e) {
                            bookingManager(input, bSystem, resources, true,
                                    "The input given was not an integer or the sequential ID is not avaliable");
                        }
                        break;
                }

                break;
            case "9":
                // Remove booking

                // Add error heading
                if (isError) {
                    System.out.println("Error!" + "\n" + message);

                }

                // Create a list for the usable bookings
                List<Booking> bookingsPossible = new ArrayList<>();

                index = 11;
                // Get the bookings from the booking system and cycle through them
                for (Booking bookingLocal : bSystem.getBookings()) {
                    if (!bookingLocal.isCompleted()) {
                        bookingsPossible.add(bookingLocal);
                        index += 1;
                    }
                }

                // Add footer
                System.out.println(
                        "Please, enter one of the following:\n\nThe sequential ID to select the booking to be removed from the listed bookings above.\n0. Back to main menu.\n-1. Quit application.\n");

                // Check for outputs
                inputLocal = getInput.nextLine();

                // Create a switch to check input
                switch (inputLocal) {
                    case "0":
                        mainScreen(bSystem, resources);
                        break;
                    case "-1":
                        System.exit(0);
                        break;
                    default:
                        try {
                            // Try parse the integer from the input string
                            int currentIndex = Integer.parseInt(inputLocal);
                            currentIndex -= 11;
                            if (currentIndex >= bookingsPossible.size() || currentIndex < 0) {
                                bookingManager(input, bSystem, resources, true,
                                        "The sequential ID is " + "not avaliable");
                            } else {
                                Booking localBooking = bookingsPossible.get(currentIndex);
                                bSystem.removeBooking(localBooking);
                                System.out.println("Booking removed successfully:   \n" + bookingsPossible.toString());
                                bookingManager(input, bSystem, resources, false, "");
                            }
                        } catch (Exception e) {
                            bookingManager(input, bSystem, resources, true, "The input given was not an integer ");
                        }
                        break;
                }
                break;
            case "10":
                // Conclude booking

                if (isError) {
                    System.out.println("Error!   \n" + message);

                }

                // Add footer message
                System.out.println(
                        "Please, enter one of the following:\n\nThe sequential ID to select the booking to be completed.\n0. Back to main menu.\n-1. Quit application.\n");

                List<Booking> bookingsScheduled = new ArrayList<>();
                index = 11;
                // Bookings with status scheduled
                for (Booking bookingLocal : bSystem.getBookings()) {
                    if (!bookingLocal.isCompleted()) {
                        bookingsScheduled.add(bookingLocal);
                        index += 1;
                    }
                }

                inputLocal = getInput.nextLine();

                // Create a switch to check input
                switch (inputLocal) {
                    case "0":
                        mainScreen(bSystem, resources);
                        break;
                    case "-1":
                        System.exit(0);
                        break;
                    default:
                        try {
                            // Try parse the integer from the input string
                            int currentIndex = Integer.parseInt(inputLocal);
                            currentIndex -= 11;
                            if (currentIndex >= bookingsScheduled.size() || currentIndex < 0) {
                                bookingManager(input, bSystem, resources, true,
                                        "The sequential " + "ID is not avaliable");
                            } else {
                                Booking localBooking = bookingsScheduled.get(currentIndex);
                                localBooking.setCompleted(true);
                                System.out.println("Booking completed successfully:\n" + bookingsScheduled.toString());
                                bookingManager(input, bSystem, resources, false, "");
                            }
                        } catch (Exception e) {
                            bookingManager(input, bSystem, resources, true, "The input given" + " was not an integer");
                        }
                        break;
                }
                break;
            default:
                // Only here when there is an unkown error (this shouldn't be possible)
                handleInvalidInput("Unknown booking error", bSystem, resources);
        }
    }

    /**
     * Deals with any input errors within the code that don't require their own
     * function
     * 
     * @param message
     * @param booking
     * @param resources
     */
    public void handleInvalidInput(String message, BookingSystem booking, UniversityResources resources) {
        System.out.println("Error!  \n" + message
                + "\nPlease, enter one of the following:\n\n0. Back to main menu.\n-1. Quit application.\n");
        // Create a scanner object
        Scanner getNumberInput = new Scanner(System.in);
        String numericalInput = getNumberInput.nextLine();

        // Create a switch to check input
        switch (numericalInput) {
            case "0":
                mainScreen(booking, resources);
                break;
            case "-1":
                getNumberInput.close();
                System.exit(0);
                break;
            default:
                handleInvalidInput("Invalid menu input", booking, resources);
        }
        getNumberInput.close();
    }

    /**
     * This is used to managed the bookable rooms side of the main menu
     * 
     * @param input
     * @param bSystem
     * @param resources
     */
    public void bookableRoomManager(String input, BookingSystem bSystem, UniversityResources resources) {
        // Create this for later input
        Scanner getInput = new Scanner(System.in);
        // Check through the input
        switch (input) {
            case "1":
                // Add the header text
                System.out.println("University of Knowledge - COVID test\n\nList of Bookable Rooms:");

                // Create a index for the list
                int index = 11;

                // List all of the bookable items in the system with their index
                for (BookableRoom bRoom : bSystem.getBookableRooms()) {
                    System.out.println(index + ". " + bRoom.toString());
                    index += 1;
                }

                // Add the footer
                System.out.println("\n0. Back to main menu.\n-1. Quit application.");

                // Await input
                String inputLocal = getInput.nextLine();

                // Check for valid or invalid input and deal with it appropriately
                if (inputLocal.equals("0")) {
                    mainScreen(bSystem, resources);
                } else if (inputLocal.equals("-1")) {
                    getInput.close();
                    System.exit(0);
                } else {
                    handleInvalidInput("Invalid menu input of, " + inputLocal, bSystem, resources);
                }
                break;
            case "2":
                // Add the header text
                System.out.println("University of Knowledge - COVID test\n\nAdding bookable room\n\nList of Rooms:");

                // Create a index for the list
                index = 11;
                // List all of the current rooms in the system with their index
                for (Room Room : resources.getRooms()) {
                    System.out.println(index + ". " + Room.toString());
                    index += 1;
                }

                // Add footer
                System.out.println(
                        "\nPlease, enter one of the following:\n\nThe sequential ID listed to a room, a date (dd/mm/yyyy), and a time (HH:MM),\nseparated by a white space.\n0. Back to main menu.\n-1. Quit application.\n");

                // Await input
                inputLocal = getInput.nextLine();
                // Check for valid input and handle any other as an input
                if (inputLocal.equals("0")) {
                    mainScreen(bSystem, resources);
                } else if (inputLocal.equals("-1")) {
                    getInput.close();
                    System.exit(0);
                } else {
                    try {
                        BookableRoom newRoom = new BookableRoom(resources.getRooms(), inputLocal);
                        bSystem.addBookableRoom(newRoom);
                        System.out.println("Bookable Room added successfully:\n" + newRoom.toString());
                        bookableRoomManager(input, bSystem, resources, false, "");
                    } catch (Exception e) {
                        bookableRoomManager(input, bSystem, resources, true, e.toString());
                    }
                }
                break;
            case "3":
                // Add the header text
                System.out.println("University of Knowledge - COVID test\n\nList of Empty Bookable Rooms:");

                // Create a index for the list
                index = 11;
                List<BookableRoom> emptyRooms = new ArrayList<>();
                // List all of the bookable items in the system with their index
                for (BookableRoom bRoom : bSystem.getBookableRooms()) {
                    if (bRoom.isEmpty()) {
                        emptyRooms.add(bRoom);
                        System.out.println(index + ". " + bRoom.toString());
                        index += 1;
                    }
                }

                // Add footer
                System.out.println(
                        "Removing bookable Room\nPlease, enter one of the following:\n\nThe sequential ID to select the bookable room to be removed.\n0. Back to main menu.\n-1. Quit application.\n");

                // Await input
                inputLocal = getInput.nextLine();
                // Check for valid input and handle any other as an input
                if (inputLocal.equals("0")) {
                    mainScreen(bSystem, resources);
                } else if (inputLocal.equals("-1")) {
                    getInput.close();
                    System.exit(0);
                } else {
                    try {
                        // Try parse the integer from the input string
                        int currentIndex = Integer.parseInt(inputLocal);
                        currentIndex -= 11;
                        if (currentIndex >= emptyRooms.size() || currentIndex < 0) {
                            bookableRoomManager(input, bSystem, resources, true,
                                    "The sequential " + "ID is not avaliable");
                        } else {
                            BookableRoom roomLocal = emptyRooms.get(currentIndex);
                            bSystem.removeRoom(roomLocal);
                            System.out.println("Bookable Room removed successfully:\n" + roomLocal.toString());
                            bookableRoomManager(input, bSystem, resources, false, "");
                        }
                    } catch (Exception e) {
                        bookableRoomManager(input, bSystem, resources, true, "The input given" + " was not an integer");
                    }
                }
                break;
            default:
                // This is an invalid input therefore we simply let it pass through

        }
    }

    /**
     * Use this when you are re-running a bookable room function with an error in it
     * 
     * @param input
     * @param bSystem
     * @param resources
     * @param isError
     * @param errorString
     */
    public void bookableRoomManager(String input, BookingSystem bSystem, UniversityResources resources, Boolean isError,
            String errorString) {
        // Create this for later input
        Scanner getInput = new Scanner(System.in);
        // Check through the input
        switch (input) {
            case "1":
                bookableRoomManager(input, bSystem, resources);
                break;
            case "2":
                // Create a new bookable room

                if (Boolean.FALSE.equals(isError)) {
                    // Add custom message
                    System.out.println(
                            "Please, enter one of the following:\n\nThe sequential ID listed to a room, a date (dd/mm/yyyy), and a time (HH:MM),\nseparated by a white space.\n0. Back to main menu.\n-1. Quit application.\n");
                } else {
                    System.out.println("Error! \n" + errorString
                            + "\nPlease, enter one of the following:\n\nThe sequential ID listed to a room, a date (dd/mm/yyyy), and a time (HH:MM),\nseparated by a white space.\n0. Back to main menu.\n-1. Quit application.\n");

                }
                // Await input
                String inputLocal = getInput.nextLine();
                // Check for valid input and handle any other as an input
                if (inputLocal.equals("0")) {
                    mainScreen(bSystem, resources);
                } else if (inputLocal.equals("-1")) {
                    getInput.close();
                    System.exit(0);
                } else {
                    try {
                        BookableRoom newRoom = new BookableRoom(resources.getRooms(), inputLocal);
                        bSystem.addBookableRoom(newRoom);
                        System.out.println("Bookable Room added successfully:\n" + newRoom.toString());
                        bookableRoomManager(input, bSystem, resources, false, "");
                    } catch (Exception e) {
                        bookableRoomManager(input, bSystem, resources, true, e.toString());
                    }
                }
                break;
            case "3":
                // Remove a bookable room

                if (Boolean.FALSE.equals(isError)) {
                    // Add custom message
                    System.out.println(
                            "Please, enter one of the following:\n\nThe sequential ID to select the bookable room to be removed.\n0. Back to main menu.\n-1. Quit application.\n");
                } else {
                    System.out.println("Error! \n" + errorString
                            + "\nPlease, enter one of the following:\n\nThe sequential ID to select the bookable room to be removed.\n0. Back to main menu.\n-1. Quit application.\n");

                }

                // Create a index for the list
                int index = 11;
                List<BookableRoom> emptyRooms = new ArrayList<>();
                // List all of the bookable items in the system with their index
                for (BookableRoom bRoom : bSystem.getBookableRooms()) {
                    if (bRoom.isEmpty()) {
                        emptyRooms.add(bRoom);
                        index += 1;
                    }
                }

                // Await input
                inputLocal = getInput.nextLine();
                // Check for valid input and handle any other as an input
                if (inputLocal.equals("0")) {
                    mainScreen(bSystem, resources);
                } else if (inputLocal.equals("-1")) {
                    getInput.close();
                    System.exit(0);
                } else {
                    try {
                        // Try parse the integer from the input string
                        int currentIndex = Integer.parseInt(inputLocal);
                        currentIndex -= 10;
                        if (currentIndex >= emptyRooms.size() || currentIndex < 0) {
                            bookableRoomManager(input, bSystem, resources, true,
                                    "The sequential" + " ID is not avaliable");
                        } else {
                            BookableRoom roomLocal = emptyRooms.get(currentIndex);
                            bSystem.removeRoom(roomLocal);
                            System.out.println("Bookable Room removed successfully:\n" + roomLocal.toString());
                            bookableRoomManager(input, bSystem, resources, false, "");
                        }
                    } catch (Exception e) {
                        bookableRoomManager(input, bSystem, resources, true, "The input given " + "was not an integer");
                    }
                }

                break;
            default:
                System.out.println("Invalid " + "Input");

        }
    }

    /**
     * This is used to managed the assistants side of the main menu
     * 
     * @param input
     * @param bSystem
     * @param resources
     */
    public void assistantsOnShiftManager(String input, BookingSystem bSystem, UniversityResources resources) {
        // Create this for later input
        Scanner getInput = new Scanner(System.in);
        // Check through the input
        switch (input) {
            case "4":
                // Add the header text
                System.out.println("University of Knowledge - COVID test\n\nList of Assistants on Shift:");

                // Create a index for the list
                int index = 11;

                // List all of the assistants on shift in the system with their index
                for (AssistantOnShift assistantOnShift : bSystem.getAssistantsOnShift()) {
                    System.out.println(index + ". " + assistantOnShift.toString());
                    index += 1;
                }

                // Add the footer
                System.out.println("\n0. Back to main menu.\n-1. Quit application.\n");

                // Await input
                String inputLocal = getInput.nextLine();

                // Check for valid or invalid input and deal with it appropriately
                if (inputLocal.equals("0")) {
                    mainScreen(bSystem, resources);
                } else if (inputLocal.equals("-1")) {
                    getInput.close();
                    System.exit(0);
                } else {
                    handleInvalidInput("Invalid assistant menu input of, " + inputLocal, bSystem, resources);
                }
                break;
            case "5":
                // Add the header text
                System.out.println(
                        "University of Knowledge - COVID test\n\nAdding assistant on shift\n\nList of Assistants:");

                // Create a index for the list
                index = 11;
                // List all of the current rooms in the system with their index
                for (Assistant assistant : resources.getAssistants()) {
                    System.out.println(index + ". " + assistant.toString());
                    index += 1;
                }

                // Add footer
                System.out.println(
                        "\nPlease, enter one of the following:\n\nThe sequential ID listed of an assistant, a date (dd/mm/yyyy),separated by a white space.\n0. Back to main menu.\n-1. Quit application.\n");

                // Await input
                inputLocal = getInput.nextLine();
                // Check for valid input and handle any other as an input
                if (inputLocal.equals("0")) {
                    mainScreen(bSystem, resources);
                } else if (inputLocal.equals("-1")) {
                    getInput.close();
                    System.exit(0);
                } else {
                    try {
                        // Create three assistants one for each shift
                        AssistantOnShift newAssistant7 = new AssistantOnShift(resources.getAssistants(),
                                inputLocal + " 07:00");
                        AssistantOnShift newAssistant8 = new AssistantOnShift(resources.getAssistants(),
                                inputLocal + " 08:00");
                        AssistantOnShift newAssistant9 = new AssistantOnShift(resources.getAssistants(),
                                inputLocal + " 09:00");
                        bSystem.addAssistantOnShift(newAssistant7);
                        bSystem.addAssistantOnShift(newAssistant8);
                        bSystem.addAssistantOnShift(newAssistant9);
                        String shiftMessage = "Assistant on Shift added successfully:\n";
                        System.out.println(shiftMessage + newAssistant7.toString());
                        System.out.println(shiftMessage + newAssistant8.toString());
                        System.out.println(shiftMessage + newAssistant9.toString());
                        assistantsOnShiftManager(input, bSystem, resources, false, "");
                    } catch (Exception e) {
                        assistantsOnShiftManager(input, bSystem, resources, true, e.toString());
                    }
                }
                break;
            case "6":
                // Add the header text
                System.out.println("University of Knowledge - COVID test\n\nList of free assistants on shift:");

                // Create a index for the list
                index = 11;
                List<AssistantOnShift> emptyAssistants = new ArrayList<>();
                // List all of the bookable items in the system with their index
                for (AssistantOnShift assistantOnShift : bSystem.getAssistantsOnShift()) {
                    if (assistantOnShift.isFree()) {
                        emptyAssistants.add(assistantOnShift);
                        System.out.println(index + ". " + assistantOnShift.toString());
                        index += 1;
                    }
                }

                // Add footer
                System.out.println(
                        "Removing assistants on shift\n\nPlease, enter one of the following:\n\nThe sequential ID to select the assistant on shift to be removed.\n0. Back to main menu.\n-1. Quit application.\n");

                // Await input
                inputLocal = getInput.nextLine();
                // Check for valid input and handle any other as an input
                if (inputLocal.equals("0")) {
                    mainScreen(bSystem, resources);
                } else if (inputLocal.equals("-1")) {
                    getInput.close();
                    System.exit(0);
                } else {
                    try {
                        // Try parse the integer from the input string
                        int currentIndex = Integer.parseInt(inputLocal);
                        currentIndex -= 11;
                        if (currentIndex >= emptyAssistants.size() || currentIndex < 0) {
                            assistantsOnShiftManager(input, bSystem, resources, true,
                                    "The sequential ID is not avaliable");
                        } else {
                            AssistantOnShift assistantOnShift = emptyAssistants.get(currentIndex);
                            bSystem.removeAssistant(assistantOnShift);
                            System.out.println("Assistant removed successfully:\n" + assistantOnShift.toString());
                            assistantsOnShiftManager(input, bSystem, resources, false, "");
                        }
                    } catch (Exception e) {
                        assistantsOnShiftManager(input, bSystem, resources, true, "The input given was not an integer");
                    }
                }
                break;
            default:
                System.out.println("Invalid Input");

        }
    }

    /**
     * Use this when you are re-running an assistant function with an error in it
     * 
     * @param input
     * @param bSystem
     * @param resources
     * @param isError
     * @param errorString
     */
    public void assistantsOnShiftManager(String input, BookingSystem bSystem, UniversityResources resources,
            Boolean isError, String errorString) {
        // Create this for later input
        Scanner getInput = new Scanner(System.in);
        // Check through the input
        switch (input) {
            case "4":
                assistantsOnShiftManager(input, bSystem, resources);
                break;
            case "5":
                // Create a new assistant on shift

                if (Boolean.FALSE.equals(isError)) {
                    // Add custom message
                    System.out.println(
                            "Please, enter one of the following:\n\nThe sequential ID of an assistant and date (dd/mm/yyyy), separated by a white space.\n0. Back to main menu.\n-1. Quit application.\n");
                } else {
                    System.out.println("Error!\n" + errorString
                            + "\nPlease, enter one of the following:\n\nThe sequential ID of an assistant and date (dd/mm/yyyy), separated by a white space.\n0. Back to main menu.\n-1. Quit application.\n");

                }
                // Await input
                String inputLocal = getInput.nextLine();
                // Check for valid input and handle any other as an input
                if (inputLocal.equals("0")) {
                    mainScreen(bSystem, resources);
                } else if (inputLocal.equals("-1")) {
                    getInput.close();
                    System.exit(0);
                } else {
                    try {
                        // Create three assistants one for each shift
                        AssistantOnShift newAssistant7 = new AssistantOnShift(resources.getAssistants(),
                                inputLocal + " 07:00");
                        AssistantOnShift newAssistant8 = new AssistantOnShift(resources.getAssistants(),
                                inputLocal + " 08:00");
                        AssistantOnShift newAssistant9 = new AssistantOnShift(resources.getAssistants(),
                                inputLocal + " 09:00");
                        bSystem.addAssistantOnShift(newAssistant7);
                        bSystem.addAssistantOnShift(newAssistant8);
                        bSystem.addAssistantOnShift(newAssistant9);
                        String shiftMessage = "Assistant on Shift added successfully:\n";
                        System.out.println(shiftMessage + newAssistant7.toString());
                        System.out.println(shiftMessage + newAssistant8.toString());
                        System.out.println(shiftMessage + newAssistant9.toString());
                        assistantsOnShiftManager(input, bSystem, resources, false, "");
                    } catch (Exception e) {
                        assistantsOnShiftManager(input, bSystem, resources, true, e.toString());
                    }
                }
                break;
            case "6":
                // Remove an assistant on shift

                if (Boolean.FALSE.equals(isError)) {
                    // Add custom message
                    System.out.println(
                            "Please, enter one of the following:\n\nThe sequential ID to select the assistant on shift to be removed.\n0. Back to main menu.\n-1. Quit application.\n");
                } else {
                    System.out.println("Error!\n" + errorString
                            + "\nPlease, enter one of the following:\n\nThe sequential ID to select the assistant on shift to be removed.\n0. Back to main menu.\n-1. Quit application.\n");

                }

                // Create a index for the list
                int index = 11;
                List<AssistantOnShift> emptyAssistants = new ArrayList<>();
                // List all of the bookable items in the system with their index
                for (AssistantOnShift assistantOnShift : bSystem.getAssistantsOnShift()) {
                    if (assistantOnShift.isFree()) {
                        emptyAssistants.add(assistantOnShift);
                        index += 1;
                    }
                }

                // Await input
                inputLocal = getInput.nextLine();
                // Check for valid input and handle any other as an input
                if (inputLocal.equals("0")) {
                    mainScreen(bSystem, resources);
                } else if (inputLocal.equals("-1")) {
                    getInput.close();
                    System.exit(0);
                } else {
                    try {
                        // Try parse the integer from the input string
                        int currentIndex = Integer.parseInt(inputLocal);
                        currentIndex -= 11;
                        if (currentIndex >= emptyAssistants.size() || currentIndex < 0) {
                            assistantsOnShiftManager(input, bSystem, resources, true,
                                    "The sequential ID is not avaliable");
                        } else {
                            AssistantOnShift assistantOnShift = emptyAssistants.get(currentIndex);
                            bSystem.removeAssistant(assistantOnShift);
                            System.out.println("Assistant removed successfully:\n" + assistantOnShift.toString());
                            assistantsOnShiftManager(input, bSystem, resources, false, "");
                        }
                    } catch (Exception e) {
                        assistantsOnShiftManager(input, bSystem, resources, true, "The input given was not an integer");
                    }
                }

                break;
            default:
                System.out.println("Invalid Input, " + input);

        }
    }

    /**
     * Preload a booking system using the already created university resources
     * 
     * @param resources
     * @return A preloaded BookingSystem
     */
    public BookingSystem preLoadBooking(UniversityResources resources) {
        BookingSystem booking = new BookingSystem();
        try {
            // Get a list of the current assistants in the system
            List<Assistant> assistants = resources.getAssistants();

            // Setting up a local time constructor of today so we can setup our rooms.
            LocalDateTime currentDate = LocalDateTime.now().with(LocalTime.of(7, 0));

            AssistantOnShift localBookingAssistant = null;
            BookableRoom locaBookableRoom = null;
            AssistantOnShift localBookingAssistant2 = null;
            BookableRoom locaBookableRoom2 = null;

            int index = 0;

            // Loop through all assistants
            for (Assistant assistant : assistants) {
                if (index < 2) {
                    AssistantOnShift shift1 = new AssistantOnShift(assistant, currentDate);
                    AssistantOnShift shift2 = new AssistantOnShift(assistant, currentDate.with(LocalTime.of(8, 0)));
                    AssistantOnShift shift3 = new AssistantOnShift(assistant, currentDate.with(LocalTime.of(9, 0)));
                    booking.addAssistantOnShift(shift1);
                    booking.addAssistantOnShift(shift2);
                    booking.addAssistantOnShift(shift3);
                    localBookingAssistant = shift1;
                    localBookingAssistant2 = shift3;
                }
                index += 1;

            }

            // Get a list of all the rooms in the system
            List<Room> rooms = resources.getRooms();

            index = 0;
            // Loop through all assistants
            for (Room room : rooms) {
                if (index < 3) {
                    BookableRoom room1 = new BookableRoom(room, currentDate, 10);
                    BookableRoom room2 = new BookableRoom(room, currentDate.with(LocalTime.of(8, 0)), 0);
                    BookableRoom room3 = new BookableRoom(room, currentDate.with(LocalTime.of(9, 0)), 20);
                    booking.addBookableRoom(room1);
                    booking.addBookableRoom(room2);
                    booking.addBookableRoom(room3);
                    locaBookableRoom = room1;
                    locaBookableRoom2 = room3;
                }
                index += 1;

            }
            booking.addBookingWithObjects(locaBookableRoom, localBookingAssistant, true, 1237,
                    "studentEmail@uok.ac.uk");
            booking.addBookingWithObjects(locaBookableRoom2, localBookingAssistant2, false, 1347,
                    "studentNew@uok.ac.uk");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return booking;
    }

    /**
     * Preload the system with three rooms and three assistants
     * 
     * @return Preloaded University Resources
     */
    public UniversityResources preLoadResources() {
        UniversityResources resources = new UniversityResources();
        try {
            Room room1 = new Room("theRoom", 30);
            Room room2 = new Room("mainRoom", 20);
            Room room3 = new Room("secondRoom", 50);
            Room room4 = new Room("BigRoom", 75);
            resources.addRoom(room1);
            resources.addRoom(room2);
            resources.addRoom(room3);
            resources.addRoom(room4);
            Assistant assistant1 = new Assistant("assitantuno@uok.ac.uk", "First Assistant");
            Assistant assistant2 = new Assistant("assitantduo@uok.ac.uk", "Main Assistant");
            Assistant assistant3 = new Assistant("assitanttrio@uok.ac.uk", "Main Assistant");
            resources.addAssistant(assistant1);
            resources.addAssistant(assistant2);
            resources.addAssistant(assistant3);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return resources;
    }

}
