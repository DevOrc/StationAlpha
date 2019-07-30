package com.noahcharlton.stationalpha.worker;

import java.util.Random;

public class WorkerNameGenerator {

    private static final Random random = new Random();

    static final String[] firstNames = new String[]{
            "Alex", "John", "Ian", "Olivia", "Emily", "Jenna", "Noah", "Kyle", "Ava", "Mia", "Emma",
            "Michael", "Daniel", "Paul", "Mark", "Eric", "Frank", "Steve", "Peter", "Ben", "Roy",
            "Mary", "Linda", "Marie", "Maria", "Nancy", "Karen", "Amy", "Anna", "Diane", "Frances"
    };

    static final String[] lastNames = new String[]{
            "Smith", "Hall", "Kennedy", "Miller", "Jones", "Wood", "Ross", "Bell", "Cook",
            "Butler", "Baker", "Carter", "Ward", "White", "Gray", "Clark", "Taylor", "Lee", "Lewis"
    };

    public static String generate() {
        return randomFromArray(firstNames) + "  " + randomFromArray(lastNames);
    }

    static String randomFromArray(String[] list) {
        return list[random.nextInt(list.length)];
    }
}
