package com.noahcharlton.stationalpha.engine.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.StationAlpha;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public final class Settings {

    private static final Logger logger = LogManager.getLogger(Settings.class);

    public static final Setting fullscreen = new BooleanSetting("Fullscreen", false,
            fullscreen -> {
                if(fullscreen) {
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                } else {
                    Gdx.graphics.setWindowedMode(StationAlpha.DEFAULT_WIDTH, StationAlpha.DEFAULT_HEIGHT);
                }
            });

    public static final Setting vSync = new BooleanSetting("VSync", true, vSync -> Gdx.graphics.setVSync(vSync));

    private static final List<Setting> settings = Arrays.asList(fullscreen, vSync);

    public static List<Setting> getSettings() {
        return settings;
    }

    public static void load() {
        FileHandle file = getFile();
        logger.info("Loading settings from " + file.path());

        if(!file.exists()){
            logger.info("Failed to find settings file!");
            return;
        }

        try{
            XmlReader.Element xml = new XmlReader().parse(file);
            settings.forEach(setting -> setting.load(xml));
        }catch(GdxRuntimeException e){
            logger.info("Failed to load settings!", e);

            throw e;
        }

        settings.forEach(Setting::apply);
    }

    public static void save(){
        FileHandle file = getFile();
        logger.info("Saving settings to " + file.path());

        try{
            QuietXmlWriter writer = new QuietXmlWriter(file).element("Settings");
            settings.forEach(setting -> setting.save(writer));
            writer.pop();
            writer.flush();
        }catch(GdxRuntimeException e){
            logger.info("Failed to save settings!", e);

            throw e;
        }
    }

    private static FileHandle getFile(){
        return Gdx.files.external("/station-alpha/settings.xml");
    }
}
