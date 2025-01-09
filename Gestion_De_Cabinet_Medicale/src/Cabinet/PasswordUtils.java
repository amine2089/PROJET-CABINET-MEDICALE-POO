package Cabinet;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {
    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public static void main(String[] args) {
        // Example usage
        String hashedPassword = hashPassword("my_secure_password");
        System.out.println("Hashed Password: " + hashedPassword);
    }
}
