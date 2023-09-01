package brightspark.overthere.network.message;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

/**
 * Requires a constructor with the single parameter {@link FriendlyByteBuf} or a default constructor.
 */
public interface Message {
	/**
	 * Encodes this message to the given {@link FriendlyByteBuf}.
	 */
	default void encode(FriendlyByteBuf buf) {}

	/**
	 * Handles this message after decoding it on the receiving side.
	 */
	void handle(NetworkEvent.Context context);
}
