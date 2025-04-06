package dreamhome.controller;

import dreamhome.dao.PropertyDAO;
import dreamhome.model.Property;
import dreamhome.model.Staff;
import dreamhome.utils.Session;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PropertyListController {

    @FXML private TableView<Property> propertyTable;
    @FXML private TableColumn<Property, String> addressCol;
    @FXML private TableColumn<Property, String> typeCol;
    @FXML private TableColumn<Property, Integer> roomsCol;
    @FXML private TableColumn<Property, Float> rentCol;
    @FXML private TableColumn<Property, Boolean> availableCol;

    @FXML
    public void initialize() {
        addressCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAddress()));
        typeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getType()));
        roomsCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getRooms()).asObject());
        rentCol.setCellValueFactory(data -> new SimpleFloatProperty(data.getValue().getRent()).asObject());
        availableCol.setCellValueFactory(data -> new SimpleBooleanProperty(data.getValue().isAvailable()).asObject());

        // Load properties for the current staff
        loadPropertiesForUser(Session.getLoggedInStaff());
    }

    public void loadPropertiesForUser(Staff staff) {
        List<Property> properties;

        if (staff.getRole().equalsIgnoreCase("Manager")) {
            properties = PropertyDAO.getAllProperties(); // Fetch all
        } else {
            properties = PropertyDAO.getPropertiesByBranch(staff.getBranchId(), true); // Only available in branch
        }

        propertyTable.getItems().setAll(properties);
    }

    @FXML
    private void onBackClicked() {
        try {
            Parent root = FXMLLoader.load(new File("view/dashboard.fxml").toURI().toURL());
            Stage stage = (Stage) propertyTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
