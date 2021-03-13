
/**
 * Used to create a new new assistant
 */
public class Assistant {
    // Variables
    private String name;

    private String universityEmail;

    // Constructors

    /**
     * Create a new assistant
     */
    public Assistant(String universityEmail, String name) throws AssistantNonCompliantException {
        checkInput(universityEmail, name);
        this.universityEmail = universityEmail;
        this.name = name;
    }

    // Methods

    /**
     * @return String version of the assistant in the form, | <code>name</code> |
     *         <code>email</code> |
     */
    public String toString() {
        return "| " + name + " | " + universityEmail + " |";
    }

    /**
     * This is used to check the name of the assistant isn't empty and non blank
     * aswell as checking the email follows the patterns "*@uok.ac.uk"
     * 
     * @param email
     * @param name
     * @throws AssistantNonCompliantException
     */
    public void checkInput(String email, String name) throws AssistantNonCompliantException {
        // Split the string into two parts at the @ symbol
        String[] checkStrings = email.split("@");

        // Check the second-half of the string array is following our email pattern
        if (!checkStrings[1].equals("uok.ac.uk")) {
            throw new AssistantNonCompliantException("The Email, " + email + " doesn't end with @uok.ac.uk");
        } else if (name.isEmpty() || name.isBlank()) {
            throw new AssistantNonCompliantException("The name, " + name + " is null or blank");
        }
    }

    /**
     * @return Returns the assistants email
     */
    public String getEmail() {
        return this.universityEmail;
    }
}
