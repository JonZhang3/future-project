package com.future.framework.common.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

/**
 * io 工具类
 *
 * @author JonZhang
 */
public final class IoUtils extends IOUtils {

    private IoUtils() {
    }

    public static String readToString(Reader reader) throws IOException {
        final StringBuilder builder = new StringBuilder();
        final CharBuffer buffer = CharBuffer.allocate(8192);
        try {
            while (reader.read(buffer) != -1) {
                builder.append(buffer.flip());
            }
        } finally {
            closeQuietly(reader);
        }
        return builder.toString();
    }

}
