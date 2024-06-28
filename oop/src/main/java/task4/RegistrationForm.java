package task4;

public class RegistrationForm {

    private String username;
    private String email;
    private String password;

    public static final int USERNAME_LENGTH = 10;
    public static final int PASSWORD_LENGTH = 8;

    public RegistrationForm(
            final String username,
            final String email,
            final String password
    ) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public RegistrationForm() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
