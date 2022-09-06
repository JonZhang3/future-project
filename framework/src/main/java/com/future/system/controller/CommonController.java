package com.future.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.future.common.utils.file.FileUtils;
import com.future.framework.config.FrameworkConfig;
import com.future.common.exception.BusinessException;

@RestController
@RequestMapping("/api/common")
public class CommonController {
    


    @GetMapping("/download")
    public void downloadFile(String fileName, Boolean delete, HttpServletRequest request, HttpServletResponse response) {
        try {
            if(!FileUtils.checkAllowDownload(fileName)) {
                throw new BusinessException("");
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = FrameworkConfig.getDownloadPath() + fileName;
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            
        } catch(Exception e) {

        }
    }

}
