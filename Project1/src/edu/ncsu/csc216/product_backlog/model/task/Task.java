/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.task;

import java.util.ArrayList;

import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue;

/**
 * This class manages individual tasks within a product backlog, providing methods to set properties, 
 * track state transitions, and store relevant information like type, title, and owner. It employs a 
 * state pattern to handle task progression through distinct states, such as Backlog, Owned, Processing, 
 * Verifying, Done, and Rejected. Additionally, it offers functionality for adding and retrieving notes 
 * associated with the tasks.
 * @author Riya Gunda
 */
public class Task {
	/**
		 * Interface for states in the Task State Pattern.  All 
		 * concrete task states must implement the TaskState interface.
		 * 
		 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu) 
		 */
		private interface TaskState {
			
			/**
			 * Update the Task based on the given Command
			 * An UnsupportedOperationException is thrown if the Command is not a
			 * is not a valid action for the given state.  
			 * @param c Command describing the action that will update the Task
			 * state.
			 * @throws UnsupportedOperationException if the Command is not a valid action
			 * for the given state.
			 */
			void updateState(Command c);
			
			/**
			 * Returns the name of the current state as a String.
			 * @return the name of the current state as a String.
			 */
			String getStateName();
		
		}
		
		

		/**
		 * This class represents the Backlog state of a task and manages updates for this state
		 * @author Riya Gunda
		 */
		public class BacklogState implements TaskState {
			
			/** Private parameterless constructor */
			private BacklogState() {
				currentState = backlogState;
				owner = UNOWNED;
				isVerified = false;
			}

			/**
			 * Update the Task based on the given Command
			 * An UnsupportedOperationException is thrown if the Command is not a
			 * is not a valid action for the given state.  
			 * @param c Command describing the action that will update the Task
			 * state.
			 * @throws UnsupportedOperationException if the Command is not a valid action
			 * for the given state.
			 */
			@Override
			public void updateState(Command c) {
				if(CommandValue.CLAIM == c.getCommand()) {
					owner = c.getOwner();
					setState(OWNED_NAME); 
					notes.add("[Owned] " + c.getNoteText());
				} else if(CommandValue.REJECT == c.getCommand()) {
					owner = UNOWNED;
					setState(REJECTED_NAME);
					notes.add("[Rejected] " + c.getNoteText());
				} else {
					throw new UnsupportedOperationException("Invalid transition.");				
				}
			}

			/**
			 * Returns the name of the current state as a String.
			 * @return the name of the current state as a String.
			 */
			@Override
			public String getStateName(	) {
				return BACKLOG_NAME;
			}

		}
		
		/**
		 * This class represents the TaskState state of a task and manages updates for this state
		 * @author Riya Gunda
		 */
		public class DoneState implements TaskState {
			
			/**
			 * Private parameterless constructor for the class
			 */
			private DoneState() {
				currentState = doneState;
			}
			
			/**
			 * Update the Task based on the given Command
			 * An UnsupportedOperationException is thrown if the Command is not a
			 * is not a valid action for the given state.  
			 * @param c Command describing the action that will update the Task
			 * state.
			 * @throws UnsupportedOperationException if the Command is not a valid action
			 * for the given state.
			 */
			@Override
			public void updateState(Command c) {
				if(CommandValue.BACKLOG == c.getCommand()) {
					setState(BACKLOG_NAME); 
					notes.add("[Backlog] " + c.getNoteText());
					isVerified = false;
				} else if(CommandValue.PROCESS == c.getCommand()) {
					
					setState(PROCESSING_NAME);
					notes.add("[Processing] " + c.getNoteText());
					isVerified = false;
				} else {
					throw new UnsupportedOperationException("Invalid transition.");				
				}
			}

			/**
			 * Returns the name of the current state as a String.
			 * @return the name of the current state as a String.
			 */
			@Override
			public String getStateName() {
				return DONE_NAME;
			}

		}
		
		/**
		 * This class represents the OwnedState state of a task and manages updates for this state
		 * @author Riya Gunda
		 */
		public class OwnedState implements TaskState {
			
			/**
			 * Private parameterless constructor for the class
			 */
			private OwnedState() {
				isVerified = false;
			}
			
