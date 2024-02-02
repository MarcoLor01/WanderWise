package com.example.wanderwisep.enumeration;

public enum roleEnum {

    TOURISTGUIDE("TouristGuide"),
    USER("User");

    private final String roleName;

    roleEnum(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public static roleEnum fromString(String roleName) {
        for (roleEnum state : roleEnum.values()) {
            if (state.getRoleName().equalsIgnoreCase(roleName)) {
                return state;
            }
        }
        throw new IllegalArgumentException("No enum constant for string: " + roleName);
    }

    public static String toString(stateEnum state) {
        return state.getStateName();
    }
}
