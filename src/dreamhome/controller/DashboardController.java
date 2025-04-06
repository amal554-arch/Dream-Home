package dreamhome.controller;

import dreamhome.model.Staff;
import dreamhome.utils.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class DashboardController {

    @FXML private MenuButton userMenuBtn;
    @FXML private Button registerStaffBtn;
    @FXML private Button viewStaffBtn;
    @FXML private Button registerPropertyBtn;
    @FXML private Button viewPropertyBtn;
    @FXML private Button registerClientBtn;
    @FXML private Button viewClientBtn;
    @FXML private Button viewOwnerBtn;
    @FXML private Button reviewLeasesBtn;
    @FXML private Button sendReportsBtn;

    @FXML
    public void initialize() {
        Staff staff = Session.getLoggedInStaff();
        userMenuBtn.setText("Welcome, " + staff.getFullName());

        // Role-based access control
        String role = staff.getRole().toLowerCase();

        registerStaffBtn.setVisible(role.equals("manager"));
        registerPropertyBtn.setVisible(!role.equals("assistant"));
        registerClientBtn.setVisible(!role.equals("assistant"));
        reviewLeasesBtn.setVisible(!role.equals("assistant"));

        // Everyone gets these
        viewStaffBtn.setVisible(true);
        viewPropertyBtn.setVisible(true);
        viewOwnerBtn.setVisible(true);
        viewClientBtn.setVisible(true);
        sendReportsBtn.setVisible(role.equals("manager") || role.equals("assistant") || role.equals("supervisor"));
    }

    @FXML
    private void onViewProfileClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("view/staff_profile.fxml").toURI().toURL());
            Parent root = loader.load();
            Stage stage = (Stage) userMenuBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("My Profile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onChangePasswordClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("view/change_password.fxml").toURI().toURL());
            Parent root = loader.load();
            Stage stage = (Stage) userMenuBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Change Password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    

    @FXML private void onLogoutClicked() {
        Session.logout();
        try {
            FXMLLoader loader = new FXMLLoader(new File("view/login.fxml").toURI().toURL());
            Parent loginRoot = loader.load();
            Stage stage = (Stage) userMenuBtn.getScene().getWindow();
            stage.setScene(new Scene(loginRoot));
            stage.setTitle("DreamHome Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void onRegisterStaffClicked() {
        loadScene("staff_form.fxml", "Register Staff");
    }

    @FXML private void onViewStaffClicked() {
        loadScene("staff_list.fxml", "Staff List");
    }

    @FXML private void onRegisterPropertyClicked() {
        loadScene("property_form.fxml", "Register Property");
    }

    @FXML
    private void onViewPropertyClicked() {
        loadScene("property_list.fxml", "Property List");
    }

    @FXML private void onRegisterClientClicked() {
        loadScene("client_form.fxml", "Register Client");
    }

    @FXML private void onViewClientClicked() {
        loadScene("client_list.fxml", "Client List");
    }

    @FXML private void onViewOwnerClicked() {
        // TODO: Implement view owner list
    }

    @FXML
    private void onReviewLeasesClicked() {
        loadScene("lease_list.fxml", "Review Leases");
    }

    @FXML private void onSendReportsClicked() {
        // TODO: Implement email report sending
    }

    private void loadScene(String fxml, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(new File("view/" + fxml).toURI().toURL());
            Parent root = loader.load();
            Stage stage = (Stage) userMenuBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("DreamHome - " + title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
