package dreamhome.controller;

import dreamhome.dao.OwnerDAO;
import dreamhome.model.Owner;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer; // ✅ 1. Import this

public class OwnerLookupController {

    @FXML private TextField searchField;
    @FXML private TableView<Owner> ownerTable;
    @FXML private TableColumn<Owner, String> nameCol;
    @FXML private TableColumn<Owner, String> phoneCol;
    @FXML private TableColumn<Owner, String> emailCol;
    @FXML private TableColumn<Owner, String> addressCol;

    private Owner selectedOwner;

    private Consumer<Owner> callback; // ✅ 2. Callback field

    @FXML
    public void initialize() {
        // Bind columns
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        // 👇 Load all owners immediately
        List<Owner> owners = OwnerDAO.getAllOwners();
        ownerTable.setItems(FXCollections.observableArrayList(owners));
    }

    @FXML
    private void onSearchClicked() {
        String keyword = searchField.getText().trim();
        if (!keyword.isEmpty()) {
            List<Owner> matches = OwnerDAO.searchOwners(keyword);
            ownerTable.setItems(FXCollections.observableArrayList(matches));
        }
    }

    @FXML
    private void onSelectClicked() {
        selectedOwner = ownerTable.getSelectionModel().getSelectedItem();
        if (selectedOwner != null) {
            if (callback != null) {
                callback.accept(selectedOwner); // ✅ 4. Fire callback
            }
            ((Stage) searchField.getScene().getWindow()).close();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an owner.");
            alert.showAndWait();
        }
    }

    @FXML
    private void onCancelClicked() {
        selectedOwner = null;
        ((Stage) searchField.getScene().getWindow()).close();
    }

    public Owner getSelectedOwner() {
        return selectedOwner;
    }

    public void setCallback(Consumer<Owner> callback) { // ✅ 3. Add this method
        this.callback = callback;
    }

    @FXML
    private void onNewOwnerClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("view/owner_form.fxml").toURI().toURL());
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Register New Owner");
            stage.showAndWait();  // Wait until new owner form is closed

            // Refresh the list in case a new owner was added
            onSearchClicked();  

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
