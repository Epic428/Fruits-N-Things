package com.teamresourceful.fruits.common.blockentities;

import com.teamresourceful.fruits.common.recipes.CrushingRecipe;
import com.teamresourceful.fruits.common.registry.ModBlockEntities;
import com.teamresourceful.fruits.common.registry.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class CrushingBarrelBlockEntity extends BlockEntity implements Container {

    private CrushingRecipe recipe;
    private final NonNullList<ItemStack> items;
    private int presses;
    private Fluid fluid = Fluids.EMPTY;

    public CrushingBarrelBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.CRUSHING_BARREL_ENTITY, blockPos, blockState);
        this.items = NonNullList.withSize(2, ItemStack.EMPTY);
    }

    public Fluid getFluid() {
        return fluid;
    }

    public void removeFluid() {
        fluid = Fluids.EMPTY;
    }

    public float progress() {
        if (!fluid.isSame(Fluids.EMPTY)) return 1f;
        if (recipe == null) return 0f;
        return (float)this.presses / (float)recipe.getPresses();
    }

    public CrushingRecipe getCurrentRecipe() {
        return recipe;
    }

    public void processRecipe() {
        if (this.level == null) return;
        items.clear();
        if (this.level.random.nextFloat() < recipe.getChance())
            items.set(1, recipe.getWeightedResult().copy());
        fluid = recipe.getOutput();
        recipe = null;
    }

    public void increasePresses() {
        if (this.level == null) return;
        recipe = this.level.getRecipeManager().getRecipeFor(ModRecipes.CRUSHING_RECIPE_TYPE, this, this.level).orElse(null);
        if (recipe == null){
            presses = 0;
            return;
        }
        if (recipe.getIngredient().getItems()[0].getCount() != getItem(0).getCount()) {
            presses = 0;
            return;
        }
        presses++;
        if (presses >= recipe.getPresses()){
            processRecipe();
            presses = 0;
        }
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        ContainerHelper.loadAllItems(compoundTag, this.items);
        presses = compoundTag.getInt("presses");
        fluid = Registry.FLUID.get(new ResourceLocation(compoundTag.getString("fluid")));
        if (this.level == null) return;
        recipe = this.level.getRecipeManager().getRecipeFor(ModRecipes.CRUSHING_RECIPE_TYPE, this, this.level).orElse(null);
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        ContainerHelper.saveAllItems(compoundTag, this.items);
        compoundTag.putString("fluid", Registry.FLUID.getKey(fluid).toString());
        compoundTag.putInt("presses", presses);
        return super.save(compoundTag);
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    @Override
    public ItemStack getItem(int i) {
        return this.items.get(i);
    }

    @Override
    public ItemStack removeItem(int i, int j) {
        return ContainerHelper.removeItem(this.items, i, j);
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        return ContainerHelper.takeItem(this.items, i);
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {
        this.items.set(i, itemStack);
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }
}
