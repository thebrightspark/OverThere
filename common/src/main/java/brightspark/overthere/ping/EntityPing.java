package brightspark.overthere.ping;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public non-sealed class EntityPing extends Ping {
	public static final String TYPE = "entity";
	private final int entityId;

	public EntityPing(FriendlyByteBuf buf) {
		super(buf);
		entityId = buf.readInt();
	}

	public EntityPing(Player player, net.minecraft.world.entity.Entity entity) {
		super(player, entity.level(), entity.position());
		entityId = entity.getId();
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public Component getChatComponent(Level level) {
		Player playerSource = level.getPlayerByUUID(getPlayerUuid());
		Entity entity = level.getEntity(entityId);
		Vec3 pos = getPosition();
		return Component.empty()
			.append(playerSource == null ? COMPONENT_UNKNOWN : playerSource.getDisplayName())
			.append(" has pinged entity ")
			.append(entity == null ? COMPONENT_UNKNOWN : entity.getDisplayName())
			.append(" at " + pos.x() + ", " + pos.y() + ", " + pos.z());
	}

	@Override
	public void encode(FriendlyByteBuf buf) {
		super.encode(buf);
		buf.writeInt(entityId);
	}

	public int getEntityId() {
		return entityId;
	}
}
