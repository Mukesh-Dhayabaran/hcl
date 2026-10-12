
package model;

public class ITAssetManager extends User {

    public ITAssetManager(int userId, String name, String email) {
        super(userId, name, email);
    }

    @Override
    public String getRole() {
        return "IT Asset Manager";
    }

    @Override
    public void displayPermissions() {
        System.out.println(
                "IT Asset Manager: Register, assign, maintain, and track assets."
        );
    }
}
