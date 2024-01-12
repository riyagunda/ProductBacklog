/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.command;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue;

/**
 * This JUnit test class tests the methods of the Command class
 * @author Riya Gunda
 *
 */
class CommandTest {

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.command.Command#Command(edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue, java.lang.String, java.lang.String)}.
	 */
	@Test
	void testCommand() {
			
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Command(null, "rgunda", "Hello"));
		assertEquals("Invalid command.", e1.getMessage());
		
		e1 = assertThrows(IllegalArgumentException.class,
				() -> new Command(CommandValue.BACKLOG, "rgunda", ""));
		assertEquals("Invalid command.", e1.getMessage());
		
		e1 = assertThrows(IllegalArgumentException.class,
				() -> new Command(CommandValue.BACKLOG, "rgunda", null));
		assertEquals("Invalid command.", e1.getMessage());
		
		e1 = assertThrows(IllegalArgumentException.class,
				() -> new Command(CommandValue.CLAIM, null, "Hello"));
		assertEquals("Invalid command.", e1.getMessage());
		
		e1 = assertThrows(IllegalArgumentException.class,
				() -> new Command(CommandValue.CLAIM, "", "Hello"));
		assertEquals("Invalid command.", e1.getMessage());
		
			
	}



	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.command.Command#getNoteText()}.
	 */
	@Test
	void testGetNoteText() {
		Command com = new Command(CommandValue.CLAIM, "rgunda", "Hello");
		assertEquals("Hello", com.getNoteText()); 

	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.command.Command#getOwner()}.
	 */
	@Test
	void testGetOwner() {
		Command com = new Command(CommandValue.CLAIM, "rgunda", "Hello");
		assertEquals("rgunda", com.getOwner());
	}

}
