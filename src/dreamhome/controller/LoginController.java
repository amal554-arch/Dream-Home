package dreamhome.controller;

import dreamhome.model.Staff;

import java.io.File;

import dreamhome.dao.StaffDAO;
import dreamhome.utils.PasswordHasher;
import dreamhome.utils.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    @FXML
    private void onLoginClicked() {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Email and password required.");
            return;
        }

        Staff staff = StaffDAO.getStaffByEmail(email);
        if (staff == null) {
            statusLabel.setText("No such user.");
        } else if (!PasswordHasher.verifyPassword(password, staff.getPasswordHash())) {
            statusLabel.setText("Incorrect password.");
        } else {
            Session.login(staff);
            try {
                FXMLLoader loader = new FXMLLoader(new File("view/dashboard.fxml").toURI().toURL());
                Parent root = loader.load();
                Stage stage = (Stage) emailField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("DreamHome Dashboard");
            } catch (Exception e) {
                e.printStackTrace();
                statusLabel.setText("Failed to load dashboard.");
            }
        }
    }
}
