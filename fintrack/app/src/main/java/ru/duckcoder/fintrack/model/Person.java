package ru.duckcoder.fintrack.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Entity
@Table(name = "persons")
@Getter
@Setter
@Log4j2
public class Person extends BaseEntity {
    @ManyToOne(cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER)
    private Account account;
    @Column(name = "person_type",
            nullable = false,
            updatable = false,
            length = 16)
    private String personType;
    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Individual individual;
    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Company company;

    public String getName() {
        if (this.getPersonType().equals(Individual.class.getSimpleName()))
            return this.getIndividual().getFullName();
        else
            return this.getCompany().getShortName();
    }

    public static PersonBuilder builder() {
        log.debug("Instantiate " + PersonBuilder.class.getSimpleName());
        return new PersonBuilder();
    }

    public static class PersonBuilder {
        private final Person person;

        protected PersonBuilder() {
            this.person = new Person();
            log.debug("Instantiate empty " + Person.class.getSimpleName());
        }

        public PersonBuilder personType(boolean isIndividual) {
            this.person.setPersonType(isIndividual ? Individual.class.getSimpleName() : Company.class.getSimpleName());
            log.debug("Set personType to:" + (isIndividual ? Individual.class.getSimpleName() : Company.class.getSimpleName()));
            return this;
        }

        public PersonBuilder individual(Individual individual) {
            if (this.person.getPersonType() != null
                    && this.person.getPersonType().equals(Individual.class.getSimpleName())
                    && this.person.getCompany() == null) {
                individual.setPerson(this.person);
                this.person.setIndividual(individual);
                log.debug("Set individual to:" + individual);
            } else if (this.person.getPersonType() == null)
                log.debug("Can not set individual cause person type is null");
            else if (!this.person.getPersonType().equals(Company.class.getSimpleName()))
                log.debug("Can not set individual cause person type is:" + Company.class.getSimpleName());
            else
                log.debug("Can not set individual cause company is set");
            return this;
        }

        public PersonBuilder company(Company company) {
            if (this.person.getPersonType() != null
                    && this.person.getPersonType().equals(Company.class.getSimpleName())
                    && this.person.getIndividual() == null) {
                company.setPerson(this.person);
                this.person.setCompany(company);
                log.debug("Set company to:" + company);
            } else if (this.person.getPersonType() == null)
                log.debug("Can not set company cause person type is null");
            else if (!this.person.getPersonType().equals(Company.class.getSimpleName()))
                log.debug("Can not set company cause person type is:" + Individual.class.getSimpleName());
            else
                log.debug("Can not set company cause individual is set");
            return this;
        }

        public Person build() throws NullPointerException {
            if (this.person.getPersonType() != null
                    && (this.person.getIndividual() != null
                    || this.person.getCompany() != null)) {
                log.debug("Build person with:{" +
                        this.person.personByTypeToString() + "}");
                return this.person;
            }
            throw new NullPointerException();
        }
    }

    public String personByTypeToString() {
        if (this.getPersonType() == null)
            throw new NullPointerException();
        return this.getPersonType().equals(Individual.class.getSimpleName())
                ? "individual:" + this.getIndividual()
                : "company:" + this.getCompany();
    }

    @Override
    public String toString() {
        String accountString = this.getAccount() == null
                ? "null"
                : "{id:" + this.getAccount().getId() + "}";
        return "{id:" + this.getId()
                + ",account:" + accountString
                + "," + this.personByTypeToString() + "}";
    }
}
