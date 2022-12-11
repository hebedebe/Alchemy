package io.github.hebedebe.alchemy.item;

import io.github.hebedebe.alchemy.alchemy;
import io.github.hebedebe.alchemy.block.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, alchemy.MODID);

    public static final RegistryObject<Item> ALCHEMICAL_SERUM = ITEMS.register("alchemical_serum", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_BREWING)));
    public static final RegistryObject<Item> ALCHEMICAL_CRYSTAL = ITEMS.register("alchemical_crystal", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> DENSE_LAPIS = ITEMS.register("dense_lapis", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> GILDED_LAPIS = ITEMS.register("gilded_lapis", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> ALCHEMICAL_ESSENCE = ITEMS.register("alchemical_essence", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> POTENT_INGOT = ITEMS.register("potent_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> STABLE_INGOT = ITEMS.register("stable_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> POWER_INGOT = ITEMS.register("power_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> CHAOS_INGOT = ITEMS.register("chaos_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> POTENTIAL_INGOT = ITEMS.register("potential_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

    public static final RegistryObject<Item> INERT_DUST = ITEMS.register("inert_dust", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> STABLE_DUST = ITEMS.register("stable_dust", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> POWER_DUST = ITEMS.register("power_dust", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> CHAOS_DUST = ITEMS.register("chaos_dust", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> POTENTIAL_DUST = ITEMS.register("potential_dust", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

    public static final RegistryObject<Item> STABLE_SEEDS = ITEMS.register("stable_seeds", () -> new ItemNameBlockItem(ModBlocks.STABLE_PLANT.get(), new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
