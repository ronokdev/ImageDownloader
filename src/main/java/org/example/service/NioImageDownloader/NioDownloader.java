package org.example.service.NioImageDownloader;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class NioDownloader implements ImageDownload {

    @Override
    public void downloadImage(String urlStr, String outputDirectory) {
            try{
                URL url = new URL(urlStr);
                ReadableByteChannel rbc = Channels.newChannel(url.openStream());
                FileOutputStream fos = new FileOutputStream(outputDirectory);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                fos.close();
                rbc.close();
            }
            catch (Exception e){
                System.out.println("Error occurred while downloading "+ e);
            }
    }
}
