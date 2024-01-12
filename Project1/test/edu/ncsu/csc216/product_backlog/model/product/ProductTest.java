/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.product;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.product_backlog.model.task.Task;
import edu.ncsu.csc216.product_backlog.model.task.Task.Type;
import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue;


/**
 * This JUnit test class test the methods of the Product class
 * @author Riya Gunda
 *
 */ 
class ProductTest {
	/**
	 * Test method for the constructor of the class
	 */
	@Test
	void testProduct() { 
		Product p = new Product("Shopping cart");
		assertEquals(0, p.getTasks().size());
		assertEquals("Shopping cart", p.getProductName());
		
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Product(""));
		assertEquals("Invalid product name.", e1.getMessage());
		
		e1 = assertThrows(IllegalArgumentException.class,
				() -> new Product(null));
		assertEquals("Invalid product name.", e1.getMessage());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.product.Product#addTask(edu.ncsu.csc216.product_backlog.model.task.Task)}.
	 */
	@Test
	void testAddTaskTask() { // check
		Task.Type type = Type.FEATURE;
		Task t = new Task(1, "cart 01", type, "sesmith5", "Hello, this is my note.");
		Product p = new Product("Shopping cart");
		p.addTask(t);
		assertEquals(1, p.getTasks().size());
		
		Task t2 = new Task(2, "cart 01", type, "sesmith5", "Hello, this is my note.");
		Task t3 = new Task(4, "cart 01", type, "sesmith5", "Hello, this is my note.");
		Task t4 = new Task(3, "cart 01", type, "sesmith5", "Hello, this is my note.");
		Task t5 = new Task(5, "cart 01", type, "sesmith5", "Hello, this is my note.");
		
		p.addTask(t2);
		p.addTask(t3);
		p.addTask(t4);
		p.addTask(t5);
		assertEquals(5, p.getTasks().size());
		assertEquals(1, p.getTasks().get(0).getTaskId());
		assertEquals(2, p.getTasks().get(1).getTaskId());
		assertEquals(3, p.getTasks().get(2).getTaskId());
		
		
	}
		
	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.product.Product#addTask(java.lang.String, edu.ncsu.csc216.product_backlog.model.task.Task.Type, java.lang.String, java.lang.String)}.
	 */
	@Test
	void testAddTaskStringTypeStringString() {
		Product p = new Product("Shopping cart");
		Task.Type type = Type.FEATURE;
		Task tester = new Task(1, "Cart 1", type, "sesmith5", "Cart 1 for this user");
		p.addTask("Cart 1", type, "sesmith5", "Cart 1 for this user");
		assertEquals(tester.getTitle(), p.getTaskById(1).getTitle());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.product.Product#getTasks()}.
	 */
	@Test
	void testGetTasks() {
		Task.Type type = Type.FEATURE;
		Task t = new Task(1, "cart 01", type, "sesmith5", "Hello, this is my note.");
		ArrayList<Task> tasks = new ArrayList<Task>();
		Product p = new Product("Shopping cart");
		tasks.add(t);
		assertEquals(1, tasks.size());
		
		Task t2 = new Task(2, "cart 01", type, "sesmith5", "Hello, this is my note.");
		
		tasks.add(t2);
		
		p.addTask(t);
		p.addTask(t2);
		
		
		assertEquals(tasks, p.getTasks());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.product.Product#getTaskById(int)}.
	 */
	@Test
	void testGetTaskById() {
		Task.Type type = Type.FEATURE;
		Task t = new Task(1, "cart 01", type, "sesmith5", "Hello, this is my note.");
		Product p = new Product("Shopping cart");
		p.addTask(t);
		assertEquals(1, p.getTasks().size());
		
		Task t2 = new Task(2, "cart 01", type, "sesmith5", "Hello, this is my note.");
		Task t3 = new Task(4, "cart 01", type, "sesmith5", "Hello, this is my note.");
		Task t4 = new Task(3, "cart 01", type, "sesmith5", "Hello, this is my note.");
		Task t5 = new Task(5, "cart 01", type, "sesmith5", "Hello, this is my note.");
		
		p.addTask(t2);
		p.addTask(t3);
		p.addTask(t4);
		p.addTask(t5);
		
		assertEquals(t2, p.getTaskById(2));
		assertNull(p.getTaskById(10));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.product.Product#executeCommand(int, edu.ncsu.csc216.product_backlog.model.command.Command)}.
	 */
	@Test
	void testExecuteCommand() {
		Task.Type type = Type.FEATURE;
		Task t = new Task(1, "cart 01", type, "sesmith5", "Hello, this is my note.");
		Product p = new Product("Shopping cart");
		p.addTask(t);
		assertEquals(1, p.getTasks().size());
		
		Command c = new Command(CommandValue.CLAIM, "rgunda", "Notes");
		p.executeCommand(1, c);
		assertEquals("Owned", p.getTaskById(1).getStateName());
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.product.Product#deleteTaskById(int)}.
	 */
	@Test
	void testDeleteTaskById() {
		Task.Type type = Type.FEATURE;
		Task t = new Task(1, "cart 01", type, "sesmith5", "Hello, this is my note.");
		Product p = new Product("Shopping cart");
		p.addTask(t);
		assertEquals(1, p.getTasks().size());
		
		Task t2 = new Task(2, "cart 01", type, "sesmith5", "Hello, this is my note.");
		Task t3 = new Task(4, "cart 01", type, "sesmith5", "Hello, this is my note.");
		Task t4 = new Task(3, "cart 01", type, "sesmith5", "Hello, this is my note.");
		Task t5 = new Task(5, "cart 01", type, "sesmith5", "Hello, this is my note.");
		
		p.addTask(t2);
		p.addTask(t3);
		p.addTask(t4);
		p.addTask(t5);
		
		p.deleteTaskById(1);
		assertEquals(4, p.getTasks().size());
	}

}