			/**
			 * Update the Task based on the given Command
			 * An UnsupportedOperationException is thrown if the Command is not a
			 * is not a valid action for the given state.  
			 * @param c Command describing the action that will update the Task
			 * state.
			 * @throws UnsupportedOperationException if the Command is not a valid action
			 * for the given state.
			 */
			@Override
			public void updateState(Command c) {
				if(CommandValue.PROCESS == c.getCommand()) {
					notes.add("[Processing] " + c.getNoteText());
					setState(PROCESSING_NAME); 
				} else if(CommandValue.BACKLOG == c.getCommand()) {
					notes.add("[Backlog] " + c.getNoteText());
					owner = UNOWNED;
					setState(BACKLOG_NAME);
					isVerified = false;
				} else if(CommandValue.REJECT == c.getCommand()) {
					notes.add("[Rejected] " + c.getNoteText());
					owner = UNOWNED;
					setState(REJECTED_NAME);
				} else {
					throw new UnsupportedOperationException("Invalid transition.");
				}
				
			}

			/**
			 * Returns the name of the current state as a String.
			 * @return the name of the current state as a String.
			 */
			@Override
			public String getStateName() {
				return OWNED_NAME;
			}
		}

		/**
		 * This class represents the ProcessingState state of a task and manages updates for this state
		 * @author Riya Gunda
		 */
		public class ProcessingState implements TaskState {

			/**
			 * Private parameterless constructor for the class
			 */
			private ProcessingState() {
				currentState = processingState;
				isVerified = false;
			}
			
			/**
			 * Update the Task based on the given Command
			 * An UnsupportedOperationException is thrown if the Command is not a
			 * is not a valid action for the given state.  
			 * @param c Command describing the action that will update the Task
			 * state.
			 * @throws UnsupportedOperationException if the Command is not a valid action
			 * for the given state.
			 */
			@Override
			public void updateState(Command c) {
				if(CommandValue.VERIFY == c.getCommand() && !(getType() == Type.KNOWLEDGE_ACQUISITION)) {
					notes.add("[Verifying] " + c.getNoteText());
					setState(VERIFYING_NAME); 
				} else if(CommandValue.PROCESS == c.getCommand()) {
					notes.add("[Processing] " + c.getNoteText());
				} else if(CommandValue.BACKLOG == c.getCommand()) {
					notes.add("[Backlog] " + c.getNoteText());
					owner = UNOWNED;
					setState(BACKLOG_NAME);
					isVerified = false;
				} else if (CommandValue.COMPLETE == c.getCommand() && getType() == Type.KNOWLEDGE_ACQUISITION) {
					notes.add("[Done] " + c.getNoteText());
					setState(DONE_NAME); 
				} else {
					throw new UnsupportedOperationException("Invalid transition.");
				}
			}

			/**
			 * Returns the name of the current state as a String.
			 * @return the name of the current state as a String.
			 */
			@Override
			public String getStateName() {
				return PROCESSING_NAME;
			}
		}

		/**
		 * This class represents the RejectedState state of a task and manages updates for this state
		 * @author Riya Gunda
		 */
		public class RejectedState implements TaskState {

			/**
			 * Private parameterless constructor for the class
			 */
			private RejectedState() {
				isVerified = false;
				owner = UNOWNED;
			}
			
			/**
			 * Update the Task based on the given Command
			 * An UnsupportedOperationException is thrown if the Command is not a
			 * is not a valid action for the given state.  
			 * @param c Command describing the action that will update the Task
			 * state.
			 * @throws UnsupportedOperationException if the Command is not a valid action
			 * for the given state.
			 */
			@Override
			public void updateState(Command c) {
				if(CommandValue.BACKLOG == c.getCommand()) {
					notes.add("[Backlog] " + c.getNoteText());
					setState(BACKLOG_NAME); 
					isVerified = false;
				} else {
					throw new UnsupportedOperationException("Invalid transition.");
				}
			}

			/**
			 * Returns the name of the current state as a String.
			 * @return the name of the current state as a String.
			 */
			@Override
			public String getStateName() {
				return REJECTED_NAME;
			}
		}
		
		/**
		 * This class represents the VerifyingState state of a task and manages updates for this state
		 * @author Riya Gunda
		 */
		public class VerifyingState implements TaskState {

			/**
			 * Private parameterless constructor for the class
			 */
			private VerifyingState() {
				currentState = verifyingState;
			}

