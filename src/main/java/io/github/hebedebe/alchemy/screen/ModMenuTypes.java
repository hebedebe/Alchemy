package io.github.hebedebe.alchemy.screen;

import io.github.hebedebe.alchemy.alchemy;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, alchemy.MODID);

    public static final RegistryObject<MenuType<CrudeAlchemicalWorkbenchMenu>> CRUDE_ALCHEMICAL_WORKBENCH_MENU =
            registerMenuType(CrudeAlchemicalWorkbenchMenu::new, "crude_alchemical_workbench_menu");
    public static final RegistryObject<MenuType<EvaporationTableMenu>> EVAPORATION_TABLE_MENU =
            registerMenuType(EvaporationTableMenu::new, "evaporation_table_menu");

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
