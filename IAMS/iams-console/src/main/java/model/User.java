
package model;

public abstract class User extends BaseEntity {

    private String name;
    private String email;

    protected User(int userId, String name, String email) {
        super(userId);
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public abstract String getRole();

    public abstract void displayPermissions();
}
