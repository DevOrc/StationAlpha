package com.noahcharlton.stationalpha.block.experiment;

public class Experiment {

    enum Stage{PRE_START, IN_PROGRESS, FINISHED};

    private final String name;
    private final int length;
    private final int scienceEarned = 20;

    private Stage stage;
    private int progress = 0;

    public Experiment(int length) {
        this("ExpName", 0, length);
    }

    public Experiment(String name, int progress, int length) {
        this.name = name;
        this.length = length;
        this.progress = progress;
        this.stage = Stage.PRE_START;

        if(length < progress){
            throw new IllegalArgumentException("Progress cannot be bigger than length...");
        }
    }

    public void start(){
        if(stage != Stage.PRE_START)
            throw new UnsupportedOperationException();

        stage = Stage.IN_PROGRESS;
    }

    public void onTick() {
        if(stage != Stage.IN_PROGRESS)
            return;

        progress++;

        if(progress >= length) {
            stage = Stage.FINISHED;
        }
    }

    public int getProgress() {
        return progress;
    }

    public int getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    public Stage getStage() {
        return stage;
    }

    public int getScienceEarned() {
        return scienceEarned;
    }
}
