package com.noahcharlton.stationalpha.science;

import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.world.World;
import com.noahcharlton.stationalpha.world.load.LoadTestUtils;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.io.StringWriter;

public class GoalManagerTests {

    private final GoalManager goalManager = new GoalManager(new World());

    @ParameterizedTest
    @EnumSource(GoalTab.class)
    void goalManagerHasTabTest(GoalTab tab) {
        Assertions.assertTrue(goalManager.getTabs().contains(tab));
    }

    @Test
    void updatedBasicTest() {
        TestGoal testGoal = new TestGoal();

        goalManager.updateGoal(testGoal);

        Assertions.assertTrue(testGoal.isUpdated());
    }

    @Test
    void completedDoesNotUpdateTest() {
        TestGoal goal = new TestGoal();
        goal.setCompleted(true);

        goalManager.updateGoal(goal);

        Assertions.assertFalse(goal.isUpdated());
    }

    @Test
    void requirementsNotCompleteDoesNotUpdateTest() {
        TestGoal goal = new TestGoal();
        goal.addRequirement(new TestGoal());

        goalManager.updateGoal(goal);

        Assertions.assertFalse(goal.isUpdated());
    }

    @Test
    void saveGoalBasicTest() {
        StringWriter writer = new StringWriter();
        TestGoal goal = new TestGoal();
        goal.setName("Zebra");
        goal.setCompleted(true);

        goalManager.saveGoal(new QuietXmlWriter(writer), goal);

        String expected = "<Goal name=\"Zebra\" completed=\"true\"/>\n";
        Assertions.assertEquals(expected, writer.toString());
    }

    @Test
    void earnScienceTest() {
        goalManager.earnSciencePoints(25);

        Assertions.assertEquals(25, goalManager.getSciencePoints());
    }

    @Test
    void earnScienceNotBelowZeroTest() {
        goalManager.earnSciencePoints(-5);

        Assertions.assertEquals(0, goalManager.getSciencePoints());
    }

    @Test
    void removeScienceTest() {
        goalManager.earnSciencePoints(22);
        goalManager.removeSciencePoints(10);

        Assertions.assertEquals(12, goalManager.getSciencePoints());
    }

    @Test
    void removeScienceNotBelowZeroTest() {
        goalManager.removeSciencePoints(7);

        Assertions.assertEquals(0, goalManager.getSciencePoints());
    }

    @Test
    void saveScienceTest() {
        StringWriter writer = new StringWriter();
        QuietXmlWriter xmlWriter = new QuietXmlWriter(writer);

        goalManager.earnSciencePoints(22);
        goalManager.saveScience(xmlWriter);

        Assertions.assertEquals("<Science value=\"22\"/>\n", writer.toString());
    }

    @Test
    void loadScienceTest() {
        String input = "<Science value=\"129\"/>\n";
        XmlReader.Element element = LoadTestUtils.asChild(input);

        goalManager.loadScience(element);

        Assertions.assertEquals(129, goalManager.getSciencePoints());
    }
}
class TestGoal extends Goal{

    private boolean updated = false;

    @Override
    public void update(World world) {
        updated = true;
    }

    public boolean isUpdated() {
        return updated;
    }
}