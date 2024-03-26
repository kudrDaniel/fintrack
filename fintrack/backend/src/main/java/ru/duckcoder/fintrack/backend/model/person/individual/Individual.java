package ru.duckcoder.fintrack.backend.model.person.individual;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.backend.model.person.Person;

@Entity
@Table(name = "individuals")
@Getter
@Log4j2
public class Individual extends Person {
    @Column(name = "first_name",
            nullable = false,
            length = 64)
    private String firstName;
    @Column(name = "last_name",
            nullable = false,
            length = 64)
    private String lastName;
    @Column(name = "father_name",
            length = 128)
    private String fatherName;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        log.debug(new StringBuilder().append("Set firstName to:").append(this.firstName));
        this.createLabel();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        log.debug(new StringBuilder().append("Set lastName to:").append(this.lastName));
        this.createLabel();
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
        log.debug(new StringBuilder().append("Set fatherName to:").append(this.fatherName));
        this.createLabel();
    }

    @Override
    protected void createLabel() {
        if (this.firstName == null || this.lastName == null)
            return;
        this.label = new StringBuilder()
                .append(this.lastName)
                .append(" ").append(this.firstName.charAt(0)).append('.')
                /*.append("")*/.append(this.fatherName == null ? "" : this.fatherName.charAt(0)).append(this.fatherName == null ? "" : '.')
                .toString();
        log.debug(new StringBuilder().append("Set label to:").append(this.label));
    }

    @Override
    public int hashCode() {
        return new StringBuilder()
                .append(super.hashCode())
                .append(this.firstName)
                .append(this.lastName)
                .append(this.fatherName)
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        return (obj instanceof Individual)
                && this.id == ((Individual) obj).id
                && this.user.equals(((Individual) obj).user)
                && this.label.equals(((Individual) obj).label)
                && this.firstName.equals(((Individual) obj).firstName)
                && this.lastName.equals(((Individual) obj).lastName)
                && this.fatherName.equals(((Individual) obj).fatherName);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(super.toString())
                .append(",firstName:").append(this.firstName)
                .append(",lastName:").append(this.lastName)
                .append(",fatherName:").append(this.fatherName)
                .toString();
    }
}