			/**
			 * Update the Task based on the given Command
			 * An UnsupportedOperationException is thrown if the Command is not a
			 * is not a valid action for the given state.  
			 * @param c Command describing the action that will update the Task
			 * state.
			 * @throws UnsupportedOperationException if the Command is not a valid action
			 * for the given state.
			 */
			@Override
			public void updateState(Command c) {
				if(CommandValue.COMPLETE == c.getCommand()) {
					notes.add("[Done] " + c.getNoteText());
					isVerified = true;
					setState(DONE_NAME); 
				} else if(CommandValue.PROCESS == c.getCommand()) {
					notes.add("[Processing] " + c.getNoteText());
					setState(PROCESSING_NAME);
				}
				else throw new UnsupportedOperationException("Invalid transition.");
			}

			/**
			 * Returns the name of the current state as a String.
			 * @return the name of the current state as a String.
			 */
			@Override
			public String getStateName() {
				return VERIFYING_NAME;
			}
		}

		/**
		 * Enum representing the possible types of a task
		 */ 
		public enum Type { /** Feature type */ FEATURE, /** Bug type */BUG, 
			/** Technical work type */TECHNICAL_WORK, /** Knowledge acquisition type */KNOWLEDGE_ACQUISITION };

		/**
		 * Integer representing the unique identifier for a task.
		 */
		private int taskId;

		/**
		 * The type of the task defined by the Type enum
		 */
		private Type type;

		/**
		 * The title of the task.
		 */
		private String title;

		/**
		 * The creator of the task.
		 */
		private String creator;

		/**
		 * The owner of the task
		 */
		private String owner;

		/**
		 * Boolean indicating whether the task has been verified.
		 */
		private boolean isVerified;

		/**
		 * List of strings containing additional notes or details about the task.
		 */
		private ArrayList<String> notes;

		/**
		 * The current state of the task, determined by the TaskState interface implementation.
		 */
		private TaskState currentState;
	
	/** Instance representing the Backlog state of a task */
	private final BacklogState backlogState = new BacklogState();
	
	/** Instance representing the Owned state of a task */
	private final OwnedState ownedState = new OwnedState();
	
	/** Instance representing the Processing state of a task */
	private final ProcessingState processingState = new ProcessingState();
	
	/** Instance representing the Verifying state of a task */
	private final VerifyingState verifyingState = new VerifyingState();
	
	/** Instance representing the Done state of a task */
	private final DoneState doneState = new DoneState();
	
	/** Instance representing the Rejected state of a task */
	private final RejectedState rejectedState = new RejectedState();
	
	/**
	 * String constant representing the name of the "Backlog" state.
	 */
	public static final String BACKLOG_NAME = "Backlog";

	/**
	 * String constant representing the name of the "Owned" state.
	 */
	public static final String OWNED_NAME = "Owned";

	/**
	 * String constant representing the name of the "Processing" state.
	 */
	public static final String PROCESSING_NAME = "Processing";

	/**
	 * String constant representing the name of the "Verifying" state.
	 */
	public static final String VERIFYING_NAME = "Verifying";

	/**
	 * String constant representing the name of the "Done" state.
	 */
	public static final String DONE_NAME = "Done";

	/**
	 * String constant representing the name of the "Rejected" state.
	 */
	public static final String REJECTED_NAME = "Rejected";

	/**
	 * String constant representing the name of the "Feature" type.
	 */
	public static final String FEATURE_NAME = "Feature";

	/**
	 * String constant representing the name of the "Bug" type
	 */
	public static final String BUG_NAME = "Bug";

	/**
	 * String constant representing the name of the "Technical Work" type.
	 */
	public static final String TECHNICAL_WORK_NAME = "Technical Work";

	/**
	 * String constant representing the name of the "Knowledge Acquisition" type.
	 */
	public static final String KNOWLEDGE_ACQUISITION_NAME = "Knowledge Acquisition";

	/**
	 * String constant representing the short name for the "Feature" type
	 */
	public static final String T_FEATURE = "F";

	/**
	 * String constant representing the short name for the "Bug" type
	 */
	public static final String T_BUG = "B";

	/**
	 * String constant representing the short name for the "Technical Work" type
	 */
	public static final String T_TECHNICAL_WORK = "TW";

	/**
	 * String constant representing the short name for the "Knowledge Acquisition" type
	 */
	public static final String T_KNOWLEDGE_ACQUISITION = "KA";

	/**
	 * String constant representing the status of being "unowned"
	 */
	public static final String UNOWNED = "unowned";
	
