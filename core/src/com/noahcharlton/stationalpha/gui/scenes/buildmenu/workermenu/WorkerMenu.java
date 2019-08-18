package com.noahcharlton.stationalpha.gui.scenes.buildmenu.workermenu;

import com.noahcharlton.stationalpha.HelpInfo;
import com.noahcharlton.stationalpha.gui.components.layout.VStretchLayout;
import com.noahcharlton.stationalpha.gui.scenes.buildmenu.BuildBarMenu;
import com.noahcharlton.stationalpha.gui.scenes.message.MessageQueue;
import com.noahcharlton.stationalpha.worker.Worker;
import com.noahcharlton.stationalpha.world.World;

import java.util.Collections;
import java.util.List;

public class WorkerMenu extends BuildBarMenu<Worker> {

    private static final int WIDTH = WorkerInfoRow.WIDTH + 10;
    private boolean opened;
    private int tableRowCount;

    public WorkerMenu() {
        super(Collections.emptyList());

        VStretchLayout layout = new VStretchLayout();
        layout.setVGap(0);

        setLayoutManager(layout);
    }

    @Override
    protected void update() {
        createButtons(World.getInstance().get().getWorkers());
    }

    @Override
    protected void createButtons(List<Worker> items) {
        this.getSubGuis().clear();
        this.tableRowCount = items.size() + 1;

        for(int i = 0; i < items.size(); i++){
            Worker worker = items.get(i);

            WorkerInfoRow workerGui = new WorkerInfoRow(worker);

            addGui(workerGui);
        }

        addGui(new WorkerTitleRow());
    }

    @Override
    protected void updateSize() {
        setHeight(tableRowCount * (WorkerInfoRow.PREFERRED_HEIGHT + 10));
        setWidth(WIDTH);
    }

    @Override
    protected Runnable createRunnable(Worker worker) {
        return () -> {};
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);

        if(!opened && visible){
            opened = true;
            MessageQueue.getInstance().add("Tip!", HelpInfo.get("worker_menu_info"));
        }
    }

    @Override
    public String getName() {
        return "Workers";
    }
}
