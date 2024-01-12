/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.io;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import edu.ncsu.csc216.product_backlog.model.product.Product;
import edu.ncsu.csc216.product_backlog.model.task.Task;




/**
 * This JUnit test class tests the methods of the ProductsWriter class to make the contents are being 
 * saved into the given file with no errors
 * @author Riya Gunda
 *
 */
class ProductsWriterTest {

	

	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.io.ProductsWriter#writeProductsToFile(java.lang.String, java.util.ArrayList)}.
	 */
	@Test
	void testWriteProductsToFileValid() {
		Product p = new Product("Riya's list");
		Product s = new Product("Saanvi's list");
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("This is my note");
		Task t = new Task(1, "Backlog", "Cart 1", "F", "rgunda", "unowned", "False", notes);
		p.addTask(t);
		s.addTask(t);
		
		ArrayList<Product> products = new ArrayList<Product>();
		products.add(p);
		products.add(s);
		ProductsWriter.writeProductsToFile("test-files/tester.txt", products);
		Exception exception = assertThrows(IllegalArgumentException.class, 
				() -> ProductsWriter.writeProductsToFile("/home/sesmith5/actual_student_records.txt", products));
		assertEquals("Unable to save file", exception.getMessage());
	
	}

	@Test
	void testWriteProductsToFileInvalid() {
		try {
			Product p = new Product("Riya's list");
			Product s = new Product("Saanvi's list");
			ArrayList<String> notes = new ArrayList<String>();
			notes.add("This is my note");
			Task t = new Task(1, "Backlog", "Cart 1", "F", "rgunda", "UNOWNED", "False", notes);
			p.addTask(t);
			s.addTask(t);
			
			ArrayList<Product> products = new ArrayList<Product>();
			products.add(p);
			products.add(s);
			ProductsWriter.writeProductsToFile("", products);
			fail();
		} catch (IllegalArgumentException e) {
			//Do nothing
		}
	}
}
