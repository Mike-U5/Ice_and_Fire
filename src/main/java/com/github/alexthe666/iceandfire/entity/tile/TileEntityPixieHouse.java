package com.github.alexthe666.iceandfire.entity.tile;

import java.util.Random;
import java.util.UUID;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.message.MessageUpdatePixieHouseModel;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;

public class TileEntityPixieHouse extends TileEntity implements ITickableTileEntity {

    private static final float PARTICLE_WIDTH = 0.3F;
    private static final float PARTICLE_HEIGHT = 0.6F;
    public int houseType;
    public boolean hasPixie;
    public boolean tamedPixie;
    public UUID pixieOwnerUUID;
    public int pixieType;
    public int ticksExisted;
    public NonNullList<ItemStack> pixieItems = NonNullList.withSize(1, ItemStack.EMPTY);
    private Random rand;

    public TileEntityPixieHouse() {
        super(IafTileEntityRegistry.PIXIE_HOUSE);
        this.rand = new Random();
    }

    public static int getHouseTypeFromBlock(Block block) {
        if (block == IafBlockRegistry.PIXIE_HOUSE_MUSHROOM_RED) {
            return 1;
        }
        if (block == IafBlockRegistry.PIXIE_HOUSE_MUSHROOM_BROWN) {
            return 0;
        }
        if (block == IafBlockRegistry.PIXIE_HOUSE_OAK) {
            return 3;
        }
        if (block == IafBlockRegistry.PIXIE_HOUSE_BIRCH) {
            return 2;
        }
        if (block == IafBlockRegistry.PIXIE_HOUSE_SPRUCE) {
            return 5;
        }
        if (block == IafBlockRegistry.PIXIE_HOUSE_DARK_OAK) {
            return 4;
        }
        return 0;
    }

    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt("HouseType", houseType);
        compound.putBoolean("HasPixie", hasPixie);
        compound.putInt("PixieType", pixieType);
        compound.putBoolean("TamedPixie", tamedPixie);
        if (pixieOwnerUUID != null) {
            compound.putUniqueId("PixieOwnerUUID", pixieOwnerUUID);
        }
        ItemStackHelper.saveAllItems(compound, this.pixieItems);
        return compound;
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(pos, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet) {
        func_230337_a_(this.getBlockState(), packet.getNbtCompound());
        if (!world.isRemote) {
            IceAndFire.sendMSGToAll(new MessageUpdatePixieHouseModel(pos.toLong(), packet.getNbtCompound().getInt("HouseType")));
        }
    }

    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    public void func_230337_a_(BlockState state, CompoundNBT compound) {
        houseType = compound.getInt("HouseType");
        hasPixie = compound.getBoolean("HasPixie");
        pixieType = compound.getInt("PixieType");
        tamedPixie = compound.getBoolean("TamedPixie");
        if(compound.hasUniqueId("PixieOwnerUUID")){
            pixieOwnerUUID = compound.getUniqueId("PixieOwnerUUID");
        }
        this.pixieItems = NonNullList.withSize(1, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, pixieItems);
        super.func_230337_a_(state, compound);
    }

    @Override
    public void tick() {
        ticksExisted++;
        if (!world.isRemote && this.hasPixie && new Random().nextInt(100) == 0) {
            releasePixie();
        }
    }

    public void releasePixie() {

    }
}
