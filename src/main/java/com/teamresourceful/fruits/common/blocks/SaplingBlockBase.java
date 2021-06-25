package com.teamresourceful.fruits.common.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.material.Material;

public class SaplingBlockBase extends SaplingBlock {

    public SaplingBlockBase(AbstractTreeGrower abstractTreeGrower) {
        super(abstractTreeGrower, FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(SoundType.GRASS));
    }
}
