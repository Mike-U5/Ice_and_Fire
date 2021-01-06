package com.github.alexthe666.iceandfire.item;

import java.util.List;

import javax.annotation.Nullable;

import com.github.alexthe666.iceandfire.IceAndFire;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ItemPixieWand extends Item {

    public ItemPixieWand() {
        super(new Item.Properties().group(IceAndFire.TAB_ITEMS).maxStackSize(1).maxDamage(500));
        this.setRegistryName(IceAndFire.MODID, "pixie_wand");
    }

    public boolean isInfinite(ItemStack stack, ItemStack bow, net.minecraft.entity.player.PlayerEntity player) {
        int enchant = net.minecraft.enchantment.EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, bow);
        return enchant > 0 && stack.getItem() == IafItemRegistry.PIXIE_DUST;
    }

    protected boolean isAmmo(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() == IafItemRegistry.PIXIE_DUST;
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("item.iceandfire.legendary_weapon.desc").func_240699_a_(TextFormatting.GRAY));
        tooltip.add(new TranslationTextComponent("item.iceandfire.pixie_wand.desc_0").func_240699_a_(TextFormatting.GRAY));
        tooltip.add(new TranslationTextComponent("item.iceandfire.pixie_wand.desc_1").func_240699_a_(TextFormatting.GRAY));
    }
}
