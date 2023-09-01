package brightspark.overthere.network.message;

import brightspark.overthere.OTConstants;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class FabricCreatePingMessage implements FabricPacket {
	public static final PacketType<FabricCreatePingMessage> TYPE = PacketType.create(
		new ResourceLocation(OTConstants.MOD_ID, "create_ping"),
		buf -> new FabricCreatePingMessage()
	);

	@Override
	public void write(FriendlyByteBuf buf) {}

	@Override
	public PacketType<?> getType() {
		return TYPE;
	}
}
