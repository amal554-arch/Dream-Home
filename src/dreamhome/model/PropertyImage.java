package dreamhome.model;

public class PropertyImage {
    private int imageId;
    private int propertyId;
    private String imagePath;

    public PropertyImage(int imageId, int propertyId, String imagePath) {
        this.imageId = imageId;
        this.propertyId = propertyId;
        this.imagePath = imagePath;
    }

    public int getImageId() {
        return imageId;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public String getImagePath() {
        return imagePath;
    }
}
