package dreamhome.controller;

import dreamhome.dao.BranchDAO;
import dreamhome.dao.ClientDAO;
import dreamhome.model.Branch;
import dreamhome.model.Client;
import dreamhome.model.ClientViewModel;
import dreamhome.utils.Session;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class ClientListController {

    @FXML private ComboBox<Branch> branchComboBox;
    @FXML private TableView<ClientViewModel> clientTable;
    @FXML private TableColumn<ClientViewModel, String> fullNameColumn;
    @FXML private TableColumn<ClientViewModel, String> phoneColumn;
    @FXML private TableColumn<ClientViewModel, String> emailColumn;
    @FXML private TableColumn<ClientViewModel, String> preferredTypeColumn;
    @FXML private TableColumn<ClientViewModel, Integer> maxRentColumn;
    @FXML private Button backButton;

    @FXML
    public void initialize() {
        // Table column bindings
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        preferredTypeColumn.setCellValueFactory(new PropertyValueFactory<>("preferredType"));
        maxRentColumn.setCellValueFactory(new PropertyValueFactory<>("maxRent"));

        // Populate branches
        List<Branch> branches = BranchDAO.getAllBranches();
        branchComboBox.setItems(FXCollections.observableArrayList(branches));

        // Default select staff’s own branch
        int branchId = Session.getLoggedInStaff().getBranchId();
        Branch staffBranch = BranchDAO.getBranchById(branchId);
        if (staffBranch != null) {
            branchComboBox.getSelectionModel().select(staffBranch);
            loadClients(staffBranch.getBranchID());
        }

        branchComboBox.setOnAction(e -> {
            Branch selected = branchComboBox.getSelectionModel().getSelectedItem();
            if (selected != null) {
                loadClients(selected.getBranchID());
            }
        });
    }

    private void loadClients(int branchId) {
        List<Client> clients = ClientDAO.getClientsByBranch(branchId);
        List<ClientViewModel> viewModels = clients.stream()
                .map(c -> new ClientViewModel(
                        c.getFullName(),
                        c.getPhone(),
                        c.getEmail(),
                        c.getPreferredType(),
                        (int) c.getMaxRent()
                )).collect(Collectors.toList());

        clientTable.setItems(FXCollections.observableArrayList(viewModels));
    }

    @FXML
    private void handleBack(ActionEvent event) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close(); // or navigate back to dashboard
    }
}
