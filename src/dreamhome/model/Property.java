package dreamhome.model;

public class Property {
    private int propertyId;
    private String address;
    private String type;
    private int rooms;
    private float rent;
    private boolean isAvailable;
    private int branchId;
    private int ownerId;
    private int staffId;

    public Property(int propertyId, String address, String type, int rooms, float rent,
                    boolean isAvailable, int branchId, int ownerId, int staffId) {
        this.propertyId = propertyId;
        this.address = address;
        this.type = type;
        this.rooms = rooms;
        this.rent = rent;
        this.isAvailable = isAvailable;
        this.branchId = branchId;
        this.ownerId = ownerId;
        this.staffId = staffId;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public int getRooms() {
        return rooms;
    }

    public float getRent() {
        return rent;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public int getBranchId() {
        return branchId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getStaffId() {
        return staffId;
    }
}
