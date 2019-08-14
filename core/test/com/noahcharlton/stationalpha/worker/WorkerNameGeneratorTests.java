package com.noahcharlton.stationalpha.worker;

import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.opentest4j.AssertionFailedError;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class WorkerNameGeneratorTests {

    @Test
    void nameAlreadyChosenFalseTest() {
        World world = new World();
        world.getWorkers().add(new Worker("John Smith", world));

        Assertions.assertFalse(WorkerNameGenerator.isNameChosen(world, "Zebra"));
    }

    @Test
    void nameAlreadyChosenTrueTest() {
        World world = new World();
        world.getWorkers().add(new Worker("Elliot Gould", world));

        Assertions.assertTrue(WorkerNameGenerator.isNameChosen(world, "Elliot Gould"));
    }


    @ParameterizedTest
    @MethodSource("getNameSets")
    void baseNameTest(String[] names) {
        Set<String> set = new HashSet<>();
        for(String name: names){
            if(set.contains(name)){
                throw new AssertionFailedError("Duplicate Name: " + name);
            }

            set.add(name);
        }
    }

    static Stream<Arguments> getNameSets(){
        return Stream.of(Arguments.of((Object) WorkerNameGenerator.firstNames),
                Arguments.of((Object) WorkerNameGenerator.lastNames));
    }
}
