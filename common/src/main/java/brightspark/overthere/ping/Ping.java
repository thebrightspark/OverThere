package brightspark.overthere.ping;

import brightspark.overthere.OTConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public sealed abstract class Ping permits EntityPing, LocationPing {
	protected static final Component COMPONENT_UNKNOWN = Component.literal("Unknown").withStyle(ChatFormatting.ITALIC);

	protected final UUID playerUuid;
	protected final ResourceKey<Level> dimension;
	protected final Vec3 position;
	protected final long levelTimeCreated;

	protected Ping(FriendlyByteBuf buf) {
		playerUuid = buf.readUUID();
		dimension = buf.readResourceKey(Registries.DIMENSION);
		position = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
		levelTimeCreated = buf.readLong();
	}

	protected Ping(Player playerUuid, Level level, Vec3 position) {
		this.playerUuid = playerUuid.getUUID();
		dimension = level.dimension();
		this.position = position;
		levelTimeCreated = level.getGameTime();
	}

	public static Ping create(Player player) {
		if (player == null) return null;

		HitResult hitResult = ProjectileUtil.getHitResultOnViewVector(
			player,
			entity -> !entity.isInvisible() && !entity.isSpectator(),
			OTConstants.PING_RANGE
		);

		return switch (hitResult.getType()) {
			case ENTITY -> new EntityPing(player, ((EntityHitResult) hitResult).getEntity());
			case BLOCK -> new LocationPing(player, player.level(), hitResult.getLocation());
			case MISS -> null;
		};
	}

	public static Ping decode(FriendlyByteBuf buf) {
		String pingType = buf.readUtf();
		return switch (pingType) {
			case EntityPing.TYPE -> new EntityPing(buf);
			case LocationPing.TYPE -> new LocationPing(buf);
			default -> null;
		};
	}

	public abstract String getType();

	public abstract Component getChatComponent(Level level);

	public UUID getPlayerUuid() {
		return playerUuid;
	}

	public ResourceKey<Level> getDimension() {
		return dimension;
	}

	public Vec3 getPosition() {
		return position;
	}

	public long getLevelTimeCreated() {
		return levelTimeCreated;
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeUtf(getType());
		buf.writeUUID(playerUuid);
		buf.writeResourceKey(dimension);
		buf.writeDouble(position.x());
		buf.writeDouble(position.y());
		buf.writeDouble(position.z());
		buf.writeLong(levelTimeCreated);
	}
}
