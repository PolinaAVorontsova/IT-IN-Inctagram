package brows.data;

public class User {
    private String userName;
    private String email;
    private String password;

    public User(String name, String email, String password) {
        this.userName = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
