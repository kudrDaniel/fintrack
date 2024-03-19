package ru.duckcoder.fintrack.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Entity
@Table(name = "individuals")
@Getter
@Setter
@Log4j2
public class Individual extends Person {
    @Column(name = "first_name",
            nullable = false,
            length = 32)
    private String firstName;
    @Column(name = "last_name",
            nullable = false,
            length = 64)
    private String lastName;
    @Column(name = "father_name",
            length = 32)
    private String fatherName;

    public static IndividualBuilder builder() {
        IndividualBuilder builder = new IndividualBuilder();
        log.debug("Instantiate " + builder.getClass().getSimpleName());
        return builder;
    }

    public static class IndividualBuilder {
        private final Individual individual;

        protected IndividualBuilder() {
            this.individual = new Individual();
            log.debug("Instantiate empty " + this.individual.getClass().getSimpleName());
        }

        public IndividualBuilder account(Account account) {
            this.individual.setAccount(account);
            log.debug("Set account to:" + account);
            return this;
        }

        public IndividualBuilder fullName(String firstName, String lastName) {
            return this.fullName(firstName, lastName, null);
        }

        public IndividualBuilder fullName(String firstName, String lastName, String fatherName) {
            this.individual.setFirstName(firstName);
            log.debug("Set firstName to:" + firstName);
            this.individual.setLastName(lastName);
            log.debug("Set lastName to:" + lastName);
            this.individual.setFatherName(fatherName);
            log.debug("Set fatherName to:" + fatherName);
            return this;
        }

        public Individual build() throws NullPointerException {
            if (this.individual.getFirstName() != null
                    && this.individual.getLastName() != null) {
                String label = this.individual.createLabel();
                this.individual.setLabel(label);
                log.debug("Set label to:" + label);
                log.debug("Build individual with:" + this.individual);
                return this.individual;
            }
            throw new NullPointerException();
        }
    }

    @Override
    protected String createLabel() {
        String fatherNameDot = this.getFatherName() == null
                ? ""
                : this.getFatherName().charAt(0) + ".";
        return this.getLastName()
                + this.getFirstName().charAt(0) + "."
                + fatherNameDot;
    }

    @Override
    public String toString() {
        return "{"
                + super.toString()
                + ",firstName:" + this.getFirstName()
                + ",lastName:" + this.getLastName()
                + ",fatherName:" + this.getFatherName() + "}";
    }
}
