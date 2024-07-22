package org.gyh.forestry.service;

import lombok.extern.slf4j.Slf4j;
import org.gyh.forestry.domain.User;
import org.gyh.forestry.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * create by GYH on 2024/3/24
 */
@Slf4j
@Service
public class FileService {
    @Value("${fileUploadPath}")
    private String fileUploadPath;
    private final String fileAiPath = "upload";

    public String uploadFile(MultipartFile file) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = user.getId();
        return uploadFile(userId, file);
    }

    public String uploadRecognitionFile(MultipartFile file) {
        if (file != null) {
            String originalFilename = file.getOriginalFilename();
            var suffix = "";
            if (StringUtils.hasText(originalFilename)) {
                var split = originalFilename.split("\\.");
                if (split.length > 0) {
                    suffix = "." + split[split.length - 1];
                }
            }
            var fileName = UUID.randomUUID() + suffix;
            String pathname = fileUploadPath + File.separator + fileName;
            var dest = new File(pathname);

            try {
                if (!dest.getParentFile().exists()) {
                    boolean mkdirs = dest.getParentFile().mkdirs();
                    if (!mkdirs) throw new BusinessException("创建目录失败");
                }
                file.transferTo(dest.getAbsoluteFile());
            } catch (IOException e) {
                log.error("上传文件失败", e);
                throw new BusinessException(e);
            }
            return fileName;
        }
        throw new BusinessException("上传文件不能为空");
    }

    public String getPath(String fileName) {
        return fileAiPath + File.separator + fileName;
    }

    public String uploadFile(Integer userId, MultipartFile file) {
        if (file != null) {
            String originalFilename = file.getOriginalFilename();
            var suffix = "";
            if (StringUtils.hasText(originalFilename)) {
                var split = originalFilename.split("\\.");
                if (split.length > 0) {
                    suffix = "." + split[split.length - 1];
                }
            }
            var fileName = userId + File.separator + UUID.randomUUID() + suffix;
            String pathname = fileUploadPath + File.separator + fileName;
            var dest = new File(pathname);

            try {
                if (!dest.getParentFile().exists()) {
                    boolean mkdirs = dest.getParentFile().mkdirs();
                    if (!mkdirs) throw new BusinessException("创建目录失败");
                }
                file.transferTo(dest.getAbsoluteFile());
            } catch (IOException e) {
                log.error("上传文件失败", e);
                throw new BusinessException(e);
            }
            return fileName;
        }
        throw new BusinessException("上传文件不能为空");
    }
}
