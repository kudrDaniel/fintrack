package ru.duckcoder.fintrack.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@Log4j2
public class Account extends BaseEntity {
    @Column(
            name = "username",
            unique = true,
            nullable = false,
            length = 64)
    @Size(min = 3)
    private String username;
    @Column(name = "password",
            nullable = false,
            length = 2048)
    private String password;
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "account")
    private List<Person> persons = new ArrayList<>();



    public static AccountBuilder builder() {
        log.debug("Instantiate " + AccountBuilder.class.getSimpleName());
        return new AccountBuilder();
    }

    public static class AccountBuilder {
        private final Account account;

        protected AccountBuilder() {
            this.account = new Account();
            log.debug("Instantiate empty " + Account.class.getSimpleName());
        }

        public AccountBuilder username(String username) {
            this.account.setUsername(username);
            log.debug("Set username to:" + username);
            return this;
        }

        public AccountBuilder password(String password) {
            this.account.setPassword(password);
            log.debug("Set password to:" + password);
            return this;
        }

        public AccountBuilder persons(Person... persons) {
            for (Person person : persons) {
                person.setAccount(this.account);
                this.account.getPersons().add(person);
                log.debug("Add person with:" + person);
            }
            return this;
        }

        public Account build() throws NullPointerException {
            if (this.account.getUsername() != null
                    && this.account.getPassword() != null
                    && !this.account.getPersons().isEmpty()) {
                log.debug("Build account with:{"
                        + "username:" + this.account.getUsername()
                        + ",password:" + this.account.getPassword()
                        + ",persons:[" + this.account.personsToString() + "]}");
                return this.account;
            }
            throw new NullPointerException();
        }
    }

    public String personsToString() {
        return this.getPersons().stream()
                .map(Person::toString)
                .collect(Collectors.joining(","));
    }

    @Override
    public String toString() {
        return "{id:" + this.getId()
                + ",username:" + this.getUsername()
                + ",password:" + this.getPassword()
                + ",persons:[" + this.personsToString() + "]}";
    }
}
