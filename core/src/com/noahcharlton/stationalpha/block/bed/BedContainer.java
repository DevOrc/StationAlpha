package com.noahcharlton.stationalpha.block.bed;

import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.worker.Worker;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;

import java.util.Optional;

public class BedContainer extends BlockContainer {

    private Optional<Worker> worker = Optional.empty();

    public BedContainer(Tile tile, Block block, BlockRotation rotation) {
        super(tile, block, rotation);
    }

    @Override
    public void onUpdate() {
        if(!worker.isPresent()){
            findWorkerWithoutBedroom();
        }
    }

    private void findWorkerWithoutBedroom() {
        World world = this.getTile().getWorld();

        for(Worker worker : world.getWorkers()){
            if(!worker.getBedroom().isPresent()){
                setWorker(worker);
                break;
            }
        }
    }

    void setWorker(Worker worker) {
        this.worker = Optional.of(worker);

        worker.setBedroom(this);
    }

    @Override
    public void onSave(QuietXmlWriter writer) {
        worker.ifPresent(worker -> writer.element("Owner", worker.getName()));
    }

    @Override
    public void onLoad(XmlReader.Element element) {
        String owner = element.get("Owner", null);

        if(owner != null){
            setOwnerByName(owner);
        }
    }

    private void setOwnerByName(String owner) {
        for(Worker worker : this.getTile().getWorld().getWorkers()){
            if(worker.getName().equals(owner) && !worker.getBedroom().isPresent()){
                setWorker(worker);
            }
        }
    }

    @Override
    public void onDestroy() {
        worker.ifPresent(w -> w.setBedroom(null));
    }

    public Optional<Worker> getWorker(){
        return worker;
    }
}
