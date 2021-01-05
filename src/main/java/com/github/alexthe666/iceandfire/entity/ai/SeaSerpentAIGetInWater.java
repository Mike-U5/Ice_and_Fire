package com.github.alexthe666.iceandfire.entity.ai;

import java.util.EnumSet;
import java.util.Random;

import com.github.alexthe666.iceandfire.entity.EntitySeaSerpent;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;

public class SeaSerpentAIGetInWater  extends Goal {
    private final EntitySeaSerpent creature;
    private BlockPos targetPos;

    public SeaSerpentAIGetInWater(EntitySeaSerpent creature) {
        this.creature = creature;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean shouldExecute() {
        if ((this.creature.jumpCooldown == 0 || this.creature.func_233570_aj_()) && !this.creature.world.getFluidState(this.creature.func_233580_cy_()).isTagged(FluidTags.WATER)){
            targetPos = generateTarget();
            return targetPos != null;
        }
        return false;
    }

    public void startExecuting() {
        if(targetPos != null){
            this.creature.getNavigator().tryMoveToXYZ(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1.5D);
        }
    }

    public boolean shouldContinueExecuting() {
        return !this.creature.getNavigator().noPath() && targetPos != null && !this.creature.world.getFluidState(this.creature.func_233580_cy_()).isTagged(FluidTags.WATER);
    }

    public BlockPos generateTarget() {
        BlockPos blockpos = null;
        Random random = new Random();
        int range = 16;
        for(int i = 0; i < 15; i++){
            BlockPos blockpos1 = this.creature.func_233580_cy_().add(random.nextInt(range) - range/2, 3, random.nextInt(range) - range/2);
            while(this.creature.world.isAirBlock(blockpos1) && blockpos1.getY() > 1){
                blockpos1 = blockpos1.down();
            }
            if(this.creature.world.getFluidState(blockpos1).isTagged(FluidTags.WATER)){
                blockpos = blockpos1;
            }
        }
        return blockpos;
    }
}
