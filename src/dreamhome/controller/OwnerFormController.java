package dreamhome.controller;

import dreamhome.dao.OwnerDAO;
import dreamhome.model.Owner;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class OwnerFormController {

    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private TextArea addressField;
    @FXML private Label statusLabel;

    @FXML
    private void onSaveClicked() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String address = addressField.getText().trim();

        // Validate required fields
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty()) {
            statusLabel.setText("All fields are required.");
            return;
        }

        // Check if owner already exists
        if (OwnerDAO.existsByEmailOrPhone(email, phone)) {
            statusLabel.setText("Owner with this email or phone already exists.");
            return;
        }

        // Create and insert owner
        Owner owner = new Owner(0, name, phone, email, address);
        boolean success = OwnerDAO.insertOwner(owner);

        if (success) {
            statusLabel.setStyle("-fx-text-fill: green;");
            statusLabel.setText("✅ Owner registered successfully.");
            clearForm();
        } else {
            statusLabel.setText("Failed to register owner.");
        }
    }

    @FXML
    private void onCancelClicked() {
        // Close the form window
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    private void clearForm() {
        nameField.clear();
        phoneField.clear();
        emailField.clear();
        addressField.clear();
    }
}
