package com.noahcharlton.stationalpha.gui.components.layout;

import com.noahcharlton.stationalpha.gui.GuiComponent;

import java.util.ArrayList;

public class VStretchLayout implements LayoutManager{

    private int padding;
    private int vGap;

    private int containerX;
    private int containerY;
    private int containerWidth;
    private int containerHeight;

    public VStretchLayout() {
        setVGap(5);
        setPadding(5);
    }

    @Override
    public void layout(GuiComponent parent, ArrayList<GuiComponent> children) {
        containerX = parent.getX() + padding;
        containerY = parent.getY() + padding;
        containerWidth = parent.getWidth() - (padding * 2);
        containerHeight = parent.getHeight() - (padding * 2);

        layoutChildren(children);
    }

    private void layoutChildren(ArrayList<GuiComponent> children) {
        int heightPerChild = calculateWidthPerChild(children.size());
        int currentY = containerY;

        for(GuiComponent child : children){
            child.setX(containerX);
            child.setY(currentY);
            child.setWidth(containerWidth);
            child.setHeight(heightPerChild);

            currentY += heightPerChild;
            currentY += vGap;
        }
    }

    private int calculateWidthPerChild(int size) {
        if(size == 0)
            return 0;

        return (containerHeight / (size)) - vGap + (vGap / size);
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public void setVGap(int vGap) {
        this.vGap = vGap;
    }
}
