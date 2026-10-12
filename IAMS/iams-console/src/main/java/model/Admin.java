
package model;

public class Admin extends User {

    public Admin(int userId, String name, String email) {
        super(userId, name, email);
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    @Override
    public void displayPermissions() {
        System.out.println("Admin: Manage users, assets, and system settings.");
    }
}
