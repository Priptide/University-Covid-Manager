import java.util.ArrayList;
import java.util.List;

/**
 * A system created to managed bookable rooms, assistants on shift and bookings
 */
public class BookingSystem {

    // Variables
    private List<BookableRoom> bookableRooms;

    private List<AssistantOnShift> assistantsOnShift;

    private List<Booking> bookings;

    // Constructor

    // Without setting the lists

    /**
     * Will create an empty booking system
     */
    public BookingSystem() {
        this.assistantsOnShift = new ArrayList<>();
        this.bookableRooms = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    /**
     * This version of intialisation requires two lists of pre made sets, although
     * they will be checked on input too assure they are correct
     * 
     * This will only be used for developers and hence can print it's own errors
     * 
     * @param bookableRooms
     * @param assistantOnShifts
     */
    public BookingSystem(List<BookableRoom> inputBookableRooms, List<AssistantOnShift> inputAssistantOnShift) {
        for (BookableRoom room : inputBookableRooms) {
            try {
                addBookableRoom(room);

            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

        for (AssistantOnShift assistant : inputAssistantOnShift) {
            try {
                addAssistantOnShift(assistant);

            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    // Methods

    /**
     * Adds a bookable room too the system, the provided room code must be unique
     * 
     * @param room
     * @throws BookingSystemException
     */
    public void addBookableRoom(BookableRoom room) throws BookingSystemException {

        // If there are bookable rooms in the system make sure this one is unique
        if (!bookableRooms.isEmpty()) {
            for (BookableRoom localRoom : bookableRooms) {
                if (room.getCode().equals(localRoom.getCode()) && room.getTimeSlot().equals(localRoom.getTimeSlot())) {
                    throw new BookingSystemException("Room code is not unique for the given timeslot, "
                            + room.toString() + ", was not added, as it was the same as " + localRoom.toString());
                }
            }
        }

        // Make sure the bookable room is between 7am and 10am
        int timeHour = room.getTimeSlot().getHour();
        if (timeHour >= 10 || timeHour < 7) {
            throw new BookingSystemException("The time slots must be between 7am and 10am, this rooms slot starts at"
                    + timeHour + " therefore the room " + room.toString() + " was not added.");
        }

        // Add the room too the system
        bookableRooms.add(room);
    }

    /**
     * Adds an asistant on shift too the system, provided the
     * 
     * @param assistant
     * @throws BookingSystemException
     */
    public void addAssistantOnShift(AssistantOnShift assistant) throws BookingSystemException {

        // Check there are assistants on shift in the system and if so make sure ours is
        // unique
        if (!assistantsOnShift.isEmpty()) {
            for (AssistantOnShift localAssistant : assistantsOnShift) {
                if (assistant.getEmail().equals(localAssistant.getEmail())
                        && assistant.getTimeSlot().equals(localAssistant.getTimeSlot())) {
                    throw new BookingSystemException("Assistant on shift is already on this time slot, "
                            + assistant.toString() + ", was not added.");
                }
            }
        }

        // Make sure the time slot is within 7-10 (7-8,8-9,9-10) hence 10 isn't
        // inclusive but 7 is
        int timeHour = assistant.getTimeSlot().getHour();
        if (timeHour >= 10 || timeHour < 7) {
            throw new BookingSystemException(
                    "The time slots must be between 7am and 10am, this assistants shift starts at" + timeHour
                            + " therefore the assistant " + assistant.toString() + " was not added.");
        }

        // If it passes all these then add the assistant on shift
        assistantsOnShift.add(assistant);
    }

    /**
     * Gives all bookable rooms in the system
     * 
     * @return Current Bookable Rooms in the system
     */
    public List<BookableRoom> getBookableRooms() {
        return bookableRooms;
    }

    /**
     * Gives all assistants on shift in the system
     * 
     * @return Current Assistants on Shift in the system
     */
    public List<AssistantOnShift> getAssistantsOnShift() {
        return assistantsOnShift;
    }

    /**
     * Adds a booking too the system
     * 
     * @param room         Must not be full
     * @param assistant    Must be free
     * @param isCompleted  Set wether the booking is scheduled or completed
     * @param bookingCode  A unique code for the booking
     * @param studentEmail An email for the student following the '@uok.ac.uk'
     *                     pattern
     * @throws BookingException
     * @return The booking created
     */
    public Booking addBookingWithObjects(BookableRoom room, AssistantOnShift assistant, Boolean isCompleted,
            int bookingCode, String studentEmail) throws BookingException {

        // Make sure the bookings aren't empty
        if (bookableRooms.isEmpty()) {
            throw new BookingException("There are no bookable rooms in the system");
        }
        // Make sure the assistants aren't empty
        if (assistantsOnShift.isEmpty()) {
            throw new BookingException("There are no assistants on shift in the system");
        }
        // Cycle through the bookings to make sure the code is unique
        for (Booking b : bookings) {
            if (b.getCode() == (bookingCode)) {
                throw new BookingException("The booking code must be unique");
            }
        }

        // Check the room exsists in the system and that it isn't full
        if (bookableRooms.contains(room)) {
            if (room.isFull()) {
                throw new BookingException(
                        "The selected room is full or doesn't have enough space for both the tester and student please choose another room or time slot");
            }
        } else {
            throw new BookingException("The selected room doesn't exsist in the current system");
        }

        // Check the assistant is in our system and is free during this time slot
        if (assistantsOnShift.contains(assistant)) {
            if (!assistant.isFree()) {
                throw new BookingException("The selected assistant, " + assistant.toString() + " isn't free");
            }
        } else {
            throw new BookingException("The selected assistant doesn't exsist in the current system");
        }

        // Create the new booking
        Booking localBooking = new Booking(room, assistant, bookingCode, studentEmail, isCompleted);

        // Assuming this all worked we will then add the booking and update the room
        // capacity by 1 test and set the assistant to busy
        bookings.add(localBooking);
        assistantsOnShift.get(assistantsOnShift.indexOf(assistant)).setBusy(true);
        bookableRooms.get(bookableRooms.indexOf(room)).updateOccupancy(1);
        bookableRooms.get(bookableRooms.indexOf(room)).updateStatus();
        return localBooking;
    }

    /**
     * Given any booking it will remove it from the system and update it's children
     * objects
     * 
     * @param booking
     * @throws BookingException Throws if the booking is completed or doesn't exsist
     */
    public void removeBooking(Booking booking) throws BookingException {
        if (bookings.contains(booking)) {
            if (booking.isCompleted()) {
                throw new BookingException("Can't remove an already completed booking");
            }
            // Take one off the bookable rooms occupancy
            booking.getBookableRoom().updateOccupancy(-1);
            // Update the rooms status
            booking.getBookableRoom().updateStatus();
            // Mark the assistant as free
            booking.getAssistantOnShift().setBusy(false);
            // Finally Remove the booking from the system
            bookings.remove(booking);
        } else {
            throw new BookingException("This booking doesn't exsist");
        }
    }

    /**
     * Used to remove a bookable room from the system as long as that room is empty
     * 
     * @param room
     * @throws BookingException
     */
    public void removeRoom(BookableRoom room) throws BookingException {
        if (bookableRooms.contains(room)) {
            if (room.isEmpty()) {
                bookableRooms.remove(room);
            } else {
                throw new BookingException(
                        "The bookable room, " + room.toString() + " is not empty, it must be empty to be remove");
            }
        } else {
            throw new BookingException(
                    "The bookable room, " + room.toString() + " is not avaliable in the current system");
        }
    }

    /**
     * Is used to remove the assistant on shift from the system, providing that
     * assistant is free
     * 
     * @param assistantOnShift
     * @throws BookingException
     */
    public void removeAssistant(AssistantOnShift assistantOnShift) throws BookingException {
        if (assistantsOnShift.contains(assistantOnShift)) {
            if (assistantOnShift.isFree()) {
                assistantsOnShift.remove(assistantOnShift);
            } else {
                throw new BookingException("The assistant on shift, " + assistantOnShift.toString()
                        + " is not free, it must be free to be remove");
            }
        } else {
            throw new BookingException("The assistant on shift, " + assistantOnShift.toString()
                    + " is not avaliable in the current system");
        }
    }

    /**
     * Gives all bookings in the system
     * 
     * @return Gives all the bookings
     */
    public List<Booking> getBookings() {
        return bookings;
    }

}
