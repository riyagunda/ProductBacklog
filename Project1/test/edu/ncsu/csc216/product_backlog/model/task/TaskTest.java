/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.task;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue;
import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.task.Task.Type;

/**
 * This JUnit test class tests the methods of the Task class
 * @author Riya Gunda
 *
 */
class TaskTest {
	/** private constant for the Tasks's id*/
	private static final int ID = 001;
	
	/** private constant for the Tasks's state */
	private static final String STATE = "Processing";
	
	/* /** private constant for the Tasks's title */
	private static final String TITLE = "Cart";
	
	/** private constant for the Tasks's type */
	private static final Task.Type TYPE = Type.FEATURE;
	
	/** private constant for the Tasks's creator */
	private static final String CREATOR = "sesmith5";
	
	/** private constant for the Tasks's owner */
	private static final String OWNER = "sesmith5";
	
	/** private constant for the note to be added to the task */
	private static final String NOTE = "This is a sentence.";
	
	/** private constant for whether a task is verified or not */
	private static final String VERIFIED = "true";
	
	/** private constant for the Tasks's notes list */
	private static ArrayList<String> notes = new ArrayList<String>();
	
	/** private constant for the type of task (alternate type value)*/
	private static final String TYPE1 = "Feature";
	

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.task.Task#Task(int, java.lang.String, edu.ncsu.csc216.product_backlog.model.task.Task.Type, java.lang.String, java.lang.String)}.
	 */
	@Test
	void testTaskIntStringTypeStringString() {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(-9, TITLE, TYPE, CREATOR, NOTE));
		assertEquals("Invalid task information.", e1.getMessage());
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Task(ID, "", TYPE, CREATOR, NOTE));
		assertEquals("Invalid task information.", e2.getMessage());
		
		e2 = assertThrows(IllegalArgumentException.class,
				() -> new Task(ID, null, TYPE, CREATOR, NOTE));
		assertEquals("Invalid task information.", e2.getMessage());
		
		e2 = assertThrows(IllegalArgumentException.class,
				() -> new Task(ID, TITLE, null, CREATOR, NOTE));
		assertEquals("Invalid task information.", e2.getMessage());
		
		e2 = assertThrows(IllegalArgumentException.class,
				() -> new Task(ID, TITLE, TYPE, "", NOTE));
		assertEquals("Invalid task information.", e2.getMessage());
		
		e2 = assertThrows(IllegalArgumentException.class,
				() -> new Task(ID, TITLE, TYPE, null, NOTE));
		assertEquals("Invalid task information.", e2.getMessage());
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.task.Task#Task(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.ArrayList)}.
	 */
	@Test
	void testTaskIntStringStringStringStringStringStringArrayListOfString() {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(ID, STATE, TITLE, TYPE1, CREATOR, OWNER, VERIFIED, notes));
		assertEquals("Invalid task information.", e1.getMessage());
		
		notes.add(NOTE);
		
		e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(-9, STATE, TITLE, TYPE1, CREATOR, OWNER, VERIFIED, notes));
		assertEquals("Invalid task information.", e1.getMessage());
		
		e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(0, STATE, TITLE, TYPE1, CREATOR, OWNER, VERIFIED, notes));
		assertEquals("Invalid task information.", e1.getMessage());
		
		e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(ID, null, TITLE, TYPE1, CREATOR, OWNER, VERIFIED, notes));
		assertEquals("Invalid task information.", e1.getMessage());
		
		e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(ID, "", TITLE, TYPE1, CREATOR, OWNER, VERIFIED, notes));
		assertEquals("Invalid task information.", e1.getMessage());
		
		e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(ID, STATE, null, TYPE1, CREATOR, OWNER, VERIFIED, notes));
		assertEquals("Invalid task information.", e1.getMessage());
		
		e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(ID, STATE, "", TYPE1, CREATOR, OWNER, VERIFIED, notes));
		assertEquals("Invalid task information.", e1.getMessage());
		
		e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(ID, STATE, TITLE, null, CREATOR, OWNER, VERIFIED, notes));
		assertEquals("Invalid task information.", e1.getMessage());
		
		e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(ID, STATE, TITLE, "", CREATOR, OWNER, VERIFIED, notes));
		assertEquals("Invalid task information.", e1.getMessage());
		
		e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(ID, STATE, TITLE, TYPE1, null, OWNER, VERIFIED, notes));
		assertEquals("Invalid task information.", e1.getMessage());
		
		e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(ID, STATE, TITLE, TYPE1, "", OWNER, VERIFIED, notes));
		assertEquals("Invalid task information.", e1.getMessage());
		
		e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(ID, STATE, TITLE, TYPE1, CREATOR, "", VERIFIED, notes));
		assertEquals("Invalid task information.", e1.getMessage());
		
		e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(ID, STATE, TITLE, TYPE1, CREATOR, null, VERIFIED, notes));
		assertEquals("Invalid task information.", e1.getMessage());
		
		e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(ID, STATE, TITLE, TYPE1, CREATOR, OWNER, null, notes));
		assertEquals("Invalid task information.", e1.getMessage());
		
		e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(ID, "Backlog", TITLE, TYPE1, CREATOR, OWNER, VERIFIED, notes));
		assertEquals("Invalid task information.", e1.getMessage());
		
		e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(ID, "Rejected", TITLE, TYPE1, CREATOR, OWNER, VERIFIED, notes));
		assertEquals("Invalid task information.", e1.getMessage());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.task.Task#addNoteToList(java.lang.String)}.
	 */
	@Test
	void testAddNoteToList() {
		notes.add("testing");
		Task t = new Task(ID, STATE, TITLE, TYPE1, CREATOR, OWNER, VERIFIED, notes);
		t.addNoteToList(NOTE);
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> t.addNoteToList(""));
		assertEquals("Invalid task information.", e1.getMessage());
		
		e1 = assertThrows(IllegalArgumentException.class,
				() -> t.addNoteToList(null));
		assertEquals("Invalid task information.", e1.getMessage());
		assertEquals("[Processing] This is a sentence.", t.getNotes().get(1));
		notes.clear();
		t.addNoteToList(NOTE);
		assertEquals(1, notes.size());
		notes.clear();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.task.Task#getTaskId()}.
	 */
	@Test
	void testGetTaskId() {
		notes.add(NOTE);
		Task t = new Task(ID, STATE, TITLE, TYPE1, CREATOR, OWNER, VERIFIED, notes);
		assertEquals(ID, t.getTaskId());
		notes.clear();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.task.Task#getStateName()}.
	 */
	@Test
	void testGetStateName() {
		notes.add(NOTE);
		Task t = new Task(ID, STATE, TITLE, TYPE1, CREATOR, OWNER, VERIFIED, notes);
		assertEquals(STATE, t.getStateName());
		notes.clear();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.task.Task#getType()}.
	 */
	@Test
	void testGetType() {
		notes.add(NOTE);
		Task t = new Task(ID, STATE, TITLE, TYPE1, CREATOR, OWNER, VERIFIED, notes);
		assertEquals(TYPE, t.getType());
		notes.clear();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.task.Task#getTypeShortName()}.
	 */
	@Test
	void testGetTypeShortName() {
		Task t = new Task(1, "Cart", Type.BUG, "rgunda", "Notes");
		assertEquals("B", t.getTypeShortName());

		Task t2 = new Task(2, "Cart 2", Type.FEATURE, "rgunda", "Notes");
		assertEquals("F", t2.getTypeShortName());
		
		Task t3 = new Task(3, "Cart 3", Type.KNOWLEDGE_ACQUISITION, "rgunda", "Notes");
		assertEquals("KA", t3.getTypeShortName());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.task.Task#getTypeLongName()}.
	 */
	@Test
	void testGetTypeLongName() {
		Task t = new Task(1, "Cart", Type.BUG, "rgunda", "Notes");
		assertEquals("Bug", t.getTypeLongName());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.task.Task#getOwner()}.
	 */
	@Test
	void testGetOwner() {
		notes.add(NOTE);
		Task t = new Task(ID, STATE, TITLE, TYPE1, CREATOR, OWNER, VERIFIED, notes);
		assertEquals(OWNER, t.getOwner());
		notes.clear();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.task.Task#getTitle()}.
	 */
	@Test
	void testGetTitle() {
		notes.add(NOTE);
		Task t = new Task(ID, STATE, TITLE, TYPE1, CREATOR, OWNER, VERIFIED, notes);
		assertEquals(TITLE, t.getTitle());
		notes.clear();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.task.Task#getCreator()}.
	 */
	@Test
	void testGetCreator() {
		notes.add(NOTE);
		Task t = new Task(ID, STATE, TITLE, TYPE1, CREATOR, OWNER, VERIFIED, notes);
		assertEquals(CREATOR, t.getCreator());
		notes.clear();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.task.Task#isVerified()}.
	 */
	@Test
	void testIsVerified() {
		notes.add(NOTE);
		Task t = new Task(ID, STATE, TITLE, TYPE1, CREATOR, OWNER, VERIFIED, notes);
		assertTrue(t.isVerified());
		notes.clear();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.task.Task#getNotes()}.
	 */
	@Test
	void testGetNotes() {
		notes.add(NOTE);
		Task t = new Task(ID, STATE, TITLE, TYPE1, CREATOR, OWNER, VERIFIED, notes);
		assertEquals(notes, t.getNotes());
		notes.clear();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.task.Task#getNotesList()}.
	 */
	@Test
	void testGetNotesList() {
		notes.add(NOTE);
		Task t = new Task(ID, STATE, TITLE, TYPE1, CREATOR, OWNER, VERIFIED, notes);
		assertEquals("- This is a sentence.\n", t.getNotesList());
		notes.clear();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.task.Task#toString()}.
	 */
	@Test
	void testToString() {
		notes.add(NOTE);
		Task t = new Task(ID, STATE, TITLE, TYPE1, CREATOR, OWNER, VERIFIED, notes);
		assertEquals("1,Processing,Cart,F,sesmith5,true", t.toString());
		notes.clear();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.task.Task#update(edu.ncsu.csc216.product_backlog.model.command.Command)}.
	 */
	@Test
	void testUpdate() {
		notes.add(NOTE);
		Task t = new Task(1, "Backlog", "Cart", TYPE1, "rgunda", "unowned", "false", notes);
		Command c = new Command(CommandValue.CLAIM, "rgunda", "Notes");
		t.update(c);
		assertEquals("rgunda", t.getOwner());
		
		Task t1 = new Task(2, "Backlog", "Cart", TYPE1, "rgunda", "unowned", "false", notes);
		Command c1 = new Command(CommandValue.REJECT, null, "Notes");
		t1.update(c1);
		assertEquals("unowned", t1.getOwner());
		
		Task t2 = new Task(3, "Done", "Cart", TYPE1, "rgunda", "rgunda", "false", notes);
		Command c2 = new Command(CommandValue.BACKLOG, null, "Notes");
		t2.update(c2);
		assertEquals("rgunda", t2.getOwner());
		
		Task t3 = new Task(4, "Owned", "Cart", TYPE1, "rgunda", "rgunda", "false", notes);
		Command c3 = new Command(CommandValue.PROCESS, null, "Notes");
		t3.update(c3);
		assertEquals("rgunda", t3.getOwner());
		
		Task t4 = new Task(4, "Owned", "Cart", TYPE1, "rgunda", "rgunda", "false", notes);
		Command c4 = new Command(CommandValue.BACKLOG, null, "Notes");
		t4.update(c4);
		assertEquals("unowned", t4.getOwner());
		
		Task t5 = new Task(5, "Owned", "Cart", TYPE1, "rgunda", "rgunda", "false", notes);
		Command c5 = new Command(CommandValue.REJECT, null, "Notes");
		t5.update(c5);
		assertEquals("unowned", t5.getOwner());
		
		try {
			Task t6 = new Task(6, "Done", "Cart", TYPE1, "rgunda", "rgunda", "false", notes);
			Command c6 = new Command(CommandValue.REJECT, null, "Notes");
			t6.update(c6);
			fail();
		} catch(UnsupportedOperationException e) {
			//DO NOTHING;
		}
		
		Task t7 = new Task(7, "Processing", "Cart", TYPE1, "rgunda", "rgunda", "false", notes);
		Command c7 = new Command(CommandValue.BACKLOG, null, "Notes");
		t7.update(c7);
		assertEquals("unowned", t7.getOwner());
		
		Task t8 = new Task(8, "Rejected", "Cart", TYPE1, "rgunda", "unowned", "false", notes);
		Command c8 = new Command(CommandValue.BACKLOG, null, "Notes");
		t8.update(c8);
		assertEquals("unowned", t8.getOwner());
		
		Task t9 = new Task(9, "Verifying", "Cart", TYPE1, "rgunda", "rgunda", "false", notes);
		Command c9 = new Command(CommandValue.COMPLETE, null, "Notes");
		t9.update(c9);
		assertEquals("rgunda", t9.getOwner());
		
		notes.clear();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.task.Task#getNotesArray()}.
	 */
	@Test
	void testGetNotesArray() {
		Task t2 = new Task(2, "Cart 2", Type.FEATURE, "rgunda", "Hello");
		String[] temp = {"[Backlog] Hello"};
		assertEquals(temp[0], t2.getNotesArray()[0]);
	}

}
