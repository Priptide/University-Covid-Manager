import java.time.format.DateTimeFormatter;

/**
 * Used for the status of the bookable room
 */
enum BookingStatus {
    SCHEDULED, COMPLETED
}

/**
 * Used to create a booking
 */
public class Booking {
    // Variables
    private BookableRoom room;

    private AssistantOnShift assistant;

    private int bookingCode;

    private String studentEmail;

    private BookingStatus status;

    // Constructors

    /**
     * Used to create a booking without setting the booking status and hence it will
     * be set to scheduled
     * 
     * @param room
     * @param assistant
     * @param bookingCode
     * @param studentEmail
     * @throws BookingException For any formatting errors
     */
    public Booking(BookableRoom room, AssistantOnShift assistant, int bookingCode, String studentEmail)
            throws BookingException {
        checkInput(room, assistant, studentEmail);
        this.room = room;
        this.assistant = assistant;
        this.bookingCode = bookingCode;
        this.studentEmail = studentEmail;
        this.status = BookingStatus.SCHEDULED;
    }

    /**
     * Used to create a booking with setting whether it is or isn't completed when
     * creating the booking
     * 
     * @param room
     * @param assistant
     * @param bookingCode
     * @param studentEmail
     * @param isCompleted
     * @throws BookingException Throws on formatting errors
     */
    public Booking(BookableRoom room, AssistantOnShift assistant, int bookingCode, String studentEmail,
            boolean isCompleted) throws BookingException {
        checkInput(room, assistant, studentEmail);
        this.room = room;
        this.assistant = assistant;
        this.bookingCode = bookingCode;
        this.studentEmail = studentEmail;
        setCompleted(isCompleted);
    }

    // Methods

    /**
     * Sets the status completed<code>(true)</code> or scheduled<code>(false)</code>
     * 
     * @param isCompleted
     */
    public void setCompleted(boolean isCompleted) {
        if (isCompleted) {
            this.status = BookingStatus.COMPLETED;
        } else {
            this.status = BookingStatus.SCHEDULED;
        }
    }

    /**
     * Checks the room is empty, assistant is free and student email follows the
     * pattern "*@uok.ac.uk" and throws an exception if not. Although these are
     * checked in the system we check most again to make sure there isn't any
     * discrepancy
     * 
     * @param room
     * @param assistant
     * @param studentEmail
     * @throws BookingException
     */
    public void checkInput(BookableRoom room, AssistantOnShift assistant, String studentEmail) throws BookingException {

        // Just to make sure the status is correct
        room.updateStatus();
        // Check room isn't full
        if (room.isFull()) {
            throw new BookingException("The selected room, " + room.toString() + " is full!");
        }

        // Check assistant is free
        if (!assistant.isFree()) {
            throw new BookingException("The selected assistant, " + assistant.toString() + " isn't free!");
        }

        // Check the student email is valid

        // Split the string into two parts at the @ symbol
        String[] checkStrings = studentEmail.split("@");

        // Check the second-half of the string array is following our email pattern
        if (!checkStrings[1].equals("uok.ac.uk")) {
            // If the email is invalid send an error
            throw new BookingException("The student email, " + studentEmail + " doesn't end with @uok.ac.uk");
        }

        // Check that the time slot line up
        if (!room.getTimeSlot().equals(assistant.getTimeSlot())) {
            // If they aren't the same time slot then we throw an exception
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            throw new BookingException("The time slot of the room, " + room.getTimeSlot().format(dateTimeFormatter)
                    + " is different to the assistant's time slot, "
                    + assistant.getTimeSlot().format(dateTimeFormatter));
        }
    }

    /**
     * Returns the string format of a given booking
     * 
     * @return | <code>dd/mm/yyyy HH:MM</code> | <code>status</code> |
     *         <code>assistant_email</code> | <code>room_code</code> |
     *         <code>student_email</code> |
     */
    public String toString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return "| " + room.getTimeSlot().format(dateTimeFormatter) + " | " + status + " | " + assistant.getEmail()
                + " | " + room.getCode() + " | " + studentEmail + " |";
    }

    /**
     * Gets the unique code for this booking
     * 
     * @return Booking Code
     */
    public int getCode() {
        return this.bookingCode;
    }

    /**
     * Shows wether or not the booking is completed
     * 
     * @return True if the booking is completed
     */
    public boolean isCompleted() {
        if (status == BookingStatus.COMPLETED) {
            return true;
        }
        return false;
    }

    /**
     * Gives the assistant on shift for the booking
     * 
     * @return Assistant On Shift
     */
    public AssistantOnShift getAssistantOnShift() {
        return assistant;
    }

    /**
     * Gives the bookable room for the booking
     * 
     * @return Bookable Room
     */
    public BookableRoom getBookableRoom() {
        return room;
    }

}
