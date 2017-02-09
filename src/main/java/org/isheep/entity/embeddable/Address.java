package org.isheep.entity.embeddable;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
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

    public void setStreetNumber(final String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(final String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }
    
    public String asStringAddress() {
        return this.getStreetNumber() + " " + this.getStreet() + " " + this.getZip() + " " + this.getCity();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Address address = (Address) o;
        return Objects.equal(streetNumber, address.streetNumber) &&
                Objects.equal(street, address.street) &&
                Objects.equal(zip, address.zip) &&
                Objects.equal(city, address.city);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(streetNumber, street, zip, city);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("streetNumber", streetNumber)
                .add("street", street)
                .add("zip", zip)
                .add("city", city)
                .toString();
    }
}
