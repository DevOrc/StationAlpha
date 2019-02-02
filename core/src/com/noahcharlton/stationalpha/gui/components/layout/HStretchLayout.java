package com.noahcharlton.stationalpha.gui.components.layout;

import com.noahcharlton.stationalpha.gui.GuiComponent;

import java.util.ArrayList;

public class HStretchLayout implements LayoutManager {

    private int padding;
    private int hGap;

    private int containerY;
    private int containerX;
    private int containerWidth;
    private int containerHeight;

    public HStretchLayout() {
        setHGap(5);
        setPadding(10);
    }

    @Override
    public void layout(GuiComponent parent, ArrayList<GuiComponent> children) {
        containerY = parent.getX() + padding;
        containerX = parent.getY() + padding;
        containerWidth = parent.getWidth() - (padding * 2);
        containerHeight = parent.getHeight() - (padding * 2);

        layoutChildren(children);
    }

    private void layoutChildren(ArrayList<GuiComponent> children) {
        int widthPerChild = calculateWidthPerChild(children.size());
        int currentX = containerX;

        for(GuiComponent child : children){
            child.setX(currentX);
            child.setY(containerY);
            child.setWidth(widthPerChild);
            child.setHeight(containerHeight);

            currentX += widthPerChild;
            currentX += hGap;
        }
    }

    private int calculateWidthPerChild(int size) {
        if(size == 0)
            return 0;

        return (containerWidth / (size)) - hGap + (hGap / size);
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public void setHGap(int hGap) {
        this.hGap = hGap;
    }
}
