package com.noahcharlton.stationalpha.engine.settings;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.engine.input.SimpleInputProcessor;
import com.noahcharlton.stationalpha.gui.GuiComponent;
import com.noahcharlton.stationalpha.gui.components.MenuButton;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KeySetting extends Setting<Integer> {

    private final String name;

    public KeySetting(String name, int defaultState) {
        super(state -> {});

        this.name = name;
        this.setState(defaultState);
    }

    @Override
    void save(QuietXmlWriter writer) {
        writer.element(name, getState());
    }

    @Override
    void load(XmlReader.Element xml) {
        XmlReader.Element child = xml.getChildByName(name);

        if(child != null){
            setState(Integer.parseInt(child.getText()));
            logger.info("Loaded setting ({}): {}", name, Input.Keys.toString(getState()));
        }else{
            logger.info("Failed to find setting for " + name);
        }

    }

    @Override
    public GuiComponent createMenuComponent() {
        return new KeyButton(this);
    }

    public String getName() {
        return name;
    }
}
class KeyButton extends MenuButton implements SimpleInputProcessor {

    protected static final Logger logger = LogManager.getLogger(Setting.class);

    private final KeySetting setting;
    private boolean selectingKey = false;

    public KeyButton(KeySetting setting) {
        super("", () -> {});

        this.setting = setting;

        InputHandler.getInstance().getInputMultiplexer().addProcessor(this);
    }

    @Override
    public boolean keyDown(int keycode) {
        if(selectingKey){
            selectingKey = false;
            setting.setState(keycode);

            logger.info("KEY UPDATE! {}: {}", setting.getName(), Input.Keys.toString(setting.getState()));
        }

        return false;
    }

    @Override
    protected void onClick() {
        selectingKey = !selectingKey;
    }

    @Override
    protected void update() {
        if(selectingKey){
            setText("Press any key...");
        }else{
            setText(setting.getName() + ": " + Input.Keys.toString(setting.getState()));
        }
    }
}
