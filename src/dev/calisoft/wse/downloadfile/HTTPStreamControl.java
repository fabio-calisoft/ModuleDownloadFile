package dev.calisoft.wse.downloadfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Set;

import com.wowza.wms.http.HTTProvider2Base;
import com.wowza.wms.http.IHTTPRequest;
import com.wowza.wms.http.IHTTPResponse;
import com.wowza.wms.logging.WMSLogger;
import com.wowza.wms.logging.WMSLoggerFactory;
import com.wowza.wms.vhost.IVHost;

public class HTTPStreamControl extends HTTProvider2Base {

	public static final String CLASSNAME = "fdlx.HTTPStreamControl.";
	private static final Class<HTTPStreamControl> CLASS = HTTPStreamControl.class;
	private WMSLogger logger = WMSLoggerFactory.getLogger(CLASS);
	private String ROOT_PATH = "/u01/vidStorage-NFS/VOD-vault1";

	@Override
	public void init()
	{
		super.init();
		logger.info(CLASSNAME + "init: Build# 1");
	}

	@Override
	public void onHTTPRequest(IVHost vhost, IHTTPRequest req, IHTTPResponse resp)
	{
		logger.info(CLASSNAME + "onHTTPRequest");

		if (!doHTTPAuthentication(vhost, req, resp)) {
			logger.error(CLASSNAME + "onHTTPRequest auth error");
			return;
		}

		StringBuffer ret = new StringBuffer();
		Set<String> paramNames = req.getParameterNames();

		logger.info(CLASSNAME + "paramNames size:" + paramNames.size());
		if (paramNames.contains("asset")) {
			String asset = req.getParameter("asset");
			logger.info(CLASSNAME + "paramNames asset:" + asset);


			String fileName = ROOT_PATH + "/" + asset;
			resp.setHeader("Content-Description", "File Transfer");
			resp.setHeader("Content-Disposition", "attachment; filename=\"" + asset +"\"");
			resp.setHeader("Content-Type", "application/force-download");
			logger.info(CLASSNAME + " OutputStream start for fileName:" + fileName);
			try {
				byte[] buffer = new byte[1024];
				File file = new File(fileName);
				if (file.exists()) {
					FileInputStream fis = new FileInputStream(file);
					OutputStream out = resp.getOutputStream();
					byte[] outBytes = ret.toString().getBytes();
					out.write(outBytes);

					int len = fis.read(buffer);
					while (len != -1) {
//					logger.info(CLASSNAME + " OutputStream write len:" + len);
						out.write(buffer, 0, len);
						out.flush();
						len = fis.read(buffer);
					}
					out.close();
				} else {
					logger.info(CLASSNAME + "file [" + fileName + "] not found");
					resp.setResponseCode(404);
				}

			} catch (Exception e) {
				logger.error(CLASSNAME + "OutputStream exception:" + e);
			} finally {
				logger.info(CLASSNAME + " OutputStream completed");
			}

		} else {
			logger.info(CLASSNAME + "paramNames asset is undefined !!");
			resp.setResponseCode(404);
		}



		/*
		DownloadFile runnable = new DownloadFile("/tmp/WSE.dmg", resp);
		Thread thread = new Thread(runnable);
		thread.start();
		*/

	}



}
