package epam.gym.entity;

public class Address {

    private String city;
    private String street;
    private int buildingNumber;
    private int apartmentNumber;

    public Address(String city, String street, int buildingNumber, int apartmentNumber){
        this.city = city;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;

    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", buildingNumber=" + buildingNumber +
                ", apartmentNumber=" + apartmentNumber +
                '}';
    }
}
