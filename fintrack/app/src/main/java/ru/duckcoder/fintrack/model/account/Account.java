package ru.duckcoder.fintrack.model.account;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import ru.duckcoder.fintrack.model.AbstractEntity;
import ru.duckcoder.fintrack.model.person.Person;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@Log4j2
public class Account extends AbstractEntity {
    @Column(name = "username",
            unique = true,
            nullable = false,
            length = 32)
    @Size(min = 3)
    private String username;
    @Column(name = "password",
            nullable = false,
            length = 2048)
    @NotEmpty
    private String password;
    @OneToMany(cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER,
            mappedBy = "account")
    private List<Person> persons = new ArrayList<>();

    @Override
    public int hashCode() {
        return new StringBuilder()
                .append(super.hashCode())
                .append(this.username)
                .append(this.password)
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        return (obj instanceof Account)
                && this.id == ((Account) obj).id
                && this.username.equals(((Account) obj).username)
                && this.password.equals(((Account) obj).password);
    }

    @Override
    public String toString() {
        // Possible security vulnerability. Role verification needs to be added!
        return new StringBuilder()
                .append(super.toString())
                .append(",username:").append(this.username)
                .append(",password:").append(this.password)
                .toString();
    }
}
