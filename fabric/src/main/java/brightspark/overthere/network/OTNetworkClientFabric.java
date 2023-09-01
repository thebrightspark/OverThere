package brightspark.overthere.network;

import brightspark.overthere.OTConstants;
import brightspark.overthere.network.message.FabricCreatePingMessage;
import brightspark.overthere.network.message.FabricNotifyPingMessage;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

public class OTNetworkClientFabric {
	public static void init() {
		ClientPlayNetworking.registerGlobalReceiver(
			FabricNotifyPingMessage.TYPE,
			OTNetworkClientFabric::handleNotifyPingMessage
		);
	}

	private static void handleNotifyPingMessage(
		FabricNotifyPingMessage message,
		LocalPlayer player,
		PacketSender responseSender
	) {
		OTConstants.LOG.info("Handling notify ping message");
		player.sendSystemMessage(message.getPing().getChatComponent(Minecraft.getInstance().level));
	}

	public static void sendCreatePingToServer() {
		ClientPlayNetworking.send(new FabricCreatePingMessage());
	}
}
