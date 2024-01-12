/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.backlog;

import java.util.ArrayList;



import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.io.ProductsReader;
import edu.ncsu.csc216.product_backlog.model.io.ProductsWriter;
import edu.ncsu.csc216.product_backlog.model.product.Product;
import edu.ncsu.csc216.product_backlog.model.task.Task;

/**
 * The BacklogManager class provides functionality for editing the product backlog, including methods
 * for adding, deleting, and managing tasks and products. This class implements the Singleton design 
 * pattern to ensure a single instance is used throughout the application.
 * @author Riya Gunda
 */
public class BacklogManager {
	/**
	 * Constant value for the instance of the class
	 */
	private static BacklogManager singleton;
	
	/**
	 * Private list of all the products
	 */
	private ArrayList<Product> products;
	
	/**
	 * Private variable for the current product
	 */
	private Product currentProduct;
	
	/**
	 * This is a parameterless constructor for the class
	 */
	private BacklogManager() {		
		clearProducts();
	}

	/**
	 * Returns the instance of the backlog manager
	 * @return instance of the class
	 */
	public static BacklogManager getInstance() {
		if(singleton == null) {
			singleton = new BacklogManager();
		}
		return singleton;
	}

	
	/**
	 * Saves the products list into a file.
	 * @param fileName of the file to save to
	 * @throws IllegalArgumentException if the current product is null or empty with the message
	 * "Unable to save file."
	 */
	public void saveToFile(String fileName) {
		if(currentProduct == null || currentProduct.getTasks().size() == 0) {
			throw new IllegalArgumentException("Unable to save file.");
		}
		
		ProductsWriter.writeProductsToFile(fileName, products);

	}

	/**
	 * Loads the products and tasks into the end of the products list 
	 * @param fileName the method should load data from
	 */
	public void loadFromFile(String fileName) {
		ArrayList<Product> temp = ProductsReader.readProductsFile(fileName);
		
		if(temp.size() == 0) {
			if(products.size() == 0) {
				currentProduct = null;
			} else {
				currentProduct = products.get(0);
			}
		} else {
			for(int i = 0; i < temp.size(); i++) {
				products.add(temp.get(i));
			}
			currentProduct = temp.get(0);
		}
	} 

	/**
	 * Finds the product with the given name in the list of products and replaces currentProduct with
	 * that value.
	 * @param product of the product to load
	 * @throws IllegalArgumentException with the message "Product not available." if the product does
	 * not exist in the list.
	 */
	public void loadProduct(String product) {
		boolean exists = false;
		for(int i = 0; i < products.size(); i++) {
			if(products.get(i).getProductName().equals(product)) {
				currentProduct = products.get(i);
				exists = true;
			}
		}
		if(!exists) {
			throw new IllegalArgumentException("Product not available.");
		}
	}

	/**
	 * Checks to see if the product to be added or edited already exists in the products list
	 * @param productName of the product being added or edited
	 */
	private void isDuplicateProduct(String productName) {
		for(int i = 0; i < products.size(); i++) {
			if(products.get(i).getProductName().equals(productName)) {
				throw new IllegalArgumentException("Invalid product name.");
			}
		}
	}

	/**
	 * Returns a 2D array with the tasks id, state, type, and title
	 * @return a 2D array with the above information
	 */
	public String[][] getTasksAsArray() {
		if(currentProduct == null) {
			return null;
		}
		String[][] taskArr = new String[currentProduct.getTasks().size()][4];
		for(int i = 0; i < currentProduct.getTasks().size(); i++) {
			taskArr[i][0] = ""  + currentProduct.getTasks().get(i).getTaskId();
			taskArr[i][1] = ""  + currentProduct.getTasks().get(i).getStateName();
			taskArr[i][2] = ""  + currentProduct.getTasks().get(i).getTypeLongName();
			taskArr[i][3] = ""  + currentProduct.getTasks().get(i).getTitle();
		}
		return taskArr;
	}

	/**
	 * Returns the task corresponding to the given id number
	 * @param id of the task to find
	 * @return task to find
	 */
	public Task getTaskById(int id) {	
		if(currentProduct == null) {
			return null;
		}
		return currentProduct.getTaskById(id);
	}

