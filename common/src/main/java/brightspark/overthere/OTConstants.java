package brightspark.overthere;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OTConstants {
	public static final String MOD_ID = "overthere";
	public static final String MOD_NAME = "Over There";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	// TODO: Make configs
	public static final double PING_RANGE = 100D;
	public static final double NOTIF_RANGE = 50D;
	public static final double NOTIF_RANGE_SQR = Math.pow(50D, 3);
}
