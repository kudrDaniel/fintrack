package ru.duckcoder.fintrack.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@MappedSuperclass
@Getter
@Setter
@Log4j2
public abstract class AbstractEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    protected long id;

    @Override
    public int hashCode() {
        return String.valueOf(this.id).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        return (obj instanceof AbstractEntity)
                && this.id == ((AbstractEntity) obj).id;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("id:").append(this.id)
                .toString();
    }
}
