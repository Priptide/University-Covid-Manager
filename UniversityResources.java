import java.util.ArrayList;
import java.util.List;

/**
 * Used as a place to store all current assistants and rooms
 */
public class UniversityResources {
    // Variables
    private List<Assistant> assistants;

    private List<Room> rooms;

    // Constructor

    /**
     * Creating with empty lists
     */
    public UniversityResources() {
        this.assistants = new ArrayList<>();
        this.rooms = new ArrayList<>();
    }

    /**
     * Creating with already made lists
     * 
     * @param assistants
     * @param rooms
     */
    public UniversityResources(List<Assistant> assistants, List<Room> rooms) {
        this.assistants = assistants;
        this.rooms = rooms;
    }

    /**
     * Add a new assistant, making sure to have a
     * <code> unique university email </code> following the pattern
     * <code> "*@uok.ac.uk" </code> and a <code> non-blank name </code>
     * 
     * @param assistant
     * @throws AddAssistantException
     */

    // All these parameters except uniqueness are checked before so that is all we
    // need to verify.
    public void addAssistant(Assistant assistant) throws AddAssistantException {
        // Check the assistants email isn't already in the system
        for (Assistant localAssistant : this.assistants) {
            // Check it isn't a duplicate assistant or duplicate email if it is we throw an
            // error alerting the user
            if (assistant.toString().equals(localAssistant.toString())
                    || assistant.getEmail().equals(localAssistant.getEmail())) {
                throw new AddAssistantException("The assistant, " + assistant.toString()
                        + " doesn't have a unique email, as it is the same as, " + localAssistant.toString());
            }
        }

        // Add the assistant too the list
        assistants.add(assistant);
    }

    /**
     * Add a new room, making sure to have a <code> unique room code </code> and a
     * <code> greater than zero integer capacity </code>
     * 
     * @param room
     * @throws AddRoomException
     */

    // All these parameter are checked when creating the object so we don't need to
    // check the greater than zero only if it's unique.
    public void addRoom(Room room) throws AddRoomException {
        // Check the room code isn't already in the system
        for (Room localRoom : this.rooms) {
            // Check it isn't a duplicate room or duplicate code then throw an error if so
            if (room.toString().equals(localRoom.toString()) || room.getCode().equals(localRoom.getCode())) {
                throw new AddRoomException(
                        "The room code, " + room.getCode() + " is the same as the room, " + localRoom.toString());
            }
        }

        // Otherwise we add the assistant too the list
        rooms.add(room);

    }

    /**
     * Returns the list of assistants in the university resources.
     * 
     * @return Assistants List
     */
    public List<Assistant> getAssistants() {
        return assistants;
    }

    /**
     * Returns the list of rooms in the university resources.
     * 
     * @return Rooms List
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * This is mainly for testing to make sure rooms are loaded into the system
     * 
     * @param id
     * @return
     * @throws AddRoomException
     */
    public Room getRoomFromID(String id) throws AddRoomException {
        for (Room room : rooms) {
            if (room.getCode().equals(id)) {
                return room;
            }
        }
        throw new AddRoomException("Room with id, " + id + " doesn't exsist in the current system");
    }
}
