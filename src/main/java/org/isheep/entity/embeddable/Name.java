package org.isheep.entity.embeddable;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
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

    public Name(final String firstname, final String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Name name = (Name) o;
        return Objects.equal(firstname, name.firstname) &&
                Objects.equal(lastname, name.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(firstname, lastname);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("firstname", firstname)
                .add("lastname", lastname)
                .toString();
    }
}
