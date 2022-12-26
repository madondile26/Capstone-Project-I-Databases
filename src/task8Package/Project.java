package task8Package;
import java.text.DecimalFormat;

/**
 * This is a Project class containing all the relevant attributes, constructor, and
 * methods that make a suitable class.
 * 
 * @author Xhasa Madondile
 * @version 1.00, Nov 27-2022
 * 
 * 
 */
public class Project {
	private String projectName;
	private int projectNumber;
	private String typeOfBuilding;
	private String physicalAddress;
	private int ERF_Number;
	private double totalCharged;
	private double totalPaid;
	private String projectDueDate;
	
	/**
	 * Constructor method that reference each of the attributes of the Project class.
	 * 
	 * @param projectName name of the project
	 * @param projectNumber number of the project
	 * @param typeOfBuildingString type of building being designed
	 * @param physicalAddress physical address of the building
	 * @param EFR_Number EFR number
	 * @param totalCharged total amount that is being charged
	 * @param totalPaid total amount that is paid
	 * @param projectDueDate due date of the project
	 * 
	 * 
	 */
	public Project(String projectName,int projectNumber, String typeOfBuildingString, String physicalAddress,
			int EFR_Number, double totalCharged, double totalPaid, String projectDueDate) {
		this.projectNumber = projectNumber;
		this.projectName = projectName;
		this.typeOfBuilding = typeOfBuildingString;
		this.physicalAddress = physicalAddress;
		this.ERF_Number = EFR_Number;
		this.totalCharged = totalCharged;
		this.totalPaid = totalPaid;
		this.projectDueDate = projectDueDate;
	}
	
	//The methods below are used to obtain the new attributes of each Project object.

	/**
	 * Set the projectName attribute so that it can be modified inside the Project 
	 * class.
	 * 
	 * @param projectName name of the project
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	/**
	 * Set the projectNumber attribute so that it can be modified inside the Project 
	 * class.
	 * 
	 * @param projectNumber number of the project
	 */
	public void setProjectNumber(int projectNumber) {
		this.projectNumber = projectNumber;
	}
	
	/**
	 * Set the typeOfBuilding attribute so that it can be modified inside the Project 
	 * class.
	 * 
	 * @param typeOfBuilding type of building being designed
	 */
	public void setTypeOfBuilding(String typeOfBuilding) {
		this.typeOfBuilding = typeOfBuilding;
	}
	
	/**
	 * Set the physicalAddress attribute so that it can be modified inside the Project 
	 * class.
	 * 
	 * @param physicalAddess physical address of the project
	 */
	public void setPhysicalAddress(String physicalAddess) {
		this.physicalAddress = physicalAddess;
	}
	
	/**
	 * Set the EFR_Number attribute so that it can be modified inside the Project 
	 * class.
	 * 
	 * @param EFR_Number EFR number of the project
	 */
	public void setEFR_Number(int EFR_Number) {
		this.ERF_Number = EFR_Number;
	}
	
	/**
	 * Set the totalCharged attribute so that it can be modified inside the Project 
	 * class.
	 * 
	 * @param totalCharged total amount being charged
	 * 
	 * 
	 */
	public void setTotalCharged(double totalCharged) {
		this.totalCharged = totalCharged;
	}
	
	/**
	 * Set the totalPaid attribute so that it can be modified inside the Project 
	 * class.
	 * 
	 * @param totalPaid total amount paid
	 */
	public void setTotalpaid(double totalPaid) {
		this.totalPaid = totalPaid;
	}
	
	/**
	 * Set the projectDeadline attribute so that it can be modified inside the Project 
	 * class.
	 * 
	 * @param projectDeadline deadline of the project.
	 */
	public void setProjectDeadline(String projectDeadline) {
		this.projectDueDate = projectDeadline;
	}
	
	/**
	 * Get the projectName attribute so that it can be accessible outside the Project
	 * class.
	 * 
	 * @return returning the String value of projectName
	 */
	public String getProjectName() {
		return projectName;
	}
	
	/**
	 * Get the projectNumber attribute so that it can be accessible outside the Project
	 * class.
	 * 
	 * @return returning the integer value of projectNumber
	 */
	
	public int getProjectNumber() {
		return projectNumber;
	}
	
	/**
	 * Get the typeOfBuilding attribute so that it can be accessible outside the Project
	 * class.
	 * 
	 * @return returning the String value of typeOfBuilding
	 */
	public String getTypeOfBuilding() {
		return typeOfBuilding;	
	}
	
	/**
	 * Get the physicalAddress attribute so that it can be accessible outside the Project
	 * class.
	 * 
	 * @return returning the String value of physicalAddress
	 */
	public String getPhysicalAddress() {
		return physicalAddress;
	}
	
	/**
	 * Get the EFR_Number attribute so that it can be accessible outside the Project
	 * class.
	 * 
	 * @return returning the integer value of EFR_Number
	 */
	public int getEFR_Number() {
		return ERF_Number;
	}
	
	/**
	 * Get the totalCharged so that it can be accessible outside the Project
	 * class.
	 * 
	 * @return returning the double value of totalCharged
	 */
	public double getTotalCharged() {
		return totalCharged;
	}	
	
	/**
	 * Get the totalPaid attribute so that it can be accessible outside the Project
	 * class.
	 * 
	 * @return returning the double value of totalPaid
	 */
	public double getTotalPaid() {
		return totalPaid;
	}
	
	/**
	 * Get the projectDeadline attribute so that it can be accessible outside the Project
	 * class.
	 * 
	 * @return returning the String value of projectDeadline
	 */
	public String getProjectDeadline() {
		return projectDueDate;
	}	
	
	//The DecimalFormat class will be used to convert the totalCharged and the totalPaid attributes to correct currency figures.
	DecimalFormat df = new DecimalFormat("$0,000.00");
	
	/**
	 * Each class is displayed together with its attributes using this toString method.
	 */
	public String toString() {
		String output = "Project Name: " + projectName;
		output += "\nProject Number: " + projectNumber;
		output += "\nBuilding Type: " + typeOfBuilding;
		output += "\nPhysical Address: " + physicalAddress;
		output += "\nEFR Number: " + ERF_Number;
		output += "\nTotal Fee Charged: " + df.format(totalCharged) ;
		output += "\nTotal Amount Paid: " + df.format(totalPaid);
		output += "\nDue Date Of The Project: " + projectDueDate;
		
		return output;
	}
}