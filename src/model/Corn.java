package model;

public class Corn {

    private double dateCreated;
    private double repairAmount;

    public Corn(double dateCreated) {
        this.dateCreated = dateCreated;
        this.repairAmount = 0;
    }

    public double getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(double dateCreated) {
        this.dateCreated = dateCreated;
    }

    public double getRepairAmount() {
        return repairAmount;
    }

    public void setRepairAmount(double repairAmount) {
        this.repairAmount = repairAmount;
    }
}