	/**
	 * This method calls the update method in the Task class and updates the given task to the next state
	 * based on the given condition
	 * @param id of the task to update
	 * @param c command to execute on the given task
	 */
	public void executeCommand(int id, Command c) {
		getTaskById(id).update(c);
	}

	/**
	 * Deletes the task with the given id
	 * @param id of the task to be deleted
	 * @throws IllegalArgumentException with the message "No task selected." if the task does not exist
	 * or is empty or null.
	 */
	public void deleteTaskById(int id) {
		if(currentProduct != null) {
			currentProduct.deleteTaskById(id);	
		}
	}

	/**
	 * This method adds a task to the given product
	 * @param title of the task to be added
	 * @param taskType of the task to be added
	 * @param creator of the task to be added
	 * @param note for the task to be added
	 * @throws IllegalArgumentException with the message "Task cannot be created." if any one of the 
	 * parameters are empty except note.
	 */
	public void addTaskToProduct(String title, Task.Type taskType, String creator, String note) {
		try {
			currentProduct.addTask(title, taskType, creator, note);
		} catch(IllegalArgumentException e) {
			throw new IllegalArgumentException("Task cannot be created.");
		}
		
	}

	/**
	 * R'eturns the product name for the product that is currently in currentProduct. Returns null if 
	 * it holds nothing.
	 * @return null or the name of the product
	 */
	public String getProductName() {
		if(currentProduct == null) {
			return null;
		}
		return currentProduct.getProductName();
	}

	/**
	 * Returns a String array of product names in the order they are listed in the products list. 
	 * @return a String array of products
	 */
	public String[] getProductList() {
		String[] p = new String[products.size()];
		for(int i = 0; i < products.size(); i++) {
			p[i] = products.get(i).getProductName();
		}
		return p;
	}

	/**
	 * Resets the products list so that it is empty and sets currentProduct to null.
	 */
	public void clearProducts() {
		products = new ArrayList<Product>();
		currentProduct = null;
	}

	/**
	 * This method updates the name of the product with the given name in the parameter. This method
	 * checks if a product with the same name already exists in the list of products.
	 * @param updateName the name of the product will change to
	 * @throws IllegalArgumentException with the message "No product selected." if the current product
	 * is null or empty
	 */
	public void editProduct(String updateName) {
		isDuplicateProduct(updateName);
		
		if(currentProduct == null) {
			throw new IllegalArgumentException("No product selected.");
		}
		
		for(int i = 0; i < products.size(); i++) {
			if(products.get(i).getProductName().equals(currentProduct.getProductName())) {
				products.get(i).setProductName(updateName);
			}
		}
	}

	/**
	 * Creates a new product with the given name in the parameter and adds it to the list of products.
	 * @param productName of the product
	 * @throws IllegalArgumentException is thrown with the message "Invalid product name." if the name
	 * is null or an empty string.
	 */
	public void addProduct(String productName) {
		isDuplicateProduct(productName);
		
		Product p = new Product(productName);
		products.add(p);
		loadProduct(productName);
	}

	/**
	 * Deletes the current product and updates currentProduct to the product at index 0 or null if the
	 * product list is empty.
	 * @throws IllegalArgumentException with the message "No product selected" if the current product
	 * is null or empty.
	 */
	public void deleteProduct() {
		if(products.size() == 0) {
			currentProduct = null;
		} else {
			currentProduct = products.get(0);
		}
		
		if(currentProduct == null) {
			throw new IllegalArgumentException("No product selected.");
		}
		
		for(int i = 0; i < products.size(); i++) {
			if(products.get(i).getProductName().equals(currentProduct.getProductName())) {
				
				products.remove(i);
			}
		}
		
		if(products.size() == 0) {
			currentProduct = null;
		} else {
			currentProduct = products.get(0);
		}
	}

	/**
	 * This method was created to test the BacklogManager class and it sets the value of the singleton
	 * to null
	 */
	protected void resetManager() {
		singleton = null;
	}
}