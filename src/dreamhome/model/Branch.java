package dreamhome.model;

public class Branch {
    private int branchID;
    private String address;
    private String phone;

    public Branch(int branchID, String address, String phone) {
        this.branchID = branchID;
        this.address = address;
        this.phone = phone;
    }

    public int getBranchID() {
        return branchID;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return address; // Display address in ComboBox
    }
}
