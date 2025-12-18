package com.naming.bot.domain;

public enum NamingType {
    VARIABLE("변수"),
    METHOD("메소드"),
    CLASS("클래스");

    private final String description;

    NamingType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}