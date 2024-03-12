package ru.duckcoder.fintrack.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Entity
@Table(name = "Users")
@Getter
@Setter
@Log4j2
public class User extends BaseEntity {
    @Column(
            name = "username",
            unique = true,
            nullable = false)
    private String username;
    @Column(name = "password",
            nullable = false)
    private String password;

    public static UserBuilder builder() {
        log.debug("Instantiate user builder");
        return new UserBuilder();
    }

    public static class UserBuilder {
        private final User user;

        protected UserBuilder() {
            this.user = new User();
            log.debug("Instantiate empty user");
        }

        public UserBuilder username(String username) {
            this.user.setUsername(username);
            log.debug("Set username to: " + username);
            return this;
        }

        public UserBuilder password(String password) {
            this.user.setPassword(password);
            log.debug("Set password to: " + password);
            return this;
        }

        public User build() {
            log.debug(String.format(
                    "Build user:{username:%s, password: %s}",
                    this.user.getUsername(),
                    this.user.getPassword()
            ));
            return this.user;
        }
    }

    @Override
    public String toString() {
        return "{id:" + this.getId() +
                ", username:" + this.getUsername() +
                ", password:" + this.getPassword() + "}";
    }
}
