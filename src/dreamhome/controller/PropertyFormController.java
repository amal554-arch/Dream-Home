package dreamhome.controller;

import dreamhome.dao.PropertyDAO;
import dreamhome.dao.PropertyImageDAO;
import dreamhome.model.Owner;
import dreamhome.model.Property;
import dreamhome.utils.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class PropertyFormController {

    @FXML private TextField addressField;
    @FXML private ComboBox<String> typeBox;
    @FXML private Spinner<Integer> roomsSpinner;
    @FXML private TextField rentField;
    @FXML private Label selectedOwnerLabel;
    @FXML private ListView<String> imageList;
    @FXML private Button uploadButton;
    @FXML private Label imageStatusLabel;
    @FXML private Label statusLabel;

    private Owner selectedOwner;
    private List<File> selectedImages = new ArrayList<>();

    @FXML
    public void initialize() {
        typeBox.getItems().addAll("House", "Apartment", "Studio", "Villa");
        typeBox.setPromptText("Select Property Type");

        roomsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));
    }

    @FXML
    private void onUploadImagesClicked() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Property Images");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg"));

        List<File> files = chooser.showOpenMultipleDialog(uploadButton.getScene().getWindow());
        if (files != null) {
            selectedImages.clear();
            imageList.getItems().clear();
            for (File f : files) {
                selectedImages.add(f);
                imageList.getItems().add(f.getName());
            }
            imageStatusLabel.setText(files.size() + " image(s) selected");
        } else {
            imageStatusLabel.setText("No images selected");
        }
    }

    @FXML
    private void onOwnerLookupClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("view/owner_lookup.fxml").toURI().toURL());
            Parent root = loader.load();

            OwnerLookupController controller = loader.getController();
            controller.setCallback(owner -> {
                selectedOwner = owner;
                selectedOwnerLabel.setText(owner.getFullName() + " (" + owner.getEmail() + ")");
            });

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Lookup Owner");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onRegisterPropertyClicked() {
        String address = addressField.getText().trim();
        String type = typeBox.getValue();
        int rooms = roomsSpinner.getValue();
        String rentText = rentField.getText().trim();

        if (address.isEmpty() || type == null || rentText.isEmpty() || selectedOwner == null) {
            statusLabel.setText("Please fill in all fields and select an owner.");
            return;
        }

        float rent;
        try {
            rent = Float.parseFloat(rentText);
        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid rent value.");
            return;
        }

        int branchId = Session.getLoggedInStaff().getBranchId();
        int staffId = Session.getLoggedInStaff().getStaffId();

        Property property = new Property(0, address, type, rooms, rent, true, branchId, selectedOwner.getOwnerId(), staffId);

        if (PropertyDAO.insertProperty(property)) {
            int propertyId = PropertyDAO.getPropertiesByBranch(branchId, true).stream()
                    .filter(p -> p.getAddress().equals(address))
                    .findFirst()
                    .map(Property::getPropertyId)
                    .orElse(-1);

            saveImages(propertyId);

            statusLabel.setStyle("-fx-text-fill: green;");
            statusLabel.setText("✅ Property registered successfully!");
            clearForm();
        } else {
            statusLabel.setStyle("-fx-text-fill: red;");
            statusLabel.setText("Failed to register property.");
        }
    }

    private void saveImages(int propertyId) {
        if (propertyId <= 0) return;
    
        String localDir = "images/";
        String webDir = "C:/xampp/htdocs/website/images/";
    
        new File(localDir).mkdirs(); // Ensure JavaFX image folder exists
        new File(webDir).mkdirs();   // Ensure website image folder exists
    
        for (File file : selectedImages) {
            String imageName = "property_" + propertyId + "_" + file.getName();
    
            Path localPath = Paths.get(localDir + imageName);
            Path webPath = Paths.get(webDir + imageName);
    
            try {
                // Copy to JavaFX folder
                Files.copy(file.toPath(), localPath, StandardCopyOption.REPLACE_EXISTING);
    
                // Copy to website folder
                Files.copy(file.toPath(), webPath, StandardCopyOption.REPLACE_EXISTING);
    
                // Save only the filename (not full path) in the DB
                PropertyImageDAO.insertImage(propertyId, imageName);
    
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }    

    private void clearForm() {
        addressField.clear();
        typeBox.setValue(null);
        roomsSpinner.getValueFactory().setValue(1);
        rentField.clear();
        selectedImages.clear();
        imageList.getItems().clear();
        selectedOwner = null;
        selectedOwnerLabel.setText("No owner selected");
        imageStatusLabel.setText("No images selected");
    }

    @FXML
    private void onCancelClicked() {
        try {
            Parent root = FXMLLoader.load(new File("view/dashboard.fxml").toURI().toURL());
            Stage stage = (Stage) addressField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
