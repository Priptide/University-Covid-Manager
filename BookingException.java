/**
 * Used for any errors in creating a booking
 */
public class BookingException extends Exception {
    /**
     * Generated ID
     */
    private static final long serialVersionUID = -9127974927242480288L;

    public BookingException(String message) {
        super(message);
    }
}
