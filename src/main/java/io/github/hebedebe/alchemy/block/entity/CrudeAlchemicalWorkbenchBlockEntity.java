package io.github.hebedebe.alchemy.block.entity;

import io.github.hebedebe.alchemy.item.ModItems;
import io.github.hebedebe.alchemy.recipe.AlchemicalRecipe;
import io.github.hebedebe.alchemy.screen.CrudeAlchemicalWorkbenchMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.logging.Logger;

public class CrudeAlchemicalWorkbenchBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(6) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;

    //progress vars
    private int progress = 0;
    private int maxProgress = 50;

    //Alchemical value vars
    public int stability = 0;
    public int power = 0;
    public int chaos = 0;
    public int potential = 0;

    public CrudeAlchemicalWorkbenchBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CRUDE_ALCHEMICAL_WORKBENCH.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> CrudeAlchemicalWorkbenchBlockEntity.this.progress;
                    case 1 -> CrudeAlchemicalWorkbenchBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> CrudeAlchemicalWorkbenchBlockEntity.this.progress = value;
                    case 1 -> CrudeAlchemicalWorkbenchBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Crude Alchemical Workbench");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new CrudeAlchemicalWorkbenchMenu(id, inventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("crude_alchemical_workbench.progress", this.progress);
        nbt.putInt("crude_alchemical_workbench.stability", this.progress);
        nbt.putInt("crude_alchemical_workbench.power", this.progress);
        nbt.putInt("crude_alchemical_workbench.chaos", this.progress);
        nbt.putInt("crude_alchemical_workbench.potential", this.progress);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("crude_alchemical_workbench.progress");
        stability = nbt.getInt("crude_alchemical_workbench.stability");
        power = nbt.getInt("crude_alchemical_workbench.power");
        chaos = nbt.getInt("crude_alchemical_workbench.chaos");
        potential = nbt.getInt("crude_alchemical_workbench.potential");
    }
    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, CrudeAlchemicalWorkbenchBlockEntity pEntity) {
        if(level.isClientSide()) {
            return;
        }

        //insertion of alchemical modifiers
        if (pEntity.itemHandler.getStackInSlot(0).getItem().equals(Items.LAPIS_LAZULI)){
            pEntity.itemHandler.extractItem(0, 1, false);
            pEntity.stability += 1;
        }
        if (pEntity.itemHandler.getStackInSlot(1).getItem().equals(Items.REDSTONE)){
            pEntity.itemHandler.extractItem(1, 1, false);
            pEntity.power += 1;
        }
        if (pEntity.itemHandler.getStackInSlot(2).getItem().equals(Items.BLAZE_POWDER)){
            pEntity.itemHandler.extractItem(2, 1, false);
            pEntity.chaos += 1;
        }
        if (pEntity.itemHandler.getStackInSlot(3).getItem().equals(Items.COAL)){
            pEntity.itemHandler.extractItem(3, 1, false);
            pEntity.potential += 1;
        }

        //Alchemical Ingots

        if (pEntity.itemHandler.getStackInSlot(0).getItem().equals(ModItems.STABLE_INGOT.get())){
            pEntity.itemHandler.extractItem(0, 1, false);
            pEntity.stability += 15;
        }
        if (pEntity.itemHandler.getStackInSlot(1).getItem().equals(ModItems.POWER_INGOT.get())){
            pEntity.itemHandler.extractItem(1, 1, false);
            pEntity.power += 15;
        }
        if (pEntity.itemHandler.getStackInSlot(2).getItem().equals(ModItems.CHAOS_INGOT.get())){
            pEntity.itemHandler.extractItem(2, 1, false);
            pEntity.chaos += 15;
        }
        if (pEntity.itemHandler.getStackInSlot(3).getItem().equals(ModItems.POTENTIAL_INGOT.get())){
            pEntity.itemHandler.extractItem(3, 1, false);
            pEntity.potential += 15;
        }
        //


        if(hasRecipe(pEntity)) {
            pEntity.progress++;
            setChanged(level, pos, state);

            if(pEntity.progress >= pEntity.maxProgress) {
                craftItem(pEntity);
            }
        } else {
            pEntity.resetProgress();
            setChanged(level, pos, state);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(CrudeAlchemicalWorkbenchBlockEntity pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }


        Optional<AlchemicalRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(AlchemicalRecipe.Type.INSTANCE, inventory, level);

        if(hasRecipe(pEntity)) {
            pEntity.itemHandler.extractItem(4, 1, false); //eventually set amount to the count specified by the recipe
            pEntity.itemHandler.setStackInSlot(5, new ItemStack(recipe.get().getResultItem().getItem(),
                    pEntity.itemHandler.getStackInSlot(5).getCount() + recipe.get().getOutputAmount()));

            pEntity.stability -= recipe.get().getStability();
            pEntity.power -= recipe.get().getPower();
            pEntity.chaos -= recipe.get().getChaos();
            pEntity.potential -= recipe.get().getPotential();

            pEntity.resetProgress();
        }
    }

    private static boolean modifiersPresent(CrudeAlchemicalWorkbenchBlockEntity pEntity, Optional<AlchemicalRecipe> recipe) {
        boolean stabilityPresent = false;
        boolean powerPresent = false;
        boolean chaosPresent = false;
        boolean potentialPresent = false;

        if (pEntity.stability >= recipe.get().getStability()) {
            stabilityPresent = true;
        }
        if (pEntity.power >= recipe.get().getPower()) {
            powerPresent = true;
        }
        if (pEntity.chaos >= recipe.get().getChaos()) {
            chaosPresent = true;
        }
        if (pEntity.potential >= recipe.get().getPotential()) {
            potentialPresent = true;
        }

        return stabilityPresent && powerPresent && chaosPresent && potentialPresent;
    }

    private static boolean hasRecipe(CrudeAlchemicalWorkbenchBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<AlchemicalRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(AlchemicalRecipe.Type.INSTANCE, inventory, level);


        return recipe.isPresent() && canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, recipe.get().getResultItem()) && modifiersPresent(entity, recipe);
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack) {
        return inventory.getItem(5).getItem() == stack.getItem() || inventory.getItem(5).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(5).getMaxStackSize() > inventory.getItem(5).getCount();
    }
}
