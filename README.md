# CompletedProject.java

This Java program is part of the "Capstone Project I: Databases" and is designed to manage and finalize project records for a structural engineering firm named "Poised."

## Features

- **Project Completion:** Marks projects as completed by recording the completion date and updating the project status.

- **Finalization Process:** Generates invoices for clients with outstanding balances and ensures all project details are up-to-date before finalization.

- **Data Persistence:** Reads project data from a text file at startup and saves any changes back to the file upon exit, ensuring data consistency.

- **User Interaction:** Provides a command-line interface for users to select and finalize projects, enhancing usability.

## How to Use

1. **Setup:** Ensure that the project data file (`projects.txt`) is located in the appropriate directory.

2. **Running the Program:** Execute the `CompletedProject` class. The program will load existing project data and prompt you to select a project for completion.

3. **Finalizing a Project:** Follow the on-screen instructions to mark a project as completed. If there is an outstanding balance, the program will generate an invoice for the client.

4. **Saving Changes:** Upon exiting, the program will automatically save any updates to the `projects.txt` file.

## Dependencies

- Java Development Kit (JDK) 8 or higher

## Notes

- This program is intended for educational purposes as part of a capstone project on database management.

- Ensure that the `projects.txt` file is properly formatted to avoid any runtime errors.

For more information on database considerations in Java projects, you might find the following resource helpful:

- [Capstone: Database Considerations - CodingNomads](https://codingnomads.com/java-301-capstone-database-considerations)

*This README provides an overview of the `CompletedProject.java` program, its features, usage instructions, and dependencies.* 
