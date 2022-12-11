package io.github.hebedebe.alchemy.block.entity;

import io.github.hebedebe.alchemy.alchemy;
import io.github.hebedebe.alchemy.block.ModBlocks;
import io.github.hebedebe.alchemy.block.custom.EvaporationTableBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, alchemy.MODID);

    public static final RegistryObject<BlockEntityType<CrudeAlchemicalWorkbenchBlockEntity>> CRUDE_ALCHEMICAL_WORKBENCH =
            BLOCK_ENTITIES.register("crude_alchemical_workbench", () -> BlockEntityType.Builder.of(CrudeAlchemicalWorkbenchBlockEntity::new, ModBlocks.CRUDE_ALCHEMICAL_WORKBENCH.get()).build(null));
    public static final RegistryObject<BlockEntityType<EvaporationTableBlockEntity>> EVAPORATION_TABLE =
            BLOCK_ENTITIES.register("evaporation_table", () -> BlockEntityType.Builder.of(EvaporationTableBlockEntity::new, ModBlocks.EVAPORATION_TABLE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
