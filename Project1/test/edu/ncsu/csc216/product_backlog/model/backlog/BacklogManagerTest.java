/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.backlog;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue;
import edu.ncsu.csc216.product_backlog.model.io.ProductsWriter;
import edu.ncsu.csc216.product_backlog.model.task.Task;
import edu.ncsu.csc216.product_backlog.model.task.Task.Type;
import edu.ncsu.csc216.product_backlog.model.product.Product;
import edu.ncsu.csc216.product_backlog.model.command.Command;
import java.util.ArrayList;

/**
 * The BacklogManagerTest class is responsible for testing the functionality of the BacklogManager class.
 * 
 * @author Riya Gunda
 *
 */
class BacklogManagerTest {
	/** Instance of BacklogManager */
	BacklogManager instance = BacklogManager.getInstance();

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.backlog.BacklogManager#saveToFile(java.lang.String)}.
	 */
	@Test
	void testSaveToFile() {
		Product p = new Product("Shopping cart");
		ArrayList<Product> products = new ArrayList<Product>();
		products.add(p);
		ProductsWriter.writeProductsToFile("test-files/tester", products);
		Exception exception = assertThrows(IllegalArgumentException.class, 
				() -> ProductsWriter.writeProductsToFile("/home/sesmith5/actual_student_records.txt", products));
		assertEquals("Unable to save file", exception.getMessage());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.backlog.BacklogManager#loadFromFile(java.lang.String)}.
	 */
	@Test
	void testLoadFromFile() {
		instance.loadFromFile("test-files/tasks1.txt");
		assertEquals(2, instance.getProductList().length);
		assertEquals("Shopping Cart Simulation", instance.getProductName());
		try {
			instance.addProduct("Shopping Cart Simulation");
			fail("Should not add product");
		} catch (IllegalArgumentException e) {
			//DO NOTHING 
		}
		instance.clearProducts();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.backlog.BacklogManager#loadProduct(java.lang.String)}.
	 */
	@Test
	void testLoadProduct() {
		instance.addProduct("Shopping cart");
		instance.addProduct("List");
		try {
			instance.loadProduct("Not in the list");
			fail();
		} catch (IllegalArgumentException e) {
			// DO NOTHING
		}
		instance.clearProducts();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.backlog.BacklogManager#getTasksAsArray()}.
	 */
	@Test
	void testGetTasksAsArray() {
		instance.addProduct("Shopping cart");
		instance.addTaskToProduct("Cart 1", Type.BUG, "rgunda", "Hello");
		instance.addTaskToProduct("Cart 2", Type.BUG, "sesmith5", "This is my second cart");
		instance.addTaskToProduct("Cart 3", Type.BUG, "sesmith5", "This is my third cart");
		
		String id = "" + instance.getTasksAsArray()[0][0];
		assertEquals("1", id);
		instance.clearProducts();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.backlog.BacklogManager#getTaskById(int)}.
	 */
	@Test
	void testGetTaskById() {
		instance.addProduct("Shopping cart");
		instance.addTaskToProduct("Cart 1", Type.BUG, "rgunda", "Hello");
		instance.addTaskToProduct("Cart 2", Type.BUG, "sesmith5", "This is my second cart");
		instance.addTaskToProduct("Cart 3", Type.BUG, "sesmith5", "This is my third cart");
		Task t = new Task(3, "Cart 3", Type.BUG, "sesmith5", "This is my third cart");
		assertEquals(t.toString(), instance.getTaskById(3).toString());
		instance.clearProducts();
	}


	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.backlog.BacklogManager#deleteTaskById(int)}.
	 */
	@Test
	void testDeleteTaskById() {
		instance.addProduct("Shopping cart");
		instance.addTaskToProduct("Cart 1", Type.BUG, "rgunda", "Hello");
		instance.addTaskToProduct("Cart 2", Type.BUG, "sesmith5", "This is my second cart");
		instance.addTaskToProduct("Cart 3", Type.BUG, "sesmith5", "This is my third cart");
		assertEquals(3, instance.getTasksAsArray().length);
		instance.deleteTaskById(2);
		instance.deleteTaskById(1);
		assertEquals(1, instance.getTasksAsArray().length);
		assertEquals("Cart 3", instance.getTaskById(3).getTitle());
		instance.executeCommand(3, new Command(CommandValue.REJECT, null, "Hello"));
		instance.clearProducts();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.backlog.BacklogManager#addTaskToProduct(java.lang.String, edu.ncsu.csc216.product_backlog.model.task.Task.Type, java.lang.String, java.lang.String)}.
	 */
	@Test
	void testAddTaskToProduct() {
		instance.addProduct("Shopping cart");
		instance.addTaskToProduct("Cart 1", Type.BUG, "rgunda", "Hello");
		assertEquals(1, instance.getTasksAsArray().length);
		assertEquals("Cart 1", instance.getTaskById(1).getTitle());
		
		try {
			instance.addTaskToProduct(null, Type.BUG, "rgunda", "Hello");
			fail();
		} catch (IllegalArgumentException e) {
			// DO NOTHING
		}
		instance.clearProducts();

	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.backlog.BacklogManager#getProductName()}.
	 */
	@Test
	void testGetProductName() {
		instance.clearProducts();
		assertEquals(0, instance.getProductList().length);
		assertNull(instance.getProductName());
	}


	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.backlog.BacklogManager#clearProducts()}.
	 */
	@Test
	void testClearProducts() {
		instance.addProduct("Product 1");
		instance.addProduct("Product 2");
		assertEquals(2, instance.getProductList().length);
		instance.clearProducts();
		assertEquals(0, instance.getProductList().length);
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.backlog.BacklogManager#editProduct(java.lang.String)}.
	 */
	@Test
	void testEditProduct() {
		instance.addProduct("Shopping cart");
		assertEquals("Shopping cart", instance.getProductName());
		instance.editProduct("To-do list");
		assertEquals("To-do list", instance.getProductName());
		instance.clearProducts();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.backlog.BacklogManager#addProduct(java.lang.String)}.
	 */
	@Test
	void testAddProduct() {
		instance.addProduct("Riya's cart");
		assertEquals("Riya's cart", instance.getProductName());
		
		try {
			instance.addProduct("Riya's cart");
			fail();
		} catch(IllegalArgumentException e) {
			// DO NOTHING
		}
		instance.clearProducts();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.backlog.BacklogManager#deleteProduct()}.
	 */
	@Test
	void testDeleteProduct() {
		instance.addProduct("Shopping cart");
		
		assertEquals(1, instance.getProductList().length);
		instance.deleteProduct();
		assertEquals(0, instance.getProductList().length);
		instance.clearProducts();
	}


}
