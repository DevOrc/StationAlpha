package com.noahcharlton.stationalpha.world.save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlWriter;
import com.noahcharlton.stationalpha.StationAlpha;
import com.noahcharlton.stationalpha.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Writer;

public class SaveGame {

    private final Logger logger = LogManager.getLogger(SaveGame.class);
    private final World world;

    private QuietXmlWriter writer;

    SaveGame(World world) {
        this.world = world;
    }

    private void save(FileHandle file) {
        logger.info("Saving to: " + file.file().getPath());
        writer = new QuietXmlWriter(file).element("SaveGame");

        save();
        writer.pop();
        writer.flush();
    }

    private void save() {
        writeMiscInfo();
        world.getGoalManager().save(writer);
        new WorkerSaver(world).save(writer);
        new InventorySaver(world).save(writer);
        new ManufacturingManagerSaver(world.getManufacturingManager()).save(writer);
        new WorldSaver(world).save(writer);
    }

    void writeMiscInfo() {
        writer.element("SaveInfo")
                .attribute("GameVersion", StationAlpha.VERSION)
                .pop();
    }

    void setWriter(Writer writer) {
        this.writer = new QuietXmlWriter(new XmlWriter(writer));
    }

    public static void create(World world, int saveNumber) {
        new SaveGame(world).save(Gdx.files.external("/station-alpha/save" + saveNumber + ".xml"));
    }
}
