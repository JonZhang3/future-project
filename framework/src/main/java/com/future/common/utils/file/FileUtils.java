package com.future.common.utils.file;

import java.io.File;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.future.common.utils.StringUtils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FileUtils {
    
    /**
     * 检查文件是否可下载
     * 
     * @param resource 要下载的资源
     * @return true 可以下载
     */
    public static boolean checkAllowDownload(String resource) {
        // 禁止目录上跳级别
        if(StringUtils.contains(resource, "..")) {
            return false;
        }
        // 检查允许下载的文件规则
        if(ArrayUtils.contains(MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION, getFileExtension(resource))) {
            return true;
        }
        // 不在允许下载的文件规则
        return false;
    }

    public static String getFileExtension(File file) {
        if(file == null) {
            return "";
        }
        return FilenameUtils.getExtension(file.getName());
    }

    public static String getFileExtension(String fileName) {
        return FilenameUtils.getExtension(fileName);
    }

    public static void setAttachmentResponseHeader(HttpServletResponse response, String realFileName) {

    }

}