	/**
	 * The constructor sets the values for the task's id, title, type, creator, and note
	 * @param id of the task
	 * @param title of the task
	 * @param type of the task
	 * @param creator of the task
	 * @param note of the task
	 */
	public Task(int id, String title, Type type, String creator, String note) {		
		setTaskId(id);
		setTitle(title);
		setType(type);
		setCreator(creator);
		this.notes = new ArrayList<String>();
		currentState = backlogState;
		owner = "unowned";
		addNoteToList(note);
	}
	
	/**
	 * The constructor sets the values for the task's id, state, title, type, creator, owner, verified,
	 * and notes
	 * @param id of the task
	 * @param state of the task
	 * @param title of the task
	 * @param type of the task
	 * @param creator of the task
	 * @param owner of the task
	 * @param verified of the task
	 * @param notes of the task
	 * @throws IllegalArgumentException with the message "Invalid task information" if any of the 
	 * parameters are null or empty or 0 if an int
	 */
	public Task(int id, String state, String title, String type, String creator, String owner, String verified, ArrayList<String> notes) {
		setTaskId(id);
		setState(state);
		setTitle(title);
		setTypeFromString(type);
		setCreator(creator);
		setOwner(owner);
		setVerified(verified);
		setNotes(notes);
	}
	
	/**
	 * Sets the id value for the task
	 * @param taskId of the task
	 * @throws IllegalArgumentException with the message "Invalid task information." if the id is less 
	 * than or equal to 0
	 */
	private void setTaskId(int taskId) {
		if(taskId <= 0) {
			throw new IllegalArgumentException("Invalid task information.");
		}	
		
		
		this.taskId = taskId;
	}
	
	/**
	 * Sets the title value for the task
	 * @param title of the task
	 * @throws IllegalArgumentException with the message "Invalid task information." if the title is 
	 * invalid: null or empty
	 */
	private void setTitle(String title) {
		if(title == null || "".equals(title)) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		this.title = title;
	}
	
	/**
	 * Sets the type of the task
	 * @param type of the task
	 * @throws IllegalArgumentException with the message "Invalid task information." if the type
	 * is invalid: null or empty
	 */
	private void setType(Type type) {
		if(type == null) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		
		this.type = type;	
	}
	
	/**
	 * Sets the creator name for the task
	 * @param creator of the task
	 * @throws IllegalArgumentException with the message "Invalid task information." if the creator
	 * is invalid: null or empty
	 */
	private void setCreator(String creator) {
		if(creator == null || "".equals(creator)) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		this.creator = creator;
	}
	
	/**
	 * Sets the owner of the task
	 * @param owner of the task
	 * @throws IllegalArgumentException with the message "Invalid task information." if the owner
	 * is invalid: null or empty
	 */
	private void setOwner(String owner) {
		if(owner == null || "".equals(owner)) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		
		if((currentState == backlogState || currentState == rejectedState) && !("unowned".equals(owner))) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		
		if((currentState == ownedState || currentState == processingState || currentState == verifyingState || doneState
				== currentState) && "unowned".equals(owner)) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		
		this.owner = owner;
	}
	
	/**
	 * Sets the value for the verified variable
	 * @param isVerified the task or not
	 * @throws IllegalArgumentException with the message "Invalid task information." if the isVerified
	 * value is null
	 */
	private void setVerified(String isVerified) {
		if(isVerified == null) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		this.isVerified = Boolean.parseBoolean(isVerified);	
	}
	
	/**
	 * Sets the notes value for the task
	 * @param notes of the task 
	 * @throws IllegalArgumentException with the message "Invalid task information." if the notes
	 * are invalid: null or empty
	 */
	private void setNotes(ArrayList<String> notes) {
		if(notes.size() == 0 || notes == null) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		this.notes = notes;
	}
	
	/**
	 * This method adds notes to the list of existing notes
	 * @param note for the task
	 * @return id for the next task
	 */
	public int addNoteToList(String note) {
		if (note == null || note.isEmpty()) {
	        throw new IllegalArgumentException("Invalid task information.");
	    } 
		String concat = "[" + currentState.getStateName() + "] " + note;
		
	    this.notes.add(concat);
	    return this.notes.size() - 1;
	}
	
	/**
	 * Returns the task's ID
	 * @return the ID of the task as an int
	 */
	public int getTaskId() {
		return taskId;
	} 
	
	/**
	 * Returns the task's state name
	 * @return state name of the task
	 */
	public String getStateName() {
		return currentState.getStateName();
	}
	
