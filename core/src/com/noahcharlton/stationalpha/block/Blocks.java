package com.noahcharlton.stationalpha.block;

import com.noahcharlton.stationalpha.block.bed.BedBlock;
import com.noahcharlton.stationalpha.block.composter.ComposterBlock;
import com.noahcharlton.stationalpha.block.door.DoorBlock;
import com.noahcharlton.stationalpha.block.plant.PotatoPlant;
import com.noahcharlton.stationalpha.block.plant.TestPlant;
import com.noahcharlton.stationalpha.block.tree.TreeBlock;
import com.noahcharlton.stationalpha.block.tree.TreeSaplingBlock;
import com.noahcharlton.stationalpha.block.workbench.Workbench;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class Blocks {

    private static final ArrayList<Block> blocks = new ArrayList<>();
    private static Block wall = new WallBlock();
    private static Block door = new DoorBlock();
    private static Block compressor = new CompressorBlock();
    private static Block testPlant = new TestPlant();
    private static Block potatoPlant = new PotatoPlant();
    private static Block workbench = new Workbench();
    private static Block bedBlock = new BedBlock();
    private static Block treeBlock = new TreeBlock();
    private static Block treeSapling = new TreeSaplingBlock();
    private static Block composter = new ComposterBlock();
    private static Block testRotate;
    private static Block ice;

    public static void init(){
        testRotate = createSimple("test_rotate.png");
        testRotate.setOpaque(true);
        ice = createSimple("ice.png");
        ice.setOpaque(true);

        Collections.addAll(blocks, wall, door, compressor, ice, testRotate, testPlant,
                potatoPlant, workbench, bedBlock, treeBlock, treeSapling, composter);
    }

    private static Block createSimple(String textureName) {
        return new Block() {
            @Override
            protected Optional<String> getTextureFileName() {
                return Optional.of(textureName);
            }
        };
    }

    public static Block getWall() {
        return wall;
    }

    public static Block getIce() {
        return ice;
    }

    public static Block getDoor() {
        return door;
    }

    public static Block getCompressor(){
        return compressor;
    }

    public static Block getTestPlant() {
        return testPlant;
    }

    public static Block getPotatoPlant() {
        return potatoPlant;
    }

    public static Block getWorkbench() {
        return workbench;
    }

    public static Block getBedBlock() {
        return bedBlock;
    }

    public static Block getTreeBlock() {
        return treeBlock;
    }

    public static Block getTreeSapling() {
        return treeSapling;
    }

    public static Block getComposter() {
        return composter;
    }

    public static ArrayList<Block> getBlocks() {
        return blocks;
    }
}
