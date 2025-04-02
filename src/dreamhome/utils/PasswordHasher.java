package dreamhome.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));  // convert byte to hex
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found!", e);
        }
    }

    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return hashPassword(plainPassword).equals(hashedPassword);
    }
}
