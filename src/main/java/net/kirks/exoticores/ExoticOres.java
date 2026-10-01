package net.kirks.exoticores;

import com.mojang.logging.LogUtils;
import net.kirks.exoticores.datagen.ModSoundProvider;
import net.kirks.exoticores.effect.ClearExceptProtectedConsumeEffect;
import net.kirks.exoticores.effect.ModEffectEvents;
import net.kirks.exoticores.effect.RadiationExposure;
import net.kirks.exoticores.network.GeigerTickClient;
import net.kirks.exoticores.network.ModNetworkEvents;
import net.kirks.exoticores.registry.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ClearAllStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;
import net.kirks.exoticores.client.screen.CatalyzerTableScreen;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import java.util.List;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ExoticOres.MODID)
public class ExoticOres {
    public static final String MODID = "exoticores";
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public ExoticOres(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::modifyDefaults);
        modEventBus.addListener(ModNetworkEvents::registerPayloads);
        modEventBus.addListener(ModNetworkEvents.Client::registerClientHandlers);

        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModEffects.MOB_EFFECTS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        ModMenuTypes.MENU_TYPES.register(modEventBus);
        ModDataAttachments.ATTACHMENTS.register(modEventBus);
        ModConsumeEffects.CONSUME_EFFECTS.register(modEventBus);
        ModSounds.SOUND_EVENTS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExoticOres) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(ModEffectEvents.class);
        NeoForge.EVENT_BUS.register(GeigerTickClient.class);
        NeoForge.EVENT_BUS.register(RadiationExposure.class);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        //modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("Starting common setup");

//        if (Config.logDirtBlock) LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
//
//        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);
//
//        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("Starting server setup");
    }

    public void modifyDefaults(ModifyDefaultComponentsEvent event) {
        event.modify(Items.MILK_BUCKET, ((components, context, item) -> {
            Consumable vanilla = Consumables.MILK_BUCKET;

            var effects = vanilla.onConsumeEffects().stream()
                    .map(e -> e instanceof ClearAllStatusEffectsConsumeEffect ? new ClearExceptProtectedConsumeEffect() : e)
                    .toList();

            components.set(DataComponents.CONSUMABLE, new Consumable(
                    vanilla.consumeSeconds(), vanilla.animation(), vanilla.sound(), vanilla.hasConsumeParticles(), effects
            ));
        }));
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("Starting client setup");
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenuTypes.CATALYZER_TABLE_MENU.get(), CatalyzerTableScreen::new);
        }
    }
}
