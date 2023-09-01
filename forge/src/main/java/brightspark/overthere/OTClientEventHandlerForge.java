package brightspark.overthere;

import brightspark.overthere.network.OTNetworkForge;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class OTClientEventHandlerForge {
	public static void registerListeners() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(OTClientEventHandlerForge::registerKeyMappings);

		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addListener(OTClientEventHandlerForge::clientSetup);
		forgeBus.addListener(OTClientEventHandlerForge::onKey);
	}

	public static void clientSetup(FMLClientSetupEvent event) {

	}

	// https://docs.minecraftforge.net/en/1.20.x/misc/keymappings/
	public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
		OTKeyMappings.PING.setKeyConflictContext(KeyConflictContext.IN_GAME);
		event.register(OTKeyMappings.PING);
	}

	public static void onKey(TickEvent.ClientTickEvent event) {
		if (event.phase != TickEvent.Phase.END) return;

		while (OTKeyMappings.PING.consumeClick()) {
			OTConstants.LOG.info("Telling server to create ping");
			OTNetworkForge.sendCreatePingToServer();
		}
	}
}
