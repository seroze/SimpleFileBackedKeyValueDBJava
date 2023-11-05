package org.example;

import java.io.Serializable;

public enum EntryType implements Serializable {
    GET,
    SET,
    DEL;

    @Override
    public String toString() {
        return "EntryType{" + "SET"+ "}";
//        return "Entry{" +
//                "key='" + key + '\'' +
//                ", value='" + value + '\'' +
//                ", type='" + entryType + '\'' +
//                '}';
    }
}
