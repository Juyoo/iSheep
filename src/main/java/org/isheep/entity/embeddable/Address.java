package org.isheep.entity.embeddable;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

/**
 * Created by anthony on 08/11/16.
 */
@Embeddable
public class Address {

    @Pattern(regexp = "^[0-9]+(\\s?[A-Za-z]+)?$", message = "Invalid street number")
    private String streetNumber;

    @NotEmpty
    private String street;

    @Pattern(regexp = "^[0-9]{5}$", message = "is supposed to be 5 digits")
    private String zip;

    @NotEmpty
    private String city;

    Address() {

    }

    public Address(final String streetNumber, final String street, final String zip, final String city) {
        this.streetNumber = streetNumber;
        this.street = street;
        this.zip = zip;
        this.city = city;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
