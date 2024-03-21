package ru.duckcoder.fintrack.core.util;

import lombok.Getter;

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
