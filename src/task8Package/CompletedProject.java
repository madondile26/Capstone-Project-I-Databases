package task8Package;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

/**
 * Date: Nov 27-2022
 * <p>
 * This is an application that keeps track of the many projects worked 
 * on by the structural engineering firm called "Poised".
 * 
 * @author Xhasa Madondile
 * @version 1.00
 * 
 * 
 */
public class CompletedProject {

	private static Scanner input;
	
	/**
	 * Main method of this application
	 * 
	 * @param args array of string arguments
	 * @throws ParseException general class of exception produced
	 * by failed or interrupted SQL operations
	 */
	
	public static void main(String[] args) throws ParseException {
		
		try {			
			input = new Scanner(System.in); //Create a scanner that will store all the user inputs.

			Date thisDate = new Date(); //Date class to reference the current date
			SimpleDateFormat dateForm1 = new SimpleDateFormat("yyyy-MM-dd HH:mm a"); //Date format with time.
			SimpleDateFormat dateForm2 = new SimpleDateFormat("yyyy-MM-dd"); //Date format that will be used to track the completion date of a finalised project.
			
			//Create ArrayLists that store lists of entered projects, architects, contractors and customers.
			List<Project> projectList = new ArrayList<Project>(); 
			List<Project> projectList2 = new ArrayList<Project>(); //Create an ArrayList that stores a list containing projects that are going to be renamed using the building type + customer surname.
			List<Person> managerList = new ArrayList<Person>();
			List<Person> architectList = new ArrayList<Person>(); 
			List<Person> strucEngList = new ArrayList<>();
			List<Person> contractorList = new ArrayList<Person>(); 
			List<Person> clientList = new ArrayList<Person>(); 
			
			//Declare prepared statements and initialize them to null.
			PreparedStatement pst1 = null;
			PreparedStatement pst2 = null;
			PreparedStatement pst3 = null;
			PreparedStatement pst4 = null;
			PreparedStatement pst5 = null;
			PreparedStatement pst6 = null;
			PreparedStatement pst7 = null;
			PreparedStatement pst8 = null;
			PreparedStatement pst9 = null;
			PreparedStatement pst10 = null;
			PreparedStatement pst11 = null;
			PreparedStatement pst12 = null;
			PreparedStatement pst13 = null;
			PreparedStatement pst14 = null;
			PreparedStatement pst15 = null;
			PreparedStatement pst16 = null;
			PreparedStatement pst17 = null;
			PreparedStatement pst18 = null;
			PreparedStatement pst19 = null;
			PreparedStatement pst20 = null;
			PreparedStatement pst21 = null;
			PreparedStatement pst22 = null;
			PreparedStatement pst23 = null;

			//Declare and initialize all the listIterators that are going to be used throughout the program.
			ListIterator<Project> li1 = null; 
			ListIterator<Person> li2 = null;
			ListIterator<Person> li3 = null;
			ListIterator<Person> li4 = null;
			ListIterator<Person> li5 = null;
			ListIterator<Person> li6 = null;
			
			//Declare and initialize all variables that will be needed throughout the program.
			int Option;
			int size;
			boolean again = true;
			boolean found = false;
			boolean notPastDeadline = false;
			boolean pastDeadline = false;
			String projectName = "";
			String newProjectName = "";
			int projectNumber = 0;
			int projNumber = 0;
			int manager_ID = 0;
			int strucEng_ID = 0;
			int architect_ID = 0;
			int contractor_ID = 0;
			int numberOfDepartments = 4;
			int super_ID1 = 0;
			int super_ID2 = 0;
			int super_ID3 = 0;
			int super_ID4 = 0;
			int client_ID = 0;
			String buildingType = "";
			String physicalAddress = "";
			int ERF_Number = 0;
			String decimalFormat_1 = "";
			String decimalFormat_2 = "";
			double totalCharged = 0;
			double totalPaid = 0;
			String name5 = "";
			String surname5 = "";
			String physicalAddress5 = "";
			String currentDate = dateForm2.format(thisDate); //Completion date that will be displayed in the database.
			String currentDateTime = dateForm1.format(thisDate); //The current date and time that is found in the console whenever a project is displayed and finalised.
			String dueDate = ""; //String date that will be entered by the user.
			String telephoneNumber5 = "";
			String emailAddress5 = "";
			String requiredDate = "2022-06-16"; //This is a string representation of the final deadline for every project.
			Date projectDeadline = dateForm2.parse(requiredDate); //Any date entered by the user that comes after this deadline will be viewed as overdue/PAST DUE DATE.
			Date newDueDate = null; //A date variable that will be used to parse the String date entered by the user in order to convert it from a String to a Date variable.  
			
			System.out.println(currentDateTime); // Display the date and time at which the user enters the program.
			
			//String representations of the URL, Username, and Password that are passed as arguments in the Connection object;
			String url = "jdbc:mysql://localhost:3306/poisepms";
			String username = "root";
			String password = "madondile$26";
			
			//Below are all the database queries that are used during the progression of this program.  
			String insertQuery1 = "INSERT INTO project(Project_Number, Project_Name, Type_of_Building, Physical_Address, ERF_Number, Total_Charged, Total_Paid, Manager_ID, Due_Date) VALUES(?,?,?,?,?,?,?,?,?)";
			String insertQuery2 = "INSERT INTO employee(Employee_ID, First_Name, Last_Name, Job_Class, Telephone_Number, Email_Address, Physical_Address, Super_ID, Project_Number) VALUES(?,?,?,?,?,?,?,?,?)";
			String insertQuery3 = "INSERT INTO client(Client_ID, First_Name, Last_Name, Telephone_Number, Email_Address, Physical_Address, Project_Number) VALUES(?,?,?,?,?,?,?)";		
			String insertQuery4 = "INSERT INTO finalised_projects(Employee_ID, Completion_Date, Client_ID, Proj_Name, Phys_Address, ERF_Num, Proj_Num) VALUES(?,?,?,?,?,?,?)";
			String insertQuery5 = "INSERT INTO outdated_projects(Employee_ID, Past_Due_Date, Client_ID, Proj_Name, Phys_Address, ERF_Num, Proj_Num) VALUES(?,?,?,?,?,?,?)";
			String insertQuery6 = "INSERT INTO works_with(Employee_ID, Client_ID, Payment_Balance) VALUES(?,?,?)";
			String updateSuper_ID = "UPDATE Employee SET Super_ID=? WHERE Employee_ID=?";
			String updateQuery = "UPDATE Project SET Project_Number=?,Project_Name=?,Type_of_Building=?,Physical_Address=?,ERF_Number=?,Total_Charged=?,Total_Paid=?,Due_Date=? WHERE Project_Number=?";
			
			//String values for the foreign key checks that help avoid avoid ERROR 1452.
			String disableVariable = "SET FOREIGN_KEY_CHECKS=0;";
			String enableVariable = "SET FOREIGN_KEY_CHECKS=1;";
			
			//Connect to the poisepms database, via the jdbc:mysql: channel on localhost (this PC)
			Connection con = DriverManager.getConnection(url, username, password);
			Statement statement = con.createStatement();
			ResultSet results = null;
			int rowsAffected = 0;
			
			while (again) {
				try {
					do { //Menu options.
						System.out.println("\nPlease select your choice of operation out of the following options:\n");
						System.out.println("1.INSERT a project.");
						System.out.println("2.DISPLAY inserted projects.");
						System.out.println("3.SEARCH for a project.");
						System.out.println("4.UPDATE a project.");
						System.out.println("5.FINALISE a project.");
						System.out.println("6.DISPLAY projects that are PAST DUE DATE.");
						System.out.println("7.EXIT THE PROGRAM...");
						System.out.print("Enter your choice: ");
						Option = input.nextInt();
						input.nextLine();
						
						switch (Option) {
							case 1: 
								do {
									System.out.print("\nHow many projects would you like to add? ");
									size = input.nextInt();
									input.nextLine(); //Enter the number of projects that are within the given range.
									while (size < 1 || size > 5) { //Display the below error message if the user enters a project size that is out of range. 
										System.out.println("\nERROR...!!!The size you've just entered is not within the specified range....");									
										System.out.print("Please enter a project size that is greater than 0 but less than 6: ");
										size = input.nextInt();
										input.nextLine();
									}
								} while (size < 1 || size > 5);
								
								//Declare and initialize all the arrays of objects that are going to be used throughout the program.
								Project[] projectArray = new Project[size];
								Project[] projectArray2 = new Project[size];
								Person[] managerArray = new Person[size];
								Person[] architectArray = new Person[size];
								Person[] structEngArray = new Person[size];
								Person[] contractorArray = new Person[size];
								Person[] clientArray = new Person[size];
								int[] supervisorArray = new int[numberOfDepartments];
								
								for (int i = 0; i < size; i++) {
									//Disabling the foreign key check to avoid ERROR 1452.
									rowsAffected = statement.executeUpdate(disableVariable);
									
									System.out.println("\nEnter the details for project # " + (i + 1) + ":\n");
									
									//Prompt the user to enter all the details of the project.
									System.out.print("What is the name of project " + (i + 1) + "? ");
									projectName = input.nextLine();
									System.out.print("What is the number of project " + (i + 1) + "? ");
									projectNumber = input.nextInt();
									input.nextLine();
									System.out.print("What type of building is being designed for project " + (i + 1) +"? ");
									buildingType = input.nextLine();
									
									while (true) { //Input validation while loop to ensure that the user enters only "House", "Apartment", or "Store" as a valid building type.
										if (buildingType.equalsIgnoreCase("House") || buildingType.equalsIgnoreCase("Apartment") || buildingType.equalsIgnoreCase("Store")) {
											break;
										} //Below is an error message that will be displayed whenever the user enters an invalid building type.
										System.out.println("\nERROR!!!...The building type you've just entered is invalid...");
										System.out.print("Please enter either 'House', 'Apartment', or 'Store': ");
										buildingType = input.nextLine();
										System.out.println();
									}
								
									System.out.print("Enter the physical address for project " + (i + 1) + ": ");
									physicalAddress = input.nextLine();
									System.out.print("What is the ERF number for project " + (i + 1) +"? ");
									ERF_Number = input.nextInt();
									input.nextLine();
									
									while (true) { //Input validation while loop to ensure that the user enters an EFR number greater than or equal to 1000 but less than or equal to 9999.
										if (ERF_Number >= 10000 && ERF_Number <= 99999) {
											break;
										} //Below is an error message that will be displayed whenever the user enters an EFR number that is out of range.
										System.out.println("\nERROR!!!...The number you've just entered is not within the specified range...");
										System.out.print("Please enter a five digit ERF number: ");
										ERF_Number = input.nextInt();
										input.nextLine();
										System.out.println();
									}
									
									System.out.print("What is the total amount being charged for project " + (i + 1) + "? ");
									totalCharged = input.nextDouble();
									input.nextLine();
									
									while (true) { //Input validation while loop to ensure that the user enters an amount that is greater than or equal to $10,000,000.00 and less than or equal to $200,000,000.00.
										if (totalCharged >= 10000000 && totalCharged <= 200000000) {
											break;
										} //Below is an error message that will be displayed whenever the property price is out of range.
										System.out.println("\nERROR!!!...The property price you've just entered is too high/low...");
										System.out.println("Please enter a property price ranging between $10,000,000.00");
										System.out.print("and $200,000,000.00: ");
										totalCharged = input.nextDouble();
										input.nextLine();
										System.out.println();
									}
									
									System.out.print("What is the total amount paid up to date for project " + (i + 1) + "? ");
									totalPaid = input.nextDouble();
									input.nextLine();
									
									while (true) { //Input validation while loop to ensure that the user enters an amount that is greater than or equal to $10,000,000.00.
										if (totalPaid >= 10000000) {
											break;
										} //Below is an error message that will be displayed whenever the amount paid is less than $10,000,000.00.
										System.out.println("\nERROR!!!...The amount paid for the property is lower than the minimum required price...");
										System.out.print("Please enter an amount that is greater than or equal to $10,000,000.00: ");
										totalPaid = input.nextDouble();
										input.nextLine();
										System.out.println();
									}
									
									System.out.print("What is the due date for project " + (i + 1) + "? ");
									dueDate = input.nextLine();
									
									while (validDate(dueDate) == false) {
										dueDate = input.nextLine();									
										//Input validation while loop to ensure that the user enters a date that corresponds to the required date format.
										while (dueDate.matches("[0-9]{4}[-]{1}[0-9]{2}[-]{1}[0-9]{2}") == false) {
											System.out.println("\nERROR!!!...The characters of the date you've just entered are invalid...");
											System.out.print("Please enter characters that corresponds with the required date format: ");
											dueDate = input.nextLine();		
										}
									}
									
									//The date entered by the user is now parsed converting it from a String to Date data type. 
									newDueDate = dateForm2.parse(dueDate); //This date will be compared to '2022-06-16', which is the final deadline date for all the projects. 
									
									//Creating an array of objects for the Project class to reference the details of the project:
									projectArray[i] = new Project(projectName, projectNumber, buildingType, physicalAddress, ERF_Number, totalCharged, totalPaid, dueDate);
									projectList = Arrays.asList(projectArray); //Convert the projectArray to an ArrayList of project objects.		
									
									//The DecimalFormat class will be used to convert the totalCharged and the totalPaid attributes to correct currency figures.
									DecimalFormat df = new DecimalFormat("$0,000.00");
									
									//Currency figure conversion.
									decimalFormat_1 = df.format(totalCharged);
									decimalFormat_2 = df.format(totalPaid);
									
									//Make use of a prepared statement since it is a precompiled SQL statement and does not require the user to hard code the values for the INSERT of the Product table.
									pst1 = con.prepareStatement(insertQuery1);
									
									//Set parameter values:
									pst1.setInt(1, projectNumber);
									pst1.setString(2, projectName);
									pst1.setString(3, buildingType);
									pst1.setString(4, physicalAddress);
									pst1.setInt(5, ERF_Number);
									pst1.setString(6, decimalFormat_1);
									pst1.setString(7, decimalFormat_2);
									pst1.setInt(8, manager_ID);
									pst1.setString(9, dueDate);
									
									//Add a row:
									rowsAffected = pst1.executeUpdate();
									
									System.out.println("\nEnter the details of project manager # " + (i + 1) + ":\n" );
									
									//Prompt the user to enter all the details of the manager.
									System.out.print("What is the ID number of manager " + (i + 1) + "? ");
									manager_ID = input.nextInt();
									input.nextLine();
									
									//Add the manager_ID integer to the first index of the supervisor array.
									supervisorArray[0] = manager_ID;
									
									//This query will be used to update the value of the manager ID modifying it from to zero to whatever manager ID number entered by the user.
									String updateManager_ID = "UPDATE Project SET Manager_ID=? WHERE Project_Number=?";
									
									//Make use of a prepared statement since it is a precompiled SQL statement and does not require the user to hard code the architect values for the UPDATE operation of the Project table.
									pst2 = con.prepareStatement(updateManager_ID);
									
									//Set parameter values:
									pst2.setInt(1, manager_ID);
									pst2.setInt(2, projectNumber);
									
									//Update a row
									rowsAffected = pst2.executeUpdate();
									
									System.out.print("What is the name of manager " +  (i + 1) + "? ");
									String name1 = input.nextLine();
									System.out.print("What is manager " +  (i + 1) + "'s surname? ");
									String surname1 = input.nextLine();
									System.out.print("Confirm manager " + (i + 1) + "'s job description: ");
									String jobClass1 = input.nextLine();
									
									while (true) { //Input validation while loop to ensure that the user enters only 'Manager' as a valid job description.
										if (jobClass1.equalsIgnoreCase("Manager")) {
											break;
										} //Below is an error message that will be displayed whenever an invalid job description is entered.
										System.out.println("\nERROR!!!...The job description you've just entered for this employee is invalid...");
										System.out.print("Please enter 'Manager' to confirm this employee's job description: ");
										jobClass1 = input.nextLine();
										System.out.println();
									}
									
									System.out.print("What is manager " +  (i + 1) + "'s telephone number? ");
									String telephoneNumber1 = input.nextLine();
									
									for (int j = 0; j < telephoneNumber1.length(); j++) {
										while (true) { //Input validation while loop to ensure that the user enters only 10 digits for the architect's telephone number.
											if (telephoneNumber1.matches("[(]{1}[0-9]{3}[)]{1}[-]{1}[0-9]{3}[' ']{1}[0-9]{4}") == true) {
												break;
											} //Below is an error message that will be displayed whenever the user has entered the manager's telephone number in an incorrect format.
											System.out.println("\nERROR!!!.....The characters you've used for this phone number are invalid.....");
											System.out.println("Please enter the manager's phone number again using parentheses-hyphen-space formatting");
											System.out.print("(800)-555 1212 as the required phone number format: ");
											telephoneNumber1 = input.nextLine();
											System.out.println();
										}
									}
									
									System.out.print("What is manager " +  (i + 1)  + "'s email address? ");
									String emailAddress1 = input.nextLine();
									System.out.print("What is manager " +  (i + 1) + "'s physical address? ");
									String physicalAddress1 = input.nextLine();
									
									//Creating an array of objects for the Person class to reference the details of the manager: 
									managerArray[i] = new Person(manager_ID, name1, surname1, telephoneNumber1, emailAddress1, physicalAddress1);
									managerList = Arrays.asList(managerArray); //Convert managerArray to an ArrayList of manager objects.
									
									//Make use of a prepared statement since it is a precompiled SQL statement and does not require the user to hard code the manager values for the INSERT operation of the Employee table.
									pst3 = con.prepareStatement(insertQuery2);
									
									//Set parameter values:
									pst3.setInt(1, manager_ID);
									pst3.setString(2, name1);
									pst3.setString(3, surname1);
									pst3.setString(4, jobClass1);
									pst3.setString(5, telephoneNumber1);
									pst3.setString(6, emailAddress1);
									pst3.setString(7, physicalAddress1);
									pst3.setInt(8, super_ID1);
									pst3.setInt(9, projectNumber);
									
									//Add a row:
									rowsAffected = pst3.executeUpdate();
									
									System.out.println("\nEnter the details of architect # " + (i + 1) + ":\n");
									
									//Prompt the user to enter all the details of the architect.
									System.out.print("What is the ID number of architect " + (i + 1) + "? ");
									architect_ID = input.nextInt();
									input.nextLine();
									
									//Add the architect_ID integer to the second index of the supervisor array.
									supervisorArray[1] = architect_ID;
									
									System.out.print("What is the name of architect " +  (i + 1) + "? ");
									String name2 = input.nextLine();
									System.out.print("What is architect " +  (i + 1) + "'s surname? ");
									String surname2 = input.nextLine();
									System.out.print("Confirm architect " + (i + 1) + "'s job description: ");
									String jobClass2 = input.nextLine();
									
									while (true) { //Input validation while loop to ensure that the user enters only 'Architect' as a valid job description.
										if (jobClass2.equalsIgnoreCase("Architect")) {
											break;
										} //Below is an error message that will be displayed whenever an invalid job description is entered.
										System.out.println("\nERROR!!!...The job description you've just entered for this employee is invalid...");
										System.out.print("Please enter 'Architect' to confirm this employee's job description: ");
										jobClass2 = input.nextLine();
										System.out.println();
									}
									
									System.out.print("What is architect " +  (i + 1) + "'s telephone number? ");
									String telephoneNumber2 = input.nextLine();
									
									for (int j = 0; j < telephoneNumber2.length(); j++) {
										while (true) { //Input validation while loop to ensure that the user enters only 10 digits for the architect's telephone number.
											if (telephoneNumber2.matches("[(]{1}[0-9]{3}[)]{1}[-]{1}[0-9]{3}[' ']{1}[0-9]{4}") == true) {
												break;
											} //Below is an error message that will be displayed whenever the user has entered the architect's telephone number in an incorrect format.
											System.out.println("\nERROR!!!.....The characters you've used for this phone number are invalid.....");
											System.out.println("Please enter the architect's phone number again using parentheses-hyphen-space formatting");
											System.out.print("(800)-555 1212 as the required phone number format: ");
											telephoneNumber2 = input.nextLine();
											System.out.println();
										}
									}
									
									System.out.print("What is architect " +  (i + 1)  + "'s email address? ");
									String emailAddress2 = input.nextLine();
									System.out.print("What is architect " +  (i + 1) + "'s physical address? ");
									String physicalAddress2 = input.nextLine();
									
									//Creating an array objects for the Person class to reference the details of the architect:
									architectArray[i] = new Person(architect_ID, name2, surname2, telephoneNumber2, emailAddress2, physicalAddress2);
									architectList = Arrays.asList(architectArray); //Convert architectArray into an ArrayList of architect objects.	
									
									//Make use of a prepared statement since it is a precompiled SQL statement and does not require the user to hard code the architect values for the INSERT operation of the Employee table.
									pst4 = con.prepareStatement(insertQuery2);
									
									//Set parameter values:
									pst4.setInt(1, architect_ID);
									pst4.setString(2, name2);
									pst4.setString(3, surname2);
									pst4.setString(4, jobClass2);
									pst4.setString(5, telephoneNumber2);
									pst4.setString(6, emailAddress2);
									pst4.setString(7, physicalAddress2);
									pst4.setInt(8, super_ID2);
									pst4.setInt(9, projectNumber);
									
									//Add a row:
									rowsAffected = pst4.executeUpdate();
									
									System.out.println("\nEnter the details of structural engineer # " + (i + 1) + ":\n");
									
									//Prompt the user to enter all the details of the structural engineer.
									System.out.print("What is the ID number of structural engineer " + (i + 1) + "? ");
									strucEng_ID = input.nextInt();
									input.nextLine();
									
									//Add the strucEng_ID integer to the third index of the supervisor array.
									supervisorArray[2] = strucEng_ID;
									
									System.out.print("What is the name of structural engineer " +  (i + 1) + "? ");
									String name3 = input.nextLine();
									System.out.print("What is structural engineer " +  (i + 1) + "'s surname? ");
									String surname3 = input.nextLine();
									System.out.print("Confirm structural engineer " + (i + 1) + "'s job description: ");
									String jobClass3 = input.nextLine();
									
									while (true) { //Input validation while loop to ensure that the user enters only 'Structural Engineer' as a valid job description.
										if (jobClass3.equalsIgnoreCase("Structural Engineer")) {
											break;
										} //Below is an error message that will be displayed whenever an invalid job description is entered.
										System.out.println("\nERROR!!!...The job description you've just entered for this employee is invalid...");
										System.out.print("Please enter 'Structural Engineer' to confirm this employee's job description: ");
										jobClass3 = input.nextLine();
										System.out.println();
									}
									
									System.out.print("What is structural engineer " +  (i + 1) + "'s telephone number? ");
									String telephoneNumber3 = input.nextLine();
									
									for (int j = 0; j < telephoneNumber3.length(); j++) {
										while (true) { //Input validation while loop to ensure that the user enters only 10 digits for the architect's telephone number.
											if (telephoneNumber3.matches("[(]{1}[0-9]{3}[)]{1}[-]{1}[0-9]{3}[' ']{1}[0-9]{4}") == true) {
												break;
											} //Below is an error message that will be displayed whenever the user has entered the structural engineer's telephone number in an incorrect format.
											System.out.println("\nERROR!!!.....The characters you've used for this phone number are invalid.....");
											System.out.println("Please enter the structural engineer's phone number again using parentheses-hyphen-space");
											System.out.print("formatting (800)-555 1212 as the required phone number format: ");
											telephoneNumber3 = input.nextLine();
											System.out.println();
										}
									}
									
									System.out.print("What is structural engineer " +  (i + 1)  + "'s email address? ");
									String emailAddress3 = input.nextLine();
									System.out.print("What is structural engineer " +  (i + 1) + "'s physical address? ");
									String physicalAddress3 = input.nextLine();
									
									//Creating an array of objects for the Person class to reference the details of the structural engineer.
									structEngArray[i] = new Person(strucEng_ID, name3, surname3, telephoneNumber3, emailAddress3, physicalAddress3);
									strucEngList = Arrays.asList(structEngArray); //Convert strucEngArray to an Arraylist of structural engineer objects.
									
									//Make use of a prepared statement since it is a precompiled SQL statement and does not require the user to hard code the structural engineer values for the INSERT operation of the Employee table.
									pst5 = con.prepareStatement(insertQuery2);
									
									//Set parameter values:
									pst5.setInt(1, strucEng_ID);
									pst5.setString(2, name3);
									pst5.setString(3, surname3);
									pst5.setString(4, jobClass3);
									pst5.setString(5, telephoneNumber3);
									pst5.setString(6, emailAddress3);
									pst5.setString(7, physicalAddress3);
									pst5.setInt(8, super_ID3);
									pst5.setInt(9, projectNumber);
									
									//Add a row:
									rowsAffected = pst5.executeUpdate();
									
									System.out.println("\nEnter the details of contractor # " +  (i + 1) + ":\n");
									
									//Prompt the user to enter all the details of the contractor.
									System.out.print("What is the ID number of contractor " + (i + 1) + "? ");
									contractor_ID = input.nextInt();
									input.nextLine();
									
									//Add the contractor_ID integer to the forth index of the supervisor array.
									supervisorArray[3] = contractor_ID;
									
									System.out.print("What is the name of contractor " + (i + 1) + "? ");
									String name4 = input.nextLine();
									System.out.print("What is contractor " +  (i + 1) + "'s surname? ");
									String surname4 = input.nextLine();
									System.out.print("Confirm contractor " + (i + 1) + "'s job description: ");
									String jobClass4 = input.nextLine();
									
									while (true) { //Input validation while loop to ensure that the user enters only 'Contractor' as a valid job description.
										if (jobClass4.equalsIgnoreCase("Contractor")) {
											break;
										} //Below is an error message that will be displayed whenever an invalid job description is entered.
										System.out.println("\nERROR!!!...The job description you've just entered for this employee is invalid...");
										System.out.print("Please enter 'Contractor' to confirm this employee's job description: ");
										jobClass4 = input.nextLine();
										System.out.println();
									}
									
									System.out.print("What is contractor " +  (i + 1) + "'s telephone number? ");
									String telephoneNumber4 = input.nextLine();

									for (int j = 0; j < telephoneNumber4.length(); j++) {
										while (true) { //Input validation while loop to ensure that the user enters only 10 digits for the contractor's telephone number.
											if (telephoneNumber4.matches("[(]{1}[0-9]{3}[)]{1}[-]{1}[0-9]{3}[' ']{1}[0-9]{4}") == true) {
												break;
											} //Below is an error message that will be displayed whenever the user has entered the contractor's telephone number in an incorrect format.
											System.out.println("\nERROR!!!.....The characters you've used for this phone number are invalid.....");
											System.out.println("Please enter the contractor's phone number again using parentheses-hyphen-space");
											System.out.print("formatting (800)-555 1212 as the required phone number format: ");
											telephoneNumber4 = input.nextLine();
											System.out.println();
										}
									}									
									
									System.out.print("What is contractor " +  (i + 1) + "'s email address? ");
									String emailAddress4 = input.nextLine();
									System.out.print("What is contractor " +  (i + 1) + "'s physical address? ");
									String physicalAddress4 = input.nextLine();
									
									//Creating an array of objects for the Person class to reference the details of the contractor.
									contractorArray[i] = new Person(contractor_ID, name4, surname4, telephoneNumber4, emailAddress4, physicalAddress4);
									contractorList = Arrays.asList(contractorArray); //Convert contractorArray to an Arraylist of contractor objects.		
									
									//Make use of a prepared statement since it is a precompiled SQL statement and does not require the user to hard code the contractor values for the INSERT operation of the Employee table.
									pst6 = con.prepareStatement(insertQuery2);
									
									//Set parameter values:
									pst6.setInt(1, contractor_ID);
									pst6.setString(2, name4);
									pst6.setString(3, surname4);
									pst6.setString(4, jobClass4);
									pst6.setString(5, telephoneNumber4);
									pst6.setString(6, emailAddress4);
									pst6.setString(7, physicalAddress4);
									pst6.setInt(8, super_ID4);
									pst6.setInt(9, projectNumber);
									
									//Add a row:
									rowsAffected = pst6.executeUpdate();
									
									System.out.println("\nEnter the details of client # " +  (i + 1) + ":\n");
									
									//Prompt the user to enter all the details of the customer.
									System.out.print("What is the ID number of client " + (i + 1) + "? ");
									client_ID = input.nextInt();
									input.nextLine();
									System.out.print("What is the name of client " +  (i + 1) + "? ");
									name5 = input.nextLine();
									System.out.print("What is client " +  (i + 1) + "'s surname? ");
									surname5 = input.nextLine();
									System.out.print("What is client " +  (i + 1) + "'s telephone number? ");
									telephoneNumber5 = input.nextLine();
									
									for (int j = 0; j < telephoneNumber5.length(); j++) {
										while (true) { //Input validation while loop to ensure that the user enters only 10 digits for the customer's telephone number.
											if (telephoneNumber5.matches("[(]{1}[0-9]{3}[)]{1}[-]{1}[0-9]{3}[' ']{1}[0-9]{4}") == true) {
												break;
											} //Below is an error message that will be displayed whenever the user has entered the client's telephone number in an incorrect format.
											System.out.println("\nERROR!!!.....The characters you've used for this phone number are invalid.....");
											System.out.println("Please enter the client's phone number again using parentheses-hyphen-space formatting");
											System.out.print("(800)-555 1212 as the required phone number format: ");
											telephoneNumber5 = input.nextLine();
											System.out.println();
										}
									}
									
									System.out.print("What is client " +  (i + 1) + "'s email address? ");
									emailAddress5 = input.nextLine();
									System.out.print("What is client " +  (i + 1) + "'s physical address? ");
									physicalAddress5 = input.nextLine();
									
									//Creating an array of objects for the Person class to reference the details of the customer.
									clientArray[i] = new Person(client_ID, name5, surname5, telephoneNumber5, emailAddress5, physicalAddress5);									
									clientList = Arrays.asList(clientArray); //Convert customerArray to an ArrayList of customer objects.	
									
									//Make use of a prepared statement since it is a precompiled SQL statement and does not require the user to hard code the values for the INSERT operation of the customer table.
									pst7 = con.prepareStatement(insertQuery3);
									
									//Set parameter values:
									pst7.setInt(1, client_ID);
									pst7.setString(2, name5);
									pst7.setString(3, surname5);
									pst7.setString(4, telephoneNumber5);
									pst7.setString(5, emailAddress5);
									pst7.setString(6, physicalAddress5);
									pst7.setInt(7, projectNumber);
									
									//Add a row:
									rowsAffected = pst7.executeUpdate();
									
									//Employees will each get a chance to supervise each other except for the manager, who is in charge of self-supervision an the supervision of other employees.
									System.out.print("\nEnter the supervisor ID of the Manager: ");
									super_ID1 = input.nextInt();
									input.nextLine();
									
									for (int j = 0; j < supervisorArray.length; j++) { //Iterate through the array.
										while (true) { //Input validation while loop to ensure that the user enters only the ID of the manager to show that they are in charge of self-supervision.
											if (super_ID1 == manager_ID) {
												break;
											} //Below is an error message that will be displayed if an employee ID that does not belong to the manager is entered.
											System.out.println("\nERROR!!!......The supervisor ID you've just entered has resulted in a logical error....");
											System.out.println("Please enter the manager's ID to confirm that he/she is in charge of both self-supervision");
											System.out.print("and the supervision of other employees so that correct protocol can be followed: ");
											super_ID1 = input.nextInt();
											input.nextLine();
										}
									}
									
									//Make use of a prepared statement since it is a precompiled SQL statement and does not require the user to hard code the values for the UPDATE operation.
									pst8 = con.prepareStatement(updateSuper_ID);
									
									//Set parameter values:
									pst8.setInt(1, super_ID1);
									pst8.setInt(2, manager_ID);
									
									//Update a row
									rowsAffected = pst8.executeUpdate();
									
									System.out.print("\nEnter the supervisor ID of the Architect: ");
									super_ID2 = input.nextInt();
									input.nextLine();
									
									for (int j = 0; j < supervisorArray.length; j++) { //Iterate through the array.
										while (true) { //Input validation while loop to ensure that the user enters the ID of either manager, structural engineer, or contractor.
											if (super_ID2 == manager_ID || super_ID2 == strucEng_ID || super_ID2 == contractor_ID) {
												break;
											} //Below is an error message that will be displayed whenever the user enters the ID of the architect or an invalid ID number.
											System.out.println("\nERROR!!!...The supervisor ID you've just entered is invalid/does not correspond with any of the employee ID numbers...");
											System.out.println("Please enter any of the stated employee ID numbers that will take the role of a superviser while taking into account that");
											System.out.print("employees from different departments can supervise each other: ");
											super_ID2 = input.nextInt();
											input.nextLine();
										}
									}
									
									//Make use of a prepared statement since it is a precompiled SQL statement and does not require the user to hard code the values for the UPDATE operation.
									pst9 = con.prepareStatement(updateSuper_ID);
									
									//Set parameter values:
									pst9.setInt(1, super_ID2);
									pst9.setInt(2, architect_ID);
									
									//Update a row
									rowsAffected = pst9.executeUpdate();
																	
									System.out.print("\nEnter the supervisor ID of the Structural Engineer: ");
									super_ID3 = input.nextInt();
									input.nextLine();
									
									for (int j = 0; j < supervisorArray.length; j++) { //Iterate through the array.
										while (true) { //Input validation while loop to ensure that the user enters the ID of either manager, architect, or contractor.
											if (super_ID3 == manager_ID || super_ID3 == architect_ID || super_ID3 == contractor_ID) {
												break;
											} //Below is an error message that will be displayed whenever the user enters the ID of the structural engineer or an invalid ID number.
											System.out.println("\nERROR!!!...The supervisor ID you've just entered does not correspond with any of the employee ID numbers...");
											System.out.println("Please enter any of the stated employee ID number that will take the role of a superviser while taking into");
											System.out.print("account that employees from different departments can supervise each other: ");
											super_ID3 = input.nextInt();
											input.nextLine();
										}
									}
									
									//Make use of a prepared statement since it is a precompiled SQL statement and does not require the user to hard code the values for the UPDATE operation.
									pst10 = con.prepareStatement(updateSuper_ID);
									
									//Set parameter values:
									pst10.setInt(1, super_ID3);
									pst10.setInt(2, strucEng_ID);
									
									//Update a row
									rowsAffected = pst10.executeUpdate();
									
									System.out.print("\nEnter the supervisor ID of the Contractor: ");
									super_ID4 = input.nextInt();
									input.nextLine();
									
									for (int j = 0; j < supervisorArray.length; j++) { //Iterate through the array.
										while (true) { //Input validation while loop to ensure that the user enters the ID of either manager, architect, or structural engineer.
											if (super_ID4 == manager_ID || super_ID4 == architect_ID || super_ID4 == strucEng_ID) {
												break;
											} //Below is an error message that will be displayed whenever the user enters the ID of the contractor or an invalid ID number.
											System.out.println("\nERROR!!!...The supervisor ID you've just entered does not correspond with any of the employee ID numbers...");
											System.out.println("Please enter any of the stated employee ID number that will take the role of a superviser while taking into");
											System.out.print("account that employees from different departments can supervise each other: ");
											super_ID4 = input.nextInt();
											input.nextLine();
										}
									}
									
									//Make use of a prepared statement since it is a precompiled SQL statement and does not require the user to hard code the values for the UPDATE operation.
									pst11 = con.prepareStatement(updateSuper_ID);
									
									//Set parameter values:
									pst11.setInt(1, super_ID4);
									pst11.setInt(2, contractor_ID);
									
									//Update a row
									rowsAffected = pst11.executeUpdate();
									
									if (projectName.equalsIgnoreCase("")) { //If the project name is not provided.
										
										//Replace the instance of the empty("") string with the building type followed by the customer's surname.
										newProjectName = projectName.replaceAll("", projectArray[i].getTypeOfBuilding() + " " + clientArray[i].getSurname()); 
										
										//Creating an array of objects for the Project class to reference the project names renamed using the building type followed by the customer's surname.
										projectArray2[i] = new Project(newProjectName, projectNumber, buildingType, physicalAddress, ERF_Number, totalCharged, totalPaid, dueDate);
										projectList2 = Arrays.asList(projectArray2); //Convert project2Array to an ArrayList of project2 objects.
										
										//This query is used for entering a building type followed by the client's surname on condition that a project name is not provided.
										String updateProjName = "UPDATE project SET Project_Name=? WHERE Project_Number=?";
										
										//Make use of a prepared statement since it is a precompiled SQL statement and does not require the user to hard code the values for the UPDATE operation.
										pst12 = con.prepareStatement(updateProjName);
										
										//Set parameter values:
										pst12.setString(1, newProjectName);
										pst12.setInt(2, projectNumber);
										
										//Change a row:
										rowsAffected = pst12.executeUpdate();
									} 
									
									//Foreign key check enabled.
									rowsAffected = statement.executeUpdate(enableVariable);
								}
								//Call the projectDetails function while passing all the other necessary arguments.
								projectDetails(projectList, projectList2, managerList, architectList, strucEngList, contractorList, clientList,
										projectName, projectArray, projectArray2, managerArray, architectArray, structEngArray, contractorArray,
										clientArray, size);			
							break;
							
							case 2:
								//List iterators that are used to traverse through all the lists and display them on console.
								li1 = projectList.listIterator();
								li2 = managerList.listIterator();
								li3 = architectList.listIterator();
								li4 = strucEngList.listIterator();
								li5 = contractorList.listIterator();
								li6 = clientList.listIterator();
								
								//Call all the functions that read the inserted project, architect, contractor and the customer information from the database in a table format.
								readFromDatabase1(statement, results);
								readFromDatabase2(statement, results);
								readFromDatabase3(statement, results);
								
								//Display all the inserted data in a readable format.
								System.out.println("\n                                       POISED STRUCTURAL ENGINEERING MAIN");
								System.out.println("                                       ----------------------------------");
								System.out.println("\nNEW PROJECTS THAT HAVE BEEN RECENTLY ADDED SUCCESSFULLY...!!!");
								System.out.println("Date And Time: " + currentDateTime);
								System.out.println("----------------------------------------------------------------------------------------------------------------");
								
								//Display all the inserted projects along with their respective architects, contractors, and customers on the output screen by using the listIterator.
								for (int j = 0; j < projectList.size(); j++) {							
									while (li1.hasNext()) {
										System.out.println("PROJECT MANAGEMENT SYSTEM # " + (j + 1) +":");
										System.out.println("----------------------------------------------------------------------------------------------------------------");
										Project p = (Project) li1.next();
										System.out.println("PROJECT DETAILS # " + (j + 1) + ":");
										System.out.println("--------------------");
										System.out.println(p); 
										System.out.println("----------------------------------------------------------------------------------------------------------------");
										Person p1 = (Person) li2.next();										
										System.out.println("MANAGER DETAILS # " + (j + 1) + ":");
										System.out.println("--------------------");
										System.out.println(p1);
										System.out.println("----------------------------------------------------------------------------------------------------------------");									
										Person p2 = (Person) li3.next();										
										System.out.println("ARCHITECT DETAILS # " + (j + 1) + ":");
										System.out.println("----------------------");
										System.out.println(p2);
										System.out.println("----------------------------------------------------------------------------------------------------------------");								
										Person p3 = (Person) li4.next();
										System.out.println("STRUCTURAL ENGINEER DETAILS # " + (j + 1) + ":");
										System.out.println("--------------------------------");
										System.out.println(p3);
										System.out.println("----------------------------------------------------------------------------------------------------------------");
										Person p4 = (Person) li5.next();
										System.out.println("CONTRACTOR DETAILS # " + (j + 1) + ":");
										System.out.println("-----------------------");
										System.out.println(p4);
										System.out.println("----------------------------------------------------------------------------------------------------------------");
										Person p5 = (Person) li6.next();
										System.out.println("CLIENT DETAILS # " + (j + 1) + ":");
										System.out.println("-------------------");
										System.out.println(p5);
										System.out.println("----------------------------------------------------------------------------------------------------------------");
										j++;
									}	
									
									//Display this message if the projects are successfully displayed.
									System.out.println("Projects Displayed Successfully...!!!");
								}
								System.out.println("----------------------------------------------------------------------------------------------------------------");
							break;
							
							case 3:
								System.out.print("\nEnter the Project Number to SEARCH: ");
								projNumber = input.nextInt(); //This project number will be compared to the project number entered by the user initially.
								
								System.out.println("----------------------------------------------------------------------------------------------------------------");
								li1 = projectList.listIterator();
								li2 = managerList.listIterator();
								li3 = architectList.listIterator();
								li4 = strucEngList.listIterator();
								li5 = contractorList.listIterator();
								li6 = clientList.listIterator();
								
								while (li1.hasNext()) {
									Project p = (Project) li1.next();
									if (p.getProjectNumber() == projNumber) { //Search for the project using the project number entered by the user.
										System.out.println("PROJECT MANAGEMENT SYSTEM:");
										System.out.println("----------------------------------------------------------------------------------------------------------------");
										System.out.println("PROJECT DETAILS:");
										System.out.println("----------------");
										System.out.println(p);
										System.out.println("----------------------------------------------------------------------------------------------------------------");
										Person p1 = (Person) li2.next();
										System.out.println("MANAGER DETAILS:");
										System.out.println("----------------");
										System.out.println(p1);
										System.out.println("----------------------------------------------------------------------------------------------------------------");
										Person p2 = (Person) li3.next();
										System.out.println("ARCHITECT DETAILS:");
										System.out.println("------------------");
										System.out.println(p2);
										System.out.println("----------------------------------------------------------------------------------------------------------------");
										Person p3 = (Person) li4.next();
										System.out.println("STRUCTURAL ENGINEER DETAILS:");
										System.out.println("----------------------------");
										System.out.println(p3);
										System.out.println("----------------------------------------------------------------------------------------------------------------");
										Person p4 = (Person) li5.next();
										System.out.println("CONTRACTOR DETAILS:");
										System.out.println("-------------------");
										System.out.println(p4);
										System.out.println("----------------------------------------------------------------------------------------------------------------");
										Person p5 = (Person) li6.next();
										System.out.println("CLIENT DETAILS:");
										System.out.println("---------------");
										System.out.println(p5);
										System.out.println("----------------------------------------------------------------------------------------------------------------");
										System.out.println("Record Displayed Successfully...!!!");

										found = true;
									}
									
								}
								if (!found) { //Display this message if the record cannot be found.
									System.out.println("Record Not Found...");
								}
								System.out.println("----------------------------------------------------------------------------------------------------------------");
								
							break;
							
							case 4:
								System.out.print("\nEnter the Project Number to UPDATE: ");
								projNumber = input.nextInt(); //This project number will be compared to the project number entered by the user initially.
								input.nextLine();
								li1 = projectList.listIterator();
								
								//Disabling the foreign key check to avoid ERROR 1452.
								rowsAffected = statement.executeUpdate(disableVariable);
								
								while (li1.hasNext()) {
									Project p = (Project) li1.next();
									if (p.getProjectNumber() == projNumber) { //If the project number entered to update is equivalent the project number entered by the user initially, proceed by updating all the project information.
										System.out.print("\nEnter the new Project Name: ");
										String projectName2 = input.nextLine();
										System.out.print("Enter the new Project Number: ");
										int projectNumber2 = input.nextInt();
										input.nextLine();
										System.out.print("Enter the new Building Type: ");
										String buildingType2 = input.nextLine();
										
										while (true) { //Input validation while loop to ensure that the user enters either "House", "Apartment block", or "Store" as a valid building type.
											if (buildingType2.equalsIgnoreCase("House") || buildingType2.equalsIgnoreCase("Apartment") || buildingType2.equalsIgnoreCase("Store")) {
												break;
											} //Below is an error message that will be displayed whenever the user enters an invalid building type.
											System.out.println("\nERROR!!!...The building type you've just entered is invalid...");
											System.out.print("Please enter either 'House', 'Apartment', or 'Store': ");
											buildingType2 = input.nextLine();
											System.out.println();
										}
									
										System.out.print("Enter the new Physical Address: ");
										String physicalAddress2 = input.nextLine();
										System.out.print("Enter the new EFR Number: ");
										int ERF_Number2 = input.nextInt();
										input.nextLine();
										System.out.print("Enter the new Total Amount Charged: ");
										double totalCharged2 = input.nextDouble();
										input.nextLine();
										System.out.print("Enter the new Total Amount Paid up to date: ");
										double totalPaid2 = input.nextDouble();
										input.nextLine();
										System.out.print("Enter the new Due Date for the project: ");
										String dueDate2 = input.nextLine();
										
										while (validDate(dueDate2) == false) {
											dueDate2 = input.nextLine();
											//Input validation while loop to ensure that the user enters a date that corresponds to the required date format.
											while (dueDate2.matches("[0-9]{4}[-]{1}[0-9]{2}[-]{1}[0-9]{2}") == false) {
												System.out.println("\nERROR!!!...The characters of the date you've just entered are invalid...");
												System.out.print("Please enter characters that corresponds with the required date format: ");
												dueDate2 = input.nextLine();		
											}
										}
										
										li1.set(new Project(projectName, projectNumber, buildingType2, physicalAddress, ERF_Number, totalCharged, totalPaid, dueDate2));
										
										//The DecimalFormat class will be used to convert the totalCharged and the totalPaid attributes to correct currency figures.
										DecimalFormat df2 = new DecimalFormat("$0,000.00");
										
										//Currency figure conversion.
										String decimalFormat1 = df2.format(totalCharged2);
										String decimalFormat2 = df2.format(totalPaid2);
																			
										//Make use of a prepared statement since it is a precompiled SQL statement and does not require the user to hard code the values for the UPDATE operation.
										pst13 = con.prepareStatement(updateQuery);
										
										//Set parameter values:
										pst13.setInt(1, projectNumber2);
										pst13.setString(2, projectName2);
										pst13.setString(3, buildingType2);
										pst13.setString(4, physicalAddress2);
										pst13.setInt(5, ERF_Number2);
										pst13.setString(6, decimalFormat1);
										pst13.setString(7, decimalFormat2);
										pst13.setString(8, dueDate2);
										pst13.setInt(9, projectNumber);
										
										//Update a row:
										rowsAffected = pst13.executeUpdate();
										
										//Call the function that reads the updated version of the project table. 
										readFromDatabase1(statement, results);
										
										//Call this the updateToFile function while passing as argument all the updated information that is to be displayed on console.
										updateToConsole(p, projNumber, projectName2, projectNumber2, buildingType2, 
												physicalAddress2, ERF_Number2, totalCharged2, totalPaid2, dueDate2, currentDateTime);
										
										found = true;			
									}
									
									//Enable foreign key check.
									rowsAffected = statement.executeUpdate(enableVariable);
									
									System.out.println("----------------------------------------------------------------------------------------------------------------");
									if (found) { //Display this message if the record got updated successfully.
										System.out.println("Record Updated Successfully...!!!");
									}else if(!found) { //Display this message if the record cannot be found.
										System.out.println("Record Not Found...");
									}
									System.out.println("----------------------------------------------------------------------------------------------------------------");
								}
							break;
							
							case 5:
								String answer = ""; //User response to the question/prompt.
								
								System.out.print("\nEnter the project number to FINALISE: ");
								projNumber = input.nextInt(); //This project number will be compared to the project number entered by the user initially.
								input.nextLine();

								//List iterators that are used to traverse through all the lists and display them on console.
								li1 = projectList.listIterator();
								li2 = managerList.listIterator();
								li3 = architectList.listIterator();
								li4 = strucEngList.listIterator();
								li5 = contractorList.listIterator();
								li6 = clientList.listIterator();
								
								while (li1.hasNext()) {
									Project p = (Project)li1.next();
									Person p1 = (Person) li2.next();											
									Person p2 = (Person) li3.next();										
									Person p3 = (Person) li4.next();
									Person p4 = (Person) li5.next();
									Person p5 = (Person) li6.next();
									
									if (p.getProjectNumber() == projNumber) {
										System.out.print("\nAre you sure this is the project you want to finalise? ");
										answer = input.nextLine(); //Prompt the user for the certainty of their project finalization.
										
										while (answer.equalsIgnoreCase("Yes") == false) { //Input validation while loop ensuring that the user enters only "Yes" as confirmation to continue.
											System.out.println("\nERROR!!!...Your response to the question is not valid...");
											System.out.print("Please enter 'Yes' to signify that you want this project finalised: ");
											answer = input.nextLine(); //Ensure that the user enters only 'Yes' in order to be able to proceed with the project  
											}	
										
										if (projectDeadline.compareTo(newDueDate) >= 0) { //If the due date of the project is before the final deadline(2022-06-16).
											
											//This function is called to display the invoice of the amount owed by every client who has paid less than the total price of the House, Apartment Block, or Store.
											clientInvoicefunction(totalCharged, totalPaid, physicalAddress5, dueDate, telephoneNumber5, 
													emailAddress5, con, rowsAffected, pst16, pst17, pst18, contractor_ID, client_ID,
													insertQuery6, statement, results);
											
											//Make use of a prepared statement since it is a precompiled SQL statement and does not require the user to hard code the values for the INSERT operation of the Finalised_Projects table.
											pst14 = con.prepareStatement(insertQuery4);
											
											//Set parameter values:
											pst14.setInt(1, contractor_ID);
											pst14.setString(2, currentDate);
											pst14.setInt(3, client_ID);
											pst14.setString(4, projectName);
											pst14.setString(5, physicalAddress);
											pst14.setInt(6, ERF_Number);
											pst14.setInt(7, projectNumber);
											
											//Add a row:
											rowsAffected = pst14.executeUpdate();
											
											//Create new objects of the Person and Project class.
											Person client = new Person(client_ID, name5, surname5, telephoneNumber5, emailAddress5, physicalAddress5);
											Project projectX = new Project(projectName, projectNumber, buildingType, physicalAddress, ERF_Number, totalCharged, totalPaid, dueDate);;
											
											if (projectName.equalsIgnoreCase("")) { //If the project name is not provided.
												
												//Replace the instance of the empty("") string with the building type followed by the client's surname.
												newProjectName = projectName.replaceAll("", projectX.getTypeOfBuilding() + " " + client.getSurname());                                           
												projectX = new Project(newProjectName, projectNumber, buildingType, physicalAddress5, ERF_Number, totalCharged, totalPaid, dueDate);
												
												//This query is used for entering a building type followed by the client's surname on condition that a project name is not provided.
												String updateProjName2 = "UPDATE Finalised_Projects SET Proj_Name=? WHERE Proj_Num=?";
												
												//Make use of a prepared statement since it is a precompiled SQL statement and does not require the user to hard code the values for the UPDATE operation of the Finalised_Projects table.
												pst15 = con.prepareStatement(updateProjName2);
												
												//Set parameter values:
												pst15.setString(1, newProjectName);
												pst15.setInt(2, projectNumber);
												
												//Update a row:
												rowsAffected = pst15.executeUpdate();
											}
											
											//Call the function that reads from the database all the finalised projects.
											readFromDatabase5(statement, results);
											
											//Call the function that displays on console the recently added finalised project.
											finalisedProjects(currentDateTime, projectX, projectName , p, p1, p2, p3, p4, p5);
											
											notPastDeadline = true;
										}
										
										found = true;	
									}
									if (!found) { //Display the message below if the record is found and finalised successfully.	
										System.out.println("----------------------------------------------------------------------------------------------------------------");
										System.out.println("Record Not Found..."); //Display this message if the record cannot be found.
										System.out.println("----------------------------------------------------------------------------------------------------------------");
									}else if(!notPastDeadline){ //Display the message below if the record is past due date.
										System.out.println("----------------------------------------------------------------------------------------------------------------");
										System.out.println("Record Past Due Date...");
										System.out.println("----------------------------------------------------------------------------------------------------------------");
									}				
								}
							break;
							
							case 6:
								String response = ""; //User response to the question/prompt.
								
								System.out.print("\nEnter the number of the project that is past due date: ");
								projNumber = input.nextInt(); //This project number will be compared to the project number entered by the user initially.
								input.nextLine();
								
								//List iterators that are used to traverse through all the lists and display them on console.
								li1 = projectList.listIterator();
								li2 = managerList.listIterator();
								li3 = architectList.listIterator();
								li4 = strucEngList.listIterator();
								li5 = contractorList.listIterator();
								li6 = clientList.listIterator();
								
								while (li1.hasNext()) {
									Project p = (Project)li1.next();
									Person p1 = (Person) li2.next();											
									Person p2 = (Person) li3.next();										
									Person p3 = (Person) li4.next();
									Person p4 = (Person) li5.next();
									Person p5 = (Person) li6.next();
									
									if (p.getProjectNumber() == projNumber) {
										System.out.print("\nAre you sure this project is overdue/PAST DUE DATE? "); //Input validation while loop ensuring that the user enters only "Yes" as confirmation to continue.
										response = input.nextLine(); //Prompt the user for the certainty of their project finalization.
										
										while (response.equalsIgnoreCase("Yes") == false) { //Input validation while loop ensuring that the user enters only "Yes" as confirmation to continue.
											System.out.println("\nERROR!!!...Your response to the question is not valid...");
											System.out.print("Please enter 'Yes' to signify that you are sure of this project being outdated: ");
											response = input.nextLine(); //Ensure that the user enters only 'Yes' in order to be able to proceed with the project  
											}	
										
										if (projectDeadline.compareTo(newDueDate) < 0) { //If the due date of the project is after the final deadline(2022-06-16).
											
											//The DecimalFormat class will be used to convert the totalCharged and the totalPaid attributes to correct currency figures.
											DecimalFormat df = new DecimalFormat("$0,000.00");
											
											if (totalCharged > totalPaid) { //This condition applies if the total amount charged is greater than the total amount paid.
												double balance = totalCharged - totalPaid;
												String amountDue = "-" + df.format(balance); //Currency formated string representation of the amount owed by the client.
												
												//Make use of a prepared statement since it is a precompiled SQL statement and does not require the user to hard code the values for the INSERT operation of the Works_With database table.
												pst21 = con.prepareStatement(insertQuery6);
												
												//Set parameter values:
												pst21.setInt(1, contractor_ID);
												pst21.setInt(2, client_ID);
												pst21.setString(3, amountDue);
												
												//Add a row:
												rowsAffected = pst21.executeUpdate();
												
												//Call the function that reads from database the relationship between the contractor and client together with the payment agreement made between them. 
												readFromDatabase(statement, results);
												
												//Below is a message that will be displayed on console explaining the results of the latest database table entry.
												System.out.println("\nThe latest entry in the database table shows that the client has paid less than the amount ");
												System.out.println("charged meaning that they still owe the above amount....");
												
											}else if (totalCharged < totalPaid) { //This condition applies if the total amount charged is less than the total amount paid.
												double balance = totalPaid - totalCharged;
												String positiveCredit = "+" + df.format(balance); //Currency formated string representation of the positive credit.
												
												//Make use of a prepared statement since it is a precompiled SQL statement and does not require the user to hard code the values for the INSERT operation of the Works_With database table.
												pst22 = con.prepareStatement(insertQuery6);
												
												//Set parameter values:
												pst22.setInt(1, contractor_ID);
												pst22.setInt(2, client_ID);
												pst22.setString(3, positiveCredit);
												
												//Add a row:
												rowsAffected = pst22.executeUpdate();
												
												//Call the function that reads from database the relationship between the contractor and client together with the payment agreement made between them. 
												readFromDatabase(statement, results);
												
												//Below is a message that will be displayed on console explaining the results of the latest database table entry.
												System.out.println("\nThe latest entry in the database table shows that the client has paid more than the amount ");
												System.out.println("charged meaning that they are willing to invest the above amount in future projects....");
												
											} else if (totalCharged == totalPaid) { //This condition applies if the total amount charged is equal to the total amount paid.
												double balance = 0;
												String availableAmount = " " + df.format(balance);  //Currency formated string representation of the available amount($0,000.00).
												
												//Make use of a prepared statement since it is a precompiled SQL statement and does not require the user to hard code the values for the INSERT operation of the Works_With database table.
												pst23 = con.prepareStatement(insertQuery6);
												
												//Set parameter values:
												pst23.setInt(1, contractor_ID);
												pst23.setInt(2, client_ID);
												pst23.setString(3, availableAmount);
												
												//Add a row:
												rowsAffected = pst23.executeUpdate();
												
												//Call the function that reads from database the relationship between the contractor and client together with the payment agreement made between them.
												readFromDatabase(statement, results);
												
												//Below is a message that will be displayed on console explaining the results of the latest database table entry.
												System.out.println("\nThe latest entry in the database table shows that the client has paid the total amount");
												System.out.println("charged in full meaning that they don't owe anything nor do they plan on investing in future");
												System.out.println("projects....");
											}
										
											//Make use of a prepared statement since it is a precompiled SQL statement and does not require the user to hard code the values for the INSERT operation of the Outdated_Projects table.
											pst19 = con.prepareStatement(insertQuery5);
											
											//Set parameter values:
											pst19.setInt(1, contractor_ID);
											pst19.setString(2, dueDate);
											pst19.setInt(3, client_ID);
											pst19.setString(4, projectName);
											pst19.setString(5, physicalAddress);
											pst19.setInt(6, ERF_Number);
											pst19.setInt(7, projectNumber);
											
											//Add a row:
											rowsAffected = pst19.executeUpdate();
											
											//Create new objects of the Person and Project class.
											Person client = new Person(client_ID, name5, surname5, telephoneNumber5, emailAddress5, physicalAddress5);
											Project projectX = new Project(projectName, projectNumber, buildingType, physicalAddress, ERF_Number, totalCharged, totalPaid, dueDate);;
											
											if (projectName.equalsIgnoreCase("")) { //If the project name is not provided.
												
												//Replace the instance of the empty("") string with the building type followed by the customer's surname.
												newProjectName = projectName.replaceAll("", projectX.getTypeOfBuilding() + " " + client.getSurname());                                           
												projectX = new Project(newProjectName, projectNumber, buildingType, physicalAddress5, ERF_Number, totalCharged, totalPaid, dueDate);
												
												//This query is used for entering a building type followed by the client's surname on condition that a project name is not provided.
												String updateProjName3 = "UPDATE Outdated_Projects SET Proj_Name=? WHERE Proj_Num=?";
												
												//Make use of a prepared statement since it is a precompiled SQL statement and does not require the user to hard code the values for the UPDATE operation of the Finalised_Projects table.
												pst20= con.prepareStatement(updateProjName3);
												
												//Set parameter values:
												pst20.setString(1, newProjectName);
												pst20.setInt(2, projectNumber);
												
												//Update a row:
												rowsAffected = pst20.executeUpdate();
											}
											
											//Call the function that reads from the database all the outdated projects.
											readFromDatabase6(statement, results);
											
											//Call the function that displays on console the recently added outdated project.
											outdatedProjects(currentDateTime, projectX, projectName , p, p1, p2, p3, p4, p5);
											
											pastDeadline = true;
										}
										
										found = true;	
									}
									if (!found) { //Display this message if the record is found and finalized successfully.	
										System.out.println("----------------------------------------------------------------------------------------------------------------");
										System.out.println("Record Not Found..."); //Display this message if the record cannot be found.
										System.out.println("----------------------------------------------------------------------------------------------------------------");
									}else if(!pastDeadline){ 
										System.out.println("----------------------------------------------------------------------------------------------------------------");
										System.out.println("Record Not Past Due Date...");
										System.out.println("----------------------------------------------------------------------------------------------------------------");
									}				
								}
								
							break;
								
							case 7: //Provides the only point of exit from the program.
								System.out.println("--------------------------------------YOU'VE JUST EXITED THE PROGRAM...!!!--------------------------------------");
								System.exit(0);
						}
					} while (Option != 7);					

					again = false;
					
				} catch (InputMismatchException e) { //Display an error message if the user enters a value that does not correspond to the required data type.
					System.out.println("\nAN ERROR OCCURED!!!...The input you've just entered does");
					System.out.println("not satisfy/validate the variable type declared...");
					input.next();
				}
			}	
		} catch (SQLException e) { // The only objective is to catch an SQLException.
            e.printStackTrace();
		}
	}
	
