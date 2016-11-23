package org.isheep.entity.embeddable;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Embeddable;

/**
 * Created by anthony on 08/11/16.
 */
@Embeddable
public class Name {

    @NotEmpty
    private String firstname;

    @NotEmpty
    private String lastname;

    Name() {
    }

    public Name(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
