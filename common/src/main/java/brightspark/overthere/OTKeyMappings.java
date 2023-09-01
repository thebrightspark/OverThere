package brightspark.overthere;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class OTKeyMappings {
	private static final String CATEGORY = "key.categories.overthere";

	public static KeyMapping PING = new KeyMapping(
		"key.overthere.ping",
		InputConstants.Type.MOUSE,
		GLFW.GLFW_MOUSE_BUTTON_MIDDLE,
		CATEGORY
	);
}