	/**
	 * Public method that validates the date entered by the user to ensure
	 * that it is in a correct format.
	 * 
	 * @param date String date entered by the user
	 * @return returning a Boolean of true if the date is entered in a correct 
	 *  format or false if the date date is not entered in a correct format
	 *  
	 *  
	 */
	public static boolean validDate(String date) {	
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //Date format that is to be used.
	
		try {
			LocalDate newDate = LocalDate.parse(date, dtf);
			System.out.println("\nDUE DATE: " + newDate); //Display the valid date on screen.
			return true;
			
		} catch (DateTimeParseException e) { //Display this error message if the date entered by the user cannot be parsed.
			System.out.println("\nAN ERROR OCCURED!!!...The date you've just entered is Unparseable...");
			System.out.println("The date needs to be entered in a correct format for it to be parseable...");
			System.out.print("Please enter the date in a correct format: ");
			return false;	
		}				
	}
	
	/**
	 * Public method that displays the project details on the console.
	 * 
	 * @param projectList first set of projects stored in an ArrayList
	 * @param projectList2 second set of projects stored in an ArrayList
	 * @param managerList collection of managers stored in an ArrayList
	 * @param architectList collection of architects stored in an ArrayList
	 * @param strucEngList collection of structural engineers stored in an ArrayList
	 * @param contractorList collection of contractors stored in an ArrayList
	 * @param clientList collection of clients stored in an ArrayList
	 * @param projectName the name of the project
	 * @param projectArray array of type Project containing project details
	 * @param projectArray2 array of type Project with the project names renamed
	 * @param managerArray array of type Person containing the managers' details
	 * @param architectArray array of type Person containing the architects' details
	 * @param strucEngArray array of type Person containing the structural engineers' details
	 * @param contractorArray array of type Person containing the contractors' details
	 * @param clientArray array of type Person containing the clients' details
	 * @param size number of projects requested by the user
	 * 
	 * 
	 */
	public static void projectDetails(List<Project> projectList, List<Project> projectList2, List<Person> managerList, List<Person> architectList, 
			List<Person> strucEngList,List<Person> contractorList, List<Person> clientList, String projectName, Project[] projectArray, Project[] projectArray2, 
			Person[] managerArray, Person[] architectArray, Person[] strucEngArray, Person[] contractorArray, Person[] clientArray, int size) {
		
		//Display the following information to the console.
		System.out.print("                                          POISED STRUCTURAL ENGINEERING\r\n");
		System.out.print("                                          -----------------------------\r\n");
		System.out.print("\r\nNEW PROJECTS HAVE BEEN ADDED SUCCESSFULLY...!!!\r\n");
		System.out.print("Number of projects: " + size + "\r\n");
		System.out.print("----------------------------------------------------------------------------------------------------------------\r\n");
		for (int j = 0; j < projectArray.length; j++) {
			System.out.print("PROJECT MANAGEMENT SYSTEM # " + (j + 1) + ":\r\n");
			System.out.print("----------------------------------------------------------------------------------------------------------------\r\n");
			System.out.print("PROJECT DETAILS # " + (j + 1) + ":\r\n");
			System.out.print("--------------------\r\n");
			if (projectName.equalsIgnoreCase("") == false) { //Display to the console all the projects that do not have the empty("") string which signifies that a project name is not provided.
				for (Project p : projectList) {
					if (p == projectArray[j]) {
						System.out.print(p + "\r\n");
					}	
				}
			}else if(projectName.equalsIgnoreCase("") == true) { //Name the project using the building type + surname of the customer if the project name is not provided when the information is captured.
				for (Project p2 : projectList2) {
					if (p2 == projectArray2[j]) {
						System.out.print(p2 + "\r\n"); 
					}
				}
			}
			
			//Display to the console all the information captured about the managers.
			System.out.print("----------------------------------------------------------------------------------------------------------------\r\n");
			System.out.print("MANAGER DETAILS # " + (j + 1) + ":\r\n");
			System.out.print("--------------------\r\n");
			for (Person m : managerList) {
				if (m == managerArray[j]) {
					System.out.print(m + "\r\n");
				}
			}
			
			//Display to the console all the information captured about the architects.
			System.out.print("----------------------------------------------------------------------------------------------------------------\r\n");
			System.out.print("ARCHITECT DETAILS # " + (j + 1) + ":\r\n");
			System.out.print("----------------------\r\n");
			for (Person a : architectList) {
				if (a == architectArray[j]) {
					System.out.print(a + "\r\n");
				}
			}
			
			//Display to the console all the information captured about the structural engineers.
			System.out.print("----------------------------------------------------------------------------------------------------------------\r\n");
			System.out.print("STRUCTURAL ENGINEER DETAILS # " + (j + 1) + ":\r\n");
			System.out.print("--------------------------------\r\n");
			for (Person s : strucEngList) {
				if (s == strucEngArray[j]) {
					System.out.print(s + "\r\n");
				}
			}
			
			//Display to the console all the information captured about the contractors.
			System.out.print("----------------------------------------------------------------------------------------------------------------\r\n");
			System.out.print("CONTRACTOR DETAILS # " + (j + 1) + ":\r\n");
			System.out.print("-----------------------\r\n");
			for (Person c : contractorList) {
				if (c == contractorArray[j]) {
					System.out.print(c + "\r\n");
				}
			}
			
			//Display to the console all the information captured about the customers.
			System.out.print("----------------------------------------------------------------------------------------------------------------\r\n");
			System.out.print("CLIENT DETAILS # " + (j + 1) + ":\r\n");
			System.out.print("---------------------\r\n");
			for (Person c : clientList) {
				if (c == clientArray[j]) {
					System.out.print(c + "\r\n");
				}
			}
			System.out.print("----------------------------------------------------------------------------------------------------------------\r\n");
		}
	}
	
