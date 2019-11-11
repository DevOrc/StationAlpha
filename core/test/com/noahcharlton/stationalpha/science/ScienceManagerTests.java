package com.noahcharlton.stationalpha.science;

import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.world.World;
import com.noahcharlton.stationalpha.world.load.LoadTestUtils;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

public class ScienceManagerTests {

    private final World world = new World();
    private final ScienceManager scienceManager = new ScienceManager(world);

    @Test
    void earnScienceTest() {
        scienceManager.earnSciencePoints(25);

        Assertions.assertEquals(25, scienceManager.getSciencePoints());
    }

    @Test
    void earnScienceNotBelowZeroTest() {
        scienceManager.earnSciencePoints(-5);

        Assertions.assertEquals(0, scienceManager.getSciencePoints());
    }

    @Test
    void removeScienceTest() {
        scienceManager.earnSciencePoints(22);
        scienceManager.removeSciencePoints(10);

        Assertions.assertEquals(12, scienceManager.getSciencePoints());
    }

    @Test
    void removeScienceNotBelowZeroTest() {
        scienceManager.removeSciencePoints(7);

        Assertions.assertEquals(0, scienceManager.getSciencePoints());
    }

    @Test
    void saveScienceTest() {
        StringWriter writer = new StringWriter();
        QuietXmlWriter xmlWriter = new QuietXmlWriter(writer);

        scienceManager.earnSciencePoints(22);
        scienceManager.saveScience(xmlWriter);

        Assertions.assertEquals("<Science value=\"22\"/>\n", writer.toString());
    }

    @Test
    void loadScienceTest() {
        String input = "<Science value=\"129\"/>\n";
        XmlReader.Element element = LoadTestUtils.asChild(input);

        scienceManager.loadScience(element);

        Assertions.assertEquals(129, scienceManager.getSciencePoints());
    }
}