package org.isheep.entity.embedded;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by anthony on 08/11/16.
 */
@Embeddable
public class CreditCard {

    @NotEmpty
    private String name;

    @Size(min = 16, max = 16)
    private String number;

    @Pattern(regexp = "^[0-9]{3}$", message = "is supposed to be 3 digits")
    private String csc;

    @Min(1)
    @Max(12)
    private Integer monthExpire;

    @Min(999)
    private Integer yearExpire;

    public CreditCard(final String name, final String number, final String csc, final Integer monthExpire, final Integer yearExpire) {
        this.name = name;
        this.number = number;
        this.csc = csc;
        this.monthExpire = monthExpire;
        this.yearExpire = yearExpire;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    public String getCsc() {
        return csc;
    }

    public void setCsc(final String csc) {
        this.csc = csc;
    }

    public Integer getMonthExpire() {
        return monthExpire;
    }

    public void setMonthExpire(final Integer monthExpire) {
        this.monthExpire = monthExpire;
    }

    public Integer getYearExpire() {
        return yearExpire;
    }

    public void setYearExpire(final Integer yearExpire) {
        this.yearExpire = yearExpire;
    }
}
