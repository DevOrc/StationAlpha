package com.noahcharlton.stationalpha.gui.scenes.message;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.gui.components.Pane;
import com.noahcharlton.stationalpha.gui.scenes.BuildBar;
import com.noahcharlton.stationalpha.gui.scenes.SpeedButton;

import java.util.Optional;

public class MessageMenu extends Pane {

    private static final int DISPLAYING_WIDTH = 420;
    private static final int DISPLAYING_HEIGHT = 275;
    private static final int SPACING = 16;

    private final CloseSelectableMenuButton menuButton = new CloseSelectableMenuButton(this);
    private Optional<Message> currentMessage = Optional.empty();

    public MessageMenu() {
        setDrawBorder(true, true, true, true);

        addGui(menuButton);
    }

    @Override
    protected void update() {
        if(!currentMessage.isPresent()){
            currentMessage = MessageQueue.getInstance().poll();
        }
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        currentMessage.ifPresent(message -> {
            setFontData(1f, Color.WHITE);
            drawCenteredText(b, message.getName(), DISPLAYING_HEIGHT * 9 / 10);

            setFontData(.6f, Color.WHITE);
            drawCenteredText(b, message.getDescription(), DISPLAYING_HEIGHT * 6 / 10);
            drawCenteredText(b, MessageQueue.getInstance().getMessages().size() + " Messages Left", 25);
        });
    }

    @Override
    protected void updatePosition() {
        setX(Gdx.graphics.getWidth() - SPACING - DISPLAYING_WIDTH);
        setY(BuildBar.HEIGHT + SPACING + SpeedButton.HEIGHT + SPACING);
    }

    @Override
    protected void updateSize() {
        if(isDisplayingMessage()){
            setWidth(DISPLAYING_WIDTH);
            setHeight(DISPLAYING_HEIGHT);
        }else{
            setWidth(0);
            setHeight(0);
        }
    }

    void gotoNextMessage(){
        currentMessage = MessageQueue.getInstance().poll();
    }

    boolean isDisplayingMessage(){
        return currentMessage.isPresent();
    }
}
class CloseSelectableMenuButton extends Pane {

    private static final int SIZE = 24;
    private final MessageMenu menu;

    public CloseSelectableMenuButton(MessageMenu menu) {
        this.menu = menu;

        setDrawBorder(true, true, true, true);
    }

    @Override
    protected void onClick() {
        menu.gotoNextMessage();
}

    @Override
    public void drawForeground(SpriteBatch b) {
        if(menu.isDisplayingMessage()){
            setFontData(.75f, Color.FIREBRICK);
            drawCenteredText(b, "X", SIZE * 9 / 10);
        }
    }

    @Override
    protected void updateSize() {
        if(menu.isDisplayingMessage()){
            setWidth(SIZE);
            setHeight(SIZE);
        }else{
            setWidth(0);
            setHeight(0);
        }
    }

    @Override
    protected void updatePosition() {
        setX(menu.getX() + menu.getWidth() - SIZE);
        setY(menu.getY() + menu.getHeight() - SIZE);
    }
}
