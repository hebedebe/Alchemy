package io.github.hebedebe.alchemy;

import io.github.hebedebe.alchemy.block.ModBlocks;
import io.github.hebedebe.alchemy.block.entity.ModBlockEntities;
import io.github.hebedebe.alchemy.item.ModItems;
import io.github.hebedebe.alchemy.recipe.ModRecipes;
import io.github.hebedebe.alchemy.screen.CrudeAlchemicalWorkbenchScreen;
import io.github.hebedebe.alchemy.screen.EvaporationTableMenu;
import io.github.hebedebe.alchemy.screen.EvaporationTableScreen;
import io.github.hebedebe.alchemy.screen.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(alchemy.MODID)
public class alchemy {
    public static final String MODID = "alchemy";

    public alchemy() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModRecipes.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        BrewingRecipeRegistry.addRecipe(Ingredient.of(Items.EXPERIENCE_BOTTLE), Ingredient.of(ModItems.ALCHEMICAL_CRYSTAL.get()), new ItemStack(ModItems.ALCHEMICAL_SERUM.get()));
        BrewingRecipeRegistry.addRecipe(Ingredient.of(Items.BLACKSTONE), Ingredient.of(Items.GOLD_NUGGET), new ItemStack(Items.GILDED_BLACKSTONE));
        BrewingRecipeRegistry.addRecipe(Ingredient.of(Items.STONE), Ingredient.of(Items.LAPIS_LAZULI), new ItemStack(ModBlocks.INFUSED_STONE.get()));
        BrewingRecipeRegistry.addRecipe(Ingredient.of(ModItems.DENSE_LAPIS.get()), Ingredient.of(Items.GOLD_NUGGET), new ItemStack(ModItems.GILDED_LAPIS.get()));
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.CRUDE_ALCHEMICAL_WORKBENCH_MENU.get(), CrudeAlchemicalWorkbenchScreen::new);
            MenuScreens.register(ModMenuTypes.EVAPORATION_TABLE_MENU.get(), EvaporationTableScreen::new);
        }
    }

}
