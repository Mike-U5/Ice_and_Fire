package com.github.alexthe666.iceandfire.item;

import java.util.function.Predicate;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.misc.IafTagRegistry;

import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class ItemDragonBow extends BowItem implements ICustomRendered {
    public static final Predicate<ItemStack> DRAGON_ARROWS = (p_220002_0_) -> {
        ITag<Item> tag = ItemTags.getCollection().func_241834_b(IafTagRegistry.DRAGON_ARROWS);
        return p_220002_0_.getItem().isIn(tag);
    };

    public ItemDragonBow() {
        super(new Item.Properties().group(IceAndFire.TAB_ITEMS).maxDamage(584));
        this.setRegistryName(IceAndFire.MODID, "dragonbone_bow");
    }

    public Predicate<ItemStack> getInventoryAmmoPredicate() {
        return DRAGON_ARROWS;
    }
}
