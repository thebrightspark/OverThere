package brightspark.overthere;

import brightspark.overthere.network.OTNetworkClientFabric;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.Minecraft;

public class OverThereFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		OTNetworkClientFabric.init();

		KeyBindingHelper.registerKeyBinding(OTKeyMappings.PING);
		ClientTickEvents.END_CLIENT_TICK.register(this::onKey);
	}

	private void onKey(Minecraft mc) {
		while (OTKeyMappings.PING.consumeClick()) {
			OTConstants.LOG.info("Telling server to create ping");
			OTNetworkClientFabric.sendCreatePingToServer();
		}
	}
}
