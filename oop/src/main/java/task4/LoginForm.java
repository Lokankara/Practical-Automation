package task4;

import java.util.HashMap;
import java.util.Map;

public class LoginForm extends RegistrationForm {
    private final Map<String, Integer> loginAttempts = new HashMap<>();

    public LoginForm() {
        super();
    }

    public LoginForm(String username, String email, String password) {
        super(username, email, password);
    }

    public boolean authenticate(String inputUsername, String inputPassword) {
        Integer attempts = loginAttempts.getOrDefault(inputUsername, 0);

        if (attempts >= 3) {
            throw new AuthenticationException("Account is locked due to too many login attempts.");
        }

        if (!inputUsername.equals(getUsername()) || !inputPassword.equals(getPassword())) {
            loginAttempts.put(inputUsername, attempts + 1);
            if (loginAttempts.get(inputUsername) < 3) {
                throw new AuthenticationException("Incorrect password.");
            } else {
                throw new AuthenticationException("Account is locked due to too many login attempts.");
            }
        }

        loginAttempts.put(inputUsername, 0);
        return true;
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (newPassword.length() < 8) {
            throw new IllegalArgumentException("The new password must be at least 8 characters long.");
        }

        Integer attempts = loginAttempts.getOrDefault(getUsername(), 0);
        if (attempts >= 3) {
            throw new AuthenticationException("Account is locked due to too many login attempts.");
        }

        if (oldPassword.equals(getPassword())) {
            setPassword(newPassword);
        } else {
            loginAttempts.put(getUsername(), attempts + 1);
            if (loginAttempts.get(getUsername()) < 3) {
                throw new AuthenticationException("Incorrect current password.");
            } else {
                throw new AuthenticationException("Account is locked due to too many login attempts.");
            }
        }
    }
}
