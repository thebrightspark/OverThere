package brightspark.overthere.mixin;

import brightspark.overthere.OTConstants;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {

	@Inject(at = @At("TAIL"), method = "<init>")
	private void init(CallbackInfo info) {
		OTConstants.LOG.info("Look, Over There!");
	}
}
