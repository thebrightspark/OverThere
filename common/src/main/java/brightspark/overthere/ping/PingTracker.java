package brightspark.overthere.ping;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PingTracker {
	private static final Map<ResourceKey<Level>, Set<Ping>> PINGS = new HashMap<>();

	public static void add(Ping ping) {
		PINGS.computeIfAbsent(ping.getDimension(), k -> new HashSet<>()).add(ping);
	}

	public static Set<Ping> getForLevel(Level level) {
		return PINGS.get(level.dimension());
	}
}
