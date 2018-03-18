
public class Phone {

    public String model;
    private String identifier;
    private String imeiCode;
    private double screenWidth;
    private double screenHeight;
    protected String factoryCountry;

    //methods

    public double displaySize() {
        return Math.sqrt(Math.pow(screenHeight, 2) + Math.pow(screenWidth, 2));
    }

    //constructor

    public Phone() {
    }

    public Phone(String model, String imeiCode, String factoryCountry, double screenHeight, double screenWidth) {
        this.identifier = this.toString();

        this.model = model;
        this.imeiCode = imeiCode;
        this.factoryCountry = factoryCountry;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
    }

    //get set

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getImeiCode() {
        return imeiCode;
    }

    public void setImeiCode(String imeiCode) {
        this.imeiCode = imeiCode;
    }

    public String getFactoryCountry() {
        return factoryCountry;
    }

    public void setFactoryCountry(String factoryCountry) {
        this.factoryCountry = factoryCountry;
    }


    //to string

    @Override
    public String toString() {
        return super.toString();
    }
}
