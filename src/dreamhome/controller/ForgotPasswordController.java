package dreamhome.controller;

import dreamhome.dao.StaffDAO;
import dreamhome.model.Staff;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;

public class ForgotPasswordController {

    @FXML private TextField emailField;
    @FXML private Label statusLabel;

    @FXML
    private void onSendResetLinkClicked() {
        String email = emailField.getText().trim();

        if (email.isEmpty()) {
            statusLabel.setText("Please enter your email.");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        Staff staff = StaffDAO.getStaffByEmail(email);
        if (staff == null) {
            statusLabel.setText("No staff account found with that email.");
            statusLabel.setStyle("-fx-text-fill: red;");
        } else {
            // Simulate email sending
            statusLabel.setText("Reset link sent to " + email + ". (simulation)");
            statusLabel.setStyle("-fx-text-fill: green;");
            // Future: Hook into EmailUtil to send real link
        }
    }

    @FXML
    private void onBackToLoginClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("view/login.fxml").toURI().toURL());
            Parent loginRoot = loader.load();

            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(loginRoot));
            stage.setTitle("DreamHome Login");

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Failed to return to login.");
        }
    }
}
