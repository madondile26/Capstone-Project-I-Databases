package task8Package;

/**
 * This is a Person class containing all the relevant attributes, constructor, and
 * methods that make a suitable class.
 * 
 * @author Xhasa Madondile
 * @version 1.00, Nov 27-2022
 * 
 *
 */
public class Person {
	//Attributes
		private int personID;
		private String name;
		private String surname;
		private String telephoneNumber;
		private String emailAddress;
		private String physicalAddress;
		
		/**
		 * Constructor method that reference each attribute of the Person class.
		 * 
		 * @param personID ID of the person that will be used as a primary key in the database table
		 * @param name name of the person
		 * @param surname surname of the person
		 * @param telephoneNumber telephone number of the person
		 * @param emailAddress email address of the person
		 * @param physicalAdress physical address of the person
		 * 
		 * 
		 */
		public Person(int personID, String name, String surname, String telephoneNumber, String emailAddress, String physicalAdress) {
			this.personID = personID;
			this.name = name;
			this.surname = surname;
			this.telephoneNumber = telephoneNumber;
			this.emailAddress = emailAddress;
			this.physicalAddress = physicalAdress;
		}
		
		//The methods below are used to obtain the new attributes of each Person object.
		
		/**
		 * Set the personID attribute so that it can be modified inside the Person
		 * class.
		 * 
		 * @param personID ID of the person that will be used as a primary key in the database table 
		 */
		public void setPersonID( int personID) {
			this.personID = personID;
		}
		
		/**
		 * Set the name attribute so that it can be modified inside the Person
		 * class.
		 * 
		 * @param name name of the person
		 */
		public void setName(String name) {
			this.name = name;
		}
		
		/**
		 * Set the surname attribute so that it can be modified inside the Person
		 * class.
		 * 
		 * @param surname surname of the person
		 */
		public void setSurname(String surname) {
			this.surname = surname;
		}
		
		/**
		 * Set the telephoneNumber attribute so that it can be modified inside the Person
		 * class.
		 * 
		 * @param telephoneNumber telephone number of the person
		 */
		public void setTelephoneNumber(String telephoneNumber) {
			this.telephoneNumber = telephoneNumber;	
		}
		
		/**
		 * Set the emailAddress attribute so that it can be modified inside the Person
		 * class.
		 * 
		 * @param emailAddress email address of the person
		 */
		public void setEmailAddress(String emailAddress) {
			this.emailAddress = emailAddress;
		}
		
		/**
		 * Set the physicalAddress attribute so that it can be modified inside the Person
		 * class.
		 * 
		 * @param physicalAddress physical address of the person
		 */
		public void setPhysicalAddress(String physicalAddress) {
			this.physicalAddress = physicalAddress;
		}
		
		/**
		 * Get the personID attribute so the it can be accessible outside the Person
		 * class.
		 * 
		 * @return returning the String value of name
		 */
		public int getPersonID() {
			return personID;
		}
		
		/**
		 * Get the name attribute so the it can be accessible outside the Person
		 * class.
		 * 
		 * @return returning the String value of name
		 */
		public String getName() {
			return name;
		}
	
		/**
		 * Get the surname attribute so the it can be accessible outside the Person
		 * class.
		 * 
		 * @return returning the String value of surname
		 */
		public String getSurname() {
			return surname;
		}

		/**
		 * Get the telephoneNumber attribute so the it can be accessible outside the Person
		 * class.
		 * 
		 * @return returning the String value of telephoneNumber
		 */
		public String getTelephoneNumber() {
			return telephoneNumber;
		}
		
		/**
		 * Get the emailAddress attribute so the it can be accessible outside the Person
		 * class.
		 * 
		 * @return returning the String value of emailAddress
		 */
		public String getEmailAddress() {
			return emailAddress;
		}
		
		/**
		 * Get the physicalAddress attribute so the it can be accessible outside the Person
		 * class.
		 * 
		 * @return returning the String value of physicalAddress
		 */
		public String getPhysicalAddress() {
			return physicalAddress;
		}

		/**
		 * Each class is displayed together with its attributes using this toString method.
		 */
		public String toString() {
			String output = "ID Number: " + personID;
			output += "\nName: " + name;
			output += "\nSurname: " + surname;
			output += "\nTelephone Number: " + telephoneNumber;
			output += "\nEmail Address: " + emailAddress;
			output += "\nPhysical Address: " + physicalAddress;
			
			return output;
		}
}