/**
 * Used for errors when creating a bookable room
 */
public class BookableRoomCreation extends Exception {
    /**
     * Generated ID
     */
    private static final long serialVersionUID = 7201934470656179221L;

    public BookableRoomCreation(String message) {
        super(message);
    }
}
