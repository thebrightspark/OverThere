package brightspark.overthere.network;

import brightspark.overthere.OTConstants;
import brightspark.overthere.network.message.CreatePingMessage;
import brightspark.overthere.network.message.Message;
import brightspark.overthere.network.message.NotifyPingMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class OTNetworkForge {
	private static final String NETWORK_PROTOCOL_VERSION = "1";
	public static final SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(
		new ResourceLocation(OTConstants.MOD_ID, "main"),
		() -> NETWORK_PROTOCOL_VERSION,
		NETWORK_PROTOCOL_VERSION::equals,
		NETWORK_PROTOCOL_VERSION::equals
	);
	private static int nextIndex = 0;

	public static void init() {
		register(CreatePingMessage.class);
		register(NotifyPingMessage.class);
	}

	public static void sendCreatePingToServer() {
		NETWORK.sendToServer(new CreatePingMessage());
	}

	private static <T extends Message> void register(Class<T> messageClass) {
		NETWORK.registerMessage(
			nextIndex++,
			messageClass,
			Message::encode,
			MessageFactory.create(messageClass),
			(message, context) -> {
				message.handle(context.get());
				context.get().setPacketHandled(true);
			}
		);
	}
}
