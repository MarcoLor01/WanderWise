package com.example.wanderwisep.enumeration;

public enum userRole {
    USER("User"),
    TOURISTGUIDE("TouristGuide");

    private final String roleName;

    userRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public static userRole fromString(String roleName) {
        for (userRole role : userRole.values()) {
            if (role.getRoleName().equalsIgnoreCase(roleName)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No enum constant for string: " + roleName);
    }
}

