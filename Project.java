//This class defines information about the project

public class Project {

     // data type values for project number, project name, building type, project address,
     // erf number,  project fee, amount paid, deadline, finalized,
     // engineer, architect, manager, customer

    int projectNumber;
    String projectName;
    String buildingType;
    String projectAddress;
    int erfNumber;
    int projectFee;
    int amountPaid;
    String deadline;
    String finalized;
    Person engineer;
    Person architect;
    Person manager;
    Person customer;

    // Constructor method

    public Project(int projectNumber, String projectName, String buildingType,
                   String projectAddress, int erfNumber, int projectFee,
                   int amountPaid, String deadline, String finalized, Person engineer,
                   Person architect, Person manager, Person customer) {
        this.projectNumber = projectNumber;
        this.projectName = projectName;
        this.buildingType = buildingType;
        this.projectAddress = projectAddress;
        this.erfNumber = erfNumber;
        this.projectFee = projectFee;
        this.amountPaid = amountPaid;
        this.deadline = deadline;
        this.finalized = finalized;
        this.engineer = engineer;
        this.architect = architect;
        this.manager = manager;
        this.customer = customer;
    }

    // toString method

    public String toString() {
        String output = "Project number: " + projectNumber;
        output += "\nProject Name: " + projectName;
        output += "\nBuilding type: " + buildingType;
        output += "\nAddress: " + projectAddress;
        output += "\nERF number: " + erfNumber;
        output += "\nProject fee: " + projectFee;
        output += "\nAmount paid: " + amountPaid;
        output += "\nDeadline: " + deadline;
        output += "\nFinalized: " + finalized;
        output += "\nEngineer: " + engineer.name;
        output += "\nManager: " + manager.name;
        output += "\nArchitect: " + architect.name;
        output += "\nCustomer: " + customer.name + "\n";

        return output;
    }
}
