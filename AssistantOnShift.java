import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * The two possible assistant status's
 */
enum AssistantStatus {
    FREE, BUSY
}

/**
 * Used to create an assistant on shift
 */
public class AssistantOnShift {
    // Variables
    private Assistant assistant;

    private LocalDateTime timeSlot;

    private AssistantStatus status;

    // Constructors

    // Creating without status

    /**
     * Use this to create an assistant on shift with an unset, therefore free,
     * status
     * 
     * @param assistant
     * @param timeSlot
     */
    public AssistantOnShift(Assistant assistant, LocalDateTime timeSlot) {
        this.assistant = assistant;
        this.timeSlot = timeSlot;
        this.status = AssistantStatus.FREE;
    }

    /**
     * Use this to create an assistant on shift with a set status
     * 
     * @param assistant
     * @param timeSlot
     * @param isBusy
     */
    public AssistantOnShift(Assistant assistant, LocalDateTime timeSlot, boolean isBusy) {
        this.assistant = assistant;
        this.timeSlot = timeSlot;
        setBusy(isBusy);
    }

    /**
     * Use this when creating an assistant on shift from the menu
     * 
     * @param rooms
     * @param inputString
     * @throws AssistantOnShiftException
     */
    public AssistantOnShift(List<Assistant> assitants, String inputString) throws AssistantOnShiftException {

        String[] inputStrings = inputString.split(" ");
        int index = 0;
        try {
            index = Integer.parseInt(inputStrings[0]);
            index -= 11;
        } catch (Exception e) {
            throw new AssistantOnShiftException("The input, " + inputStrings[0] + " isn't an integer");
        }
        if (index >= assitants.size() || index < 0) {
            throw new AssistantOnShiftException("The ID you selected is not in the current list");
        }

        LocalDateTime setTime = LocalDateTime.now();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            setTime = LocalDateTime.parse(inputStrings[1] + " " + inputStrings[2], formatter);
        } catch (Exception e) {
            throw new AssistantOnShiftException("Please check you have formatted the date correctly as, "
                    + inputStrings[1] + " doesn't follow the form dd/mm/yyyy" + e.toString());
        }

        try {
            this.assistant = assitants.get(index);
            this.timeSlot = setTime;
            this.status = AssistantStatus.FREE;
        } catch (Exception e) {
            throw new AssistantOnShiftException("System error, " + e.toString());
        }

    }

    // Methods

    /**
     * Set the assistant's current status
     * 
     * @param isBusy
     */
    public void setBusy(Boolean isBusy) {
        if (Boolean.TRUE.equals(isBusy)) {
            this.status = AssistantStatus.BUSY;
        } else {
            this.status = AssistantStatus.FREE;
        }
    }

    /**
     * Prints in the format: | <code>dd/mm/yyyy HH:MM</code> | <code>status</code> |
     * <code>assistant_email</code> |
     * 
     * @return String Format
     */
    public String toString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return "| " + timeSlot.format(dateTimeFormatter) + " | " + status + " | " + assistant.getEmail() + " |";
    }

    /**
     * Shows whether the assistant is free
     * 
     * @return True if they're free
     */
    public boolean isFree() {
        if (status == AssistantStatus.FREE) {
            return true;
        }
        return false;
    }

    /**
     * Gives the assistants email
     * 
     * @return The assistants email
     */
    public String getEmail() {
        return assistant.getEmail();
    }

    /**
     * Gives the time slot of the assistant
     * 
     * @return Assistants time slot
     */
    public LocalDateTime getTimeSlot() {
        return timeSlot;
    }
}
