/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.command;

/**
 * This class represents a command associated with a product in a product backlog.
 * It contains information such as the commands type, product owner, and any associated notes.
 * Valid command types are defined in the CommandValue enum. This class also provides methods to 
 * retrieve command details.
 * @author Riya Gunda
 */
public class Command {
	/** Private variable for notes */
	private String note;
	/** Private variable for the owner of the product */
	private String owner;
	/** Private variable for the command of the product */
	private CommandValue c;
	/** Enum for command values */
	public enum CommandValue { 
		/** Backlog state */
		BACKLOG, 
		/** Claimed or owned state */
		CLAIM, 
		
		/** Process state */
		PROCESS, 
		/** Verified state */
		VERIFY, 
		
		/** Completed or done state */
		COMPLETE, 
		/** Rejected state */
		REJECT }

	/**
	 * Constant for error message to be displayed when an invalid command in entered.
	 */
	private static final String COMMAND_ERROR_MESSAGE = "Invalid command.";

	/**
	 * Constructor with 3 parameters
	 * @param c Command
	 * @param owner of the product
	 * @param noteText notes for the products
	 * @throws IllegalArgumentException if any of the parameters are invalid:
	 * - CommandValue is null
	 * - noteText is null or empty
	 * - owner is null or empty if the task state is CLAIM
	 * - owner not being null or empty if the task's state is not CLAIM
	 */
	public Command(CommandValue c, String owner, String noteText) {
		 if (c == null) {
			 throw new IllegalArgumentException(COMMAND_ERROR_MESSAGE);
		 }
		    
		 if (noteText == null || noteText.isEmpty()) {
			 throw new IllegalArgumentException(COMMAND_ERROR_MESSAGE);
		 }

		 if (c == CommandValue.CLAIM && (owner == null || owner.isEmpty())) {
		     throw new IllegalArgumentException(COMMAND_ERROR_MESSAGE);
		 }

		 if (c != CommandValue.CLAIM && owner != null && !owner.isEmpty()) {
		     throw new IllegalArgumentException(COMMAND_ERROR_MESSAGE);
		 }
		 
		 this.c = c;
		 this.note = noteText;
		 this.owner = owner;				 
	}
		
	/**
	 * This method returns the command 
	 * @return the command 
	 */
	public CommandValue getCommand() {
		return c;
	}

	/**
	 * This method returns the notes under the product
	 * @return the notes
	 */
	public String getNoteText() {
		return note;
	}

	/**
	 * This method returns the owner of the product
	 * @return the name of the product owner
	 */
	public String getOwner() {
		return owner;
	}

}