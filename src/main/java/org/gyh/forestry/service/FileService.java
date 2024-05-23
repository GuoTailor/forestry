package org.gyh.forestry.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * create by GYH on 2024/3/24
 */
@Slf4j
@Service
public class FileService {
    @Value("${fileUploadPath}")
    private String fileUploadPath;

    public String uploadFile(MultipartFile file) {
        if (file != null) {
            String fileName = file.getOriginalFilename();
            String filePath = fileUploadPath + fileName;
            try {
                file.transferTo(new File(filePath));
            } catch (IOException e) {
                log.error("上传文件失败", e);
            }
            return filePath;
        }
        return fileUploadPath;
    }
}
