package edu.ncsu.csc216.product_backlog.model.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.ncsu.csc216.product_backlog.model.product.Product;
import edu.ncsu.csc216.product_backlog.model.task.Task;

/**
 * This class reads a given file and processes its contents to generate a list of products and tasks.
 * 
 * @author Riya Gunda  
 */
public class ProductsReader {

    
	/**
     * Reads the specified file and processes its contents to generate a list of products and tasks.
	 * @param fileName of the file to read from
	 * @return a list of products generated from the file
	 * @throws IllegalArgumentException with the message "Unable to load file." if the file cannot be
	 * loaded
	 */
    public static ArrayList<Product> readProductsFile(String fileName) {
        ArrayList<Product> products = new ArrayList<Product>();

        try {
            File file = new File(fileName);
            StringBuilder fileContent = new StringBuilder();

            try (Scanner fileScanner = new Scanner(new FileInputStream(file))) {
                while (fileScanner.hasNextLine()) {
                    fileContent.append(fileScanner.nextLine()).append("\n");
                }
            }

            try (Scanner productScanner = new Scanner(fileContent.toString())) {
                productScanner.useDelimiter("\\r?\\n?[#]");

                while (productScanner.hasNext()) {
                    String productToken = productScanner.next();
                    Product product = processProduct(productToken);
                    if (product != null) {
                        products.add(product);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Unable to load file."); 
        }

        return products;
    }
    
    /**
	 * Processes a product token from the file content.
	 * @param productString token string
	 * @return the product created from the token
	 */
    private static Product processProduct(String productString) {
        try (Scanner productScanner = new Scanner(productString)) {
            productScanner.useDelimiter("\\r?\\n?[#]");
            String productName = productScanner.nextLine().substring(1).trim();
            Product product = new Product(productName);

            productScanner.useDelimiter("\\r?\\n?[*]");

            while (productScanner.hasNext()) {
                String taskToken = productScanner.next().substring(1).trim();
                if (!taskToken.isEmpty()) {
                    Task task = processTask(taskToken);
                    if (task != null) {
                        if (product.getTasks() == null || product.getTasks().isEmpty()) {
                            product.addTask(task);
                        } else {
                            Task existingTask = product.getTaskById(task.getTaskId());
                            if (existingTask == null) {
                                product.addTask(task);
                            }
                        }
                    }
                }
            }
            if(!product.getTasks().isEmpty()) {
            	return product;
            } 
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
	 * Processes a task token from the file content.
	 * @param taskToken string
	 * @return the task created from the token
	 */
    private static Task processTask(String taskToken) {
        try (Scanner taskScanner = new Scanner(taskToken)) {
            taskScanner.useDelimiter("\\r?\\n?[*]");
            String taskLine = taskScanner.nextLine();

            try (Scanner line = new Scanner(taskLine)) { 
                line.useDelimiter(",");

                int id = line.nextInt();
                String state = line.next();
                String title = line.next();
                String type = line.next();
                String creator = line.next(); 
                String owner = line.next();
                String verified = line.next();
                
                if(!"Backlog".equals(state) && !"Done".equals(state) && !"Processing".equals(state) 
                		&& !"Verifying".equals(state) && !"Rejected".equals(state) && !"Owned".equals(state)) {
                	return null;
                }

                ArrayList<String> notes = new ArrayList<>();
                taskScanner.useDelimiter("\\r?\\n?[-]");
                while (taskScanner.hasNext()) {
                    String note = taskScanner.next().substring(1).trim();
                    notes.add(note);
                }

                if (notes.isEmpty()) {
                	return null; 
                }
                
                String tt = "";
                if("F".equals(type)) {
                	tt = "Feature";
                } else if("B".equals(type)) {
                	tt = "Bug";
                } else if("KA".equals(type)) {
                	tt = "Knowledge Acquisition";
                } else if("TW".equals(type)){
                	tt = "Technical Work";
                }
                
                Task t = new Task(id, state, title, tt, creator, owner, verified, notes);
                return t;
            }
        } catch (Exception e) {
            return null; 
        }
    }

}