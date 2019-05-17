package com.noahcharlton.stationalpha.worker;

import java.util.Random;

public class WorkerNameGenerator {

    private static final Random random = new Random();

    private static final String[] firstNames = new String[]{
        "Alexander", "John", "Ian", "Olivia", "Emily", "Jenna"
    };

    private static final String[] lastNames = new String[]{
            "Smith", "Johnson", "Kennedy", "Miller", "Jones", "Williams"
    };

    public static String generate() {
        return randomFromArray(firstNames) + " " + randomFromArray(lastNames);
    }

    static String randomFromArray(String[] list){
        return list[random.nextInt(list.length)];
    }
}
