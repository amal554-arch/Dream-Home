package dreamhome.controller;

import dreamhome.model.Staff;
import dreamhome.utils.PasswordHasher;
import dreamhome.utils.Session;
import dreamhome.dao.StaffDAO;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;

public class ChangePasswordController {

    @FXML private PasswordField currentPasswordField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private TextField currentTextField;
    @FXML private TextField newTextField;
    @FXML private TextField confirmTextField;
    @FXML private Label statusLabel;

    @FXML
    private void onSaveClicked() {
        String current = currentPasswordField.getText().trim();
        String newPass = newPasswordField.getText().trim();
        String confirm = confirmPasswordField.getText().trim();

        if (current.isEmpty() || newPass.isEmpty() || confirm.isEmpty()) {
            statusLabel.setText("All fields are required.");
            return;
        }

        Staff staff = Session.getLoggedInStaff();

        if (!PasswordHasher.verifyPassword(current, staff.getPasswordHash())) {
            statusLabel.setText("Current password is incorrect.");
            return;
        }

        if (!newPass.equals(confirm)) {
            statusLabel.setText("New passwords do not match.");
            return;
        }

        String newHash = PasswordHasher.hashPassword(newPass);
        boolean success = StaffDAO.updatePassword(staff.getStaffId(), newHash);

        if (success) {
            statusLabel.setText("Password updated successfully.");
            statusLabel.setTextFill(javafx.scene.paint.Color.GREEN);
        } else {
            statusLabel.setText("Failed to update password.");
            statusLabel.setTextFill(javafx.scene.paint.Color.RED);
        }
    }

    @FXML
    private void onCancelClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("view/dashboard.fxml").toURI().toURL());
            Parent root = loader.load();
            Stage stage = (Stage) currentPasswordField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("DreamHome Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void toggleCurrentVisibility() {
        toggleField(currentPasswordField, currentTextField);
    }

    @FXML
    private void toggleNewVisibility() {
        toggleField(newPasswordField, newTextField);
    }

    @FXML
    private void toggleConfirmVisibility() {
        toggleField(confirmPasswordField, confirmTextField);
    }

    private void toggleField(PasswordField pwField, TextField textField) {
        if (pwField.isVisible()) {
            textField.setText(pwField.getText());
            textField.setVisible(true);
            textField.setManaged(true);
            pwField.setVisible(false);
            pwField.setManaged(false);
        } else {
            pwField.setText(textField.getText());
            pwField.setVisible(true);
            pwField.setManaged(true);
            textField.setVisible(false);
            textField.setManaged(false);
        }
    }
}
