package com.nayki.analyzer.utils;

import javax.inject.Singleton;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

@Singleton
public class FileUtils {

    public void downloadFromURL(String urlAddress) throws IOException {
        URL url = new URL(urlAddress);
        ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
        FileOutputStream fileOutputStream = new FileOutputStream("3dprinting-posts.xml");
        FileChannel fileChannel = fileOutputStream.getChannel();
        fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
    }
}
