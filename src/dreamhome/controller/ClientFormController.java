package dreamhome.controller;

import java.io.File;
import java.io.IOException;

import dreamhome.dao.ClientDAO;
import dreamhome.model.Client;
import dreamhome.utils.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ClientFormController {

    @FXML private TextField fullNameField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private ComboBox<String> propertyTypeCombo;
    @FXML private TextField maxRentField;
    @FXML private Button registerButton;

    @FXML
    public void initialize() {
        propertyTypeCombo.getItems().addAll("House", "Apartment", "Studio", "Villa");
    }

    @FXML
    private void handleRegister() {
        String name = fullNameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String type = propertyTypeCombo.getValue();
        String rentStr = maxRentField.getText().trim();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || type == null || rentStr.isEmpty()) {
            showAlert(AlertType.ERROR, "Missing Fields", "Please fill in all fields.");
            return;
        }

        float maxRent;
        try {
            maxRent = Float.parseFloat(rentStr);
            if (maxRent <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Invalid Rent", "Please enter a valid positive rent.");
            return;
        }

        int staffId = Session.getLoggedInStaff().getStaffId();
        int branchId = Session.getLoggedInStaff().getBranchId();

        Client client = new Client();
        client.setFullName(name);
        client.setPhone(phone);
        client.setEmail(email);
        client.setPreferredType(type);
        client.setMaxRent(maxRent);
        client.setStaffId(staffId);
        client.setBranchId(branchId);

        boolean success = ClientDAO.insertClient(client);
        if (success) {
            showAlert(AlertType.INFORMATION, "Success", "Client registered successfully.");
            clearForm();
        } else {
            showAlert(AlertType.ERROR, "Database Error", "Could not register client. Please try again.");
        }
    }

    @FXML
    private void handleCancel() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("view/dashboard.fxml").toURI().toURL());
            Parent root = loader.load();

            Stage stage = (Stage) fullNameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("DreamHome Dashboard");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearForm() {
        fullNameField.clear();
        phoneField.clear();
        emailField.clear();
        propertyTypeCombo.getSelectionModel().clearSelection();
        maxRentField.clear();
    }

    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
