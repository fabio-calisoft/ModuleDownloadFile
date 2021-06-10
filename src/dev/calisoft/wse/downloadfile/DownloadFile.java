package dev.calisoft.wse.downloadfile;

import com.wowza.wms.http.IHTTPResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

public class DownloadFile implements Runnable {

    String filename;
    IHTTPResponse resp;
    // run() method contains the code that is executed by the thread.

    DownloadFile(String filename, IHTTPResponse response) {
        this.filename = filename;
        this.resp = response;
    }

    @Override
    public void run() {
        if (filename==null) {
            log("DownloadFile file not defined");
            return;
        }

        log("DownloadFile run filename: " + filename);

        try {
            byte[] buffer = new byte[1024];
            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);
            OutputStream out = resp.getOutputStream();

            int len = fis.read(buffer);
            while (len != -1) {
                out.write(buffer, 0, len);
                len = fis.read(buffer);
            }
            out.flush();
            out.close();
        } catch (Exception e) {
            log("OutputStream exception:" + e);
        } finally {
            log("OutputStream completed");
        }


    }

    private void log(String msg) {
        System.out.println(msg);
    }



}
