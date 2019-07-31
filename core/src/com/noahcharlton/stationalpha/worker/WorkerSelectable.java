package com.noahcharlton.stationalpha.worker;

import com.noahcharlton.stationalpha.engine.input.Selectable;
import com.noahcharlton.stationalpha.worker.job.Job;

public class WorkerSelectable implements Selectable {

    private final Worker worker;

    public WorkerSelectable(Worker worker) {
        this.worker = worker;
    }

    @Override
    public String getTitle() {
        return worker.getName();
    }

    @Override
    public String getDesc() {
        return "";
    }

    @Override
    public String[] getDebugInfo() {
        return new String[]{
                "Location: " + worker.getTileOn(),
                "Job: " + worker.getAi().getJobManager().getCurrentJob().map(Job::toString).orElse("None"),
        };
    }
}
