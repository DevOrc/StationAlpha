package com.noahcharlton.stationalpha.worker;

import java.util.Random;

public class WorkerNameGenerator {

    private static final Random random = new Random();

    private static final String[] firstNames = new String[]{
        "Alex", "John", "Ian", "Olivia", "Emily", "Jenna", "Noah", "Kyle", "Ava", "Mia", "Emma"
    };

    private static final String[] lastNames = new String[]{
            "Smith", "Hall", "Kennedy", "Miller", "Jones", "Wood", "Ross", "Bell", "Cook"
    };

    public static String generate() {
        return randomFromArray(firstNames) + "  " + randomFromArray(lastNames);
    }

    static String randomFromArray(String[] list){
        return list[random.nextInt(list.length)];
    }
}
