// This class defines information about a Person object
// people who are involved with projects
// architect,engineer, manager, and customer

    public class Person {

        // data type values for id, name, surname, contact number, email, and address

        int id;
        String name;
        String surname;
        String contactNumber;
        String email;
        String address;

        // Constructor method

        public Person(int id, String name, String surname, String contactNumber,
                      String email, String address) {
            this.id = id;
            this.name = name;
            this.surname = surname;
            this.contactNumber = contactNumber;
            this.email = email;
            this.address = address;
        }

        // toString method

        public String toString() {
            String output = "Name: " + name;
            output += "Surname: " + surname;
            output += "\nContact: " + contactNumber;
            output += "\nEmail: " + email;
            output += "\nAddress: " + address;

            return output;
        }
    }
