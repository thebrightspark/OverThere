package brightspark.overthere.network.message;

import brightspark.overthere.OTConstants;
import brightspark.overthere.network.OTNetworkForge;
import brightspark.overthere.ping.Ping;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

public class CreatePingMessage implements Message {
	@Override
	public void handle(NetworkEvent.Context context) {
		ServerPlayer player = context.getSender();
		if (player == null) return;

		OTConstants.LOG.info("Handling create ping message");
		Ping ping = Ping.create(player);
		if (ping == null) return;

		Vec3 pos = player.position();
		PacketDistributor.TargetPoint point = new PacketDistributor.TargetPoint(
			null, pos.x(), pos.y(), pos.z(), OTConstants.NOTIF_RANGE, ping.getDimension()
		);
		Message message = new NotifyPingMessage(ping);
		OTNetworkForge.NETWORK.send(PacketDistributor.NEAR.with(() -> point), message);
	}
}
