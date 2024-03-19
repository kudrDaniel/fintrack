package ru.duckcoder.fintrack.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Entity
@Table(name = "companies")
@Getter
@Setter
@Log4j2
public class Company extends Person {
    @Getter
    public enum CompanyType {
        EMPTY("", ""),
        OOO("ООО", "Общество с ограниченной ответственностью"),
        OAO("ОАО", "Открытое акционерное общество"),
        ZAO("ЗАО", "Закрытое акционерное общество"),
        IP("ИП", "Индивидуальный предприниматель");

        private final String abbreviation;
        private final String transcript;

        CompanyType(String abbreviation, String transcript) {
            this.abbreviation = abbreviation;
            this.transcript = transcript;
        }

        @Override
        public String toString() {
            return this.name();
        }
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "company_type",
            nullable = false)
    private CompanyType companyType;
    @Column(name = "name",
            nullable = false,
            length = 128)
    private String name;

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

        public CompanyBuilder account(Account account) {
            this.company.setAccount(account);
            log.debug("Set account to:" + account);
            return this;
        }

        public CompanyBuilder companyType(CompanyType companyType) {
            this.company.setCompanyType(companyType);
            log.debug("Set companyType to:" + companyType);
            return this;
        }

        public CompanyBuilder name(String name) {
            this.company.setName(name);
            log.debug("Set name to:" + name);
            return this;
        }

        public Company build() throws NullPointerException {
            if (this.company.getCompanyType() != null
                    && this.company.getName() != null) {
                String label = this.company.createLabel();
                this.company.setLabel(label);
                log.debug("Set label to:" + label);
                log.debug("Build company with:" + this.company);
                return this.company;
            }
            throw new NullPointerException();
        }
    }

    @Override
    protected String createLabel() {
        return this.getCompanyType().abbreviation
                + " " + this.getName();
    }

    @Override
    public String toString() {
        String companyType = this.getCompanyType() == CompanyType.EMPTY
                ? ""
                : ",companyType:" + this.getCompanyType();
        return "{"
                + super.toString()
                + companyType
                + ",name:" + this.getName() + "}";
    }
}
