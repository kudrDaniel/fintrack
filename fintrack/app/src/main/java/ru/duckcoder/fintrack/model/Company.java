package ru.duckcoder.fintrack.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Entity
@Table(name = "companies")
@Getter
@Setter
@Log4j2
public class Company extends BaseEntity {
    @Column(name = "short_name",
            nullable = false,
            length = 128)
    private String shortName;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE},
            fetch = FetchType.EAGER,
            optional = false,
            mappedBy = "company")
    private Person person;

    public static CompanyBuilder builder() {
        log.debug("Instantiate " + CompanyBuilder.class.getSimpleName());
        return new CompanyBuilder();
    }

    public static class CompanyBuilder {
        private final Company company;

        protected CompanyBuilder() {
            this.company = new Company();
            log.debug("Instantiate empty " + Company.class.getSimpleName());
        }

        public CompanyBuilder shortName(String shortName) {
            this.company.setShortName(shortName);
            log.debug("Set shortName to:" + shortName);
            return this;
        }

        public Company build() throws NullPointerException {
            if (this.company.getShortName() != null) {
                log.debug("Build company with:{"
                        + "shortName:" + this.company.getShortName() + "}");
                return this.company;
            }
            throw new NullPointerException();
        }
    }

    @Override
    public String toString() {
        String personString = this.getPerson() == null
                ? "null"
                : "{id:" + this.getPerson().getId() + "}";
        return "{id:" + this.getId()
                + ",shortName:" + this.getShortName()
                + ",person:" + personString + "}";
    }
}
