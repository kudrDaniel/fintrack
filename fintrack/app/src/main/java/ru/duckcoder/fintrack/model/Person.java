package ru.duckcoder.fintrack.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Entity
@Table(name = "persons")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@Log4j2
public abstract class Person extends BaseEntity {
    @ManyToOne(cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER)
    private Account account;
    private String label;

    abstract String createLabel();

    @Override
    public String toString() {
        String accountString = this.getAccount() == null
                ? "null"
                : "{id:" + this.getAccount().getId() + "}";
        return "id:" + this.getId()
                + ",account:" + accountString
                + ",label:" + this.getLabel();
    }
}
