package dev.calisoft.wse.downloadfile;

import com.wowza.wms.application.*;
import com.wowza.wms.logging.WMSLogger;
import com.wowza.wms.logging.WMSLoggerFactory;
import com.wowza.wms.module.*;
import com.wowza.wms.stream.publish.Playlist;
import com.wowza.wms.stream.publish.Stream;

public class ModuleDownloadFile extends ModuleBase {

	public static final String CLASSNAME = "fdl.ModuleDownloadFile";
	private static final Class<HTTPStreamControl> CLASS = HTTPStreamControl.class;
	private WMSLogger logger = WMSLoggerFactory.getLogger(CLASS);

	public void onAppStart(IApplicationInstance appInstance) {
		logger.info(CLASSNAME + ".onAppStart");
	}

	public void onAppStop(IApplicationInstance appInstance) {
		logger.info(CLASSNAME + ".onAppStop");
	}

}
