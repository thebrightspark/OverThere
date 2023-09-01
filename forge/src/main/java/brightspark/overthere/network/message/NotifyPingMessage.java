package brightspark.overthere.network.message;

import brightspark.overthere.OTConstants;
import brightspark.overthere.ping.Ping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class NotifyPingMessage implements Message {
	private final Ping ping;

	public NotifyPingMessage(FriendlyByteBuf buf) {
		ping = Ping.decode(buf);
	}

	public NotifyPingMessage(Ping ping) {
		this.ping = ping;
	}

	@Override
	public void encode(FriendlyByteBuf buf) {
		ping.encode(buf);
	}

	@Override
	public void handle(NetworkEvent.Context context) {
		LocalPlayer player = Minecraft.getInstance().player;
		if (player == null) return;

		OTConstants.LOG.info("Handling notify ping message");
		player.sendSystemMessage(ping.getChatComponent(Minecraft.getInstance().level));
	}
}
