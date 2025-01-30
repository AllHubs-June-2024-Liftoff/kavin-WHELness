package org.launchcode.PlatePlanner.model;

public enum Role {
    USER("User"),
    ADMIN("Administrator");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
