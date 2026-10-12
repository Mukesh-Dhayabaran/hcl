
package model;

public class Employee extends User {

    public Employee(int userId, String name, String email) {
        super(userId, name, email);
    }

    @Override
    public String getRole() {
        return "Employee";
    }

    @Override
    public void displayPermissions() {
        System.out.println(
                "Employee: Request assets and view assigned assets."
        );
    }
}
