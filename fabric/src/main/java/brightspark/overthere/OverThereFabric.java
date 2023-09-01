package brightspark.overthere;

import brightspark.overthere.network.OTNetworkServerFabric;
import net.fabricmc.api.ModInitializer;

public class OverThereFabric implements ModInitializer {
    @Override
    public void onInitialize() {
		OverThereCommon.init();
		OTNetworkServerFabric.init();
    }
}
