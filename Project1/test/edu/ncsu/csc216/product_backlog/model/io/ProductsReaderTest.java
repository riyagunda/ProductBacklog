/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.io;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.product_backlog.model.product.Product;

/**
 * This JUnit test class tests the methods of the ProductsReader class and makes sure the files can be
 * read with no error
 * @author Riya Gunda
 *
 */
class ProductsReaderTest {


	/**
	 * Test method for {@link edu.ncsu.csc216.product_backlog.model.io.ProductsReader#readProductsFile(java.lang.String)}.
	 */
	@Test
	void testReadProductsFile(){
		
		ArrayList<Product> products = ProductsReader.readProductsFile("test-files/tasks1.txt");
		assertEquals("Shopping Cart Simulation", products.get(0).getProductName());

		assertEquals(1, products.get(0).getTasks().get(0).getTaskId());
		assertEquals("WolfScheduler", products.get(1).getProductName());
		ProductsWriter.writeProductsToFile("test-files/tester.txt", products); 	
		
		
	}

}
