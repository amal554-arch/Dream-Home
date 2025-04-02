package dreamhome.model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class StaffViewModel {
    private final StringProperty fullName;
    private final StringProperty email;
    private final StringProperty role;
    private final StringProperty phone;
    private final ObjectProperty<LocalDate> dateJoined;
    private final DoubleProperty salary;

    public StaffViewModel(Staff staff) {
        this.fullName = new SimpleStringProperty(staff.getFullName());
        this.email = new SimpleStringProperty(staff.getEmail());
        this.role = new SimpleStringProperty(staff.getRole());
        this.phone = new SimpleStringProperty(staff.getPhone());
        this.dateJoined = new SimpleObjectProperty<>(staff.getDateJoined());
        this.salary = new SimpleDoubleProperty(staff.getSalary());
    }

    public StringProperty fullNameProperty() { return fullName; }
    public StringProperty emailProperty() { return email; }
    public StringProperty roleProperty() { return role; }
    public StringProperty phoneProperty() { return phone; }
    public ObjectProperty<LocalDate> dateJoinedProperty() { return dateJoined; }
    public DoubleProperty salaryProperty() { return salary; }
}
