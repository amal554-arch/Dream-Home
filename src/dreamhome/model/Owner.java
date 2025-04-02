package dreamhome.model;

public class Owner {
    private int ownerId;
    private String fullName;
    private String phone;
    private String email;
    private String address;

    public Owner(int ownerId, String fullName, String phone, String email, String address) {
        this.ownerId = ownerId;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return fullName + " (" + email + ")";
    }
}
