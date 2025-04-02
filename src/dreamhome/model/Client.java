package dreamhome.model;

public class Client {
    private int clientId;
    private String fullName;
    private String phone;
    private String email;
    private String preferredType;
    private float maxRent;
    private int staffId;
    private int branchId;

    // Getters and setters
    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPreferredType() { return preferredType; }
    public void setPreferredType(String preferredType) { this.preferredType = preferredType; }

    public float getMaxRent() { return maxRent; }
    public void setMaxRent(float maxRent) { this.maxRent = maxRent; }

    public int getStaffId() { return staffId; }
    public void setStaffId(int staffId) { this.staffId = staffId; }

    public int getBranchId() { return branchId; }
    public void setBranchId(int branchId) { this.branchId = branchId; }
}
