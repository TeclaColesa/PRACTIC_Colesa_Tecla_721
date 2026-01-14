package org.example.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum EventType {
    SPEEDING,
    RED_LIGHT,
    ACCIDENT,
    PRIORITY_PASS;
    @JsonCreator
    public static EventType fromString(String value) {
        return EventType.valueOf(value.toUpperCase());
    }

}
