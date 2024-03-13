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
@Table(name = "individuals")
@Getter
@Setter
@Log4j2
public class Individual extends BaseEntity {
    @Column(name = "full_name",
            nullable = false,
            length = 128)
    private String fullName;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE},
            fetch = FetchType.EAGER,
            optional = false,
            mappedBy = "individual")
    private Person person;

    public static IndividualBuilder builder() {
        log.debug("Instantiate " + IndividualBuilder.class.getSimpleName());
        return new IndividualBuilder();
    }

    public static class IndividualBuilder {
        private final Individual individual;

        protected IndividualBuilder() {
            this.individual = new Individual();
            log.debug("Instantiate empty " + Individual.class.getSimpleName());
        }

        public IndividualBuilder fullName(String fullName) {
            this.individual.setFullName(fullName);
            log.debug("Set fullName to:" + fullName);
            return this;
        }

        public Individual build() throws NullPointerException {
            if (this.individual.getFullName() != null) {
                log.debug("Build individual with:{"
                        + "fullName:" + this.individual.getFullName() + "}");
                return this.individual;
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
                + ",fullName:" + this.getFullName()
                + ",person:" + personString + "}";
    }
}
