package io.github.hebedebe.alchemy.block;

import io.github.hebedebe.alchemy.alchemy;
import io.github.hebedebe.alchemy.block.custom.CrudeAlchemicalWorkstationBlock;
import io.github.hebedebe.alchemy.block.custom.CutoutBlock;
import io.github.hebedebe.alchemy.block.custom.EvaporationTableBlock;
import io.github.hebedebe.alchemy.block.custom.StablePlantBlock;
import io.github.hebedebe.alchemy.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, alchemy.MODID);

    public static final RegistryObject<Block> CRUDE_ALCHEMICAL_WORKBENCH = registerBlock("crude_alchemical_workbench", () -> new CrudeAlchemicalWorkstationBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).dynamicShape().noOcclusion()), CreativeModeTab.TAB_BREWING);
    public static final RegistryObject<Block> ALCHEMICAL_WORKBENCH = registerBlock("alchemical_workbench", () -> new CutoutBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).dynamicShape().noOcclusion()), CreativeModeTab.TAB_BREWING);
    public static final RegistryObject<Block> EVAPORATION_TABLE = registerBlock("evaporation_table", () -> new EvaporationTableBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).dynamicShape().noOcclusion()), CreativeModeTab.TAB_BREWING);

    public static final RegistryObject<Block> GOLD_CAULDRON = registerBlock("gold_cauldron", () -> new CutoutBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).dynamicShape().noOcclusion()), CreativeModeTab.TAB_BREWING);
    public static final RegistryObject<Block> INFUSED_STONE = registerBlock("infused_stone", () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).dynamicShape().noOcclusion()), CreativeModeTab.TAB_BREWING);
    public static final RegistryObject<Block> GILDED_STONE = registerBlock("gilded_stone", () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).dynamicShape().noOcclusion()), CreativeModeTab.TAB_BREWING);
    public static final RegistryObject<Block> STABLE_PLANT = registerBlockWithoutBlockItem("stable_plant", () -> new StablePlantBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block) {
        return BLOCKS.register(name,block);
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
