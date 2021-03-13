/**
 * Used to create a room object
 */
public class Room {
    // Variables
    private String roomCode;

    private int capacity;

    // Constructors
    public Room(String roomCode, int capacity) throws RoomNonCompliantException {
        checkInput(capacity);
        this.roomCode = roomCode;
        this.capacity = capacity;
    }

    // Methods

    /**
     * This will check the capacity is greater than zero.
     * 
     * @param capacity
     * @throws RoomNonCompliantException
     */
    public void checkInput(int capacity) throws RoomNonCompliantException {
        // Checks the capacity is greater than zero
        if (capacity <= 0) {
            throw new RoomNonCompliantException("The capacity must be greater than zero");
        }
    }

    /**
     * @return String in the form, | <code>code</code> | capacity:
     *         <code>capacity</code> |
     */
    public String toString() {
        return String.format("| %s | capacity: %d |", roomCode, capacity);
    }

    /**
     * @return Room code
     */
    public String getCode() {
        return this.roomCode;
    }

    /**
     * @return Room capacity
     */
    public int getCapacity() {
        return this.capacity;
    }
}