	/**
	 * Sets the value for the state of the task
	 * @param state the task is in
	 * @throws IllegalArgumentException if the state is null/empty of invalid
	 */
	private void setState(String state) {
		 if (state == null || "".equals(state)) {
			 throw new IllegalArgumentException("Invalid task information.");
		 }
		 
		 if (state.equals(BACKLOG_NAME)) {
		     currentState = backlogState;
		 } else if (state.equals(OWNED_NAME)) {
		     currentState = ownedState;
		 } else if (state.equals(PROCESSING_NAME)) {
		     currentState = processingState;
		 } else if (state.equals(VERIFYING_NAME)) {
			 currentState = verifyingState;
		 } else if (state.equals(DONE_NAME)) {
		     currentState = doneState;
		 } else if (state.equals(REJECTED_NAME)) {
		     currentState = rejectedState;
		 }
	}
	
	/**
	 * Sets the type of a task
	 * @param type of the task
	 * @throws IllegalArgumentException if the type is null/empty of invalid
	 */
	private void setTypeFromString(String type) {
		if(type == null || "".equals(type)) {
			 throw new IllegalArgumentException("Invalid task information.");
		}
		
		if("Done".equals(currentState.getStateName()) && (type == T_BUG || type == T_FEATURE || type == T_TECHNICAL_WORK)
				&& !isVerified) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		
		if(currentState == doneState && type == T_KNOWLEDGE_ACQUISITION && isVerified) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		
		if(type == FEATURE_NAME) {
			this.type = Type.FEATURE;
		} else if(type == BUG_NAME) {
			this.type = Type.BUG;
		} else if (type == TECHNICAL_WORK_NAME) {
			this.type = Type.TECHNICAL_WORK;
		} else if (type == KNOWLEDGE_ACQUISITION_NAME) {
			this.type = Type.KNOWLEDGE_ACQUISITION;
		}
	}
	
	/**
	 * Returns the type of the given task
	 * @return type of the task
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 * Returns the short name of the task
	 * @return name of the task
	 */
	public String getTypeShortName() {
		if(type == Type.BUG) {
			return T_BUG;
		} else if (type == Type.FEATURE) {
			return T_FEATURE;
		} else if (type == Type.KNOWLEDGE_ACQUISITION) {
			return T_KNOWLEDGE_ACQUISITION;
		} 
		return T_TECHNICAL_WORK;
	}
	
	/**
	 * Returns the long name of the task
	 * @return name of the task
	 */
	public String getTypeLongName() {
		if(type == Type.BUG) {
			return "Bug";
		} else if(type == Type.FEATURE) {
			return "Feature";
		} else if(type == Type.KNOWLEDGE_ACQUISITION) {
			return "Knowledge Acquisition";
		} 
		return "Technical Work";
	}
	
	/**
	 * Returns the owner of the task
	 * @return owner of the task
	 */
	public String getOwner() {
		return owner;
	}
	
	/**
	 * Returns the title of the task
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Returns the creator of the task
	 * @return creator
	 */
	public String getCreator() {
		return creator;
	}
	
	/**
	 * Returns a boolean value depending on whether a task is in verified state or not
	 * @return true if the task is verified
	 */
	public boolean isVerified() {
		return isVerified;
	}
	
	/**
	 * Returns a list of all the notes for a task
	 * @return array list of notes for a task
	 */
	public ArrayList<String> getNotes() {
		return notes;
	}
	
	/**
	 * Return the list of notes
	 * @return notes list
	 */
	public String getNotesList() {
		String list = "";
		for(String note : notes) {
			list += "- " + note + "\n";
		}
		return list;
	}
	
	/**
	 * Returns a string of neccessary fields
	 */
	@Override
	public String toString() {
		return taskId + "," + currentState.getStateName() + "," + title + "," + getTypeShortName() + "," + 
	owner + "," + isVerified;
	}
	
	/**
	 * This method updates any task based on the command given
	 * @param command to execute
	 */
	public void update(Command command) {
		currentState.updateState(command);
	}
	
	/**
	 * Returns the notes of a task in the form of an array
	 * @return String array of notes
	 */
	public String[] getNotesArray() {
		String[] noteArray = new String[notes.size()];
		for(int i = 0; i < notes.size(); i++) {
			noteArray[i] = notes.get(i);
		}
		return noteArray;
	}
}