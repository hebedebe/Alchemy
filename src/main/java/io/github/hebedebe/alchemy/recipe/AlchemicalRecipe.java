package io.github.hebedebe.alchemy.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.hebedebe.alchemy.alchemy;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.html.parser.Entity;

public class AlchemicalRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final int recipeStability;
    private final int recipePower;
    private final int recipeChaos;
    private final int recipePotential;
    private final int outputAmount;


    public AlchemicalRecipe(ResourceLocation id, ItemStack output,
                                    NonNullList<Ingredient> recipeItems, int recipeStability, int recipePower, int recipeChaos, int recipePotential, int outputAmount) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.recipeStability = recipeStability;
        this.recipePower = recipePower;
        this.recipeChaos = recipeChaos;
        this.recipePotential = recipePotential;
        this.outputAmount = outputAmount;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }

        boolean isItemPresent = recipeItems.get(0).test(pContainer.getItem(4));

        return isItemPresent;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }


    public int getStability() {
        return recipeStability;
    }

    public int getPower() {
        return recipePower;
    }

    public int getChaos() {
        return recipeChaos;
    }

    public int getPotential() {
        return recipePotential;
    }

    public int getOutputAmount() { return outputAmount; }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<AlchemicalRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "alchemical_processing";
    }


    public static class Serializer implements RecipeSerializer<AlchemicalRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(alchemy.MODID, "alchemical_processing");

        @Override
        public AlchemicalRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

            int stability = GsonHelper.getAsInt(pSerializedRecipe, "stability");
            int power = GsonHelper.getAsInt(pSerializedRecipe, "power");
            int chaos = GsonHelper.getAsInt(pSerializedRecipe, "chaos");
            int potential = GsonHelper.getAsInt(pSerializedRecipe, "potential");
            int outputAmount = GsonHelper.getAsInt(pSerializedRecipe, "outputAmount");

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new AlchemicalRecipe(pRecipeId, output, inputs, stability, power, chaos, potential, outputAmount);
        }

        @Override
        public @Nullable AlchemicalRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            int stability = buf.readInt();
            int power = buf.readInt();
            int chaos = buf.readInt();
            int potential = buf.readInt();
            int outputAmount = buf.readInt();

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack output = buf.readItem();
            return new AlchemicalRecipe(id, output, inputs, stability, power, chaos, potential, outputAmount);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, AlchemicalRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());

            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(), false);
        }
    }
}
