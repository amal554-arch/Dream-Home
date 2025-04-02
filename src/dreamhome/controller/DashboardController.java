package dreamhome.controller;

import dreamhome.utils.Session;
import dreamhome.model.Staff;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;

public class DashboardController {

    @FXML private Label welcomeLabel;
    @FXML private Button registerStaffBtn;
    @FXML private Button viewStaffBtn;
    @FXML private Button registerPropertyBtn;
    @FXML private Button registerClientBtn;
    @FXML private Button reviewLeasesBtn;
    @FXML private Button sendReportsBtn;

    @FXML
    public void initialize() {
        Staff staff = Session.getLoggedInStaff();
        welcomeLabel.setText("Welcome, " + staff.getFullName() + " (" + staff.getRole() + ")");

        // Hide/disable buttons based on role
        switch (staff.getRole().toLowerCase()) {
            case "manager":
                // Managers get full access
                break;
            case "supervisor":
                registerStaffBtn.setVisible(false);
                sendReportsBtn.setVisible(true);
                break;
            case "assistant":
                registerStaffBtn.setVisible(false);
                registerPropertyBtn.setVisible(false);
                registerClientBtn.setVisible(false);
                reviewLeasesBtn.setVisible(false);
                sendReportsBtn.setVisible(false);
                break;
        }
    }

    @FXML
    private void onLogoutClicked() {
        Session.logout();
        try {
            FXMLLoader loader = new FXMLLoader(new File("view/login.fxml").toURI().toURL());
            Parent loginRoot = loader.load();

            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(new Scene(loginRoot));
            stage.setTitle("DreamHome Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onRegisterStaffClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("view/staff_form.fxml").toURI().toURL());
            Parent root = loader.load();

            Stage stage = (Stage) registerStaffBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Register New Staff");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onViewStaffClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("view/staff_list.fxml").toURI().toURL());
            Parent root = loader.load();

            Stage stage = (Stage) viewStaffBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onRegisterPropertyClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("view/property_form.fxml").toURI().toURL());
            Parent root = loader.load();

            Stage stage = (Stage) registerPropertyBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Register Property");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void onRegisterClientClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("view/client_form.fxml").toURI().toURL());
            Parent root = loader.load();

            Stage stage = (Stage) registerClientBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Register Client");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void onReviewLeasesClicked() {
        // TODO: Load lease_list.fxml
    }

    @FXML private void onSendReportsClicked() {
        // TODO: Trigger email sending logic
    }
}
