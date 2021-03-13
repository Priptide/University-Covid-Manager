import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Used to tell the status of the bookable room
 */
enum RoomStatus {
    EMPTY, AVAILABLE, FULL
}

/**
 * Used to create a bookable room
 */
public class BookableRoom {
    // Variables
    private Room room;

    private LocalDateTime timeSlot;

    private int occupancy;

    private RoomStatus roomStatus;

    // Contructors

    /**
     * Use this version of constructor to create without setting the room status
     * 
     * @param room
     * @param timeSlot
     * @param occupancy
     * @throws OccupancyIncorrect If any error in format occurs
     */
    public BookableRoom(Room room, LocalDateTime timeSlot, int occupancy) throws OccupancyIncorrect {
        this.room = room;
        this.timeSlot = timeSlot;
        this.occupancy = occupancy;

        // Check the occupancy
        checkInput(occupancy);

        // Check and set the room status
        updateStatus();

    }

    /**
     * Use this constructor when setting the roomstatus on creation
     * 
     * @param room
     * @param timeSlot
     * @param occupancy
     * @param roomStatus
     * @throws OccupancyIncorrect For any formatting errors
     */
    public BookableRoom(Room room, LocalDateTime timeSlot, int occupancy, RoomStatus roomStatus)
            throws OccupancyIncorrect {
        this.room = room;
        this.timeSlot = timeSlot;
        this.roomStatus = roomStatus;
        this.occupancy = occupancy;

        // Check the occupancy
        checkInput(occupancy);

        // In case the room status is set wrong we will update it too
        updateStatus();

    }

    /**
     * This is used for creating a bookable from the menu
     * 
     * @param rooms
     * @param inputString
     * @throws OccupancyIncorrect
     * @throws BookableRoomCreation
     */
    public BookableRoom(List<Room> rooms, String inputString) throws OccupancyIncorrect, BookableRoomCreation {

        String[] inputStrings = inputString.split(" ");
        int index = 0;
        try {
            index = Integer.parseInt(inputStrings[0]);
            index -= 11;
        } catch (Exception e) {
            throw new BookableRoomCreation("The input, " + inputStrings[0] + " isn't an integer");
        }
        if (index >= rooms.size() || index < 0) {
            throw new BookableRoomCreation("The ID you selected is not in the current list");
        }

        LocalDateTime setTime = LocalDateTime.now();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            setTime = LocalDateTime.parse(inputStrings[1] + " " + inputStrings[2], formatter);
        } catch (Exception e) {
            throw new BookableRoomCreation("Please check you have formatted the date correctly as, " + inputStrings[1]
                    + " doesn't follow the form dd/mm/yyyy HH:MM" + e.toString());
        }

        // Check the time isn't zero and is within 7-9am start times
        if (setTime.getMinute() == 0) {
            if (setTime.getHour() < 7 && setTime.getHour() >= 10) {
                throw new BookableRoomCreation("The hour, " + setTime.getHour()
                        + " is not avaliable make sure it's either 07:00, 08:00 or 09:00");
            }
        } else {
            throw new BookableRoomCreation("Please make sure your minutes are 00 as time slots are 1 hour!");
        }

        try {
            this.room = rooms.get(index);
            this.timeSlot = setTime;
            this.occupancy = 0;
        } catch (Exception e) {
            throw new BookableRoomCreation("System error, " + e.toString());
        }

        // Check the occupancy
        checkInput(occupancy);

        // In case the room status is set wrong we will update it too
        updateStatus();

    }

    // Methods

    /**
     * Update the status of the room, this is mainly used for premade methods but
     * can be called if needed seperatly
     */
    public void updateStatus() {
        if (occupancy == 0) {
            this.roomStatus = RoomStatus.EMPTY;
        } else if (occupancy == room.getCapacity()) {
            this.roomStatus = RoomStatus.FULL;
        } else {
            this.roomStatus = RoomStatus.AVAILABLE;
        }
    }

    /**
     * Set a bookable rooms occupancy
     * 
     * @param occupancy
     */
    public void setOccupancy(int occupancy) throws OccupancyIncorrect {
        this.occupancy = occupancy;
        checkInput(occupancy);
        updateStatus();
    }

    /**
     * Prints in the format: | <code>dd/mm/yyyy HH:MM</code> | <code>status</code> |
     * <code>room_code</code> | occupancy: <code>occupancy</code> |
     */
    public String toString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return "| " + timeSlot.format(dateTimeFormatter) + " | " + roomStatus + " | " + room.getCode()
                + " | occupancy: " + occupancy + " |";
    }

    /**
     * Checks the occupancy is greater than or equal too zero and less than or equal
     * too the room capacity
     * 
     * @param occupancy
     * @throws OccupancyIncorrect
     */
    public void checkInput(int occupancy) throws OccupancyIncorrect {
        if (occupancy < 0) {
            throw new OccupancyIncorrect("Occupancy of, " + occupancy + " is incorrect, must be greater than zero");
        } else if (occupancy > room.getCapacity()) {
            throw new OccupancyIncorrect(
                    "Occupancy of, " + occupancy + " is incorrect, must be less than or equal too the room capacity");
        }
    }

    /**
     * Will only return if the room is full or not
     * 
     * @return True if the room status is full
     */
    public boolean isFull() {
        if (roomStatus == RoomStatus.FULL) {
            return true;
        }
        return false;
    }

    /**
     * Gives the code
     * 
     * @return Code string
     */
    public String getCode() {
        return room.getCode();
    }

    /**
     * Gives the the time slot of the given bookable room
     * 
     * @return The time slot
     */
    public LocalDateTime getTimeSlot() {
        return timeSlot;
    }

    /**
     * Gives the current occupancy of a room
     * 
     * @return Current Occupancy
     */
    public int getOccupancy() {
        return occupancy;
    }

    /**
     * Updates the occupany of a bookable room
     * 
     * @param additionalOccupany
     */
    public void updateOccupancy(int additionalOccupany) {
        this.occupancy += additionalOccupany;
    }

    /**
     * Returns the current room for this booking
     * 
     * @return Booking's Room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Shows if the room is empty, hence can be removed, or not
     * 
     * @return True if the room is empty
     */
    public Boolean isEmpty() {
        if (roomStatus == RoomStatus.EMPTY) {
            return true;
        }
        return false;
    }

}