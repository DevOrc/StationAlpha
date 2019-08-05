package com.noahcharlton.stationalpha.world.save;

import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

public class SaveGameTests {

    protected final World world = new World();
    protected final SaveGame saveGame = new SaveGame(world);
    protected final StringWriter stringWriter = new StringWriter();

    @BeforeEach
    void setUp() {
        saveGame.setWriter(stringWriter);
    }

    @Test
    void writeMiscInfoBasicTest() {
        saveGame.writeMiscInfo();

        String actual = stringWriter.toString();

        Assertions.assertEquals("<SaveInfo GameVersion=\"0.2.0-prelease\"/>\n", actual);
    }
}
