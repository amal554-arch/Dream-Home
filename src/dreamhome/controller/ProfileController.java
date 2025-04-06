package dreamhome.controller;

import dreamhome.model.Staff;
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

public class ProfileController {

    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private Label roleLabel;
    @FXML private Label branchLabel;
    @FXML private Label salaryLabel;
    @FXML private Label statusLabel;

    private Staff staff;

    @FXML
    public void initialize() {
        staff = Session.getLoggedInStaff();

        nameField.setText(staff.getFullName());
        phoneField.setText(staff.getPhone());
        emailField.setText(staff.getEmail());
        roleLabel.setText(staff.getRole());
        branchLabel.setText("Branch " + staff.getBranchId());

        if (staff.getRole().equalsIgnoreCase("manager")) {
            salaryLabel.setText("£" + staff.getSalary());
        } else {
            salaryLabel.setText("Confidential");
        }
    }

    @FXML
    private void onSaveClicked() {
        String newName = nameField.getText().trim();
        String newPhone = phoneField.getText().trim();
        String newEmail = emailField.getText().trim();

        if (newName.isEmpty() || newPhone.isEmpty() || newEmail.isEmpty()) {
            statusLabel.setText("Please fill all fields.");
            statusLabel.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }

        // Update staff object
        staff.setFullName(newName);
        staff.setPhone(newPhone);
        staff.setEmail(newEmail);

        boolean success = StaffDAO.updateStaffProfile(staff);

        if (success) {
            statusLabel.setText("Profile updated successfully!");
            statusLabel.setTextFill(javafx.scene.paint.Color.GREEN);
        } else {
            statusLabel.setText("Update failed.");
            statusLabel.setTextFill(javafx.scene.paint.Color.RED);
        }
    }

    @FXML
    private void onBackClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("view/dashboard.fxml").toURI().toURL());
            Parent root = loader.load();
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("DreamHome Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
