package brightspark.overthere;

import brightspark.overthere.network.OTNetworkForge;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(OTConstants.MOD_ID)
public class OverThereForge {
	public OverThereForge() {
		OverThereCommon.init();
		OTNetworkForge.init();
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> OTClientEventHandlerForge::registerListeners);
	}
}
