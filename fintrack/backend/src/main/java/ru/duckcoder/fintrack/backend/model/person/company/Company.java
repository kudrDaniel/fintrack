package ru.duckcoder.fintrack.backend.model.person.company;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.backend.model.person.Person;
import ru.duckcoder.fintrack.core.util.CompanyType;

@Entity
@Table(name = "companies")
@Getter
@Log4j2
public class Company extends Person {

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
