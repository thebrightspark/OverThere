package brightspark.overthere.network.message;

import brightspark.overthere.OTConstants;
import brightspark.overthere.ping.Ping;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class FabricNotifyPingMessage implements FabricPacket {
	public static final PacketType<FabricNotifyPingMessage> TYPE = PacketType.create(
		new ResourceLocation(OTConstants.MOD_ID, "notify_ping"),
		FabricNotifyPingMessage::new
	);

	private final Ping ping;

	public FabricNotifyPingMessage(FriendlyByteBuf buf) {
		ping = Ping.decode(buf);
	}

	public FabricNotifyPingMessage(Ping ping) {
		this.ping = ping;
	}

	@Override
	public void write(FriendlyByteBuf buf) {
		ping.encode(buf);
	}

	@Override
	public PacketType<?> getType() {
		return TYPE;
	}

	public Ping getPing() {
		return ping;
	}
}
