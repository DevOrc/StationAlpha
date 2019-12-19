package com.noahcharlton.stationalpha.engine.settings;

import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.gui.GuiComponent;
import com.noahcharlton.stationalpha.gui.components.MenuButton;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.function.Consumer;

public abstract class Setting<T> {

    protected static final Logger logger = LogManager.getLogger(Setting.class);

    private Consumer<T> apply;
    protected T state;

    public Setting(Consumer<T> apply) {
        this.apply = apply;
    }

    abstract void load(XmlReader.Element xml);

    abstract void save(QuietXmlWriter writer);

    void apply(){
        apply.accept(state);
    }

    public abstract GuiComponent createMenuComponent();

    public void setApply(Consumer<T> apply) {
        this.apply = Objects.requireNonNull(apply);

        apply();
    }

    public T getState() {
        return state;
    }
}
class BooleanSetting extends Setting<Boolean>{

    private final String name;
    private final MenuButton menuButton;

    public BooleanSetting(String name, boolean state, Consumer<Boolean> apply) {
        super(apply);

        this.name = name;
        this.state = state;
        this.menuButton = new MenuButton(getButtonText(), this::onClick);
    }

    @Override
    void apply() {
        super.apply();

        menuButton.setText(getButtonText());
    }

    @Override
    void save(QuietXmlWriter writer) {
        writer.element(name, state);
    }

    @Override
    void load(XmlReader.Element xml) {
        XmlReader.Element child = xml.getChildByName(name);

        if(child != null){
            state = Boolean.parseBoolean(child.getText());
            logger.info("Loaded setting ({}): {}", name, state);
        }else{
            logger.info("Failed to find setting for " + name);
        }

    }

    private String getButtonText() {
        return name + (state ? ": On": ": Off");
    }

    @Override
    public GuiComponent createMenuComponent() {
        return menuButton;
    }

    void onClick() {
        state = !state;

        apply();
    }
}