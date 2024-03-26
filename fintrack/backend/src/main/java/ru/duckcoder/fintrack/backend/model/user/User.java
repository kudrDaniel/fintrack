package ru.duckcoder.fintrack.backend.model.user;

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
import ru.duckcoder.fintrack.backend.model.AbstractEntity;
import ru.duckcoder.fintrack.backend.model.person.Person;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@Log4j2
public class User extends AbstractEntity {
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
            mappedBy = "user")
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
        return (obj instanceof User)
                && this.id == ((User) obj).id
                && this.username.equals(((User) obj).username)
                && this.password.equals(((User) obj).password);
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
