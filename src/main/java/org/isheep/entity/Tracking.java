package org.isheep.entity;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.validator.constraints.NotEmpty;
import org.isheep.config.javax.validation.groups.JPAValidationGroup;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Juyo on 07/12/2016.
 *
 */
@Entity
public class Tracking {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(groups = JPAValidationGroup.class)
    private Date eventDate;

    @NotEmpty
    private String event;

    public Tracking(){}

    public Tracking(final Date eventDate, final String event){
        this.eventDate = eventDate;
        this.event = event;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Date getEventDate() {
        return eventDate;
    }
    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEvent() {
        return event;
    }
    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tracking tracking = (Tracking) o;
        return Objects.equal(eventDate, tracking.eventDate) &&
                Objects.equal(event, tracking.event);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(eventDate, event);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("eventDate", eventDate)
                .add("event", event)
                .toString();
    }
}
