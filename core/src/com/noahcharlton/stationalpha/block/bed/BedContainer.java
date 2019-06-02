package com.noahcharlton.stationalpha.block.bed;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.worker.Worker;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;

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
                this.worker = Optional.of(worker);

                worker.setBedroom(Optional.of(this));
                break;
            }
        }
    }

    @Override
    public void onDestroy() {
        worker.ifPresent(w -> w.setBedroom(Optional.empty()));
    }

    public Optional<Worker> getWorker(){
        return worker;
    }
}
