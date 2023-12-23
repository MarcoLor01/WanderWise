package com.example.wanderwisep.enumeration;

public enum stateEnum {

    WAITING("Waiting for confirmation"),
    CONFIRMED("Confirmed"),
    CANCELED("Cancelled");
    private final String stateName;
    stateEnum(String stateName) {
        this.stateName = stateName;
    }

    public String getStateName() {
        return stateName;
    }

    public static stateEnum fromString(String stateName) {
        for (stateEnum state : stateEnum.values()) {
            if (state.getStateName().equalsIgnoreCase(stateName)) {
                return state;
            }
        }
        throw new IllegalArgumentException("No enum constant for string: " + stateName);
    }
}

