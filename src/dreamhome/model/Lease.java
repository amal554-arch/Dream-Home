package dreamhome.model;

public class Lease {
    private int leaseId;
    private int propertyId;
    private int clientId;
    private int staffId;
    private String startDate;
    private String endDate;
    private String status;
    private double depositAmount;
    private String paymentFrequency;
    private String paymentMethod;

    public Lease(int leaseId, int propertyId, int clientId, int staffId,
                 String startDate, String endDate, String status,
                 double depositAmount, String paymentFrequency, String paymentMethod) {
        this.leaseId = leaseId;
        this.propertyId = propertyId;
        this.clientId = clientId;
        this.staffId = staffId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.depositAmount = depositAmount;
        this.paymentFrequency = paymentFrequency;
        this.paymentMethod = paymentMethod;
    }

    public int getLeaseId() {
        return leaseId;
    }    

    public void setLeaseId(int leaseId) {
        this.leaseId = leaseId;
    }
    
    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getPaymentFrequency() {
        return paymentFrequency;
    }

    public void setPaymentFrequency(String paymentFrequency) {
        this.paymentFrequency = paymentFrequency;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
