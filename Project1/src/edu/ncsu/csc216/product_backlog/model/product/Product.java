package edu.ncsu.csc216.product_backlog.model.product;

import java.util.ArrayList;


import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.task.Task;
import edu.ncsu.csc216.product_backlog.model.task.Task.Type;

/**
 * This class holds the product and maintains the tasks in it.
 * @author Riya Gunda
 */
public class Product {
	/** Private variable for the name of the product */
	private String productName;
	/** Private variable for number of products to set ID */
	private int counter;
	/** Private array list of the tasks for each product */
	private ArrayList<Task> tasks;
	
	/** constructor for the class that sets the name for the product
	 * @param productName for the product to be created*/
	public Product(String productName) {
		setProductName(productName);
		emptyList();
	}
	
	/**
	 * Sets the products name
	 * @param productName of the products
	 */
 	public void setProductName(String productName) {
 		if(productName == null || "".equals(productName)) {
 			throw new IllegalArgumentException("Invalid product name.");
 		}
 		this.productName = productName;
	}

 	/**
 	 * Returns the product name
 	 * @return product name as a String
 	 */
	public String getProductName() {
		return productName;
	}

	
	private void setTaskCounter() {
		counter = tasks.size() + 1;
	}

	private void emptyList() {
		tasks = new ArrayList<Task>();
		setTaskCounter();
	}

	/**
	 * This method adds the given task object to the product if it is not a duplicate and then proceeds
	 * to inrease the counter value 
	 * @param task to be added to the product
	 */
	public void addTask(Task task) {		
		for(int i = 0; i < tasks.size(); i++) {
			Task temp = tasks.get(i);
			if(temp.getTaskId() == task.getTaskId()) {
				throw new IllegalArgumentException("Task cannot be added.");
			}
		}
		
		int marker = -1;
		for(int i = 0; i < tasks.size(); i++) {
			if(tasks.get(i).getTaskId() < task.getTaskId()) {
				marker = i;
			}
		}
		tasks.add(marker + 1, task);
		counter++;
	} 

	/**
	 * This method calls the addTask(Task t) method and does the exact same thing except creates the 
	 * task in the method
	 * @param title of the Task
	 * @param type of the Task
	 * @param creator of the Task
	 * @param note for the task
	 */
	public void addTask(String title, Type type, String creator, String note) {
		Task t = new Task(counter, title, type, creator, note);
		addTask(t);
	}

	/**
	 * Returns the list of tasks in the product
	 * @return list of tasks
	 */
	public ArrayList<Task> getTasks() {
		return tasks;
	}

	/**
	 * Returns a task from the product with the given id
	 * @param id of the task to be returned
	 * @return Task from the product with the given ID
	 */
	public Task getTaskById(int id) {
		for(int i = 0; i < tasks.size(); i++) {
			if(tasks.get(i).getTaskId() == id) {
				return tasks.get(i);
			}
		}
		return null;
	}

	/**
	 * Calls the update method on the given tasks and executes the given command
	 * @param id fo the task to be updated
	 * @param c command to be executed
	 */
	public void executeCommand(int id, Command c) {
		for(int i = 0; i < tasks.size(); i++) {
			if(tasks.get(i).getTaskId() == id) {
				tasks.get(i).update(c); 
			}
		}
	}

	/**
	 * Deletes a task with the given ID from the product 
	 * @param id of the task to delete
	 */
	public void deleteTaskById(int id) {		
		for(int i = 0; i < tasks.size(); i++) {
			if(tasks.get(i).getTaskId() == id) {
				tasks.remove(i);
			}
		}
	}
	
	
}