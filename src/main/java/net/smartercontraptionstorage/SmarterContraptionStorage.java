package net.smartercontraptionstorage;

import com.jaquadro.minecraft.storagedrawers.core.ModBlocks;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import net.minecraftforge.fml.ModList;
import net.smartercontraptionstorage.AddStorage.StorageHandlerHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.smartercontraptionstorage.Ponder.SCS_Ponder;

import static net.smartercontraptionstorage.Ponder.SCS_Ponder.CONTROLLABLE_CONTAINERS;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SmarterContraptionStorage.MODID)
public class SmarterContraptionStorage {

    // Define mod id in Excludes.a common place for everything to reference
    public static final String MODID = "smartercontraptionstorage";
    // Directly reference Excludes.a slf4j logger
    public SmarterContraptionStorage() {
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SmarterContraptionStorageConfig.SPEC,"Smarter_Contraption_Storage.toml");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        ModList list = ModList.get();
        if(list.isLoaded("trashcans"))
            SCS_Ponder.registerTrashCan();
        if(list.isLoaded("create")){
            StorageHandlerHelper.register();
            SCS_Ponder.register();
            if(list.isLoaded("storagedrawers"))
                PonderRegistry.TAGS.forTag(CONTROLLABLE_CONTAINERS).add(ModBlocks.CONTROLLER.get());
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
        }
    }
}