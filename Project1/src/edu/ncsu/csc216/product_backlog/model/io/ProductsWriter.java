package edu.ncsu.csc216.product_backlog.model.io;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import edu.ncsu.csc216.product_backlog.model.product.Product;

/**
 * The ProductsWriter class is responsible for writing product data to a file. It provides a single 
 * static method that takes a file name and a list of Product objects as parameters, and writes the
 * product data to the specified file.
 * @author Riya Gunda
 */
public class ProductsWriter {	


	/**
	 * Writes the provided list of Product objects to a file with the given file name.
	 * @param fileName The name of the file to write to.
	 * @param products The list of Product objects to be written to the file.
	 * @throws IllegalArgumentException with the message "Unable to save file." if there are any errors
	 * or exceptions
	 */
	public static void writeProductsToFile(String fileName, ArrayList<Product> products) {
		try {
			PrintStream fileWriter = new PrintStream(new File(fileName));
			
			for (int i = 0; i < products.size(); i++) {
			    fileWriter.println("# " + products.get(i).getProductName());
			    for(int j = 0; j < products.get(i).getTasks().size(); j++) {
			    	fileWriter.println("* " + products.get(i).getTasks().get(j).getTaskId() + "," +
			    			products.get(i).getTasks().get(j).getStateName() + "," + products.get(i).getTasks().get(j).getTitle()
			    			+ "," + products.get(i).getTasks().get(j).getTypeShortName() + "," + products.get(i).getTasks().get(j).getCreator()
			    			+ "," + products.get(i).getTasks().get(j).getOwner() + "," +
			    			products.get(i).getTasks().get(j).isVerified());
			    	for(int r = 0; r < products.get(i).getTasks().get(j).getNotesArray().length; r++) {
				    	fileWriter.println("- " + products.get(i).getTasks().get(j).getNotesArray()[r]);
			    	}
			    }
			}
		
			fileWriter.close();
		} catch(FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to save file");
		}
	}

}