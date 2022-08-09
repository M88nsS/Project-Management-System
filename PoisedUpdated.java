// This is a project management system
// for a small structural engineering firm called "Poised"
// It is created to keep track of new and existing projects as they progress
// All data is also being stored and managed in a database called PoisePMS

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import static java.lang.System.*;

// this is the management system's class that will capture, manipulate and retrieve data about projects
// and people related to the project

public class PoisedUpdated {

    public static void main(String[] args) {

        // try with recourses to ensure automatic closing of connection

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/PoisePMS?useSSL=false",
                "otheruser",
                "swordfish"); Statement statement = connection.createStatement()) {

            // Connect to the PoisePMS database, via the jdbc:mysql
            // Use username "otheruser", password "swordfish"

            // Set up Connection and objects

            // Declare a list and fill it with existing Projects from the database

            List<Project> projects = createObjectList(statement);

            // A list for new projects added in this array

            List<Project> newProjects = new ArrayList<>();

            // Booleans to check if there have been any changes to projects

            boolean projectUpdates = false;
            boolean newProject = false;

            // Welcome menu to add, edit, or view projects

            out.println("***Welcome to Poised Project Manager System***");
            Scanner input = new Scanner(in);
            boolean optionMenu1 = true;
            while (optionMenu1) {
                try {
                    out.println("""
                            Select :
                            1 - add a new project
                            2 - edit a project
                            3 - search for a project
                            4 - view all projects
                            5 - view projects that is not yet finalized
                            6 - view projects that have exceeded the deadline
                            7 - exit the system
                            """);
                    int userChoice = input.nextInt();

                    // Add a project

                    if (userChoice == 1) {
                        newProject = true;

                        // Project input from user
                        // if user chooses to generate the project name
                        // the program will use the building type + customer surname

                        int projectNumber = projects.size() + 1;
                        out.println("Project Name : ");
                        out.println("""
                                Select :
                                1 - enter a project name
                                2 - generate a project name
                                """);

                        String projectName = "";
                        int projectUserChoice = isInt();
                        if (projectUserChoice == 1) {
                            out.println("Enter a project name");
                            projectName = input.next();
                            projectName += input.nextLine();
                        } else if (projectUserChoice == 2) {
                            projectName = "";
                        }

                        out.println("Building type - e.g. Apartment, House, Villa: ");
                        String buildingType = input.next();

                        out.println("Project Address : ");
                        String address;
                        address = input.next();
                        address += input.nextLine();

                        out.println("ERF number : ");
                        int erfNumber = isInt();

                        out.println("Project fee : ");
                        int projectFee = isInt();

                        out.println("Amount paid : ");
                        int amountPaid = isInt();

                        out.println("Deadline - e.g. 2021-02-23");
                        String deadline = input.next();
                        deadline += input.nextLine();
                        String finalized = "No";

                        // Engineer input

                        int engineerId = projects.size() + 1;
                        out.println("Engineer name : ");
                        String engineerName;
                        engineerName = input.next();
                        engineerName += input.nextLine();

                        out.println("Engineer surname : ");
                        String engineerSurname;
                        engineerSurname = input.next();
                        engineerSurname += input.nextLine();

                        out.println("Engineer contact number : ");
                        String engineerNumber = input.next();

                        out.println("Engineer email : ");
                        String engineerEmail = input.next();

                        out.println("Engineer address : ");
                        String engineerAddress;
                        engineerAddress = input.next();
                        engineerAddress += input.nextLine();

                        // Architect input

                        int architectId = projects.size() + 1;
                        out.println("Architect name : ");
                        String architectName;
                        architectName = input.next();
                        architectName += input.nextLine();

                        out.println("architect surname : ");
                        String architectSurname;
                        architectSurname = input.next();
                        architectSurname += input.nextLine();

                        out.println("Architect contact number : ");
                        String architectNumber = input.next();

                        out.println("Architect email : ");
                        String architectEmail = input.next();

                        out.println("Architect address : ");
                        String architectAddress;
                        architectAddress = input.next();
                        architectAddress += input.nextLine();

                        // Manager input

                        int managerId = projects.size() + 1;
                        out.println("Manager name : ");
                        String managerName;
                        managerName = input.next();
                        managerName += input.nextLine();

                        out.println("Manager surname : ");
                        String managerSurname;
                        managerSurname = input.next();
                        managerSurname += input.nextLine();

                        out.println("Manager contact number : ");
                        String managerNumber = input.next();

                        out.println("Manager email : ");
                        String managerEmail = input.next();

                        out.println("Manager address : ");
                        String managerAddress;
                        managerAddress = input.next();
                        managerAddress += input.nextLine();

                        // Customer input

                        int customerId = projects.size() + 1;
                        out.println("Customer name : ");
                        String customerName;
                        customerName = input.next();
                        customerName += input.nextLine();

                        out.println("Customer surname : ");
                        String customerSurname;
                        customerSurname = input.next();
                        customerSurname += input.nextLine();

                        out.println("Customer contact number : ");
                        String customerNumber = input.next();

                        out.println("Customer email : ");
                        String customerEmail = input.next();

                        out.println("Customer address : ");
                        String customerAddress;
                        customerAddress = input.next();
                        customerAddress += input.nextLine();

                        // Autogenerate project name if user didn't enter it manually

                        if (projectName.equals("")) {
                            projectName += buildingType + customerSurname;
                        }

                        // Initialize Person and Project objects using user input data

                        Person engineer = new Person(engineerId, engineerName, engineerSurname, engineerNumber, engineerEmail, engineerAddress);
                        Person architect = new Person(architectId, architectName, architectSurname, architectNumber, architectEmail, architectAddress);
                        Person manager = new Person(managerId, managerName, managerSurname, managerNumber, managerEmail, managerAddress);
                        Person customer = new Person(customerId, customerName, customerSurname, customerNumber, customerEmail, customerAddress);
                        Project project = new Project(projectNumber, projectName, buildingType, address, erfNumber, projectFee,
                                amountPaid, deadline, finalized, engineer, architect, manager, customer);

                        // Add new project to list and display it to user

                        projects.add(project);
                        newProjects.add(project);
                        out.println("\n New Project \nProject details :");
                        out.println("\n" + project);
                    }

                    // Edit a project

                    else if (userChoice == 2) {

                        // Look for projects if not empty list

                        if (projects.isEmpty()) {
                            out.println("\nThere are no projects to edit\n");

                            // Display available projects

                        } else {
                            projectUpdates = true;
                            out.println("\nCurrent projects :\n");
                            for (Project project : projects) {
                                out.println("Project number : " + project.projectNumber +
                                        "\nProject Name : " + project.projectName + "\n");
                            }

                            // Ask user to choose a project to edit

                            boolean editProject = true;
                            while (editProject) {
                                try {
                                    out.println("Enter the project number you would like to edit " +
                                            "or 'exit' to go back to the main menu : ");
                                    String projectEditSelect = input.next();

                                    // If the user entered a correct project number, the project will be selected

                                    if (Character.isDigit(projectEditSelect.charAt(0))) {
                                        int selectedProject = Integer.parseInt(projectEditSelect) - 1;
                                        Project projectToEdit = projects.get(selectedProject);
                                        out.println("\n" + projectToEdit);
                                        boolean editing = true;

                                        // Ask the user to choose what project details to edit

                                        while (editing) {
                                            try {
                                                out.println("""
                                                        Select :
                                                        1 - change due date
                                                        2 - update the fee paid to date
                                                        3 - finalize the project
                                                        4 - view project details
                                                        5 - update the engineer/manager/customer/architect details
                                                        6 - go back
                                                        """);

                                                int edit = input.nextInt();

                                                // Change deadline date

                                                if (edit == 1) {
                                                    out.println("Please enter a new deadline - e.g. 2021-01-01 ");
                                                    String newDeadline = input.next();
                                                    newDeadline += input.nextLine();
                                                    projectToEdit.deadline = newDeadline;
                                                }

                                                // Change fee paid so far by customer

                                                else if (edit == 2) {
                                                    out.println("Please enter the new amount : ");
                                                    projectToEdit.amountPaid = isInt();
                                                }

                                                // Finalize project if it is not finalized yet

                                                else if (edit == 3) {
                                                    if (projectToEdit.finalized.equalsIgnoreCase("no")) {

                                                        // Generate a date for the invoice

                                                        Date todayDate = Calendar.getInstance().getTime();
                                                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                                        String strTodayDate = dateFormat.format(todayDate);

                                                        // Generate invoice if project is not paid in full

                                                        if (projectToEdit.amountPaid < projectToEdit.projectFee) {
                                                            out.println("Invoice generated");
                                                            String customerInvoice = strTodayDate;
                                                            customerInvoice += "\nPoised Invoice";
                                                            customerInvoice += "\nTo :";
                                                            customerInvoice += "\n" + projectToEdit.customer.name;
                                                            customerInvoice += "\n" + projectToEdit.customer.contactNumber;
                                                            customerInvoice += "\n" + projectToEdit.customer.email;
                                                            customerInvoice += "\n" + projectToEdit.customer.address;
                                                            customerInvoice += "\nBalance to be paid :\n";
                                                            customerInvoice += "R " + (projectToEdit.projectFee - projectToEdit.amountPaid) + ".00";

                                                            // Invoice file
                                                            try {
                                                                Files.writeString(Path.of("./" + projectToEdit.projectName + "_invoice.txt"), customerInvoice, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                                                            } catch (Exception e) {
                                                                out.println();
                                                            }
                                                        }

                                                        // Completed projects file

                                                        projectToEdit.finalized = "Yes";
                                                        out.println("Generating project completion document");
                                                        String completedProject = "Completed Project: ";
                                                        completedProject += "\n\n" + projectToEdit;
                                                        completedProject += "\nDate completed: " + strTodayDate;
                                                        try {
                                                            Files.writeString(Path.of("./" + projectToEdit.projectName + "_completed.txt"), completedProject, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                                                        } catch (Exception e) {
                                                            out.println();
                                                        }

                                                        // If project is already finalized

                                                    } else {
                                                        out.println("This project has already been finalized.");
                                                    }
                                                }

                                                // Display edited project

                                                else if (edit == 4) {
                                                    out.println("\n" + projectToEdit);
                                                }

                                                // Edit engineer/manager/architect/customer details

                                                else if (edit == 5) {
                                                    Person personToEdit = null;
                                                    boolean personEdit = true;

                                                    // Choose which one to edit.
                                                    while (personEdit) {
                                                        try {
                                                            out.println("""
                                                                    Select :
                                                                    1 - edit the engineer
                                                                    2 - edit the manager
                                                                    3 - edit the architect
                                                                    4 - edit the customer
                                                                    """);
                                                            int personSelector = input.nextInt();
                                                            if (personSelector == 1) {
                                                                personToEdit = projectToEdit.engineer;
                                                                personEdit = false;
                                                            } else if (personSelector == 2) {
                                                                personToEdit = projectToEdit.manager;
                                                                personEdit = false;
                                                            } else if (personSelector == 3) {
                                                                personToEdit = projectToEdit.architect;
                                                                personEdit = false;
                                                            } else if (personSelector == 4) {
                                                                personToEdit = projectToEdit.customer;
                                                                personEdit = false;
                                                            } else {
                                                                out.println("Only 1, 2, 3 and 4 are valid entries.");
                                                            }
                                                        } catch (InputMismatchException e) {
                                                            out.println("You did not enter a valid NUMBER.");
                                                            input.next();
                                                        }
                                                    }

                                                    // Choose which attributes of the contractor/architect/customer to edit

                                                    boolean whoToEdit = true;
                                                    while (whoToEdit) {
                                                        try {
                                                            out.println("""
                                                                    Select :
                                                                    1 - edit name
                                                                    2 - edit contact number
                                                                    3 - edit email address
                                                                    4 - edit physical address
                                                                    5 - edit complete - display edit
                                                                    """);
                                                            int attributeToEdit = input.nextInt();

                                                            // Change name of person

                                                            if (attributeToEdit == 1) {
                                                                out.println("Please enter a new name : ");
                                                                String newName;
                                                                newName = input.next();
                                                                newName += input.nextLine();
                                                                personToEdit.name = newName;
                                                            }

                                                            // Change contact number

                                                            else if (attributeToEdit == 2) {
                                                                out.println("Please enter a new contact number : ");
                                                                personToEdit.contactNumber = input.next();
                                                            }

                                                            // Change email address

                                                            else if (attributeToEdit == 3) {
                                                                out.println("Please enter a new email address : ");
                                                                personToEdit.email = input.next();
                                                            }

                                                            // Change physical address

                                                            else if (attributeToEdit == 4) {
                                                                out.println("Please enter a new physical address : ");
                                                                String newAddress;
                                                                newAddress = input.next();
                                                                newAddress += input.nextLine();
                                                                personToEdit.address = newAddress;
                                                            }

                                                            // Display edit person

                                                            else if (attributeToEdit == 5) {
                                                                whoToEdit = false;
                                                                out.println("\nUpdated details :\n" + personToEdit);
                                                            }

                                                            // Catch invalid entry errors/exceptions

                                                            else if (attributeToEdit == 0 || attributeToEdit > 5) {
                                                                out.println("Only 1, 2, 3, 4, and 5 are valid entries.");
                                                            }

                                                            // Catch input exceptions

                                                        } catch (Exception e) {
                                                            out.println("Only 1, 2, 3, 4, and 5 are valid entries.");
                                                            input.next();
                                                        }
                                                    }
                                                }

                                                // Exit project editing loop

                                                else if (edit == 6) {
                                                    editing = false;
                                                }

                                                // Catch invalid int inputs

                                                else if (edit == 0 || edit > 6) {
                                                    out.println("Only 1, 2, 3, 4, 5, 6 are valid entries.");
                                                }

                                                // Catch input exceptions

                                            } catch (Exception e) {
                                                out.println("Only 1, 2, 3, 4, 5, 6 are valid entries.");
                                                input.next();
                                            }
                                        }

                                        // Go back to previous menu

                                    } else if (projectEditSelect.equals("exit")) {
                                        editProject = false;

                                        // Catch invalid letter entries

                                    } else {
                                        out.println("Only 'exit' is a valid entry to exit.");
                                    }

                                    // Catch invalid index entries

                                } catch (IndexOutOfBoundsException e) {
                                    out.println("Please enter a valid project number.");
                                }
                            }
                        }
                    }

                    // Search for a project if any projects exist

                    else if (userChoice == 3) {

                        if (projects.isEmpty()) {
                            out.println("\nThere are no projects to search for\n");

                            // Show limited details about all projects

                        } else {
                            out.println("\nCurrent projects :\n");
                            for (Project project : projects) {
                                out.println("Project number : " + project.projectNumber +
                                        "\nProject Name : " + project.projectName);
                            }

                            // Choose to search by name or number

                            out.println("""
                                    Select :
                                    1 - search by number
                                    2 - search by name
                                    """);
                            int searchType = isInt();

                            // If user choose to search by project number

                            if (searchType == 1) {
                                out.println("Enter the project number : ");
                                int projectSelectorInt = isInt();

                                // Check if project number match existing projects to display

                                while (projectSelectorInt == 0 || projectSelectorInt > projects.size()) {
                                    out.println("No match found - Please enter a valid project number.");
                                    projectSelectorInt = isInt();
                                }
                                for (Project project : projects) {
                                    if (projectSelectorInt == project.projectNumber) {
                                        out.println(project + "\n");
                                    }
                                }

                                // If user choose to search by project name

                            } else if (searchType == 2) {
                                boolean nameSearch = true;
                                while (nameSearch) {
                                    out.println("Enter the name of the project.");
                                    String projectSelectorString = input.next();
                                    projectSelectorString += input.nextLine();
                                    boolean projectMatch = false;

                                    // Check if project name match existing projects to display

                                    for (Project project : projects) {
                                        if (projectSelectorString.equals(project.projectName)) {
                                            out.println(project + "\n");
                                            projectMatch = true;
                                            nameSearch = false;
                                        }
                                    }
                                    if (!projectMatch) {
                                        out.println("No match found - Please enter a valid project name.");
                                    }
                                }
                            }
                        }
                    }

                    // View projects if any projects exist

                    else if (userChoice == 4) {

                        if (projects.isEmpty()) {
                            out.println("\nThere are no projects to view\n");

                        } else {
                            out.println("\n");
                            for (Project project : projects) {
                                out.println(project.toString());
                            }
                        }
                    }

                    // View projects that still need to be completed if any projects exist

                    else if (userChoice == 5) {

                        if (projects.isEmpty()) {
                            out.println("\nThere are no projects saved yet\n");

                        } else {
                            out.println("\nIncomplete projects :\n");
                            boolean incompleteProjects = false;
                            for (Project project : projects) {
                                String finalizedOrNot = project.finalized.toLowerCase();
                                if (finalizedOrNot.equals("no")) {
                                    out.println(project);
                                    incompleteProjects = true;
                                }
                            }
                            if (!incompleteProjects) {
                                out.println("All projects are complete.");
                            }
                        }
                    }

                    // View projects that are past the deadline if any projects exist

                    else if (userChoice == 6) {

                        if (projects.isEmpty()) {
                            out.println("\nThere are no projects saved yet\n");

                        } else {
                            out.println("\nProjects past deadline :\n");
                            boolean overdueProjects = false;
                            for (Project project : projects) {
                                String finalizedOrNot = project.finalized.toLowerCase();
                                if (finalizedOrNot.equals("no")) {
                                    String stringDeadline = project.deadline;
                                    SimpleDateFormat deadlineFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date deadline = deadlineFormatter.parse(stringDeadline);
                                    Date todayDate = Calendar.getInstance().getTime();
                                    if (todayDate.compareTo(deadline) > 0) {
                                        out.println(project + "\n");
                                        overdueProjects = true;
                                    }
                                }
                            }

                            if (!overdueProjects) {
                                out.println("All completed projects have been completed by the deadline.");
                            }
                        }
                    }

                    // Exit program

                    else if (userChoice == 7) {
                        optionMenu1 = false;
                        out.println("Have a great day!!");
                    }

                    // Catch invalid int inputs

                    else if (userChoice == 0 || userChoice > 7) {
                        out.println("Only 1, 2, 3, 4, 5, 6, and 7 are valid entries.");
                    }

                    // Catch string inputs

                } catch (InputMismatchException e) {
                    out.println("Only 1, 2, 3, 4, 5, 6 and 7 are valid entries.");
                    input.next();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            // Add any newly added projects to PoisePMS database

            if (newProject) {
                for (Project projectBeingWritten : newProjects) {

                    // Insert into Engineers table

                    PreparedStatement pStatement = connection.prepareStatement(
                            "INSERT INTO Engineers VALUES (?, ?, ?, ?, ?)");

                    pStatement.setInt(1, projectBeingWritten.engineer.id);
                    pStatement.setString(2, projectBeingWritten.engineer.name);
                    pStatement.setString(3, projectBeingWritten.engineer.contactNumber);
                    pStatement.setString(4, projectBeingWritten.engineer.email);
                    pStatement.setString(5, projectBeingWritten.engineer.address);
                    pStatement.executeUpdate();

                    // Insert into Architects table

                    pStatement = connection.prepareStatement(
                            "INSERT INTO Architects VALUES (?, ?, ?, ?, ?)");

                    pStatement.setInt(1, projectBeingWritten.architect.id);
                    pStatement.setString(2, projectBeingWritten.architect.name);
                    pStatement.setString(3, projectBeingWritten.architect.contactNumber);
                    pStatement.setString(4, projectBeingWritten.architect.email);
                    pStatement.setString(5, projectBeingWritten.architect.address);
                    pStatement.executeUpdate();

                    // Insert into Customers table

                    pStatement = connection.prepareStatement(
                            "INSERT INTO Customers VALUES (?, ?, ?, ?, ?)");

                    pStatement.setInt(1, projectBeingWritten.customer.id);
                    pStatement.setString(2, projectBeingWritten.customer.name);
                    pStatement.setString(3, projectBeingWritten.customer.contactNumber);
                    pStatement.setString(4, projectBeingWritten.customer.email);
                    pStatement.setString(5, projectBeingWritten.customer.address);
                    pStatement.executeUpdate();

                    // Insert into Managers table

                    pStatement = connection.prepareStatement(
                            "INSERT INTO Managers VALUES (?, ?, ?, ?, ?)");

                    pStatement.setInt(1, projectBeingWritten.manager.id);
                    pStatement.setString(2, projectBeingWritten.manager.name);
                    pStatement.setString(3, projectBeingWritten.manager.contactNumber);
                    pStatement.setString(4, projectBeingWritten.manager.email);
                    pStatement.setString(5, projectBeingWritten.manager.address);
                    pStatement.executeUpdate();

                    // Insert into Projects table

                    pStatement = connection.prepareStatement(
                            "INSERT INTO Projects VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

                    pStatement.setInt(1, projectBeingWritten.projectNumber);
                    pStatement.setString(2, projectBeingWritten.projectName);
                    pStatement.setString(3, projectBeingWritten.buildingType);
                    pStatement.setString(4, projectBeingWritten.projectAddress);
                    pStatement.setInt(5, projectBeingWritten.erfNumber);
                    pStatement.setInt(6, projectBeingWritten.projectFee);
                    pStatement.setInt(7, projectBeingWritten.amountPaid);
                    pStatement.setString(8, projectBeingWritten.deadline);
                    pStatement.setString(9, projectBeingWritten.finalized);
                    pStatement.setInt(10, projectBeingWritten.engineer.id);
                    pStatement.setInt(11, projectBeingWritten.architect.id);
                    pStatement.setInt(12, projectBeingWritten.manager.id);
                    pStatement.setInt(13, projectBeingWritten.customer.id);
                    pStatement.executeUpdate();
                }
            }

            // Write changes to existing Project objects back to database

            if (projectUpdates) {
                for (Project projectBeingWritten : projects) {

                    // Update Engineers

                    PreparedStatement pStatement = connection.prepareStatement(
                            "UPDATE Engineers SET e_name=?, e_contactNumber=?, e_email=?, e_address=? WHERE engineerId=?");

                    pStatement.setString(1, projectBeingWritten.engineer.name);
                    pStatement.setString(2, projectBeingWritten.engineer.contactNumber);
                    pStatement.setString(3, projectBeingWritten.engineer.email);
                    pStatement.setString(4, projectBeingWritten.engineer.address);
                    pStatement.setInt(5, projectBeingWritten.engineer.id);
                    pStatement.executeUpdate();

                    // Update Architects

                    pStatement = connection.prepareStatement(
                            "UPDATE Architects SET a_name=?, a_contactNumber=?, a_email=?, a_address=? WHERE architectId=?");

                    pStatement.setString(1, projectBeingWritten.architect.name);
                    pStatement.setString(2, projectBeingWritten.architect.contactNumber);
                    pStatement.setString(3, projectBeingWritten.architect.email);
                    pStatement.setString(4, projectBeingWritten.architect.address);
                    pStatement.setInt(5, projectBeingWritten.architect.id);
                    pStatement.executeUpdate();

                    // Update Customers

                    pStatement = connection.prepareStatement(
                            "UPDATE Customers SET c_name=?, c_contactNumber=?, c_email=?, c_address=? WHERE customerId=?");

                    pStatement.setString(1, projectBeingWritten.customer.name);
                    pStatement.setString(2, projectBeingWritten.customer.contactNumber);
                    pStatement.setString(3, projectBeingWritten.customer.email);
                    pStatement.setString(4, projectBeingWritten.customer.address);
                    pStatement.setInt(5, projectBeingWritten.customer.id);
                    pStatement.executeUpdate();

                    // Update Managers

                    pStatement = connection.prepareStatement(
                            "UPDATE Managers SET m_name=?, m_contactNumber=?, m_email=?, m_address=? WHERE managerId=?");

                    pStatement.setString(1, projectBeingWritten.manager.name);
                    pStatement.setString(2, projectBeingWritten.manager.contactNumber);
                    pStatement.setString(3, projectBeingWritten.manager.email);
                    pStatement.setString(4, projectBeingWritten.manager.address);
                    pStatement.setInt(5, projectBeingWritten.manager.id);
                    pStatement.executeUpdate();

                    // Update Projects

                    pStatement = connection.prepareStatement(
                            "UPDATE Projects SET projectName=?, buildingType=?, address=?, erfNumber=?, projectFee=?, amountPaid=?, deadline=?, finalized=? WHERE projectNumber=?");

                    pStatement.setString(1, projectBeingWritten.projectName);
                    pStatement.setString(2, projectBeingWritten.buildingType);
                    pStatement.setString(3, projectBeingWritten.projectAddress);
                    pStatement.setInt(4, projectBeingWritten.erfNumber);
                    pStatement.setInt(5, projectBeingWritten.projectFee);
                    pStatement.setInt(6, projectBeingWritten.amountPaid);
                    pStatement.setString(7, projectBeingWritten.deadline);
                    pStatement.setString(8, projectBeingWritten.finalized);
                    pStatement.setInt(9, projectBeingWritten.projectNumber);

                    pStatement.executeUpdate();
                }
            }

            // Print all projects in full before closing

            printAllFromTables(statement);
            out.println("\nGoodbye");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Methods to capture, display and manipulate data to database

    public static void printAllFromTables(Statement statement) throws SQLException {

        ResultSet results = statement.executeQuery("SELECT * FROM projects " +
                "JOIN architects ON projects.architectId = architects.architectId " +
                "JOIN managers ON projects.managerId = managers.managerId " +
                "JOIN engineers ON projects.engineerId = engineers.engineerId " +
                "JOIN customers ON projects.customerId = customers.customerId");
        out.println("\n");

        while (results.next()) {
            out.println(
                    "\nProject number: " + results.getInt("projectNumber")
                            + "\nProject name: " + results.getString("projectName")
                            + "\nBuilding type: " + results.getString("buildingType")
                            + "\nAddress: " + results.getString("address")
                            + "\nERF number: " + results.getInt("erfNumber")
                            + "\nProject fee: " + results.getInt("projectFee")
                            + "\nAmount paid: " + results.getInt("amountPaid")
                            + "\nDeadline: " + results.getString("deadline")
                            + "\nFinalized: " + results.getString("finalized")
                            + "\nEngineer: " + results.getString("e_name")
                            + "\nEngineer contact number: " + results.getString("e_contactNumber")
                            + "\nEngineer email: " + results.getString("e_email")
                            + "\nEngineer address: " + results.getString("e_address")
                            + "\nArchitect: " + results.getString("a_name")
                            + "\nArchitect contact number: " + results.getString("a_contactNumber")
                            + "\nArchitect email: " + results.getString("a_email")
                            + "\nArchitect address: " + results.getString("a_address")
                            + "\nManager: " + results.getString("m_name")
                            + "\nManager contact number: " + results.getString("m_contactNumber")
                            + "\nManager email: " + results.getString("m_email")
                            + "\nManager address: " + results.getString("m_address")
                            + "\nCustomer: " + results.getString("c_name")
                            + "\nCustomer contact number: " + results.getString("c_contactNumber")
                            + "\nCustomer email: " + results.getString("c_email")
                            + "\nCustomer address: " + results.getString("c_address"));
        }
    }
    public static List createObjectList(Statement statement) throws SQLException {

        List<Project> projects = new ArrayList<>();

        ResultSet results = statement.executeQuery("SELECT * FROM projects " +
                "JOIN architects ON projects.architectId = architects.architectId " +
                "JOIN managers ON projects.managerId = managers.managerId " +
                "JOIN engineers ON projects.engineerId = engineers.engineerId " +
                "JOIN customers ON projects.customerId = customers.customerId");

        out.println("\n");
        while (results.next()) {
            int projectNumber = results.getInt("projectNumber");
            String projectName = results.getString("projectName");
            String buildingType = results.getString("buildingType");
            String address = results.getString("address");
            int erfNumber = results.getInt("erfNumber");
            int projectFee = results.getInt("projectFee");
            int amountPaid = results.getInt("amountPaid");
            String deadline = results.getString("deadline");
            String finalized = results.getString("finalized");
            int eId = results.getInt("engineerId");
            String eName = results.getString("e_name");
            String eSurname = results.getString("e_surname");
            String eContactNumber = results.getString("e_contactNumber");
            String eEmail = results.getString("e_email");
            String eAddress = results.getString("e_address");
            int mId = results.getInt("managerId");
            String mName = results.getString("m_name");
            String mSurname = results.getString("m_surname");
            String mContactNumber = results.getString("m_contactNumber");
            String mEmail = results.getString("m_email");
            String mAddress = results.getString("m_address");
            int aId = results.getInt("architectId");
            String aName = results.getString("a_name");
            String aSurname = results.getString("a_Surname");
            String aContactNumber = results.getString("a_contactNumber");
            String aEmail = results.getString("a_email");
            String aAddress = results.getString("a_address");
            int cId = results.getInt("customerId");
            String cName = results.getString("c_name");
            String cSurname = results.getString("c_surname");
            String cContactNumber = results.getString("c_contactNumber");
            String cEmail = results.getString("c_email");
            String cAddress = results.getString("c_address");

            Person engineer = new Person(eId, eName, eSurname, eContactNumber, eEmail, eAddress);
            Person architect = new Person(aId, aName, aSurname, aContactNumber, aEmail, aAddress);
            Person manager = new Person(mId, mName, mSurname, mContactNumber, mEmail, mAddress);
            Person customer = new Person(cId, cName, cSurname, cContactNumber, cEmail, cAddress);
            Project project = new Project(projectNumber, projectName, buildingType, address, erfNumber, projectFee, amountPaid, deadline, finalized, engineer, architect, manager, customer);
            projects.add(project);
        }
        return projects;
    }

    // method to ensure valid integer inputs where applicable
    public static int isInt() {
        Scanner scan = new Scanner(in);
        int returnValue;
        while (!scan.hasNextInt()) {
            out.println("Please enter a valid integer...");
            scan.next();
        }
        returnValue = scan.nextInt();
        return returnValue;
    }
}
