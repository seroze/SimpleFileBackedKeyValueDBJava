package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.HashMap;

/**
 * Bitcask db implementation in java
 * What will this class do ?
 *  - expose GET, SET, DEL methods on k,v which are made up of strings
 *  - persistance is achieved by dumping everything into file
 *      - we log all the commands into file
 *
 *
 */
// TODO: Add load method
public class SimpleKV {

    private File dbFile ;
    private HashMap<String, String> store;

    final String SEP = ":";

    ObjectMapper objectMapper;
    PrintStream printStream;
    RandomAccessFile randomAccessFile;

    // offset indicates how many chars we have filled in the file at the moment
    long offset;

    /**
     * CTOR
     */
    SimpleKV(String path) throws IOException {
        this.dbFile = new File(path);
        if (!this.dbFile.exists()) {
            this.dbFile.createNewFile();
        }
        store  = new HashMap<>();
        objectMapper = new ObjectMapper();
        // could have used ObjectOutputStream
        printStream = new PrintStream(new FileOutputStream(dbFile, true));
        this.randomAccessFile = new RandomAccessFile(this.dbFile, "r");
    }

    public void set(String key, String value) throws IOException {
        Entry entry = new Entry(key, value, EntryType.SET);

        String json = this.objectMapper.writeValueAsString(entry);

        String offset_and_len = this.offset + SEP + json.length();

        // offset of value is stored in memory
        this.store.put(key, offset_and_len);
        this.offset += json.length();
        // flush to the db file
        this.printStream.print(json);
    }

    public Entry get(String key) throws IOException {
        if (this.store.containsKey(key)){
            String[] offset_and_length = this.store.get(key).split(SEP);

            int offset = Integer.parseInt(offset_and_length[0]);
            int length = Integer.parseInt(offset_and_length[1]);

            // read from file
            randomAccessFile.seek(offset);

            byte[] entry_bytearray = new byte[length];
            randomAccessFile.read(entry_bytearray, offset, length);

            String json = new String(entry_bytearray);
            return objectMapper.readValue(json, Entry.class);
        } else {
            return null;
        }
    }

    public void delete(String key) throws IOException {
        if (this.store.containsKey(key)) {

            Entry entry = new Entry(key, null, EntryType.DEL);

            String json = this.objectMapper.writeValueAsString(entry);

            String offset_length = this.offset+","+json.length();
            this.store.put(key, offset_length);
            this.offset += json.length();

            this.printStream.print(json);
        }
    }

}
