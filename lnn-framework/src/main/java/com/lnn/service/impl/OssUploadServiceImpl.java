package com.lnn.service.impl;

import com.lnn.domin.ResponseResult;
import com.lnn.service.UploadService;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Data
@ConfigurationProperties(prefix = "oss")
public class OssUploadServiceImpl implements UploadService {


    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        return null;
    }
}
