package dreamhome.controller;

import dreamhome.dao.BranchDAO;
import dreamhome.dao.StaffDAO;
import dreamhome.model.Branch;
import dreamhome.model.Staff;
import dreamhome.model.StaffViewModel;
import dreamhome.utils.Session;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StaffListController {

    @FXML private ComboBox<Branch> branchBox;
    @FXML private TableView<StaffViewModel> staffTable;
    @FXML private TableColumn<StaffViewModel, String> nameCol;
    @FXML private TableColumn<StaffViewModel, String> emailCol;
    @FXML private TableColumn<StaffViewModel, String> roleCol;
    @FXML private TableColumn<StaffViewModel, String> phoneCol;
    @FXML private TableColumn<StaffViewModel, String> dateCol;
    @FXML private TableColumn<StaffViewModel, Double> salaryCol;

    @FXML
    public void initialize() {
        List<Branch> branches = BranchDAO.getAllBranches();
        branchBox.setItems(FXCollections.observableArrayList(branches));
        
        // Set current branch and load staff
        int branchId = Session.getLoggedInStaff().getBranchId();
        Branch currentBranch = BranchDAO.getBranchById(branchId);
        branchBox.setValue(currentBranch);
        loadStaffForBranch();

        branchBox.setOnAction(e -> loadStaffForBranch());

        // Setup table columns
        nameCol.setCellValueFactory(data -> data.getValue().fullNameProperty());
        emailCol.setCellValueFactory(data -> data.getValue().emailProperty());
        roleCol.setCellValueFactory(data -> data.getValue().roleProperty());
        phoneCol.setCellValueFactory(data -> data.getValue().phoneProperty());
        dateCol.setCellValueFactory(data -> data.getValue().dateJoinedProperty().map(
            date -> date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
        ));
        salaryCol.setCellValueFactory(data -> data.getValue().salaryProperty().asObject());

        if (!"Manager".equalsIgnoreCase(Session.getLoggedInStaff().getRole())) {
            salaryCol.setVisible(false);
        }
    }


    private void loadStaffForBranch() {
        Branch selectedBranch = branchBox.getValue();
        if (selectedBranch == null) return;

        List<Staff> staffList = StaffDAO.getStaffByBranch(selectedBranch.getBranchID());
        List<StaffViewModel> viewModels = staffList.stream()
                .map(StaffViewModel::new)
                .toList();

        staffTable.setItems(FXCollections.observableArrayList(viewModels));
    }

    @FXML
    private void onBackClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("view/dashboard.fxml").toURI().toURL());
            Parent root = loader.load();

            Stage stage = (Stage) branchBox.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("DreamHome Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
