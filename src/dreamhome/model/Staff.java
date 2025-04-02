package dreamhome.model;

import java.time.LocalDate;

public class Staff {
    private int staffId;
    private String fullName;
    private String email;
    private String passwordHash;
    private String role;
    private double salary;
    private String phone;
    private LocalDate dateJoined;
    private int branchId;

    // ✅ Constructor
    public Staff(int staffId, String fullName, String role, double salary, String email, String passwordHash, String phone, LocalDate dateJoined, int branchID) {
        this.staffId = staffId;
        this.fullName = fullName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.salary = salary;
        this.phone = phone;
        this.dateJoined = dateJoined;
        this.branchId = branchID;
    }

    // ✅ Getters
    public int getStaffId() {
        return staffId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getRole() {
        return role;
    }

    public double getSalary() {
        return salary;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDate getDateJoined() {
        return dateJoined;
    }

    public int getBranchId() {
        return branchId;
    }

    // (Optional) Setters if needed in future
}
