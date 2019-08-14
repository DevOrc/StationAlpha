package com.noahcharlton.stationalpha.worker;

import com.noahcharlton.stationalpha.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class WorkerNameGenerator {

    private static final Logger logger = LogManager.getLogger(WorkerNameGenerator.class);
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

    public static String generate(World world) {
        for(int i = 0; i < 100; i++){
            String name = randomFromArray(firstNames) + "  " + randomFromArray(lastNames);

            if(!isNameChosen(world, name)){
                return name;
            }
        }

        logger.warn("100 duplicate names chosen! Using john smith...");
        return "John Smith";
    }

    static boolean isNameChosen(World world, String name) {
        return world.getWorkers().stream().anyMatch(worker -> worker.getName().equals(name));
    }

    static String randomFromArray(String[] list) {
        return list[random.nextInt(list.length)];
    }
}
