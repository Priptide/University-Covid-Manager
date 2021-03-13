/**
 * Used for errors when creating a room
 */
public class RoomNonCompliantException extends Exception {
    /**
     * Generated ID
     */
    private static final long serialVersionUID = 4525796576967703091L;

    public RoomNonCompliantException(String message) {
        super(message);
    }
}
