package dreamhome.controller;

import dreamhome.dao.LeaseDAO;
import dreamhome.model.Lease;
import dreamhome.utils.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class LeaseListController {

    @FXML private TableView<Lease> leaseTable;
    @FXML private TableColumn<Lease, Integer> leaseIdCol;
    @FXML private TableColumn<Lease, Integer> propertyIdCol;
    @FXML private TableColumn<Lease, Integer> clientIdCol;
    @FXML private TableColumn<Lease, Integer> staffIdCol;
    @FXML private TableColumn<Lease, String> startDateCol;
    @FXML private TableColumn<Lease, String> endDateCol;
    @FXML private TableColumn<Lease, Double> depositCol;
    @FXML private TableColumn<Lease, String> frequencyCol;
    @FXML private TableColumn<Lease, String> methodCol;
    @FXML private TableColumn<Lease, String> statusCol;
    @FXML private TableColumn<Lease, Void> actionCol;

    @FXML
    public void initialize() {
        leaseIdCol.setCellValueFactory(new PropertyValueFactory<>("leaseId"));
        propertyIdCol.setCellValueFactory(new PropertyValueFactory<>("propertyId"));
        clientIdCol.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        staffIdCol.setCellValueFactory(new PropertyValueFactory<>("staffId"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        depositCol.setCellValueFactory(new PropertyValueFactory<>("depositAmount"));
        frequencyCol.setCellValueFactory(new PropertyValueFactory<>("paymentFrequency"));
        methodCol.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        addActionButtons();
        loadLeases();
    }

    private void addActionButtons() {
        actionCol.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Lease, Void> call(final TableColumn<Lease, Void> param) {
                return new TableCell<>() {
                    private final Button approveBtn = new Button("✔");
                    private final Button rejectBtn = new Button("✖");
                    private final HBox actionBox = new HBox(5, approveBtn, rejectBtn);

                    {
                        approveBtn.setOnAction(e -> {
                            Lease lease = getTableView().getItems().get(getIndex());
                            updateStatus(lease.getLeaseId(), "Approved");
                        });

                        rejectBtn.setOnAction(e -> {
                            Lease lease = getTableView().getItems().get(getIndex());
                            updateStatus(lease.getLeaseId(), "Rejected");
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : actionBox);
                    }
                };
            }
        });
    }

    private void updateStatus(int leaseId, String status) {
        boolean success = LeaseDAO.updateLeaseStatus(leaseId, status);
        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Lease " + status.toLowerCase() + ".");
            alert.showAndWait();
            loadLeases();
        }
    }

    public void loadLeases() {
        int staffId = Session.getLoggedInStaff().getStaffId();
        List<Lease> leaseList = LeaseDAO.getLeasesForStaff(staffId);
        ObservableList<Lease> data = FXCollections.observableArrayList(leaseList);
        leaseTable.setItems(data);
    }

    @FXML
    private void onBackClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("view/dashboard.fxml").toURI().toURL());
            Parent root = loader.load();
            Stage stage = (Stage) leaseTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("DreamHome Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
