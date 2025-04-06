package dreamhome.model;

import javafx.beans.property.*;

public class ClientViewModel {
    private final StringProperty fullName;
    private final StringProperty phone;
    private final StringProperty email;
    private final StringProperty preferredType;
    private final IntegerProperty maxRent;

    public ClientViewModel(String fullName, String phone, String email, String preferredType, int maxRent) {
        this.fullName = new SimpleStringProperty(fullName);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
        this.preferredType = new SimpleStringProperty(preferredType);
        this.maxRent = new SimpleIntegerProperty(maxRent);
    }

    public StringProperty fullNameProperty() { return fullName; }
    public StringProperty phoneProperty() { return phone; }
    public StringProperty emailProperty() { return email; }
    public StringProperty preferredTypeProperty() { return preferredType; }
    public IntegerProperty maxRentProperty() { return maxRent; }

    public String getFullName() { return fullName.get(); }
    public String getPhone() { return phone.get(); }
    public String getEmail() { return email.get(); }
    public String getPreferredType() { return preferredType.get(); }
    public int getMaxRent() { return maxRent.get(); }
}
 