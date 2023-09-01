package brightspark.overthere.ping;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public final class LocationPing extends Ping {
	public static final String TYPE = "location";

	public LocationPing(FriendlyByteBuf buf) {
		super(buf);
	}

	public LocationPing(Player player, Level level, Vec3 position) {
		super(player, level, position);
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public Component getChatComponent(Level level) {
		Player playerSource = level.getPlayerByUUID(getPlayerUuid());
		Vec3 pos = getPosition();
		return Component.empty()
			.append(playerSource == null ? COMPONENT_UNKNOWN : playerSource.getDisplayName())
			.append(" has pinged location " + pos.x() + ", " + pos.y() + ", " + pos.z());
	}
}