	/**
	 * Public method that reads from database the details of all the projects and displays
	 * them to the console in table format.
	 * 
	 * @param statement an interface that will be used to submit to the database an SQL statement
	 * @param results will hold the data retrieved from the database after executing the SQL query
	 * ("SELECT * FROM Project;") using the Statement object
	 * 
	 * 
	 */
	public static void readFromDatabase1(Statement statement, ResultSet results) {
		String display1 = "SELECT * FROM Project;"; //String representation of an SQL query.
		try {
			results = statement.executeQuery(display1); //executeQuery: runs a SELECT statement and returns the results. 
			
			//Display the Project table to the console.
			System.out.println("\n                                                              PROJECT DETAILS:");
			System.out.printf("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------%n");
			System.out.printf("                                                       PROJECT INFROMATION FROM DATABASE                                                                                       %n");
			System.out.printf("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------%n");
			System.out.printf("| %-27s | %14s | %-16s | %-30s | %10s | %-15s | %-15s | %10s | %-10s |%n",
					"Project_Name", "Project_Number", "Type_Of_Building", "Physical_Address",
					"ERF_Number", "Total_Charged", "Total_Paid", "Manager_ID", "Due_Date");
			System.out.printf("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------%n");
			while (results.next()) { //Loop over the results, printing them all in a table format.
				System.out.printf( //Do this by using the printf function.
						"| %-27s | %14d | %-16s | %-30s | %10d | %-15s | %-15s | %10d | %-10s |%n",
						results.getString("Project_Name"),
						results.getInt("Project_Number"),
						results.getString("Type_of_Building"), 
						results.getString("Physical_Address"),
						results.getInt("ERF_Number"), 
						results.getString("Total_Charged"),
						results.getString("Total_Paid"), 
						results.getInt("Manager_ID"),
						results.getString("Due_Date")
						);
			}
			System.out.printf("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------%n");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Public method that reads from database the details of all the employees and displays
	 * them to the console in table format.
	 * 
	 * @param statement an interface that will be used to submit to the database an SQL statement
	 * @param results will hold the data retrieved from the database after executing the SQL query
	 * ("SELECT * FROM Employee;") using the Statement object
	 * 
	 * 
	 */
	public static void readFromDatabase2(Statement statement, ResultSet results) {
		String display2 = "SELECT * FROM Employee;"; //String representation of an SQL query.
		try {
			results = statement.executeQuery(display2); //executeQuery: runs a SELECT statement and returns the results.
			
			//Display the Employee table to the console.
			System.out.println("\n                                                           EMPLOYEE DETAILS:");
			System.out.printf("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------%n");
			System.out.printf("                                                      EMPLOYEE INFROMATION FROM DATABASE                                                                                        %n");
			System.out.printf("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------%n");
			System.out.printf("| %11s | %-10s | %-10s | %-19s | %-16s | %-25s | %-30s | %13s | %14s |%n",
					"Employee_ID", "First_Name", "Last_Name", "Job_Class", "Telephone_Number", 
					"Email_Address", "Physical_Address", "Superviser_ID", "Project_Number");
			System.out.printf("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------%n");
			while (results.next()) { //Loop over the results, printing them all in a table format.
				System.out.printf( //Do this by using the printf function.
						"| %11d | %-10s | %-10s | %-19s | %-16s | %-25s | %-30s | %13d | %14d |%n",
						results.getInt("Employee_ID"),
						results.getString("First_Name"), 
						results.getString("Last_Name"),
						results.getString("Job_Class"),
						results.getString("Telephone_Number"),
						results.getString("Email_Address"), 
						results.getString("Physical_Address"),
						results.getInt("Super_ID"),
						results.getInt("Project_Number")
						);
			}
			System.out.printf("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------%n");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Public method that reads from database the details of all the clients and displays
	 * them to the console in table format.
	 * 
	 * @param statement an interface that will be used to submit to the database an SQL statement.
	 * @param results will hold the data retrieved from the database after executing the SQL query
	 * ("SELECT * FROM Client;") using the Statement object.
	 * 
	 * 
	 */
	public static void readFromDatabase3(Statement statement, ResultSet results) {
		String display3 = "SELECT * FROM Client;"; //String representation of an SQL query.
		try {
			results = statement.executeQuery(display3); //executeQuery: runs a SELECT statement and returns the results.
			
			//Display the Client table to the console.
			System.out.println("\n                                                            CLIENT DETAILS:");
			System.out.printf("------------------------------------------------------------------------------------------------------------------------------------------%n");
			System.out.printf("                                                      CLIENT INFROMATION FROM DATABASE                                                    %n");
			System.out.printf("------------------------------------------------------------------------------------------------------------------------------------------%n");
			System.out.printf("| %9s | %-11s | %-11s | %-16s | %-25s | %-30s | %14s |%n",
					"Client_ID", "First_Name", "Last_Name", "Telephone_Number", 
					"Email_Address", "Physical_Address", "Project_Number");
			System.out.printf("------------------------------------------------------------------------------------------------------------------------------------------%n");
			while (results.next()) { //Loop over the results, printing them all in a table format.
				System.out.printf( //Do this by using the printf function.
						"| %9d | %-11s | %-11s | %-16s | %-25s | %-30s | %14d |%n",
						results.getInt("Client_ID"),
						results.getString("First_Name"), 
						results.getString("Last_Name"), 
						results.getString("Telephone_Number"),
						results.getString("Email_Address"), 
						results.getString("Physical_Address"),
						results.getInt("Project_Number")
						);
			}
			System.out.printf("------------------------------------------------------------------------------------------------------------------------------------------%n");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Public method that reads from database the details of all the finalised projects and displays
	 * them to the console in table format.
	 * 
	 * @param statement an interface that will be used to submit to the database an SQL statement.
	 * @param results will hold the data retrieved from the database after executing the SQL query
	 * ("SELECT * FROM Finalised_Projects;") using the Statement object
	 * 
	 * 
	 */
	public static void readFromDatabase5(Statement statement, ResultSet results) {
		String display4 = "SELECT * FROM Finalised_Projects"; //String representation of an SQL query.
		try {
			results = statement.executeQuery(display4); //executeQuery: runs a SELECT statement and returns the results.
			
			//Display the Finalised_Projects table to the console.
			System.out.println("\n                                             PROJECT FINALISATION DETAILS:");
			System.out.printf("--------------------------------------------------------------------------------------------------------------------------------------%n");
			System.out.printf("                                        PROJECT COMPLETION INFROMATION FROM DATABASE                                                  %n");
			System.out.printf("--------------------------------------------------------------------------------------------------------------------------------------%n");
			System.out.printf("| %13s | %-15s | %9s | %-27s | %-30s | %7s | %11s |%n",
					"Employee_ID", "Completion_Date", "Client_ID", "Proj_Name",
					"Phys_Address", "ERF_Num", "Proj_Num");
			System.out.printf("--------------------------------------------------------------------------------------------------------------------------------------%n");
			while (results.next()) { //Loop over the results, printing them all in a table format.
				System.out.printf( //Do this by using the printf function.
						"| %13d | %-15s | %9d | %-27s | %-30s | %7d | %11d |%n",
						results.getInt("Employee_ID"),
						results.getString("Completion_Date"),
						results.getInt("Client_ID"),
						results.getString("Proj_Name"),
						results.getString("Phys_Address"),
						results.getInt("ERF_Num"),
						results.getInt("Proj_Num")
						);
			}
			System.out.printf("--------------------------------------------------------------------------------------------------------------------------------------%n");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Public method that reads from database the details of all the projects that are past due date 
	 * and displays them to the console in table format.
	 * 
	 * @param statement an interface that will be used to submit to the database an SQL statement
	 * @param results will hold the data retrieved from the database after executing the SQL query
	 * ("SELECT * FROM Outdated_Projects;") using the Statement object
	 * 
	 * 
	 */
	public static void readFromDatabase6(Statement statement, ResultSet results) {
		String display5 = "SELECT * FROM Outdated_Projects"; //String representation of an SQL query.
		try {
			results = statement.executeQuery(display5); //executeQuery: runs a SELECT statement and returns the results.
			
			//Display the Outdated_Projects table to the console.
			System.out.println("\n                                         OUTDATED PROJECTS DETAILS:");
			System.out.printf("----------------------------------------------------------------------------------------------------------------------------------%n");
			System.out.printf("                                   PROJECTS PAST DUE DATE INFROMATION FROM DATABASE                                               %n");
			System.out.printf("----------------------------------------------------------------------------------------------------------------------------------%n");
			System.out.printf("| %11s | %-13s | %9s | %-27s | %-30s | %7s | %11s |%n",
					"Employee_ID", "Past_Due_Date", "Client_ID", "Proj_Name",
					"Phys_Address", "ERF_Num", "Proj_Num");
			System.out.printf("----------------------------------------------------------------------------------------------------------------------------------%n");
			while (results.next()) { //Loop over the results, printing them all in a table format.
				System.out.printf( //Do this by using the printf function.
						"| %11d | %-13s | %9d | %-27s | %-30s | %7d | %11d |%n",
						results.getInt("Employee_ID"),
						results.getString("Past_Due_Date"),
						results.getInt("Client_ID"),
						results.getString("Proj_Name"),
						results.getString("Phys_Address"),
						results.getInt("ERF_Num"),
						results.getInt("Proj_Num")
						);
			}
			System.out.printf("----------------------------------------------------------------------------------------------------------------------------------%n");
		} catch (SQLException e) { // The only objective is to catch an SQLException.
			e.printStackTrace();
		}
	}
	
	/**
	 * Public method that declare an initialize all variables that will be 
	 * used to make the invoice generated for each client realistic.
	 * 
	 * @param totalCharged total amount charged
	 * @param totalPaid total amount paid
	 * @param physicalAddress3 physical address of the client
	 * @param dueDate due date of the project entered by the user
	 * @param telephoneNumber3 telephone number of the client
	 * @param emailAddress3 email address of the client
	 * @param con an interface that is concerned with all methods for contacting a database
	 * @param rowsAffected integer variable that stores the number rows affected be the SQL query
	 * @param pst16 prepared statement number 16
	 * @param pst17 prepared statement number 17
	 * @param pst18 prepared statement number 18
	 * @param contractor_ID ID number of the contractor
	 * @param client_ID ID number of the client
	 * @param insertQuery6 SQL query for the INSERT operation
	 * @param statement an interface that will be used to submit to the database an SQL statement
	 * @param results will hold the data retrieved from the database after executing the SQL query
	 * @throws SQLException a class that will handle errors that occur in the database.
	 * 
	 * 
	 */
	public static void clientInvoicefunction(double totalCharged, double totalPaid, String physicalAddress3,
			String dueDate, String telephoneNumber3, String emailAddress3, Connection con, int rowsAffected, 
			PreparedStatement pst16, PreparedStatement pst17, PreparedStatement pst18, int contractor_ID,
			int client_ID, String insertQuery6, Statement statement, ResultSet results) throws SQLException {
		//The DecimalFormat class will be used to convert the values entered by the user into correct currency figures.
		DecimalFormat df = new DecimalFormat("$0,000.00");
		
		if (totalCharged > totalPaid) { //This will calculate the total amount that the customer must still pay if the total paid is less than the total charged.
			
			//Billing details:
			String invoiceDate = "24/02/2022";
			String invoiceNumber = "I60000201228421";
			String paymentMethod = "Stop Order";
			String billingAccount = "BA1149983721";
			
			//Call the customerInvoice function.
			clientInvoice(totalCharged, totalPaid, dueDate, telephoneNumber3, emailAddress3, physicalAddress3,
					insertQuery6, df, invoiceDate, invoiceNumber, paymentMethod, billingAccount, contractor_ID,
					client_ID, pst16, con, rowsAffected);
			/*
			  CONTRACTOR AND CLIENT RELATIONSHIP
			  ----------------------------------
			  The client receives a project that is finished on schedule, within budget, and to their 
			  satisfaction, and the contractor receives timely payment and makes a profit along with
			  every other employee involved.
		  	
			  Below a function displaying a database table representation of the contractor and the 
			  client's relationship. The Employee_ID column represents the ID of the contractor that 
			  has worked out the budget of the project together with consistently updating the client 
			  on any changes that occur during the course of the project, the Client_ID and the 
			  Payment_Balance column. A negative credit balance will be the result of the Payment_Balance
			  column for this condition:
			 */	
			readFromDatabase(statement, results);
			
			//Below is a message that will be displayed on console explaining the results of the latest database table entry.
			System.out.println("\nThe latest entry in the database table shows that the client has paid less than the amount ");
			System.out.println("charged meaning that they still owe the above amount as stated in the client invoice....");
			
		} else if (totalCharged < totalPaid) { //This condition applies if the total amount charged is less than the total amount paid.
			double balance = totalPaid - totalCharged;
			String positiveCredit = "+" + df.format(balance); //Currency formated string representation of the positive credit.
			
			//Make use of a prepared statement since it is a precompiled SQL statement and does not require the user to hard code the values for the INSERT operation of the Works_With database table.
			pst17 = con.prepareStatement(insertQuery6);
			
			//Set parameter values:
			pst17.setInt(1, contractor_ID);
			pst17.setInt(2, client_ID);
			pst17.setString(3, positiveCredit);
			
			//Add a row:
			rowsAffected = pst17.executeUpdate();
			
			/*
			  CONTRACTOR AND CLIENT RELATIONSHIP
			  ----------------------------------
			  The client receives a project that is finished on schedule, within budget, and to their 
			  satisfaction, and the contractor receives timely payment and makes a profit along with
			  every other employee involved.
			
			  Below a function displaying a database table representation of the contractor and the 
			  client's relationship. The Employee_ID column represents the ID of the contractor that 
			  has worked out the budget of the project together with consistently updating the client 
			  on any changes that occurs during the course of the project, the Client_ID and the 
			  Payment_Balance column. A positive credit balance will be the result of the Payment_Balance
			  column for this condition:
			 */
			readFromDatabase(statement, results);
			
			//Below is a message that will be displayed on console explaining the results of the latest database table entry.
			System.out.println("\nThe latest entry in the database table shows that the client has paid more than the amount ");
			System.out.println("charged meaning that they are willing to invest the above amount in future projects....");
 			
		}else if (totalCharged == totalPaid) { //This condition applies if the total amount charged is equal to the total amount paid.
			double balance = 0;
			String availableAmount = " " + df.format(balance); //Currency formated string representation of the available amount($0,000.00).
			
			//Make use of a prepared statement since it is a precompiled SQL statement and does not require the user to hard code the values for the INSERT operation of the Works_With database table.
			pst18 = con.prepareStatement(insertQuery6);
			
			//Set parameter values:
			pst18.setInt(1, contractor_ID);
			pst18.setInt(2, client_ID);
			pst18.setString(3, availableAmount);
			
			//Add a row:
			rowsAffected = pst18.executeUpdate();
			
			/*
			  CONTRACTOR AND CLIENT RELATIONSHIP
			  ----------------------------------
			  The client receives a project that is finished on schedule, within budget, and to their 
			  satisfaction, and the contractor receives timely payment and makes a profit along with
			  every other employee involved.
			
			  Below a function displaying a database table representation of the contractor and the 
			  client's relationship. The Employee_ID column represents the ID of the contractor that 
			  has worked out the budget of the project together with consistently updating the client 
			  on any changes that occurs during the course of the project, the Client_ID and the 
			  Payment_Balance column. A $0,000.00 balance will be the result of the Payment_Balance
			  column for this condition:
			 */
			readFromDatabase(statement, results);
			
			//Below is a message that will be displayed on console explaining the results of the latest database table entry.
			System.out.println("\nThe latest entry in the database table shows that the client has paid the total amount");
			System.out.println("charged in full meaning that they don't owe anything nor do they plan on investing in future");
			System.out.println("projects....");
		}
	}
	
	/**
	 * Public method that calculates the amount that the customer must 
	 * still pay and prints the invoice to the output screen.
	 * 
	 * @param totalCharged total amount charged
	 * @param totalPaid total amount paid
	 * @param dueDate deadline of the project
	 * @param telephoneNumber3 telephone number of the customer
	 * @param emailAddress3 email address of the customer
	 * @param physicalAddress3 physical address of the customer
	 * @param df decimal format
	 * @param invoiceDate invoice date
	 * @param invoiceNumber invoice number
	 * @param paymentMethod payment method
	 * @param billingAccount billing account
	 * @throws SQLException A class that will handle errors that occur in the database.
	 * 
	 * 
	 */	
	public static void clientInvoice(double totalCharged, double totalPaid, String dueDate, String telephoneNumber3,
			String emailAddress3, String physicalAddress3, String insertQuery6, DecimalFormat df, String invoiceDate,
			String invoiceNumber, String paymentMethod, String billingAccount, int contractor_ID, int client_ID,
			PreparedStatement pst16, Connection con, int rowsAffected) throws SQLException {
		double balance = totalCharged - totalPaid; //Work out the amount due by subtracting the total amount paid from the total amount charged.
		
		//Display on screen the invoice of the customer.
		System.out.println("\nCLIENT INVOICE");
		System.out.println("--------------");
		System.out.println("                                        Email Address          " + emailAddress3);
		System.out.println("                                        Telephone Number       " + telephoneNumber3);
		System.out.println("                                        Physical Address       " + physicalAddress3);
		System.out.println("                                        Invoice Date           " + invoiceDate);	
		System.out.println("                                        Invoice Number         " + invoiceNumber);
		System.out.println("                                        Billing Account        " + billingAccount);
		System.out.println("                                        Payment Method         " + paymentMethod);
		System.out.println("                                        Due Date               " + dueDate);
		System.out.println("\n-----------------------------------------------------------------------");
		System.out.println("Date                Description                         Charge	       ");
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("02/02/2022          BALANCE BROUGHT FORWARD             " + df.format(totalCharged));
		System.out.println("17/02/2022          PAYMENT - THANK YOU                " + "-" + df.format(totalPaid));
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("                    AMOUNT DUE:                         " + df.format(balance));
		System.out.println("-----------------------------------------------------------------------");
		
		//Currency formated string representation of the amount owed by the client.
		String amountDue = "-" + df.format(balance);
		
		//Make use of a prepared statement since it is a precompiled SQL statement and does not require the user to hard code the values for the INSERT operation of the Works_With database table.
		pst16 = con.prepareStatement(insertQuery6);
		
		//Set parameter values:
		pst16.setInt(1, contractor_ID);
		pst16.setInt(2, client_ID);
		pst16.setString(3, amountDue);
		
		//Add a row:
		rowsAffected = pst16.executeUpdate();
	}
	
	/**
	 * Public method that reads from database the details of all the payment arrangements made
	 * between each client and contractor.
	 * 
	 * @param statement an interface that will be used to submit to the database an SQL statement
	 * @param results will hold the data retrieved from the database after executing the SQL query
	 * ("SELECT * FROM Works_With;") using the Statement object
	 * 
	 * 
	 */
	public static void readFromDatabase(Statement statment, ResultSet results) {
		String display = "SELECT * FROM Works_With;"; //String representation of an SQL query.
		try {
			results = statment.executeQuery(display); //executeQuery: runs a SELECT statement and returns the results.
			
			//Display the Works_With table to the console.
			System.out.println("\n    CONTRACTOR AND CLIENT AGREEMENT:");
			System.out.printf("----------------------------------------------%n");
			System.out.printf("   CONTACTOR AND CLIENT PAYMENT ARRANGEMENT   %n");
			System.out.printf("                 FROM DATABASE                %n");
			System.out.printf("----------------------------------------------%n");
			System.out.printf("| %11s | %9s | %-16s |%n",
					"Employee_ID", "Client_ID", "Payment_Balance");
			System.out.printf("----------------------------------------------%n");
			while (results.next()) { //Loop over the results, printing them all in a table format.
				System.out.printf( //Do this by using the printf function.
						"| %11d | %9d | %-16s |%n",
						results.getInt("Employee_ID"),
						results.getInt("Client_ID"),
						results.getString("Payment_Balance")
						);
			}
			System.out.printf("----------------------------------------------%n");
		} catch (SQLException e) { // The only objective is to catch an SQLException.
			e.printStackTrace();
		}
	}
	
	/**
	 * Public method that prints the updated projects to the console.
	 * 
	 * @param projectX updated project
	 * @param deadLine today's date
	 * 
	 * 
	 */	
	public static void writeToConsole(Project projectX, String deadLine) {
		//Append to the text file a list of recently updated project information to the UpdatedProjects.txt text file. 	
		System.out.print("\r\n                                    POISED STRUCTURAL ENGINEERING UPDATE\r\n");
		System.out.print("                                    ------------------------------------\r\n");
		System.out.print("A NEW PROJECT HAS BEEN UPDATED SUCCESSFULLY...!!!\r\n");
		System.out.print("Date And Time: " + deadLine + "\r\n"); //Provide the date and the time at which the project has been finalized.
		System.out.print("----------------------------------------------------------------------------------------------------------------\r\n");
		System.out.print("PROJECT UPDATED USING THE SELECTED PROJECT NUMBER:\r\n");
		System.out.print("----------------------------------------------------------------------------------------------------------------\r\n");
		System.out.print("PROJECT DETAILS:\r\n");
		System.out.print("----------------\r\n");
		System.out.print(projectX + "\r\n");
	}
	
	/**
	 * Public method that updates a project using the project number.
	 * 
	 * @param projectX updated project
	 * @param projNumber project number entered by the user
	 * @param projectName updated project name
	 * @param projectNumber updated project number
	 * @param buildingType updated building type
	 * @param physicalAddress updated physical address
	 * @param EFR_Number updated EFR number
	 * @param totalCharged updated total amount charged
	 * @param totalPaid updated total amount paid
	 * @param dueDate updated due date for the project
	 *  
	 * 
	 */	
	public static void updateToConsole(Project projectX, int projNumber, String projectName2, int projectNumber2, String buildingType2,
			String physicalAddress2, int ERF_Number2, double totalCharged2, double totalPaid2, String dueDate2, String currentDateTime) {
		
		if (projectX.getProjectNumber() == projNumber) { //If the project number inputed by the user initially is equivalent to the number used to search, proceed by executing the blocks of code below.
			projectX = new Project(projectName2, projectNumber2, buildingType2, physicalAddress2, ERF_Number2, totalCharged2, totalPaid2, dueDate2); //Create a new object of the Project class using the new details entered by the user.                      
			writeToConsole(projectX, currentDateTime); //Call the writeToFile function while passing the newly created projectX object as argument to write the new information to the CompletedProject2 text file.
		}	
	}	
	
	/**
	 * Public method that displays all the finalized details about 
	 * the project to the console.
	 * 
	 * @param deadLine today's date
	 * @param projectX project name using building type + surname of the customer
	 * @param projectName project name entered by the user
	 * @param p iterator object to iterate through the project details
	 * @param p1 iterator object to iterate though the manager's details
	 * @param p2 iterator object to iterate through the architect's details
	 * @param p3 iterator object to iterate through the structural engineer's details
	 * @param p4 iterator object to iterate through the constructor's details
	 * @param p5 iterator object to iterate through the client's details
	 *  
	 *  
	 */
	public static void finalisedProjects(String deadLine, Project projectX, String projectName, Project p, Person p1,
			Person p2, Person p3, Person p4, Person p5) {
		
		//Create ArrayLists for the finalized projects, architects, structural engineers, constructors, and clients.
		ArrayList<Project> finalProjects = new ArrayList<Project>();
		ArrayList<Person> finalManagers = new ArrayList<Person>();
		ArrayList<Person> finalArchitects = new ArrayList<Person>();
		ArrayList<Person> finalStrucEngs = new ArrayList<Person>();
		ArrayList<Person> finalContractors = new ArrayList<Person>();
		ArrayList<Person> finalClients = new ArrayList<Person>();
		
		//Add objects to each ArrayList created.
		finalProjects.add(p);
		finalManagers.add(p1);
		finalArchitects.add(p2);
		finalStrucEngs.add(p3);
		finalContractors.add(p4);
		finalClients.add(p5);	
		
		//Display the following information to the console.
		System.out.print("\r\n                                  POISED STRUCTURAL ENGINEERING FINALIZATION\r\n");
		System.out.print("                                  ------------------------------------------\r\n");
		System.out.print("\r\nA NEW PROJECT IS BEING FINALISED SUCCESSFULLY...!!!\r\n");
		System.out.print("Date and time: " + deadLine + "\r\n"); //Provide the date and the time at which the project has been finalized.
		System.out.print("----------------------------------------------------------------------------------------------------------------\r\n");
		System.out.print("PROJECT MANAGEMENT SYSTEM:\r\n");
		System.out.print("----------------------------------------------------------------------------------------------------------------\r\n");
		System.out.print("PROJECT DETAILS:\r\n"); //Display to the console the final information captured about the completed project details.
		System.out.print("----------------\r\n");
		if (projectName.equalsIgnoreCase("") == false) { //Display to the console the project name that is not the empty("") string which signifies that a project name is not provided.
			for (Project f1 : finalProjects) {
				System.out.print(f1 + "\r\n");
			}
		}else{ //Name the project using the building type + surname of the client if the project name is not provided when the information is captured.
			System.out.print(projectX + "\r\n");
		}
		
		//Display to the console the final information captured about the manager's details.
		System.out.print("----------------------------------------------------------------------------------------------------------------\r\n");
		System.out.print("MANAGER DETAILS:\r\n");
		System.out.print("----------------\r\n");
		for (Person f2 : finalManagers) {
			System.out.print(f2 + "\r\n");
		}
	
		//Display to the console the final information captured about the architect's details.
		System.out.print("----------------------------------------------------------------------------------------------------------------\r\n");
		System.out.print("ARCHITECT DETAILS:\r\n");
		System.out.print("------------------\r\n");
		for (Person f3 : finalArchitects) {
			System.out.print(f3 + "\r\n");
		}
		
		//Display to the console the final information captured about the architect's details.
		System.out.print("----------------------------------------------------------------------------------------------------------------\r\n");
		System.out.print("STRUCTURAL ENGINEER DETAILS:\r\n");
		System.out.print("----------------------------\r\n");
		for (Person f4 : finalStrucEngs) {
			System.out.print(f4 + "\r\n");
		}
		
		//Display to the console the final information captured about the contractor's details.
		System.out.print("----------------------------------------------------------------------------------------------------------------\r\n");
		System.out.print("CONTRACTOR DETAILS:\r\n");
		System.out.print("-------------------\r\n");
		for (Person f5 : finalContractors) {
			System.out.print(f5 + "\r\n");
		}
		
		//Display to the console the final information captured about the client's details.
		System.out.print("----------------------------------------------------------------------------------------------------------------\r\n");
		System.out.print("CLIENT DETAILS:\r\n");
		System.out.print("---------------\r\n");
		for (Person f6 : finalClients) {
			System.out.print(f6 + "\r\n");
		}
		System.out.print("----------------------------------------------------------------------------------------------------------------\r\n");
		System.out.print("Record Finalised Successfully...!!!\r\n");
		System.out.print("----------------------------------------------------------------------------------------------------------------\r\n");
	}
	
	/**
	 * Public method that displays all the projects that are past due date 
	 * to the console.
	 * 
	 * @param deadLine today's date
	 * @param projectX project name using building type + surname of the customer
	 * @param projectName project name entered by the user
	 * @param p iterator object to iterate through the project details
	 * @param p1 iterator object to iterate though the manager's details
	 * @param p2 iterator object to iterate through the architect's details
	 * @param p3 iterator object to iterate through the structural engineer's details
	 * @param p4 iterator object to iterate through the constructor's details
	 * @param p5 iterator object to iterate through the client's details
	 *  
	 *  
	 */
	public static void outdatedProjects(String deadLine, Project projectX, String projectName, Project p, Person p1,
			Person p2, Person p3, Person p4, Person p5) {
		
		//Create ArrayLists for the finalized projects, architects, constructors, and customers.	
		ArrayList<Project> pastProjects = new ArrayList<Project>();
		ArrayList<Person> pastManagers = new ArrayList<Person>(); 
		ArrayList<Person> pastArchitects = new ArrayList<Person>();
		ArrayList<Person> pastStrucEngs = new ArrayList<Person>(); 
		ArrayList<Person> pastContractors = new ArrayList<Person>();
		ArrayList<Person> pastClients = new ArrayList<Person>();
		
		//Add objects to each ArrayList created.
		pastProjects.add(p);
		pastManagers.add(p1);
		pastArchitects.add(p2);
		pastStrucEngs.add(p3);
		pastContractors.add(p4);
		pastClients.add(p5);	
		
		//Display the following information to the console.
		System.out.print("\r\n                                  POISED STRUCTURAL ENGINEERING\r\n");
		System.out.print("                                  -----------------------------\r\n");
		System.out.print("\r\nA NEW PROJECT PAST DUE DATE IS NOW DISPLAYED SUCCESSFULLY...!!!\r\n");
		System.out.print("Date and time: " + deadLine + "\r\n"); //Provide the date and the time at which the project has been finalized.
		System.out.print("----------------------------------------------------------------------------------------------------------------\r\n");
		System.out.print("PROJECT MANAGEMENT SYSTEM:\r\n");
		System.out.print("----------------------------------------------------------------------------------------------------------------\r\n");
		System.out.print("PROJECT DETAILS:\r\n"); //Display to the console the final information captured about the completed project details.
		System.out.print("----------------\r\n");
		if (projectName.equalsIgnoreCase("") == false) { //Display to the console the project name that is not the empty("") string which signifies that a project name is not provided.
			for (Project f1 : pastProjects) {
				System.out.print(f1 + "\r\n");
			}
		}else{ //Name the project using the building type + surname of the client if the project name is not provided when the information is captured.
			System.out.print(projectX + "\r\n");
		}
		
		//Display to the console the final information captured about the manager's details.
		System.out.print("----------------------------------------------------------------------------------------------------------------\r\n");
		System.out.print("MANAGER DETAILS:\r\n");
		System.out.print("----------------\r\n");
		for (Person f2 : pastManagers) {
			System.out.print(f2 + "\r\n");
		}
	
		//Display to the console the final information captured about the architect's details.
		System.out.print("----------------------------------------------------------------------------------------------------------------\r\n");
		System.out.print("ARCHITECT DETAILS:\r\n");
		System.out.print("------------------\r\n");
		for (Person f3 : pastArchitects) {
			System.out.print(f3 + "\r\n");
		}

		//Display to the console the final information captured about the structural engineer's details.
		System.out.print("----------------------------------------------------------------------------------------------------------------\r\n");
		System.out.print("STRUCTURAL ENGINEER DETAILS:\r\n");
		System.out.print("----------------------------\r\n");
		for (Person f4 : pastStrucEngs) {
			System.out.print(f4 + "\r\n");
		}
				
		//Display to the console the final information captured about the contractor's details.
		System.out.print("----------------------------------------------------------------------------------------------------------------\r\n");
		System.out.print("CONTRACTOR DETAILS:\r\n");
		System.out.print("-------------------\r\n");
		for (Person f5 : pastContractors) {
			System.out.print(f5 + "\r\n");
		}
		
		//Display to the console the final information captured about the client's details.
		System.out.print("----------------------------------------------------------------------------------------------------------------\r\n");
		System.out.print("CLIENT DETAILS:\r\n");
		System.out.print("---------------\r\n");
		for (Person f6 : pastClients) {
			System.out.print(f6 + "\r\n");
		}
		System.out.print("----------------------------------------------------------------------------------------------------------------\r\n");
		System.out.print("Record Past Due Date Displayed Successfully...!!!\r\n");
		System.out.print("----------------------------------------------------------------------------------------------------------------\r\n");
	}
}