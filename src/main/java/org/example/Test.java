package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {

        SimpleKV simpleKV = new SimpleKV("test.1.db");

        long startTime = System.currentTimeMillis();
        final int N = 1_000_000;
        for (int i = 0 ;i<N;++i){
            if (Math.random() <0.5 ) {
                simpleKV.set(String.valueOf(Math.random()*0x7fffffff), "abcdefgh");
            }
            if (Math.random() < 0.5) {
                simpleKV.get(String.valueOf(Math.random()* 0x7fffffff));
            }
            if (Math.random() < 0.5) {
                simpleKV.delete(String.valueOf(Math.random() * 0x7ffffff));
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("SimpleKv time run "+ (endTime - startTime) + "ms");
    }
}
