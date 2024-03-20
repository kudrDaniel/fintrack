package ru.duckcoder.fintrack.model.person.company;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.model.person.Person;

@Entity
@Table(name = "companies")
@Getter
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
            return new StringBuilder()
                    .append("ordinal:").append(this.ordinal())
                    .append(",name:").append(this.name())
                    .append(",abbreviation:").append(this.abbreviation)
                    .append(",transcript:").append(this.transcript)
                    .toString();
        }
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "company_type",
            nullable = false)
    private CompanyType companyType;
    @Column(name = "company_name",
            nullable = false,
            length = 128)
    private String companyName;

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
        log.debug(new StringBuilder("Set companyType to:{").append(this.companyType).append('}'));
        this.createLabel();
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
        log.debug(new StringBuilder("Set companyName to:").append(this.companyName));
        this.createLabel();
    }

    @Override
    protected void createLabel() {
        if (this.companyType == null || this.companyName == null)
            return;
        this.label = new StringBuilder()
                .append(this.companyType.getAbbreviation())
                .append(' ').append(this.companyName)
                .toString();
        log.debug(new StringBuilder("Set label to:").append(this.label));
    }

    @Override
    public int hashCode() {
        return new StringBuilder()
                .append(super.hashCode())
                .append(this.companyType.hashCode())
                .append(this.companyName)
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        return (obj instanceof Company)
                && this.id == ((Company) obj).id
                && this.account.equals(((Company) obj).account)
                && this.label.equals(((Company) obj).label)
                && this.companyType.equals(((Company) obj).companyType)
                && this.companyName.equals(((Company) obj).companyName);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(super.toString())
                .append(",companyType:{").append(this.companyType).append('}')
                .append(",companyName:").append(this.companyName)
                .toString();
    }
}
