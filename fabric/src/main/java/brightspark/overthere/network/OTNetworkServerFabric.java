package brightspark.overthere.network;

import brightspark.overthere.OTConstants;
import brightspark.overthere.network.message.FabricCreatePingMessage;
import brightspark.overthere.network.message.FabricNotifyPingMessage;
import brightspark.overthere.ping.Ping;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class OTNetworkServerFabric {
	public static void init() {
		ServerPlayNetworking.registerGlobalReceiver(
			FabricCreatePingMessage.TYPE,
			OTNetworkServerFabric::handleCreatePingMessage
		);
	}

	private static void handleCreatePingMessage(
		FabricCreatePingMessage message,
		ServerPlayer player,
		PacketSender responseSender
	) {
		OTConstants.LOG.info("Handling create ping message");
		Ping ping = Ping.create(player);
		if (ping == null) return;

		FabricNotifyPingMessage notifyMessage = new FabricNotifyPingMessage(ping);
		Vec3 pingPos = ping.getPosition();
		player.serverLevel().players().stream()
			.filter(p -> p.distanceToSqr(pingPos) <= OTConstants.NOTIF_RANGE_SQR)
			.forEach(p -> {
				OTConstants.LOG.info("Sending notify ping message to " + p.getName().getString());
				ServerPlayNetworking.send(p, notifyMessage);
			});
	}
}
