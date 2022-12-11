package io.github.hebedebe.alchemy.recipe;

import io.github.hebedebe.alchemy.alchemy;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, alchemy.MODID);

    public static final RegistryObject<RecipeSerializer<AlchemicalRecipe>> ALCHEMICAL_PROCESSING_SERIALIZER =
            SERIALIZERS.register("alchemical_processing", () -> AlchemicalRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<EvaporatingRecipe>> EVAPORATION_PROCESSING_SERIALIZER =
            SERIALIZERS.register("evaporating_recipe", () -> EvaporatingRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
