package com.noahcharlton.stationalpha.block.experiment;

import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.engine.input.Selectable;
import com.noahcharlton.stationalpha.engine.input.mine.MineAction;
import com.noahcharlton.stationalpha.gui.GuiComponent;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.worker.job.JobQueue;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;

import java.util.Optional;

public class ExperimentContainer extends BlockContainer implements Selectable.GuiSelectable {

    private Optional<Job> job = Optional.empty();
    private Optional<Experiment> experiment = Optional.empty();

    public ExperimentContainer(Tile tile, Block block, BlockRotation rotation) {
        super(tile, block, rotation);
    }

    void createExperiment(){
        experiment = Optional.of(new Experiment(250));
        job = createJob();

        if(job.isPresent()){
            JobQueue.getInstance().addJob(job.get());
        }else{
            experiment = Optional.empty();
        }
    }

    @Override
    public void onSave(QuietXmlWriter writer) {
        experiment.ifPresent(e -> {
            writer.element("Experiment")
                    .attribute("Name", e.getName())
                    .attribute("Stage", e.getStage())
                    .attribute("Progress", e.getProgress())
                    .attribute("Length", e.getLength())
                    .pop();
        });
    }

    @Override
    public void onLoad(XmlReader.Element element) {
        element = element.getChildByName("Experiment");

        if(element != null){
            loadExperiment(element);
        }
    }

    private void loadExperiment(XmlReader.Element element) {
        String name = element.getAttribute("Name");
        String stageStr = element.getAttribute("Stage");
        Experiment.Stage stage = Experiment.Stage.valueOf(stageStr);
        int progress = element.getIntAttribute("Progress");
        int length = element.getIntAttribute("Length");

        experiment = Optional.of(new Experiment(name, progress, length));

        loadStage(stage);
    }

    private void loadStage(Experiment.Stage stage) {
        switch(stage){
            case PRE_START:
                job = createJob();

                if(job.isPresent()){
                    JobQueue.getInstance().addJob(job.get());
                }else{
                    experiment = Optional.empty();
                }
                break;
            case IN_PROGRESS:
                startExperiment();
                break;
        }
    }

    Optional<Job> createJob(){
        return MineAction.getOpenAdjacent(this).map(tile -> new SetupExperimentJob(tile, this));
    }

    void startExperiment(){
        experiment.orElseThrow(IllegalStateException::new).start();
    }

    @Override
    public void onUpdate() {
        experiment.ifPresent(Experiment::onTick);

        experiment.filter(e -> e.getStage() == Experiment.Stage.FINISHED).ifPresent(this::onExperimentFinished);

    }

    private void onExperimentFinished(Experiment experiment) {
        getTile().getWorld().getGoalManager().earnSciencePoints(experiment.getScienceEarned());
        this.experiment = Optional.empty();
    }

    @Override
    public void onDestroy() {
        job.ifPresent(Job::permanentEnd);
    }

    @Override
    public String[] getDebugInfo() {
        return super.combineDebugInfo("Experiment: " + getExperimentInfo());
    }

    private String getExperimentInfo() {
       return experiment.map(e ->
               String.format("%s (%d / %d)", e.getName(), e.getProgress(), e.getLength())).orElse("None");
    }

    @Override
    public GuiComponent createGui() {
        return new ExperimentBlockPane(this);
    }

    public Optional<Experiment> getExperiment() {
        return experiment;
    }

    public Optional<Job> getJob() {
        return job;
    }
}
