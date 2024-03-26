package ru.duckcoder.fintrack.backend.model.person;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.backend.model.AbstractEntity;
import ru.duckcoder.fintrack.backend.model.user.User;

@Entity
@Table(name = "persons")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@Log4j2
public abstract class Person extends AbstractEntity {
    @ManyToOne(cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    protected User user;
    @Column(name = "label",
            nullable = false,
            length = 128)
    protected String label;

    protected abstract void createLabel();

    @Override
    public int hashCode() {
        return new StringBuilder()
                .append(super.toString())
                .append(this.user == null ? "null" : this.user.hashCode())
                .append(this.label)
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        return (obj instanceof Person)
                && this.id == ((Person) obj).id
                && this.user.equals(((Person) obj).user)
                && this.label.equals(((Person) obj).label);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(super.toString())
                .append(",user:{").append(this.user).append('}')
                .append(",label:").append(this.label)
                .toString();
    }
}
