package ua.kiev.prog;

public class UserStatus {
    private String name;

    public UserStatus(String name) {
        this.name = name;
    }

    public UserStatus() {
        this("");
    }

    public String getName() {
        return name;
    }
}
