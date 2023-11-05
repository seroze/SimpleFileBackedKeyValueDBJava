package org.example;

import java.io.Serializable;

/**
 * Entry object that will be saved to file
 */
public class Entry implements Serializable {

    public String key;
    public String value;
    public EntryType entryType;

    /**
     * CTOR
     * @param key
     * @param value
     * @param entryType
     */
    Entry(String key, String value, EntryType entryType){
        this.key       = key;
        this.value     = value;
        this.entryType = entryType;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", type='" + entryType + '\'' +
                '}';
    }

}
