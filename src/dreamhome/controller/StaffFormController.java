package dreamhome.controller;

import dreamhome.dao.BranchDAO;
import dreamhome.dao.StaffDAO;
import dreamhome.model.Branch;
import dreamhome.model.Staff;
import dreamhome.utils.PasswordHasher;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class StaffFormController {

    @FXML private TextField nameField;
    @FXML private ChoiceBox<String> roleBox;
    @FXML private TextField salaryField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmField;
    @FXML private ComboBox<Branch> branchBox;
    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        // Load roles dynamically
        roleBox.setItems(FXCollections.observableArrayList("Select Role", "Manager", "Supervisor", "Assistant"));
        roleBox.setValue("Select Role");

        // Load all branches into the dropdown
        List<Branch> branches = BranchDAO.getAllBranches();
        branchBox.setItems(FXCollections.observableArrayList(branches));
    }

    @FXML
    private void onRegisterClicked() {
        String name = nameField.getText().trim();
        String role = roleBox.getValue();
        String salaryStr = salaryField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String password = passwordField.getText();
        String confirm = confirmField.getText();
        Branch branch = branchBox.getValue();

        statusLabel.setStyle("-fx-text-fill: red;");

        if (name.isEmpty() || role == null || salaryStr.isEmpty() || email.isEmpty()
                || password.isEmpty() || confirm.isEmpty() || branch == null) {
            statusLabel.setText("All fields except phone are required.");
            return;
        }

        double salary;
        try {
            salary = Double.parseDouble(salaryStr);
        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid salary format.");
            return;
        }

        if (!password.equals(confirm)) {
            statusLabel.setText("Passwords do not match.");
            return;
        }

        if (StaffDAO.getStaffByEmail(email) != null) {
            statusLabel.setText("Email is already registered.");
            return;
        }

        String passwordHash = PasswordHasher.hashPassword(password);
        Staff staff = new Staff(0, name, role, salary, email, passwordHash, phone, null, branch.getBranchID());

        boolean success = StaffDAO.insertStaff(staff);

        if (success) {
            statusLabel.setStyle("-fx-text-fill: green;");
            statusLabel.setText("✅ Staff registered successfully.");
            clearForm();
        } else {
            statusLabel.setText("Registration failed. Try again.");
        }
    }

    @FXML
    private void onBackClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("view/dashboard.fxml").toURI().toURL());
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("DreamHome Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Failed to return to dashboard.");
        }
    }

    private void clearForm() {
        nameField.clear();
        roleBox.setValue(null);
        salaryField.clear();
        emailField.clear();
        phoneField.clear();
        passwordField.clear();
        confirmField.clear();
        branchBox.setValue(null);
    }
}
