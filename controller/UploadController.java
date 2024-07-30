package com.lnn.controller;

import com.lnn.domin.ResponseResult;
import com.lnn.enums.AppHttpCodeEnum;
import com.lnn.service.UploadService;

import com.lnn.utils.AliOSSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
public class UploadController {
    @Autowired
    private UploadService uploadService;
    @Autowired
    private AliOSSUtils aliOSSUtils;

    @PostMapping("/upload")
    public ResponseResult uploadImg(MultipartFile img) throws IOException {
        try {
            //原始文件名
            String originalFilename = img.getOriginalFilename();
            //截取原始文件名后缀
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            //构建新的文件名称
            String objectName = UUID.randomUUID().toString() + extension;

            //文件请求路径
            String filePath = aliOSSUtils.upload(img.getBytes(),objectName);
            return ResponseResult.okResult(filePath);
        } catch (IOException e) {
            log.error("文件上传失败：{}",e);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.UPLOAD_FAILED);
    }

}